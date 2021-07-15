package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wonderful.workouts.R;
import wonderful.workouts.adapters.ActiveWorkoutAdapter;
import wonderful.workouts.adapters.MovementHistoryAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentMovementHistoryBinding;
import wonderful.workouts.presenters.MovementPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class MovementHistoryView extends Fragment {
    private FragmentMovementHistoryBinding binding;
    private ExpandableListView movementHistoryListView;
    private TextView movementNameView;
    private MovementHistoryAdapter movementHistoryAdapter;
    private View root;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMovementHistoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        updateMovementHistoryDisplay();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateMovementHistoryDisplay() {

        movementHistoryListView = root.findViewById(R.id.movement_history_expandable_list_view);
        movementNameView = root.findViewById(R.id.MovementName);

        new Thread(() -> {
            MovementPresenter movementPresenter = MovementPresenter.getInstance(requireContext());
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            Movement movement = movementPresenter.getCurrentMovement();

            Map<WorkoutHistory, List<WorkoutMovementHistory>> history = movementPresenter.getMovementHistory(movement);

            for (Map.Entry<WorkoutHistory, List<WorkoutMovementHistory>> entry : history.entrySet()) {
                Log.i("MovementHistoryView", String.format("History Id: %d", entry.getKey().workoutHistoryId));

                for (WorkoutMovementHistory wmh : entry.getValue()) {
                    Log.i("MovementHistoryView", String.format("   Movement reps: %.2f, weight: %.2f, duration: %.2f", wmh.reps, wmh.weight, wmh.duration));
                }
            }

            requireActivity().runOnUiThread(() -> {
                movementNameView.setText(movement.name);

                // Set the ListView's adapter to our custom adapter!
                movementHistoryListView.setAdapter(new MovementHistoryAdapter(this.getContext(), history));
            });
        }).start();

    }
}