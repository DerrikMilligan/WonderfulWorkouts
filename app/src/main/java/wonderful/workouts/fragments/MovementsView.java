package wonderful.workouts.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.adapters.MovementAdapter;
import wonderful.workouts.database.entities.Movement;
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

        // Select category spinner and set adapter
        Spinner categoryDropDown = root.findViewById(R.id.movementCategory);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryDropDown.setAdapter(categoryAdapter);
        // Set category spinner on item selected listener
        categoryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Returns category on click
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                Log.i("MovementsView", "Selected category: " + selectedItemText);
            }
            // Returns all when nothing selected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("MovementsView", "Showing all categories");
            }
        });

        // Select equipment spinner and set adapter
        Spinner equipmentDropDown = root.findViewById(R.id.movementEquipment);
        ArrayAdapter<CharSequence> equipmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipment, android.R.layout.simple_spinner_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentDropDown.setAdapter(equipmentAdapter);
        // Set equipment spinner on item selected listener
        equipmentDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Returns equipment on click
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                Log.i("MovementsView", "Selected equipment: " + selectedItemText);
            }
            // Returns all when nothing selected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("MovementsView", "Showing all equipment");
            }
        });

        // Get dummy data
        ArrayList<Movement> movements = getMovements();

        // Select list ListView
        ListView movementListView = (ListView) root.findViewById(R.id.movementList);

        // Set the movement ListView's adapter to our custom workout adapter
        movementListView.setAdapter(new MovementAdapter(this.getContext(), movements));

        // Add an onClick listener to movements
        movementListView.setOnItemClickListener((parent, view, position, id) -> {
            Movement clickedMovement = (Movement) movementListView.getItemAtPosition(position);
            Log.i("MovementsView", String.format("We clicked workout id: %d name: %s", clickedMovement.movementId, clickedMovement.name));
        });

        FloatingActionButton addMovementButton = root.findViewById(R.id.addMovementButton);
        addMovementButton.setOnClickListener(view -> {
            Log.i("MovementView", "Adding movement!");
            Navigation.findNavController(view).navigate(R.id.navigation_new_edit_movement_page);
        });

        return root;
    }

    // Movement dummy data
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}