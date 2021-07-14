package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutHistoryWithMovements {
    @Embedded
    public WorkoutHistory workoutHistory;

    @Relation(
        parentColumn = "workoutHistoryId",
        entityColumn = "movementId",
        entity = Movement.class
    )
    public List<MovementWithWorkoutMovementHistory> movementHistory;
}
