package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutMovementHistoryWithMovements {
    @Embedded
    public WorkoutMovementHistory workoutMovementHistory;

    @Relation(
        parentColumn = "workoutMovementHistoryId",
        entityColumn = "movementId",
        entity = Movement.class
    )
    public List<Movement> movements;
}