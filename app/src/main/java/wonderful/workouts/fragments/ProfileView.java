package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.databinding.FragmentProfileBinding;

public class ProfileView extends Fragment {
    private FragmentProfileBinding binding;

    private TextView weight = null;
    private TextView weightDate = null;
    private TextView biceps = null;
    private TextView bicepsDate = null;
    private TextView chest = null;
    private TextView chestDate = null;
    private TextView thighs = null;
    private TextView thighsDate = null;
    private TextView waist = null;
    private TextView waistDate = null;
    private TextView hips = null;
    private TextView hipsDate = null;
    private TextView neck = null;
    private TextView neckDate = null;
    private TextView calves = null;
    private TextView calvesDate = null;

    LocalDate date = LocalDate.now();

    public View onCreateView(

        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        weight = (TextView) root.findViewById(R.id.weight);
        weightDate = (TextView) root.findViewById(R.id.weightDate);
        biceps = (TextView) root.findViewById(R.id.biceps);
        bicepsDate = (TextView) root.findViewById(R.id.bicepsDate);
        chest = (TextView) root.findViewById(R.id.chest);
        chestDate = (TextView) root.findViewById(R.id.chestDate);
        thighs = (TextView) root.findViewById(R.id.thighs);
        thighsDate = (TextView) root.findViewById(R.id.thighsDate);
        waist = (TextView) root.findViewById(R.id.waist);
        waistDate = (TextView) root.findViewById(R.id.waistDate);
        hips = (TextView) root.findViewById(R.id.hips);
        hipsDate = (TextView) root.findViewById(R.id.hipsDate);
        neck = (TextView) root.findViewById(R.id.neck);
        neckDate = (TextView) root.findViewById(R.id.neckDate);
        calves = (TextView) root.findViewById(R.id.calves);
        calvesDate = (TextView) root.findViewById(R.id.calvesDate);

        updateMeasurementDisplay();

        Button signOutBtn = (Button) root.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_login_page);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateMeasurementDisplay() {
        new Thread(() -> {
            // List<Measurement> measurements = presenter.getCurrentMeasurements();
            List<Measurement> measurements = new ArrayList<>();

            Measurement userWeight = new Measurement();
            userWeight.type = "weight";
            userWeight.value = 160.8f;

            Measurement userBiceps = new Measurement();
            userBiceps.type = "biceps";
            userBiceps.value = 15.5f;

            Measurement userChest = new Measurement();
            userChest.type = "chest";
            userChest.value = 30.5f;

            Measurement userThighs = new Measurement();
            userThighs.type = "thighs";
            userThighs.value = 25.5f;

            Measurement userWaist = new Measurement();
            userWaist.type = "waist";
            userWaist.value = 28.5f;

            Measurement userHips = new Measurement();
            userHips.type = "hips";
            userHips.value = 32.5f;

            Measurement userNeck = new Measurement();
            userNeck.type = "neck";
            userNeck.value = 15.5f;

            Measurement userCalves = new Measurement();
            userCalves.type = "calves";
            userCalves.value = 15.5f;

            measurements.add(userWeight);
            measurements.add(userBiceps);
            measurements.add(userChest);
            measurements.add(userThighs);
            measurements.add(userWaist);
            measurements.add(userHips);
            measurements.add(userNeck);
            measurements.add(userCalves);


            for (Measurement measurement : measurements) {
                Log.i("Profile", String.format("Measurement: %s Value: %.2f", measurement.type, measurement.value));

                switch (measurement.type) {
                    case "weight":
                        Log.i("Profile", String.format("Updating weight to: %.2f", measurement.value));
                        weight.setText(String.valueOf(measurement.value) + "lbs");
                        weightDate.setText("6/29/2021");
                        break;

                    case "biceps":
                        Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
                        biceps.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "chest":
                        Log.i("Profile", String.format("Updating chest to: %.2f", measurement.value));
                        chest.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "thighs":
                        Log.i("Profile", String.format("Updating thighs to: %.2f", measurement.value));
                        thighs.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "waist":
                        Log.i("Profile", String.format("Updating waist to: %.2f", measurement.value));
                        waist.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "hips":
                        Log.i("Profile", String.format("Updating hips to: %.2f", measurement.value));
                        hips.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "neck":
                        Log.i("Profile", String.format("Updating neck to: %.2f", measurement.value));
                        neck.setText(String.valueOf(measurement.value) + "\"");
                        break;

                    case "calves":
                        Log.i("Profile", String.format("Updating calves to: %.2f", measurement.value));
                        calves.setText(String.valueOf(measurement.value) + "\"");
                        break;
                    default:
                        break;
                }
            }
        }).start();
    }
}