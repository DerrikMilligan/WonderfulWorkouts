package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutHistoryWithWorkoutMovementHistories {
    @Embedded
    public WorkoutHistory workoutHistory;

    @Relation(
        parentColumn = "workoutHistoryId",
        entityColumn = "workoutMovementHistoryId",
        entity = WorkoutMovementHistory.class
    )
    public List<WorkoutMovementHistoryWithMovements> movementHistory;
}
