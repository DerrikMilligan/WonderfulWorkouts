package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutHistoryWithWorkoutMovementHistory {
    @Embedded
    public WorkoutHistory workoutHistory;

    @Relation(
        parentColumn = "workoutHistoryId",
        entityColumn = "workoutHistoryId",
        entity = WorkoutMovementHistory.class
    )
    public List<WorkoutMovementHistoryWithMovement> movementHistory;
}
