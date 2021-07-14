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

        // Button btnLogin = root.findViewById(R.id.btn_login);
        // btnLogin.setOnClickListener(view -> {
        //     // Get the inputs so they can be validated/login
        //     EditText email = root.findViewById(R.id.input_login_username);
        //     EditText password = root.findViewById(R.id.input_login_password);
        //
        //     Log.i("LoginView", String.format("Login time! Username: %s Password: %s", email.getText(), password.getText()));
        //
        //     // Finally navigate to home!
        //     Navigation.findNavController(view).navigate(R.id.navigation_home_page);
        // });



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

//         pastWorkoutListView = root.findViewById(R.id.past_workout_expandable_list_view);
//         workoutNameView = root.findViewById(R.id.text_view_past_workout_name);
//
//         new Thread(() -> {
//             WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
//
//             Workout workout = workoutPresenter.getWorkout(workoutPresenter.getActiveWorkout().workoutId);
//
//             List<WorkoutHistory> history = workoutPresenter.getWorkoutHistories(workout);
//
//             List<WorkoutHistory> allHistory = workoutPresenter.getWorkoutHistory(history);
//
//             requireActivity().runOnUiThread(() -> {
//                 workoutNameView.setText(workout.name);
//
// //                pastWorkoutAdapter = new ActiveWorkoutAdapter(
// //                        this.getContext(),
// //                        history,
// //                        this::activeWorkoutAdapterCallback
// //                );
//                 pastWorkoutListView.setAdapter(new PastWorkoutAdapter(this.getContext(), allHistory));
//
//                 // Set the ListView's adapter to our custom adapter!
//                 //pastWorkoutListView.setAdapter(pastWorkoutAdapter);
//             });
//         }).start();
    }
//    public void activeWorkoutAdapterCallback(MovementWithWorkoutMovementHistory movementWithHistory) {
//        // Display the dialog window for the set
//        new AddSetDialog(
//                movementWithHistory.movement.name,
//                movementWithHistory.movement.type,
//                (weight, reps, duration) -> {
//                    Log.i("ActiveWorkoutAdapter", String.format("Adding set to movement: %s reps: %.2f weight: %.2f duration: %.2f", movementWithHistory.movement.name, reps, weight, duration));
//
//                    // Create the set and update the UI once inputs are given
//                    new Thread(() -> {
//                        WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
//
//                        // Store the set in the database
//                        WorkoutMovementHistory newSet = workoutPresenter.addSetToActiveWorkout(
//                                workoutPresenter.getActiveWorkout().workoutHistoryId,
//                                movementWithHistory.movement.movementId,
//                                reps,
//                                weight,
//                                duration
//                        );
//
//                        // Update the history being used to render our list with the new set
//                        movementWithHistory.workoutMovementHistories.add(newSet);
//
//                        // This will re-render the list and maintain all open tabs and scrolling state
//                        requireActivity().runOnUiThread(() -> pastWorkoutAdapter.notifyDataSetChanged());
//                    }).start();
//                }
//        ).show(getChildFragmentManager(), "active_workout_dialog");
//    }


