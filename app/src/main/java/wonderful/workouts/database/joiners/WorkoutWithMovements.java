package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutMovementCrossRef;

public class WorkoutWithMovements {
    @Embedded
    public Workout workout;

    @Relation(
        parentColumn = "workoutId",
        entityColumn = "movementId",
        associateBy = @Junction(WorkoutMovementCrossRef.class)
    )
    public List<Movement> movements;
}