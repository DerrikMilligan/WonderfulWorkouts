package wonderful.workouts.presenters;

import android.content.Context;

import java.time.Duration;
import java.util.List;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.MovementDao;
import wonderful.workouts.database.daos.WorkoutDao;
import wonderful.workouts.database.daos.WorkoutHistoryDao;
import wonderful.workouts.database.daos.WorkoutMovementHistoryDao;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class WorkoutPresenter {
    private WorkoutDao workoutDao = null;
    private MovementDao movementDao = null;
    private WorkoutHistoryDao workoutHistoryDao = null;
    private WorkoutMovementHistoryDao workoutMovementHistoryDao = null;

    // Implement the singleton pattern
    private static WorkoutPresenter INSTANCE = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private WorkoutPresenter(AppDatabase db) {
        workoutDao = db.getWorkoutDao();
        movementDao = db.getMovementDao();
        workoutHistoryDao = db.getWorkoutHistoryDao();
        workoutMovementHistoryDao = db.getWorkoutMovementHistoryDao();
    }

    // Here's the way we get our singleton instance for use
    public static WorkoutPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            // Yeah the synchronized block ensures that when we create the database that can only ever be done once and
            // to wait until its done if we happen to have race conditions. Read more here: https://www.baeldung.com/java-synchronized
            synchronized (UserPresenter.class) {
                // get the database for the singleton
                AppDatabase db = AppDatabase.getInstance(context);

                // Create the singleton if it doesn't exist
                INSTANCE = new WorkoutPresenter(db);
            }
        }

        return INSTANCE;
    }

    // Public methods

    public List<Workout> getWorkoutsForUser(User user) {
        return workoutDao.getWorkoutsForUser(user.userId);
    }

    /**
     * addSetToWorkout
     *
     * Creates a new set for a given workout history and movement.
     * This overload is for reps only.
     *
     * @param workoutHistory The current workout
     * @param movement The movement we're adding a set for
     * @param reps the number of reps
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addSetToWorkout(WorkoutHistory workoutHistory, Movement movement, float reps) {
        WorkoutMovementHistory newSet = new WorkoutMovementHistory();

        newSet.workoutHistoryId = workoutHistory.workoutHistoryId;
        newSet.movementId = movement.movementId;
        newSet.reps = reps;

        workoutMovementHistoryDao.insert(newSet);

        return newSet;
    }

    /**
     * addSetToWorkout
     *
     * Creates a new set for a given workout history and movement.
     * This overload is for reps and weight.
     *
     * @param workoutHistory The current workout
     * @param movement The movement we're adding a set for
     * @param weight the weight
     * @param reps the number of reps
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addSetToWorkout(WorkoutHistory workoutHistory, Movement movement, float weight, float reps) {
        WorkoutMovementHistory newSet = new WorkoutMovementHistory();

        newSet.workoutHistoryId = workoutHistory.workoutHistoryId;
        newSet.movementId = movement.movementId;
        newSet.weight = weight;
        newSet.reps = reps;

        workoutMovementHistoryDao.insert(newSet);

        return newSet;
    }

    /**
     * addSetToWorkout
     *
     * Creates a new set for a given workout history and movement.
     * This overload is for sets only.
     *
     * @param workoutHistory The current workout
     * @param movement The movement we're adding a set for
     * @param duration how long the set went for
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addSetToWorkout(WorkoutHistory workoutHistory, Movement movement, Duration duration) {
        WorkoutMovementHistory newSet = new WorkoutMovementHistory();

        newSet.workoutHistoryId = workoutHistory.workoutHistoryId;
        newSet.movementId = movement.movementId;
        newSet.duration = duration.getSeconds();

        workoutMovementHistoryDao.insert(newSet);

        return newSet;
    }

}
