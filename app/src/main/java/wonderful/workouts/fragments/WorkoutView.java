package wonderful.workouts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.adapters.WorkoutHistoryAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.databinding.FragmentCurrentWorkoutBinding;

public class WorkoutView extends Fragment {
    private FragmentCurrentWorkoutBinding binding;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCurrentWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Get dummy data for now
        ArrayList<WorkoutHistory> workoutHistories = getWorkoutHistory();

        ListView workoutHistoryListView = (ListView) root.findViewById(R.id.workout_view_past_entries);

        workoutHistoryListView.setAdapter(new WorkoutHistoryAdapter(this.getContext(), workoutHistories));


        ArrayList<Movement> movements = getMovements();

        ListView movementListView = (ListView) root.findViewById(R.id.workout_history_view_movements_list);

        // Set the ListView's adapter to our custom adapter!
        movementListView.setAdapter(new MovementAdapter(this.getContext(), movements));

        // Button btnLogin = root.findViewById(R.id.btn_login);
        // btnLogin.setOnClickListener(view -> {
        //     // Get the inputs so they can be validated/login
        //     EditText email = root.findViewById(R.id.input_login_username);
        //     EditText password = root.findViewById(R.id.input_login_password);
        //
        //     Log.i("LoginView", String.format("Login time! Username: %s Password: %s", email.getText(), password.getText()));
        //
        //     // Finally navigate to home!
        //     Navigation.findNavController(view).navigate(R.id.navigation_home);
        // });
        updateWorkoutHistoryDisplay();
        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
    private ArrayList<WorkoutHistory> getWorkoutHistory() {
        ArrayList<WorkoutHistory> workoutHistories = new ArrayList<>();

        WorkoutHistory wh = new WorkoutHistory();
        wh.startTime = LocalDateTime.now();
        workoutHistories.add(wh);

        WorkoutHistory wh1 = new WorkoutHistory();
        wh1.startTime = LocalDateTime.now().plusDays(5);
        workoutHistories.add(wh1);

        WorkoutHistory wh2 = new WorkoutHistory();
        wh2.startTime = LocalDateTime.now().plusDays(10);
        workoutHistories.add(wh2);

        return workoutHistories;
        
    }

    private ArrayList<Movement> getMovements() {
        ArrayList<Movement> movements = new ArrayList<>();

        Movement m = new Movement();
        m.movementId = 1;
        m.name = "Leg Press";
        movements.add(m);

        Movement m1 = new Movement();
        m1.movementId = 2;
        m1.name = "Squats";
        movements.add(m1);

        Movement m2 = new Movement();
        m2.movementId = 3;
        m2.name = "Calve Raises";
        movements.add(m2);



        return movements;
    }

    public void updateWorkoutHistoryDisplay() {



        // new Thread(() -> {
        //     // List<Measurement> measurements = presenter.getCurrentMeasurements();
        //     List<WorkoutHistory> workoutHistories = new ArrayList<>();
        //
        //
        //     WorkoutHistory wh = new WorkoutHistory();
        //     wh.startTime = new Date(2021, 6, 20);
        //
        //     WorkoutHistory wh1 = new WorkoutHistory();
        //     wh1.startTime = new Date(2021, 6, 22);
        //
        //     WorkoutHistory wh2 = new WorkoutHistory();
        //     wh2.startTime = new Date(2021, 6, 24);
        //
        //     workoutHistories.add(wh);
        //     workoutHistories.add(wh1);
        //     workoutHistories.add(wh2);
        //
        //
        //     for (WorkoutHistory history : workoutHistories) {
        //         Log.i("WorkoutView", String.format(String.valueOf(history.startTime)));
        //
        //         // switch (measurement.type) {
        //         //     case "bicep":
        //         //         Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
        //         //         biceps.setText(String.valueOf(measurement.value) + "\"");
        //         //         break;
        //         //     default:
        //         //        break;
        //         //}
        //     }
        // }).start();
    }
}