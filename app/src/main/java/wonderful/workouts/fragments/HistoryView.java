package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.adapters.WorkoutAdapter;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.databinding.FragmentHistoryBinding;

public class HistoryView extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentHistoryBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    )
    {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Workout> workouts = getWorkouts();

        ListView workoutListView = (ListView) root.findViewById(R.id.workoutHistList);

        // Set the ListView's adapter to our custom adapter!
        workoutListView.setAdapter(new WorkoutAdapter(this.getContext(), workouts));

        // Add an onClick listener just for and example!
        workoutListView.setOnItemClickListener((parent, view, position, id) -> {
            Workout clickedWorkout = (Workout) workoutListView.getItemAtPosition(position);
            Log.i("HistoryView", String.format("We clicked workout id: %d name: %s", clickedWorkout.workoutId, clickedWorkout.name));
            Navigation.findNavController(view).navigate(R.id.navigation_past_workout);
        });

        Spinner categoryDropDown = root.findViewById(R.id.categoryDropDown);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryDropDown.setAdapter(categoryAdapter);
        categoryDropDown.setOnItemSelectedListener(this);

        Spinner equipmentDropDown = root.findViewById(R.id.equipmentDropDown);
        ArrayAdapter<CharSequence> equipmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipment, android.R.layout.simple_spinner_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentDropDown.setAdapter(equipmentAdapter);
        equipmentDropDown.setOnItemSelectedListener(this);

        ArrayList<Movement> movements = getMovements();

        ListView movementListView = (ListView) root.findViewById(R.id.movementHistList);

        movementListView.setAdapter(new MovementAdapter(this.getContext(), movements));

        movementListView.setOnItemClickListener((parent, view, position, id) -> {
            Movement clickedMovement = (Movement) movementListView.getItemAtPosition(position);
            Log.i("HistoryView", String.format("We clicked workout id: %d name: %s", clickedMovement.movementId, clickedMovement.name));
            Navigation.findNavController(view).navigate(R.id.navigation_movement_history);
        });

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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

        return workouts;
    }

    private ArrayList<Movement> getMovements() {
        ArrayList<Movement> movements = new ArrayList<>();

        Movement m = new Movement();
        m.movementId = 1;
        m.name = "Squats";
        movements.add(m);

        Movement m1 = new Movement();
        m1.movementId = 2;
        m1.name = "Lunges";
        movements.add(m1);

        return movements;
    }
}