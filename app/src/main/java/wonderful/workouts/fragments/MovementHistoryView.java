package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.WorkoutWithHistoryAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithWorkoutMovementHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentMovementHistoryBinding;

public class MovementHistoryView extends Fragment {
    private FragmentMovementHistoryBinding binding;
    private ListView movementHistoryListView;
    private View root;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMovementHistoryBinding.inflate(inflater, container, false);
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

        movementHistoryListView = (ListView) root.findViewById(R.id.movement_history_list_view);

        updateMovementHistoryDisplay();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateMovementHistoryDisplay() {
        WorkoutWithHistory dummyData = getDummyData();
        ArrayList<WorkoutWithHistory> workoutHistories = new ArrayList<>();

        workoutHistories.add(dummyData);

        // Set the ListView's adapter to our custom adapter!
        movementHistoryListView.setAdapter(new WorkoutWithHistoryAdapter(this.getContext(), workoutHistories));

        new Thread(() -> {
            // Dummy data for now!

            // Add an onClick listener just for and example!
            // movementHistoryListView.setOnItemClickListener((parent, view, position, id) -> {
            //     Workout clickedWorkout = (Workout) movementHistoryListView.getItemAtPosition(position);
            //     Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
            // });

            //
            // for (WorkoutMovementHistory mh : movementHistories) {
            //     Log.i("MovementHistory", String.format("Set: %.2f %.2f", mh.weight, mh.reps));
            //
            //     // switch (measurement.type) {
            //     //     case "bicep":
            //     //         Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
            //     //         biceps.setText(String.valueOf(measurement.value) + "\"");
            //     //         break;
            //     //     default:
            //     //        break;
            //     //}
            // }
        }).start();
    }

    private WorkoutWithHistory getDummyData() {
        WorkoutWithHistory movementHistories = new WorkoutWithHistory();

        movementHistories.workout = new Workout();
        movementHistories.workout.workoutId = 1;
        movementHistories.workout.name = "Chest day";
        movementHistories.workout.userId = 1;

        List<WorkoutHistoryWithWorkoutMovementHistories> pastWorkouts = new ArrayList<>();

        WorkoutHistoryWithWorkoutMovementHistories workout1 = new WorkoutHistoryWithWorkoutMovementHistories();

        workout1.workoutHistory = new WorkoutHistory();
        workout1.workoutHistory.workoutHistoryId = 1;
        workout1.workoutHistory.workoutId = 2;
        workout1.workoutHistory.startTime = LocalDateTime.now();
        workout1.workoutHistory.endTime = LocalDateTime.now().plusHours(1).plusMinutes(10);

        workout1.movementHistory = new ArrayList<>();

        MovementWithWorkoutMovementHistory movement1 = new MovementWithWorkoutMovementHistory();
        movement1.movement = new Movement();
        movement1.movement.name = "Curl";
        movement1.movement.movementId = 1;
        movement1.movement.type = "weight&rep";
        movement1.movement.url = "www.google.com";

        List<WorkoutMovementHistory> sets1 = new ArrayList<>();

        WorkoutMovementHistory rep1_1 = new WorkoutMovementHistory();
        rep1_1.workoutMovementHistoryId = 1;
        rep1_1.reps = 10;
        rep1_1.weight = 215;
        rep1_1.movementId = 1;
        rep1_1.workoutHistoryId = 1;

        WorkoutMovementHistory rep1_2 = new WorkoutMovementHistory();
        rep1_2.workoutMovementHistoryId = 2;
        rep1_2.reps = 8;
        rep1_2.weight = 245;
        rep1_2.movementId = 1;
        rep1_2.workoutHistoryId = 1;

        sets1.add(rep1_1);
        sets1.add(rep1_2);

        movement1.workoutMovementHistories = sets1;
        workout1.movementHistory.add(movement1);
        pastWorkouts.add(workout1);
        movementHistories.pastWorkouts = pastWorkouts;

        return movementHistories;
    }
}