package wonderful.workouts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import wonderful.workouts.databinding.FragmentMovementsBinding;

public class MovementsView extends Fragment {
    private FragmentMovementsBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMovementsBinding.inflate(inflater, container, false);
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