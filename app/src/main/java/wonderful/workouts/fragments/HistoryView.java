package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import wonderful.workouts.R;
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
}