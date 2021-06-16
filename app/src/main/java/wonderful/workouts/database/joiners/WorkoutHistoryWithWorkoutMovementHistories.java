package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.WorkoutHistory;

public class WorkoutHistoryWithWorkoutMovementHistories {
    @Embedded
    public WorkoutHistory workoutHistory;

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutHistoryId"
    )
    public List<WorkoutMovementHistoryWithMovements> movementHistory;
}
