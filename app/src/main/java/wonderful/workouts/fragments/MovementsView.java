package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithWorkoutMovementHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentMovementsBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

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

        categoryDropDown = root.findViewById(R.id.movementCategory);

        equipmentDropDown = root.findViewById(R.id.movementEquipment);
        // Select list ListView
        movementListView = (ListView) root.findViewById(R.id.movementList);

        updateMovementView();

        // Select category spinner and set adapter
        //Spinner categoryDropDown = root.findViewById(R.id.movementCategory);
        //ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //categoryDropDown.setAdapter(categoryAdapter);
        // Set category spinner on item selected listener
        categoryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Returns category on click
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                Log.i("MovementsView", "Selected category: " + selectedItemText);
            }
            // Returns all when nothing selected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("MovementsView", "Showing all categories");
            }
        });

        // Select equipment spinner and set adapter
        //Spinner equipmentDropDown = root.findViewById(R.id.movementEquipment);
        //ArrayAdapter<CharSequence> equipmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipment, android.R.layout.simple_spinner_item);
        //equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //equipmentDropDown.setAdapter(equipmentAdapter);

        // Set equipment spinner on item selected listener
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
                Log.i("MovementsView", "Showing all equipment");
            }
        });


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
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userCategories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Get list of equipment for user
            List<String> userEquipment = movementPresenter.getEquipmentList(userPresenter.getCurrentUser());
            ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userEquipment);
            equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Get movements for user
            List<Movement> userMovements = movementPresenter.getUserMovements(userPresenter.getCurrentUser());


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {

                categoryDropDown.setAdapter(categoryAdapter);

                equipmentDropDown.setAdapter(equipmentAdapter);

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
}