package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.databinding.FragmentHomeBinding;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class  HomeView extends Fragment {
    private ListView workoutListView;

    private View root;
    private FragmentHomeBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        workoutListView = (ListView) root.findViewById(R.id.home_view_workout_list);

        updateWorkoutView();

        // Add an event to the Floating Action Button
        FloatingActionButton newWorkoutFab = root.findViewById(R.id.home_view_fab);
        newWorkoutFab.setOnClickListener(view -> {
            new Thread(() -> {
                UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
                WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

                Workout newWorkout = workoutPresenter.addWorkout(userPresenter.getCurrentUser(), "New Workout");

                workoutPresenter.setCurrentWorkout(newWorkout);

                // Navigate to the workout view with a new workout
                requireActivity().runOnUiThread(() -> {
                    Navigation.findNavController(view).navigate(R.id.navigation_workout);
                });
            }).start();

            // Navigate to home!
            // Navigation.findNavController(view).navigate(R.id.navigation_workout_active);
            // Navigation.findNavController(view).navigate(R.id.navigation_workout_history);
            // Navigation.findNavController(view).navigate(R.id.navigation_movements);
            // Navigation.findNavController(view).navigate(R.id.navigation_movement_history);
            // Navigation.findNavController(view).navigate(R.id.navigation_profile_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_history_page);
            // Navigation.findNavController(view).navigate(R.id.navigation_workout);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateWorkoutView() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            List<Workout> workouts = workoutPresenter.getWorkoutsForUser(userPresenter.getCurrentUser());

            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                workoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workouts));

                // Add an onClick listener just for an example!
                workoutListView.setOnItemClickListener((parent, view, position, id) -> {
                    Workout clickedWorkout = (Workout) workoutListView.getItemAtPosition(position);

                    // Store the workout in the state
                    workoutPresenter.setCurrentWorkout(clickedWorkout);

                    // new Thread(() -> {
                    //     WorkoutHistory activeWorkout = workoutPresenter.startWorkout(clickedWorkout);
                    //     workoutPresenter.setActiveWorkout(activeWorkout);
                    //     requireActivity().runOnUiThread(() -> {
                    //         Navigation.findNavController(root).navigate(R.id.navigation_workout_active);
                    //     });
                    // }).start();

                    // Navigate to the workout page to display the workout
                    Navigation.findNavController(root).navigate(R.id.navigation_workout);

                    Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
                });
            });
        }).start();
    }
}

