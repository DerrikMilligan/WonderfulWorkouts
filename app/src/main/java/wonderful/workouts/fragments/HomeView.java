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

import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.databinding.FragmentHomeBinding;

public class HomeView extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Dummy data for now!
        ArrayList<Workout> workouts = getWorkouts();

        ListView workoutListView = (ListView) root.findViewById(R.id.workout_view_movements_list);

        // Set the ListView's adapter to our custom adapter!
        workoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workouts));

        // Add an onClick listener just for and example!
        workoutListView.setOnItemClickListener((parent, view, position, id) -> {
            Workout clickedWorkout = (Workout) workoutListView.getItemAtPosition(position);
            Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
        });

        // Add an event to the Floating Action Button
        FloatingActionButton btnTesting = root.findViewById(R.id.fab_home_new_workout);
        btnTesting.setOnClickListener(view -> {
            Log.i("HomeView", "Test button pressed!");

            // Navigate to home!
            // Navigation.findNavController(view).navigate(R.id.navigation_current_workout);
            // Navigation.findNavController(view).navigate(R.id.navigation_past_workout);
            Navigation.findNavController(view).navigate(R.id.navigation_movements);
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

    // Yeah this is all just dummy data for now. And we may not end up using workouts to
    // display this all, but for now it's great!
    private ArrayList<Workout> getWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();

        Workout w = new Workout();
        w.workoutId = 1;
        w.name = "Leg Day";
        workouts.add(w);

        Workout w1 = new Workout();
        w1.workoutId = 2;
        w1.name = "Chest Day";
        workouts.add(w1);

        Workout w2 = new Workout();
        w2.workoutId = 22;
        w2.name = "Neck Day";
        workouts.add(w2);

        return workouts;
    }

}

