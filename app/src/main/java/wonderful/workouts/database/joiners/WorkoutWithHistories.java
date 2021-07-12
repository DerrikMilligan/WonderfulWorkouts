package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;

public class WorkoutWithHistories {
    @Embedded
    public Workout workout;

    @Relation(
        parentColumn = "workoutId",
        entityColumn = "workoutHistoryId",
        entity = WorkoutHistory.class
    )
    public List<WorkoutHistory> pastWorkouts;
}
