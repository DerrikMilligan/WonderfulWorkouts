package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wonderful.workouts.databinding.FragmentLoginBinding;

public class LoginView extends Fragment {
    private FragmentLoginBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    // @Override
    // public void onResume() {
    //     super.onResume();
    //     ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
    //     if (supportActionBar != null)
    //         supportActionBar.hide();
    //
    //
    // }
    //
    // @Override
    // public void onStop() {
    //     super.onStop();
    //     ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
    //     if (supportActionBar != null)
    //         supportActionBar.show();
    // }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}