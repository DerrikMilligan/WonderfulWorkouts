package wonderful.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import wonderful.workouts.database.entities.Movement;


public class PastWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        updateMovementDisplay();
    }

    public void updateMovementDisplay() {
        new Thread(() -> {
            // List<Measurement> measurements = presenter.getCurrentMeasurements();
            List<Movement> movements = new ArrayList<>();


            Movement m = new Movement();
            m.name = "Leg Press";

            Movement m1 = new Movement();
            m1.name = "Squats";

            Movement m2 = new Movement();
            m2.name = "Calve Raises";

            movements.add(m);
            movements.add(m1);
            movements.add(m2);


            for (Movement movement : movements) {
                Log.i("PastWorkout", String.format(movement.name));

                // switch (measurement.type) {
                //     case "bicep":
                //         Log.i("Profile", String.format("Updating biceps to: %.2f", measurement.value));
                //         biceps.setText(String.valueOf(measurement.value) + "\"");
                //         break;
                //     default:
                //        break;
                //}
            }
        }).start();
    }
}