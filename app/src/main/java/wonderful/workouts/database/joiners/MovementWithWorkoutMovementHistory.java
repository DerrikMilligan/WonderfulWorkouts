package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class MovementWithWorkoutMovementHistory {
    @Embedded
    public Movement movement;

    @Relation(
        parentColumn = "movementId",
        entityColumn = "movementId",
        entity = WorkoutMovementHistory.class
    )
    public List<WorkoutMovementHistory> workoutMovementHistories;
}
