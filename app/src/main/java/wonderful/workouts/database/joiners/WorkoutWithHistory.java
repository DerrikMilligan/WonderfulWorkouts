package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Workout;

public class WorkoutWithHistory {
    @Embedded
    public Workout workout;

    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    public List<WorkoutHistoryWithWorkoutMovementHistories> pastWorkouts;
}
