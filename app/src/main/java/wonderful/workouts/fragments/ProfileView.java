package wonderful.workouts.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wonderful.workouts.dialogs.DialogHelper;
import wonderful.workouts.dialogs.MeasurementDialog;
import wonderful.workouts.R;
import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.databinding.FragmentProfileBinding;
import wonderful.workouts.presenters.UserPresenter;

//Tried to pass new weight from dialog, but it says MainActivity needs to implement MeasurementDialogListener
// implements MeasurementDialog.MeasurementDialogListener

public class ProfileView extends Fragment {
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("L/d/u");

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

    private View root = null;

    private Map<ImageButton, TextView> buttonMap = null;

    LocalDate date = LocalDate.now();

    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        root = binding.getRoot();

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

        setupHashMap();

        for (Map.Entry<ImageButton, TextView> item : buttonMap.entrySet()) {
            ImageButton button = item.getKey();
            TextView text = item.getValue();

            if (button == null || text == null) {
                Log.i("ProfileView", "Skipping button!");
                continue;
            }

            button.setOnClickListener(v -> openDialog(text));
        }

        updateMeasurementDisplay();

        Button signOutBtn = (Button) root.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(view -> {
            new Thread(() -> UserPresenter.getInstance(requireContext()).setCurrentUser(null)).start();

            Navigation.findNavController(view).navigate(R.id.navigation_login_page);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupHashMap() {
        buttonMap = new HashMap<>();

        buttonMap.put(root.findViewById(R.id.addWeightBtn), weight);
        buttonMap.put(root.findViewById(R.id.addBicepsBtn), biceps);
        buttonMap.put(root.findViewById(R.id.addChestBtn), chest);
        buttonMap.put(root.findViewById(R.id.addThighsBtn), thighs);
        buttonMap.put(root.findViewById(R.id.addWaistBtn), waist);
        buttonMap.put(root.findViewById(R.id.addHipsBtn), hips);
        buttonMap.put(root.findViewById(R.id.addNeckBtn), neck);
        buttonMap.put(root.findViewById(R.id.addCalvesBtn), calves);
    }

    @SuppressLint("SetTextI18n")
    public void updateMeasurementDisplay() {
        new Thread(() -> {
            UserPresenter userPresenter = UserPresenter.getInstance(requireContext());

            List<Measurement> measurements = userPresenter.getMeasurements(userPresenter.getCurrentUser());

            requireActivity().runOnUiThread(() -> {
                for (Measurement measurement : measurements) {
                    Log.i("Profile", String.format("Measurement: %s Value: %.2f", measurement.type, measurement.value));

                    switch (measurement.type) {
                        case "weight":
                            Log.i("Profile", String.format("Updating weight to: %.2f", measurement.value));
                            weight.setText(measurement.value + "lbs");
                            if (measurement.lastUpdated != null) {
                                weightDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                weightDate.setText("");
                            }
                            break;

                        case "biceps":
                            Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
                            biceps.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                bicepsDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                bicepsDate.setText("");
                            }

                            break;

                        case "chest":
                            Log.i("Profile", String.format("Updating chest to: %.2f", measurement.value));
                            chest.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                chestDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                chestDate.setText("");
                            }

                            break;

                        case "thighs":
                            Log.i("Profile", String.format("Updating thighs to: %.2f", measurement.value));
                            thighs.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                thighsDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                thighsDate.setText("");
                            }

                            break;

                        case "waist":
                            Log.i("Profile", String.format("Updating waist to: %.2f", measurement.value));
                            waist.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                waistDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                waistDate.setText("");
                            }

                            break;

                        case "hips":
                            Log.i("Profile", String.format("Updating hips to: %.2f", measurement.value));
                            hips.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                hipsDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                hipsDate.setText("");
                            }

                            break;

                        case "neck":
                            Log.i("Profile", String.format("Updating neck to: %.2f", measurement.value));
                            neck.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                neckDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                neckDate.setText("");
                            }

                            break;

                        case "calves":
                            Log.i("Profile", String.format("Updating calves to: %.2f", measurement.value));
                            calves.setText(measurement.value + "\"");
                            if (measurement.lastUpdated != null) {
                                calvesDate.setText(measurement.lastUpdated.format(dateFormat));
                            } else {
                                calvesDate.setText("");
                            }

                            break;
                        default:
                            break;
                    }
                }
            });
        }).start();
    }

    // Takes a text view and gets the measurement type for updating in the database
    private String getViewMeasurementType(TextView view) {
        int viewId = view.getId();

        if (viewId == weight.getId()) { return Measurement.weight; }
        if (viewId == biceps.getId()) { return Measurement.biceps; }
        if (viewId == chest .getId()) { return Measurement.chest;  }
        if (viewId == thighs.getId()) { return Measurement.thighs; }
        if (viewId == waist .getId()) { return Measurement.waist;  }
        if (viewId == hips  .getId()) { return Measurement.hips;   }
        if (viewId == neck  .getId()) { return Measurement.neck;   }
        if (viewId == calves.getId()) { return Measurement.calves; }

        return null;
    }

    public void openDialog(TextView textView) {
        if (textView != null) {
            MeasurementDialog measurementDialog = new MeasurementDialog(getViewMeasurementType(textView), (value) -> {
                new Thread(() -> {
                    UserPresenter userPresenter = UserPresenter.getInstance(requireContext());

                    try {
                        float floatValue = Float.parseFloat(value);
                        userPresenter.updateMeasurement(userPresenter.getCurrentUser(), getViewMeasurementType(textView), floatValue);
                        updateMeasurementDisplay();

                    } catch (NumberFormatException exception) {
                        DialogHelper.displayAlertFromFragmentThread(
                            requireActivity(),
                            "Whoops!",
                            "Looks like you didn't input a valid number! Try again.",
                            (dialog, which) -> {}
                        );
                    }

                }).start();
            });
            measurementDialog.show(getChildFragmentManager(), "measurement_dialog");
        }
    }

    // @Override
    // public void applyText(String newWeight) {
    //     weight.setText(newWeight);
    // }
}