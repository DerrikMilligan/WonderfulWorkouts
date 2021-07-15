package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.databinding.FragmentNewEditMovementBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.UserPresenter;

public class NewEditMovementView extends Fragment {
    private wonderful.workouts.databinding.FragmentNewEditMovementBinding binding;

    EditText nameInput;
    AutoCompleteTextView categoryDropDown;
    AutoCompleteTextView equipmentDropDown;
    RadioGroup typeGroup;
    public String[] equipmentList = new String[] {"None", "Single Dumbbell", "Dumbbells", "Barbell", "Band"};
    public String type;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {

        binding = wonderful.workouts.databinding.FragmentNewEditMovementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameInput = root.findViewById(R.id.nameInput);
        categoryDropDown = root.findViewById(R.id.categoryList);
        equipmentDropDown = root.findViewById(R.id.equipmentList);
        typeGroup = root.findViewById(R.id.typeGroup);

        updateCategoryList();
        updateEquipmentList();
        loadSelectedWorkout();

        final String[] selectedId = new String[1];
        typeGroup.setOnCheckedChangeListener((typeGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.timedRadio:
                    selectedId[0] = Movement.Timed;
                    break;
                case R.id.repsRadio:
                    selectedId[0] = Movement.Reps;
                    break;
                case R.id.weightRepsRadio:
                    selectedId[0] = Movement.RepsAndWeight;
                    break;
            }
        });

        Button addMovementBtn = root.findViewById(R.id.addMovementBtn);
        addMovementBtn.setOnClickListener(view -> {
            new Thread(() -> {
                MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());
                Movement movement = movementPresenter.getCurrentMovement();

                movement.name      = nameInput.getText().toString();
                movement.type      = selectedId[0];
                movement.category  = categoryDropDown.getText().toString();
                movement.equipment = equipmentDropDown.getText().toString();

                Log.i("NewEditMovementView", String.format("Updating movement Name: %s, type: %s, category: %s, equipment:%s", movement.name, movement.type, movement.category, movement.equipment));

                movementPresenter.updateMovement(movement);

                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(root).popBackStack();
                });

            }).start();
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateCategoryList() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            // Get list of categories for user
            List<String> categoryList = movementPresenter.getCategoryList(userPresenter.getCurrentUser());
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                categoryDropDown.setAdapter(categoryAdapter);
            });
        }).start();
    }

    public void updateEquipmentList() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            // Get list of equipment for user
            List<String> equipmentList = movementPresenter.getEquipmentList(userPresenter.getCurrentUser());
            ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, equipmentList);

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                equipmentDropDown.setAdapter(equipmentAdapter);
            });
        }).start();
    }

    public void loadSelectedWorkout() {
        new Thread(() -> {
            // Get the presenter.
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());
            Movement movement = movementPresenter.getCurrentMovement();

            // Load all the current movement information into the UI
            requireActivity().runOnUiThread(() -> {
                nameInput.setText(movement.name);
                categoryDropDown.setText(movement.category);
                equipmentDropDown.setText(movement.equipment);

                switch (movement.type) {
                    case Movement.Reps:
                        typeGroup.check(R.id.repsRadio);
                        break;

                    case Movement.RepsAndWeight:
                        typeGroup.check(R.id.weightRepsRadio);
                        break;
                    case Movement.Timed:
                        typeGroup.check(R.id.timedRadio);
                        break;
                }
            });
        }).start();
    }

}