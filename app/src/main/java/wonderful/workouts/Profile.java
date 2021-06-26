package wonderful.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.database.entities.Measurement;

public class Profile extends AppCompatActivity {

    private TextView weight = null;
    private TextView biceps = null;
    private TextView chest = null;
    private TextView thighs = null;
    private TextView waist = null;
    private TextView hips = null;
    private TextView neck = null;
    private TextView calves = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        weight = (TextView) findViewById(R.id.weight);
        biceps = (TextView) findViewById(R.id.biceps);
        chest = (TextView) findViewById(R.id.chest);
        thighs = (TextView) findViewById(R.id.thighs);
        waist = (TextView) findViewById(R.id.waist);
        hips = (TextView) findViewById(R.id.hips);
        neck = (TextView) findViewById(R.id.neck);
        calves = (TextView) findViewById(R.id.calves);

        updateMeasurementDisplay();

        Button signOutBtn = (Button) findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login view class?
                //Intent intent = new Intent(this, LoginView.class);
                //startActivity(intent);
            }
        });
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