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
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.adapters.WorkoutHistoryAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.databinding.FragmentHistoryBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class HistoryView extends Fragment {

    private FragmentHistoryBinding binding;
    public ListView workoutListView;
    Spinner categoryDropDown;
    Spinner equipmentDropDown;
    public ListView movementListView;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    )
    {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Select workout ListView
        workoutListView = (ListView) root.findViewById(R.id.workoutHistList);
        // Select category spinner
        categoryDropDown = root.findViewById(R.id.categoryDropDown);
        // Select equipment spinner
        equipmentDropDown = root.findViewById(R.id.equipmentDropDown);
        // Select workout ListView
        movementListView = (ListView) root.findViewById(R.id.movementHistList);


        updateWorkoutView();
        updateCategoryList();
        updateEquipmentList();
        updateMovementView();



        // ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        // categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // categoryDropDown.setAdapter(categoryAdapter);
        // // Set category spinner on item selected listener
        // categoryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //     // Returns category on click
        //     @Override
        //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //         String selectedItemText = (String) parent.getItemAtPosition(position);
        //         Log.i("HistoryView", "Selected category: " + selectedItemText);
        //     }
        //     // Returns all when nothing selected
        //     @Override
        //     public void onNothingSelected(AdapterView<?> parent) {
        //         Log.i("HistoryView", "Showing all categories");
        //     }
        // });


        // ArrayAdapter<CharSequence> equipmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipment, android.R.layout.simple_spinner_item);
        // equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // equipmentDropDown.setAdapter(equipmentAdapter);
        // // Set equipment spinner on item selected listener
        // equipmentDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //     // Returns equipment on click
        //     @Override
        //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //         String selectedItemText = (String) parent.getItemAtPosition(position);
        //         Log.i("HistoryView", "Selected equipment: " + selectedItemText);
        //     }
        //     // Returns all when nothing selected
        //     @Override
        //     public void onNothingSelected(AdapterView<?> parent) {
        //         Log.i("HistoryView", "Showing all equipment");
        //
        //     }
        // });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void updateWorkoutView() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            List<WorkoutHistory> workouts = workoutPresenter.getUserWorkoutHistory(userPresenter.getCurrentUser());

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                workoutListView.setAdapter(new WorkoutHistoryAdapter(this.getContext(), workouts));

                // Add an onClick listener just for an example!
                workoutListView.setOnItemClickListener((parent, view, position, id) -> {
                    WorkoutHistory clickedWorkout = (WorkoutHistory) workoutListView.getItemAtPosition(position);

                    // Store the workout in the state
                    //workoutPresenter.setCurrentWorkout(clickedWorkout);

                    // new Thread(() -> {
                    //     WorkoutHistory activeWorkout = workoutPresenter.startWorkout(clickedWorkout);
                    //     workoutPresenter.setActiveWorkout(activeWorkout);
                    //     requireActivity().runOnUiThread(() -> {
                    //         Navigation.findNavController(root).navigate(R.id.navigation_workout_active);
                    //     });
                    // }).start();

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(view).navigate(R.id.navigation_workout_history);

                    Log.i("HistoryView", String.format("We clicked workout id: %d", clickedWorkout.workoutId));
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
                        Log.i("HistoryView", "Selected category: " + selectedItemText);
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


    public void updateMovementView() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

            List<Movement> movements = movementPresenter.getUserMovements(userPresenter.getCurrentUser());

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                movementListView.setAdapter(new MovementAdapter(this.getContext(), movements));


                // Add an onClick listener just for an example!
                movementListView.setOnItemClickListener((parent, view, position, id) -> {
                    Movement clickedMovement = (Movement) movementListView.getItemAtPosition(position);

                    // Store the workout in the state
                    movementPresenter.setCurrentMovement(clickedMovement);

                    // new Thread(() -> {
                    //     WorkoutHistory activeWorkout = workoutPresenter.startWorkout(clickedWorkout);
                    //     workoutPresenter.setActiveWorkout(activeWorkout);
                    //     requireActivity().runOnUiThread(() -> {
                    //         Navigation.findNavController(root).navigate(R.id.navigation_workout_active);
                    //     });
                    // }).start();

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(view).navigate(R.id.navigation_movement_history);

                    Log.i("HistoryView", String.format("We clicked workout id: %d name: %s", clickedMovement.movementId, clickedMovement.name));
                });
            });
        }).start();
    }


}