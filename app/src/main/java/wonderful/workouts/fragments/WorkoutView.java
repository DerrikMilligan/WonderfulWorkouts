package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.databinding.FragmentWorkoutBinding;
import wonderful.workouts.presenters.UserPresenter;
import wonderful.workouts.presenters.WorkoutPresenter;

public class WorkoutView extends Fragment {
    private FragmentWorkoutBinding binding;
    private ListView pastWorkoutListView;
    private ListView movementsListView;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Get dummy data for now
        //ArrayList<WorkoutHistory> workoutHistories = getWorkoutHistory();

        pastWorkoutListView = (ListView) root.findViewById(R.id.workout_view_past_entries);
        getWorkoutHistory();

//        if (workoutHistoryListView == null) {
//            Log.i("WorkoutView", "History list view was null");
//        }
//
//        workoutHistoryListView.setAdapter(new WorkoutHistoryAdapter(this.getContext(), workoutHistories));

        //ArrayList<Movement> movements = getMovements();

        movementsListView = (ListView) root.findViewById(R.id.workout_history_view_movements_list);

        // Set the ListView's adapter to our custom adapter!
        //movementListView.setAdapter(new MovementAdapter(this.getContext(), movements));

        //Add an event to the Floating Action Button
        FloatingActionButton btnTesting = root.findViewById(R.id.workout_new_movement);
        btnTesting.setOnClickListener(view -> {
            Log.i("Workout View", "Test button pressed!");

            Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);
        });

        Button startWorkoutBtn = root.findViewById(R.id.workout_start_workout_button);
        startWorkoutBtn.setOnClickListener(view ->{
            Log.i("WorkoutView", String.format("Starting a workout"));
            Navigation.findNavController(view).navigate(R.id.navigation_workout_history);
        });

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
        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<WorkoutHistory> getWorkoutHistory() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            Workout workoutClicked = workoutPresenter.getCurrentWorkout();
            WorkoutWithHistory workoutHistories = workoutPresenter.getAllPastWorkoutHistories(workoutClicked);
            //List<Workout> workouts = workoutPresenter.getWorkoutsForUser(userPresenter.getCurrentUser());


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                pastWorkoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workoutHistories));

                // Add an onClick listener just for an example!
                pastWorkoutListView.setOnItemClickListener((parent, view, position, id) -> {
                    Workout clickedWorkout = (Workout) pastWorkoutListView.getItemAtPosition(position);

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

//       new Thread(() -> {
//            // Get the presenter.
//            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
//            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());
//
//            List<Workout> workouts = workoutPresenter.getWorkoutsForUser(userPresenter.getCurrentUser());
//
//            // Now that we have the workouts build it on the UI thread to update the UI
//            requireActivity().runOnUiThread(() -> {
//                // Set the ListView's adapter to our custom adapter!
//                workoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workouts));
//
//                // Add an onClick listener just for an example!
//                workoutListView.setOnItemClickListener((parent, view, position, id) -> {
//                    Workout clickedWorkout = (Workout) workoutListView.getItemAtPosition(position);
//
//                    // Store the workout in the state
//                    workoutPresenter.setCurrentWorkout(clickedWorkout);
//
//                    // new Thread(() -> {
//                    //     WorkoutHistory activeWorkout = workoutPresenter.startWorkout(clickedWorkout);
//                    //     workoutPresenter.setActiveWorkout(activeWorkout);
//                    //     requireActivity().runOnUiThread(() -> {
//                    //         Navigation.findNavController(root).navigate(R.id.navigation_workout_active);
//                    //     });
//                    // }).start();
//
//                    // Navigate to the workout page to display the workout
//                    Navigation.findNavController(root).navigate(R.id.navigation_workout);
//
//                    Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
//                });
//            });
//        }).start();
//
//
//        ArrayList<WorkoutHistory> workoutHistories = new ArrayList<>();
//
//        WorkoutHistory wh = new WorkoutHistory();
//        wh.startTime = LocalDateTime.now();
//        workoutHistories.add(wh);
//
//        WorkoutHistory wh1 = new WorkoutHistory();
//        wh1.startTime = LocalDateTime.now().plusDays(5);
//        workoutHistories.add(wh1);
//
//        WorkoutHistory wh2 = new WorkoutHistory();
//        wh2.startTime = LocalDateTime.now().plusDays(10);
//        workoutHistories.add(wh2);
//
//        return workoutHistories;
    }

    private ArrayList<Movement> getMovements() {
        new Thread(() -> {
            // Get the presenter.
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());
            WorkoutPresenter workoutPresenter = WorkoutPresenter.getInstance(requireContext());

            Workout workoutClicked = workoutPresenter.getCurrentWorkout();
            List<Movement> movements = workoutPresenter.getWorkoutMovements(workoutClicked);


            // Now that we have the workouts build it on the UI thread to update the UI
            requireActivity().runOnUiThread(() -> {
                // Set the ListView's adapter to our custom adapter!
                movementsListView.setAdapter(new WorkoutAdapter(this.getContext(), movements));

                // Add an onClick listener just for an example!
                //movementsListView.setOnItemClickListener((parent, view, position, id) -> {
                    //Workout clickedWorkout = (Workout) movementsListView.getItemAtPosition(position);

                    // Store the workout in the state
                    //workoutPresenter.setCurrentWorkout(clickedWorkout);

                    // new Thread(() -> {
                    //     WorkoutHistory activeWorkout = workoutPresenter.startWorkout(clickedWorkout);
                    //     workoutPresenter.setActiveWorkout(activeWorkout);
                    //     requireActivity().runOnUiThread(() -> {
                    //         Navigation.findNavController(root).navigate(R.id.navigation_workout_active);
                    //     });
                    // }).start();

                    // Navigate to the workout page to display the workout
                    //Navigation.findNavController(root).navigate(R.id.navigation_workout);

                    //Log.i("HomeView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
                });
            });
        }).start();


//        ArrayList<Movement> movements = new ArrayList<>();
//
//        Movement m = new Movement();
//        m.movementId = 1;
//        m.name = "Leg Press";
//        movements.add(m);
//
//        Movement m1 = new Movement();
//        m1.movementId = 2;
//        m1.name = "Squats";
//        movements.add(m1);
//
//        Movement m2 = new Movement();
//        m2.movementId = 3;
//        m2.name = "Calve Raises";
//        movements.add(m2);
//
//
//
//        return movements;
    }
}