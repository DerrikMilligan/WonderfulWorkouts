package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.databinding.FragmentLoginBinding;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class LoginView extends Fragment {
    private FragmentLoginBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnLogin = root.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> {
            // Get the inputs so they can be validated/login
            EditText email = root.findViewById(R.id.input_login_username);
            EditText password = root.findViewById(R.id.input_login_password);

            Log.i("LoginView", String.format("Login time! Username: %s Password: %s", email.getText(), password.getText()));

            // Finally navigate to home!
            Navigation.findNavController(view).navigate(R.id.navigation_home_page);
        });

        // new Thread(() -> {
        //     WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(root.getContext());
        //
        //     EditText email = root.findViewById(R.id.input_login_username);
        //     EditText password = root.findViewById(R.id.input_login_password);
        //
        //     String sEmail = email.getText().toString();
        //     String sPassword = password.getText().toString();
        //
        //     float reps = Float.parseFloat(sEmail);
        //     float weight = Float.parseFloat(sPassword);
        //
        //     WorkoutHistory workoutHistory = new WorkoutHistory();
        //     Movement movement = new Movement();
        //
        //     workoutPresenter.addSetToWorkout(workoutHistory, movement, reps, weight);
        // }).start();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}