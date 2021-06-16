package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutMovementHistoryWithMovements {
    @Embedded
    public WorkoutMovementHistory workoutHistory;

    @Relation(
        parentColumn = "id",
        entityColumn = "movementId"
    )
    public List<WorkoutMovementHistory> movements;
}