package wonderful.workouts.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.adapters.WorkoutHistoryAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.databinding.FragmentWorkoutBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class WorkoutView extends Fragment {
    private ListView pastWorkoutListView;
    private ListView movementsListView;
    private EditText workoutNameEditText;
    private Button startWorkoutButton;

    private FragmentWorkoutBinding binding;
    private View root;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        pastWorkoutListView = root.findViewById(R.id.workout_view_past_entries);
        movementsListView   = root.findViewById(R.id.workout_view_movements_list);
        workoutNameEditText = root.findViewById(R.id.workout_view_workout_name);
        startWorkoutButton  = root.findViewById(R.id.workout_view_start_workout);

        loadWorkoutName();
        getWorkoutHistory();
        getMovements();

        startWorkoutButton.setOnClickListener((view) -> {
            new Thread(() -> {
                WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
                Workout workout = workoutPresenter.getCurrentWorkout();

                WorkoutHistory workoutHistory = workoutPresenter.startWorkout(workout);
                workoutPresenter.setActiveWorkout(workoutHistory);

                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(view).navigate(R.id.navigation_workout_active);
                });
            }).start();
        });

        // Update the name when a key is pressed in the text view.
        workoutNameEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    new Thread(() -> {
                        WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
                        Workout workout = workoutPresenter.getCurrentWorkout();

                        String newWorkoutName = s.toString();

                        Log.i("WorkoutView", String.format("Updating workout name: %s", newWorkoutName));

                        // Update the name!
                        workoutPresenter.updateWorkoutName(workout, newWorkoutName);
                    }).start();
                }
            }
        });

        FloatingActionButton btnTesting = root.findViewById(R.id.workout_view_fab);
        // Add a movement to the workout
        btnTesting.setOnClickListener(view -> {
            new Thread(() -> {
                WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
                MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());

                // Create a new dummy movement
                Movement movement = movementPresenter.createNewMovement("", Movement.Reps, "None", "None");
                movementPresenter.setCurrentMovement(movement);

                // Add it to the workout
                workoutPresenter.addMovementToWorkout(workoutPresenter.getCurrentWorkout(), movement);

                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(root).navigate(R.id.navigation_new_edit_movement_page);
                });
            }).start();
        });

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private void loadWorkoutName() {
        // Load the name initially into the text view
        new Thread(() -> {
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
            Workout workout = workoutPresenter.getCurrentWorkout();

            workoutNameEditText.setText(workout.name);
        }).start();
    }

    private void getWorkoutHistory() {
        new Thread(() -> {
            // Get the presenter.
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            List<WorkoutHistory> workoutHistories = workoutPresenter.getWorkoutHistories(workoutPresenter.getCurrentWorkout());
            //List<Workout> workouts = workoutPresenter.getWorkoutsForUser(userPresenter.getCurrentUser());

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                pastWorkoutListView.setAdapter(new WorkoutHistoryAdapter(this.getContext(), workoutHistories));

                // Add an onClick listener just for an example!
                pastWorkoutListView.setOnItemClickListener((parent, view, position, id) -> {
                    WorkoutHistory clickedWorkout = (WorkoutHistory) pastWorkoutListView.getItemAtPosition(position);

                    // Store the workout in the state
                    workoutPresenter.setActiveWorkout(clickedWorkout);

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(root).navigate(R.id.navigation_workout_history);

                    Log.i("HomeView", String.format("We clicked workoutHistoryId: %d", clickedWorkout.workoutHistoryId));
                });
            });
        }).start();
    }

    private void getMovements() {
        new Thread(() -> {
            // Get the presenter.
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            List<Movement> movements = workoutPresenter.getWorkoutMovements(workoutPresenter.getCurrentWorkout());

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                movementsListView.setAdapter(new MovementAdapter(this.getContext(), movements));

                // Add an onClick listener just for an example!
                //movementsListView.setOnItemClickListener((parent, view, position, id) -> {
                    //Workout clickedWorkout = (Workout) movementsListView.getItemAtPosition(position);

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
                    //Navigation.findNavController(root).navigate(R.id.navigation_workout);

                    //Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
                //});
            });
        }).start();

    }
}