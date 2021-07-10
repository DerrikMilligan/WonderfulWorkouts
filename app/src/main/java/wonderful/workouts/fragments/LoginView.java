package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.databinding.FragmentLoginBinding;
import wonderful.workouts.dialogs.DialogHelper;
import wonderful.workouts.presenters.UserPresenter;

public class LoginView extends Fragment {
    // Get the inputs so they can be validated/login
    private EditText editTextUsername;
    private EditText editTextPassword;

    private FragmentLoginBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the inputs so they can be validated/login
        editTextUsername = root.findViewById(R.id.input_login_username);
        editTextPassword = root.findViewById(R.id.input_login_password);

        Button btnLogin = root.findViewById(R.id.btn_login);
        // Start a thread since we need stuff from the database!
        btnLogin.setOnClickListener((view) -> new Thread(() -> loginHandler(view)).start());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loginHandler(View view) {
        // Get the presenter. We're already in a thread so this is okay!
        UserPresenter userPresenter = UserPresenter.getInstance(requireContext());

        // Get the strings we're working with
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.length() == 0 || password.length() == 0) {
            DialogHelper.displayAlertFromFragmentThread(
                requireActivity(),
                "Whoops!",
                "You have to actually input a username or password!",
                (dialog, which) -> {}
            );

            return;
        }


        // Check to see if the username exists
        if (userPresenter.usernameExists(username)) {
            // If the username exists and we have a valid password
            if (userPresenter.passwordIsValid(username, password)) {
                // Set the user to the current stored logged in user in the presenter
                userPresenter.setCurrentUser(userPresenter.getUser(username, password));

                // Then lets navigate to the home page. We have to use runOnUiThread to navigate!
                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(view).navigate(R.id.navigation_home_page);
                });

            // If we have a bad password let the user know!
            } else {
                DialogHelper.displayAlertFromFragmentThread(
                    requireActivity(),
                    "Whoops!",
                    "That seems to be an invalid password",
                    (dialog, which) -> {
                        // Clear out the password
                        editTextPassword.setText("");
                    }
                );
            }
        } else {
            User user = userPresenter.createNewUser(username, password, true);
            if (user == null) {
                DialogHelper.displayAlertFromFragmentThread(
                    requireActivity(),
                    "Whoops!",
                    "There was an error creating the user! Try again later",
                    (dialog, which) -> {}
                );
            } else {
                // Set the user on the presenter as our active user!
                userPresenter.setCurrentUser(user);

                DialogHelper.displayAlertFromFragmentThread(
                    requireActivity(),
                    "Welcome!",
                    "Successfully created a new account!",
                    (dialog, which) -> {
                        // If we created the user navigate to home!
                        Navigation.findNavController(view).navigate(R.id.navigation_home_page);
                    }
                );
            }
        }
    }

}