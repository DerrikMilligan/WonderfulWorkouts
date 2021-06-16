package wonderful.workouts.database.joiners;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.entities.Workout;

public class UserWithWorkouts {
    @Embedded
    public User user;

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    public List<Workout> workouts;
}