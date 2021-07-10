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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.databinding.FragmentNewEditMovementBinding;

public class NewEditMovementView extends Fragment {
    private wonderful.workouts.databinding.FragmentNewEditMovementBinding binding;

    public String[] categoryList = new String[] {"Legs", "Shoulders", "Chest", "Arms", "Cardio", "Back", "Abs"};
    public String[] equipmentList = new String[] {"None", "Single Dumbbell", "Dumbbells", "Barbell", "Band"};
    public String type;
    // public String selectedId;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {

        binding = wonderful.workouts.databinding.FragmentNewEditMovementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AutoCompleteTextView category = root.findViewById(R.id.categoryList);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
        category.setAdapter(categoryAdapter);

        AutoCompleteTextView equipment = root.findViewById(R.id.equipmentList);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, equipmentList);
        equipment.setAdapter(equipmentAdapter);

        RadioGroup typeGroup = root.findViewById(R.id.typeGroup);
        final String[] selectedId = new String[1];
        typeGroup.setOnCheckedChangeListener((typeGroup1, checkedId) -> {
            switch (checkedId) {
                case R.id.timedRadio:
                    selectedId[0] = "timed";
                    break;
                case R.id.repsRadio:
                    selectedId[0] = "reps";
                    break;
                case R.id.weightRepsRadio:
                    selectedId[0] = "weight/reps";
                    break;
            }
        });

        Button addMovementBtn = root.findViewById(R.id.addMovementBtn);
        addMovementBtn.setOnClickListener(view -> {
            EditText nameInput = root.findViewById(R.id.nameInput);
            int count = typeGroup.getChildCount();
            Log.i("NewEditMovementView", String.format("Create movement - Name: %s , Category: %s, Equipment: %s, Type: %s", nameInput.getText(), category.getText(), equipment.getText(), selectedId[0]));

            Navigation.findNavController(root).popBackStack();
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}