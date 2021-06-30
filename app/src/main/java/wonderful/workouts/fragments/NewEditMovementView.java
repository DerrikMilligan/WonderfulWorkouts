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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import wonderful.workouts.R;
import wonderful.workouts.databinding.FragmentNewEditMovementBinding;

public class NewEditMovementView extends Fragment implements AdapterView.OnItemSelectedListener {
    private wonderful.workouts.databinding.FragmentNewEditMovementBinding binding;

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {

        binding = wonderful.workouts.databinding.FragmentNewEditMovementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner categoryList = root.findViewById(R.id.categoryList);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categoryAdapter);
        categoryList.setOnItemSelectedListener(this);

        Spinner equipmentList = root.findViewById(R.id.equipmentList);
        ArrayAdapter<CharSequence> equipmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipment, android.R.layout.simple_spinner_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentList.setAdapter(equipmentAdapter);
        equipmentList.setOnItemSelectedListener(this);


        Button addMovementBtn = root.findViewById(R.id.addMovementBtn);
        addMovementBtn.setOnClickListener(view -> {

            EditText nameInput = root.findViewById(R.id.nameInput);

            Log.i("NewEditMovementView", String.format("Create movement - Name: %s ", nameInput.getText()));
        });

        return root;
    }

    // public void onRadioButtonClicked(View view) {
    //     // Is the button now checked?
    //     boolean checked = ((RadioButton) view).isChecked();
    //
    //     // Check which radio button was clicked
    //     switch(view.getId()) {
    //         case R.id.timedRadio:
    //             if (checked)
    //                 Log.i("NewEditMovementView", String.format("Timed radio button selected"));
    //                 break;
    //         case R.id.repsRadio:
    //             if (checked)
    //                 Log.i("NewEditMovementView", String.format("Reps radio button selected"));
    //                 break;
    //         case R.id.weightRepsRadio:
    //             if (checked)
    //                 Log.i("NewEditMovementView", String.format("Weight/reps radio button selected"));
    //             break;
    //     }
    // }

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

}