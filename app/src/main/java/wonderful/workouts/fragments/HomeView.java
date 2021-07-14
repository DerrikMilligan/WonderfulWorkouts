package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.UserWithWorkouts;
import wonderful.workouts.database.joiners.WorkoutHistoryWithWorkoutMovementHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.database.joiners.WorkoutWithMovements;
import wonderful.workouts.databinding.FragmentHomeBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class  HomeView extends Fragment {
    private ListView workoutListView;

    private View root;
    private FragmentHomeBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        workoutListView = (ListView) root.findViewById(R.id.workout_view_movements_list);

        updateWorkoutView();

        // Add an event to the Floating Action Button
        FloatingActionButton btnTesting = root.findViewById(R.id.fab_home_new_workout);
        btnTesting.setOnClickListener(view -> {
            Log.i("HomeView", "Test button pressed!");

            // Navigate to home!
            // Navigation.findNavController(view).navigate(R.id.navigation_current_workout);
            // Navigation.findNavController(view).navigate(R.id.navigation_workout_history);
            Navigation.findNavController(view).navigate(R.id.navigation_movements);
            // Navigation.findNavController(view).navigate(R.id.navigation_movement_history);
            // Navigation.findNavController(view).navigate(R.id.navigation_profile_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_history_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_workout);
        });

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

            List<Workout> workouts = workoutPresenter.getWorkoutsForUser(userPresenter.getCurrentUser());

            // This was for testing creating workout histories and sets!
            for (Workout w : workouts) {
                WorkoutHistory history = workoutPresenter.startWorkout(w);

                // Technically this is pointless here, but it's good to prove it's working
                workoutPresenter.setActiveWorkout(history);

                List<Movement> movements = workoutPresenter.getWorkoutMovements(w);

                // We'll just add dummy data for each movement
                for (Movement m : movements) {
                    switch (m.type) {
                        case Movement.Reps:
                            workoutPresenter.addRepSetToActiveWorkout(workoutPresenter.getActiveWorkout(), m, 1);
                            break;
                        case Movement.RepsAndWeight:
                            workoutPresenter.addRepAndWeightSetToActiveWorkout(workoutPresenter.getActiveWorkout(), m, 1, 2);
                            break;
                        case Movement.Timed:
                            workoutPresenter.addTimedSetToActiveWorkout(workoutPresenter.getActiveWorkout(), m, 3);
                            break;
                    }
                }

                workoutPresenter.finishWorkout(workoutPresenter.getActiveWorkout());
            }

            Workout w = workouts.get(0);

            List<WorkoutWithHistory> history = workoutPresenter.getWorkoutHistory(w);

            for (WorkoutWithHistory h : history) {
                for (WorkoutHistoryWithWorkoutMovementHistories whwmh : h.pastWorkouts) {
                    for (MovementWithWorkoutMovementHistory mwwmh : whwmh.movementHistory) {
                        for (WorkoutMovementHistory wmh : mwwmh.workoutMovementHistories) {
                            Log.i("HomeView", String.format(
                                "workout: %s startTime: %s endTime: %s movement: %s reps: %.1f weight: %.1f duration: %.1f",
                                h.workout.name,
                                whwmh.workoutHistory.startTime.format(WorkoutHistory.dateFormat),
                                whwmh.workoutHistory.endTime.format(WorkoutHistory.dateFormat),
                                mwwmh.movement.name,
                                wmh.reps,
                                wmh.weight,
                                wmh.duration
                            ));
                        }
                    }
                }
            }

            // This should return a list of all the histories for a given workout. No movement info!
            // List<WorkoutHistory> workoutHistories = workoutPresenter.getWorkoutHistories(w);

            // MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());
            // List<Movement> movements = movementPresenter.getUserMovements(userPresenter.getCurrentUser());
            // for (Movement m : movements) {
            //     Log.i("HomeView", String.format("Movement: %s", m.name));
            // }


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                workoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workouts));

                // Add an onClick listener just for an example!
                workoutListView.setOnItemClickListener((parent, view, position, id) -> {
                    Workout clickedWorkout = (Workout) workoutListView.getItemAtPosition(position);

                    // Store the workout in the state
                    workoutPresenter.setCurrentWorkout(clickedWorkout);

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(root).navigate(R.id.navigation_workout);

                    Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
                });
            });
        }).start();
    }
}

