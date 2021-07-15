package wonderful.workouts.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import wonderful.workouts.R;
import wonderful.workouts.adapters.ActiveWorkoutAdapter;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentWorkoutActiveBinding;
import wonderful.workouts.dialogs.AddSetDialog;
import wonderful.workouts.presenters.WorkoutPresenter;

public class WorkoutActiveView extends Fragment {
    private FragmentWorkoutActiveBinding binding;

    private ExpandableListView activeWorkoutListView;
    private TextView workoutNameView;
    private Timer workoutTimer;
    private ActiveWorkoutAdapter activeWorkoutAdapter;
    private View root;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentWorkoutActiveBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        Button endWorkoutButton = root.findViewById(R.id.active_workout_end_workout);
        endWorkoutButton.setOnClickListener((view) -> {
            new Thread(() -> {
                WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
                workoutPresenter.finishWorkout(workoutPresenter.getActiveWorkout());

                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(view).popBackStack();
                    // Navigation.findNavController(view).navigate(R.id.navigation_workout_history);
                });
            }).start();
        });

        startWorkoutTimer();
        updateActiveWorkoutDisplay();

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();

        workoutTimer.cancel();

        binding = null;
    }

    public void startWorkoutTimer() {
        TextView durationTextView = root.findViewById(R.id.active_workout_text_view_duration);
        LocalDateTime startTime = LocalDateTime.now();
        workoutTimer = new Timer();
        TimerTask tt = new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                requireActivity().runOnUiThread(() -> {
                    Duration duration = Duration.between(startTime, LocalDateTime.now());
                    long seconds = duration.getSeconds();
                    durationTextView.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
                });
            }
        };
        workoutTimer.scheduleAtFixedRate(tt, 0, 1000);
    }

    @SuppressLint("DefaultLocale")
    public void updateActiveWorkoutDisplay() {
        activeWorkoutListView = root.findViewById(R.id.active_workout_expandable_list_view);
        workoutNameView = root.findViewById(R.id.active_workout_text_view_workout_name);

        new Thread(() -> {
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            Workout workout = workoutPresenter.getWorkout(workoutPresenter.getActiveWorkout().workoutId);

            // WorkoutWithHistory history = workoutPresenter.getAllPastWorkoutHistoriesWithMovements(workout);
            WorkoutHistoryWithMovements history = workoutPresenter.getWorkoutHistory(workoutPresenter.getActiveWorkout());

            requireActivity().runOnUiThread(() -> {
                workoutNameView.setText(workout.name);

                activeWorkoutAdapter = new ActiveWorkoutAdapter(
                    this.getContext(),
                    history,
                    this::activeWorkoutAdapterCallback
                );

                // Set the ListView's adapter to our custom adapter!
                activeWorkoutListView.setAdapter(activeWorkoutAdapter);
            });
        }).start();
    }

    public void activeWorkoutAdapterCallback(MovementWithWorkoutMovementHistory movementWithHistory) {
        // Display the dialog window for the set
        new AddSetDialog(
            movementWithHistory.movement.name,
            movementWithHistory.movement.type,
            (weight, reps, duration) -> {
                Log.i("ActiveWorkoutAdapter", String.format("Adding set to movement: %s reps: %.2f weight: %.2f duration: %.2f", movementWithHistory.movement.name, reps, weight, duration));

                // Create the set and update the UI once inputs are given
                new Thread(() -> {
                    WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

                    // Store the set in the database
                    WorkoutMovementHistory newSet = workoutPresenter.addSetToActiveWorkout(
                        workoutPresenter.getActiveWorkout().workoutHistoryId,
                        movementWithHistory.movement.movementId,
                        reps,
                        weight,
                        duration
                    );

                    // Update the history being used to render our list with the new set
                    movementWithHistory.workoutMovementHistories.add(newSet);

                    // This will re-render the list and maintain all open tabs and scrolling state
                    requireActivity().runOnUiThread(() -> activeWorkoutAdapter.notifyDataSetChanged());
                }).start();
            }
        ).show(getChildFragmentManager(), "active_workout_dialog");
    }

}