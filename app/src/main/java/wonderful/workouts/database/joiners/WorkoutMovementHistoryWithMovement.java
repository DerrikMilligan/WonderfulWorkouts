package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutMovementHistoryWithMovement {
    @Embedded
    public WorkoutMovementHistory workoutMovementHistory;

    @Relation(
        parentColumn = "movementId",
        entityColumn = "movementId",
        entity = Movement.class
    )
    public Movement movement;
}
