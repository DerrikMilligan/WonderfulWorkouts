package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.ActiveWorkoutAdapter;
import wonderful.workouts.adapters.PastWorkoutAdapter;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentWorkoutHistoryBinding;
import wonderful.workouts.dialogs.AddSetDialog;
import wonderful.workouts.presenters.WorkoutPresenter;

public class WorkoutHistoryView extends Fragment {
    private FragmentWorkoutHistoryBinding binding;
    private ExpandableListView pastWorkoutListView;
    private TextView workoutNameView;
    private PastWorkoutAdapter pastWorkoutAdapter;

    private View root;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        updatePastWorkoutDisplay();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("DefaultLocale")
    public void updatePastWorkoutDisplay() {
         pastWorkoutListView = root.findViewById(R.id.past_workout_expandable_list_view);
         workoutNameView = root.findViewById(R.id.text_view_past_workout_name);

         new Thread(() -> {
             WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
             Workout workout = workoutPresenter.getWorkout(workoutPresenter.getActiveWorkout().workoutId);

             WorkoutHistoryWithMovements history = workoutPresenter.getWorkoutHistory(workoutPresenter.getActiveWorkout());

             for (MovementWithWorkoutMovementHistory mwwmh : history.movementHistory) {
                 Log.i("WorkoutHistoryView", String.format("Movement: %s", mwwmh.movement.name));
                 for (WorkoutMovementHistory wmh : mwwmh.workoutMovementHistories) {
                     Log.i("WorkoutHistoryView", String.format("Set reps: %.2f, weight: %.2f, duration: %.2f", wmh.reps, wmh.weight, wmh.duration));
                 }
             }

             requireActivity().runOnUiThread(() -> {
                 workoutNameView.setText(workout.name);
                 pastWorkoutListView.setAdapter(new PastWorkoutAdapter(this.getContext(), history));
             });
         }).start();
    }
}

