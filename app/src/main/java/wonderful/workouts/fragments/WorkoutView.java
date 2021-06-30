package wonderful.workouts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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