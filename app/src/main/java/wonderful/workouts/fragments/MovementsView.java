package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.databinding.FragmentMovementsBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.UserPresenter;

public class MovementsView extends Fragment {
    private FragmentMovementsBinding binding;
    public Spinner categoryDropDown;
    public Spinner equipmentDropDown;
    public ListView movementListView;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMovementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Select category Spinner
        categoryDropDown = root.findViewById(R.id.movementCategory);
        // Select equipment Spinner
        equipmentDropDown = root.findViewById(R.id.movementEquipment);
        // Select movement ListView
        movementListView = (ListView) root.findViewById(R.id.movementList);

        updateCategoryList();
        updateEquipmentList();
        updateMovementView();


        FloatingActionButton addMovementButton = root.findViewById(R.id.addMovementButton);
        addMovementButton.setOnClickListener(view -> {
            Log.i("MovementView", "Adding movement!");
            Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateMovementView() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            // Get list of categories for user
            List<String> userCategories = movementPresenter.getCategoryList(userPresenter.getCurrentUser());
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userCategories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Get list of equipment for user
            List<String> userEquipment = movementPresenter.getEquipmentList(userPresenter.getCurrentUser());
            ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userEquipment);
            equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Get movements for user
            List<Movement> userMovements = movementPresenter.getUserMovements(userPresenter.getCurrentUser());


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {

                // Set the ListView's adapter to our custom adapter!
                movementListView.setAdapter(new MovementAdapter(this.getContext(), userMovements));

                // Add an onClick listener just for an example!
                movementListView.setOnItemClickListener((parent, view, position, id) -> {
                    Movement clickedMovement = (Movement) movementListView.getItemAtPosition(position);

                    // Store the workout in the state
                    movementPresenter.setCurrentMovement(clickedMovement);

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);

                    Log.i("MovementView", String.format("We clicked movement id: %d name: %s", clickedMovement.movementId, clickedMovement.name));
                });
            });
        }).start();
    }

    public void updateCategoryList() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            // Get list of categories for user
            List<String> userCategories = movementPresenter.getCategoryList(userPresenter.getCurrentUser());
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userCategories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {

                categoryDropDown.setAdapter(categoryAdapter);

                categoryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // Returns category on click
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);
                        Log.i("MovementsView", "Selected category: " + selectedItemText);
                    }
                    // Returns all when nothing selected
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        }).start();
    }

    public void updateEquipmentList() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            // Get list of equipment for user
            List<String> userEquipment = movementPresenter.getEquipmentList(userPresenter.getCurrentUser());
            ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userEquipment);
            equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {

                equipmentDropDown.setAdapter(equipmentAdapter);

                equipmentDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // Returns equipment on click
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);
                        Log.i("MovementsView", "Selected equipment: " + selectedItemText);
                    }

                    // Returns all when nothing selected
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            });
        }).start();
    }
}