package wonderful.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.database.entities.Measurement;

public class Profile extends AppCompatActivity {

    private TextView biceps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        biceps = (TextView) findViewById(R.id.biceps);

        updateMeasurementDisplay();
    }

    public void updateMeasurementDisplay() {
        new Thread(() -> {
            // List<Measurement> measurements = presenter.getCurrentMeasurements();
            List<Measurement> measurements = new ArrayList<>();

            Measurement m = new Measurement();
            m.type = "bicep";
            m.value = 5.5f;

            Measurement m1 = new Measurement();
            m1.type = "chest";
            m1.value = 5.5f;

            Measurement m2 = new Measurement();
            m2.type = "weight";
            m2.value = 5.5f;

            measurements.add(m);
            measurements.add(m1);
            measurements.add(m2);

            for (Measurement measurement : measurements) {
                Log.i("Profile", String.format("Measurement: %s Value: %.2f", measurement.type, measurement.value));

                switch (measurement.type) {
                    case "bicep":
                        Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
                        biceps.setText(String.valueOf(measurement.value) + "\"");
                        break;
                    default:
                        break;
                }
            }
        }).start();
    }
}