//        WorkoutWithHistory dummyData = getDummyData();
//        ArrayList<WorkoutWithHistory> workoutHistories = new ArrayList<>();
//
//        workoutHistories.add(dummyData);
//
//        // Set the ListView's adapter to our custom adapter!
//        pastWorkoutListView.setAdapter(new PastWorkoutAdapter(this.getContext(), workoutHistories));
//
//        TextView tvWorkoutName     = root.findViewById(R.id.text_view_past_workout_name);
//        TextView tvWorkoutDuration = root.findViewById(R.id.text_view_past_workout_duration);
//
//        Duration duration = Duration.between(
//            dummyData.pastWorkouts.get(0).workoutHistory.startTime,
//            dummyData.pastWorkouts.get(0).workoutHistory.endTime
//        );
//
//        long workoutSeconds = duration.getSeconds();
//
//        tvWorkoutName.setText(dummyData.workout.name);
//        tvWorkoutDuration.setText(String.format("%d:%02d:%02d", workoutSeconds / 3600, (workoutSeconds % 3600) / 60, (workoutSeconds % 60)));
//
//        new Thread(() -> {
//            // Dummy data for now!
//
//            // Add an onClick listener just for and example!
//            // movementHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
//            //     Workout clickedWorkout = (Workout) movementHistoryListView.getItemAtPosition(position);
//            //     Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
//            // });
//
//            //
//            // for (WorkoutMovementHistory mh : movementHistories) {
//            //     Log.i("MovementHistory", String.format("Set: %.2f %.2f", mh.weight, mh.reps));
//            //
//            //     // switch (measurement.type) {
//            //     //     case "bicep":
//            //     //         Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
//            //     //         biceps.setText(String.valueOf(measurement.value) + "\"");
//            //     //         break;
//            //     //     default:
//            //     //        break;
//            //     //}
//            // }
//        }).start();
//    }
//
//    private WorkoutWithHistory getDummyData() {
//        WorkoutWithHistory movementHistories = new WorkoutWithHistory();
//
//        movementHistories.workout = new Workout();
//        movementHistories.workout.workoutId = 1;
//        movementHistories.workout.name = "Chest day";
//        movementHistories.workout.userId = 1;
//
//        List<WorkoutHistoryWithMovements> pastWorkouts = new ArrayList<>();
//
//        WorkoutHistoryWithMovements workout1 = new WorkoutHistoryWithMovements();
//
//        workout1.workoutHistory = new WorkoutHistory();
//        workout1.workoutHistory.workoutHistoryId = 1;
//        workout1.workoutHistory.workoutId = 2;
//        workout1.workoutHistory.startTime = LocalDateTime.now();
//        workout1.workoutHistory.endTime = LocalDateTime.now().plusHours(1).plusMinutes(10).plusSeconds(32);
//
//        workout1.movementHistory = new ArrayList<>();
//
//        // Movement 1
//        MovementWithWorkoutMovementHistory movement1 = new MovementWithWorkoutMovementHistory();
//        movement1.movement = new Movement();
//        movement1.movement.name = "Curl";
//        movement1.movement.movementId = 1;
//        movement1.movement.type = "weight&rep";
//        movement1.movement.url = "www.google.com";
//
//        List<WorkoutMovementHistory> sets1 = new ArrayList<>();
//
//        WorkoutMovementHistory rep1_1 = new WorkoutMovementHistory();
//        rep1_1.workoutMovementHistoryId = 1;
//        rep1_1.reps = 113;
//        rep1_1.weight = 1;
//        rep1_1.movementId = 1;
//        rep1_1.workoutHistoryId = 1;
//
//        WorkoutMovementHistory rep1_2 = new WorkoutMovementHistory();
//        rep1_2.workoutMovementHistoryId = 2;
//        rep1_2.reps = 8;
//        rep1_2.weight = 245;
//        rep1_2.movementId = 1;
//        rep1_2.workoutHistoryId = 1;
//
//        sets1.add(rep1_1);
//        sets1.add(rep1_2);
//
//        movement1.workoutMovementHistories = sets1;
//
//        // Movement 2
//        MovementWithWorkoutMovementHistory movement2 = new MovementWithWorkoutMovementHistory();
//        movement2.movement = new Movement();
//        movement2.movement.name = "Bench";
//        movement2.movement.movementId = 1;
//        movement2.movement.type = "weight&rep";
//        movement2.movement.url = "www.google.com";
//
//        List<WorkoutMovementHistory> sets2 = new ArrayList<>();
//
//        WorkoutMovementHistory rep2_1 = new WorkoutMovementHistory();
//        rep2_1.workoutMovementHistoryId = 1;
//        rep2_1.reps = 10;
//        rep2_1.weight = 215;
//        rep2_1.movementId = 1;
//        rep2_1.workoutHistoryId = 1;
//
//        WorkoutMovementHistory rep2_2 = new WorkoutMovementHistory();
//        rep2_2.workoutMovementHistoryId = 2;
//        rep2_2.reps = 8;
//        rep2_2.weight = 245;
//        rep2_2.movementId = 1;
//        rep2_2.workoutHistoryId = 1;
//
//        sets2.add(rep2_1);
//        sets2.add(rep2_2);
//
//        movement2.workoutMovementHistories = sets2;
//
//
//        workout1.movementHistory.add(movement1);
//        workout1.movementHistory.add(movement2);
//        pastWorkouts.add(workout1);
//        movementHistories.pastWorkouts = pastWorkouts;
//
//        return movementHistories;
//    }
}
