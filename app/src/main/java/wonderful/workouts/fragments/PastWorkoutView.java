package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wonderful.workouts.databinding.FragmentPastWorkoutBinding;

public class PastWorkoutView extends Fragment {
    private FragmentPastWorkoutBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentPastWorkoutBinding.inflate(inflater, container, false);
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
        //     Navigation.findNavController(view).navigate(R.id.navigation_home_page);
        // });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}