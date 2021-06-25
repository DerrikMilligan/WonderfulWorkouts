package wonderful.workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class MovementHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_history);

        updateMovementHistoryDisplay();
    }

    public void updateMovementHistoryDisplay() {
        new Thread(() -> {
            // List<Measurement> measurements = presenter.getCurrentMeasurements();
            List<WorkoutMovementHistory> movementHistories = new ArrayList<>();


            WorkoutMovementHistory wmh = new WorkoutMovementHistory();
            wmh.weight = 300;
            wmh.reps = 10;

            WorkoutMovementHistory wmh1 = new WorkoutMovementHistory();
            wmh1.weight = 350;
            wmh1.reps = 8;

            WorkoutMovementHistory wmh2 = new WorkoutMovementHistory();
            wmh2.weight = 300;
            wmh2.reps = 12;

            WorkoutMovementHistory wmh3 = new WorkoutMovementHistory();
            wmh3.weight = 350;
            wmh3.reps = 10;

            movementHistories.add(wmh);
            movementHistories.add(wmh1);
            movementHistories.add(wmh2);
            movementHistories.add(wmh3);


            for (WorkoutMovementHistory mh : movementHistories) {
                Log.i("MovementHistory", String.format("Set: %.2f %.2f", mh.weight, mh.reps));

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