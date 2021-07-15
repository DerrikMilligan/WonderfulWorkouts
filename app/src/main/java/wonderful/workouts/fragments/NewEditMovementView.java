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
    public String[] equipmentList = new String[] {"None", "Single Dumbbell", "Dumbbells", "Barbell", "Band"};
    public String type;
    // public String selectedId;

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

        updateCategoryList();
        updateEquipmentList();

        RadioGroup typeGroup = root.findViewById(R.id.typeGroup);
        final String[] selectedId = new String[1];
        typeGroup.setOnCheckedChangeListener((typeGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.timedRadio:
                    selectedId[0] = "Timed";
                    break;
                case R.id.repsRadio:
                    selectedId[0] = "Reps";
                    break;
                case R.id.weightRepsRadio:
                    selectedId[0] = "RepsAndWeight";
                    break;
            }
        });

        Button addMovementBtn = root.findViewById(R.id.addMovementBtn);
        addMovementBtn.setOnClickListener(view -> {
            new Thread(() -> {
                UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
                MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

                Log.i("NewEditMovementView", String.format("Create movement - Name: %s , Category: %s, Equipment: %s, Type: %s", nameInput.getText(), categoryDropDown.getText(), equipmentDropDown.getText(), selectedId[0]));
                String movementName = nameInput.getText().toString();
                String movementType = selectedId[0];
                String movementCategory = categoryDropDown.getText().toString();
                String movementEquipment = equipmentDropDown.getText().toString();
                movementPresenter.lookupOrCreateMovement(movementName, movementType, movementCategory, movementEquipment);
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
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categoryList);

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

}