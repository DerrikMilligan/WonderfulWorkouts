package wonderful.workouts.presenters;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.database.joiners.WorkoutWithMovements;

public class WorkoutPresenter {
    private WorkoutDao workoutDao = null;
    private MovementDao movementDao = null;
    private WorkoutHistoryDao workoutHistoryDao = null;
    private WorkoutMovementHistoryDao workoutMovementHistoryDao = null;

    // Implement the singleton pattern
    private static WorkoutPresenter INSTANCE = null;

    // The state holder for the current workout we're viewing
    private static Workout currentWorkout = null;

    // The state holder for the active workout
    private static WorkoutHistory activeWorkout = null;

    // ------------------------------------------------------------------------
    // Static Methods & Constructor
    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------
    // Private methods
    // ------------------------------------------------------------------------

    /**
     * addSetToActiveWorkout
     *
     * Adds a set for a given workout and movement. This requires a workoutHistoryId!
     * This is an internal helper function that takes all three types of set measurements
     *
     * @param workoutHistoryId The workout history we're adding the set to
     * @param movementId The movement we're adding the info for
     * @param reps Number of reps
     * @param weight Weight used for reps
     * @param duration Time taken for movement
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addSetToActiveWorkout(int workoutHistoryId, int movementId, float reps, float weight, float duration) {
        WorkoutHistory         wh  = workoutHistoryDao.lookupWorkoutHistory(workoutHistoryId);
        Movement               m   = movementDao.lookupMovement(movementId);
        WorkoutMovementHistory set = new WorkoutMovementHistory();

        // If we weren't given valid movement id's or workoutHistory id's then return null!
        if (wh == null || m == null) { return null; }

        // Set the information
        set.workoutHistoryId = wh.workoutHistoryId;
        set.movementId       = m.movementId;
        set.reps             = reps;
        set.weight           = weight;
        set.duration         = duration;

        // Insert into the DB and get the ID back
        set.workoutMovementHistoryId = (int) workoutMovementHistoryDao.insert(set);

        // Return the set
        return set;
    }

    // ------------------------------------------------------------------------
    // Public methods
    // ------------------------------------------------------------------------

    /**
     * setCurrentWorkout
     *
     * Set's a current workout in the singleton to track which workout is being viewed
     *
     * @param workout The workout we're storing
     */
    public void setCurrentWorkout(Workout workout) { currentWorkout = workout; }

    /**
     * getCurrentWorkout
     *
     * Gets the current workout
     *
     * @return Workout
     */
    public Workout getCurrentWorkout() { return currentWorkout; }

    /**
     * setActiveWorkout
     *
     * Set's an active workout in the singleton to track which workout is being viewed
     *
     * @param workoutHistory The workout we're storing
     */
    public void setActiveWorkout(WorkoutHistory workoutHistory) { activeWorkout = workoutHistory; }

    /**
     * getActiveWorkout
     *
     * Gets the active workout
     *
     * @return WorkoutHistory
     */
    public WorkoutHistory getActiveWorkout() { return activeWorkout; }

    /**
     * getWorkout
     *
     * Returns a workout from an id
     *
     * @param workoutId The id of the workout to get
     *
     * @return Workout
     */
    public Workout getWorkout(int workoutId) {
        return workoutDao.lookupWorkout(workoutId);
    }

    /**
     * getWorkoutsForUser
     *
     * Returns all the workouts for a given user
     *
     * @param user The user to get workouts for
     *
     * @return List<Workout>
     */
    public List<Workout> getWorkoutsForUser(User user) {
        return workoutDao.getWorkoutsForUser(user.userId);
    }

    /**
     * addWorkout
     *
     * Adds a new workout for a given user
     *
     * @param user The user adding the workout
     * @param name The new workout name
     *
     * @return Workout
     */
    public Workout addWorkout(User user, String name) {
        Workout newWorkout = new Workout();

        newWorkout.userId    = user.userId;
        newWorkout.name      = name;
        newWorkout.workoutId = (int) workoutDao.insert(newWorkout);

        return newWorkout;
    }

    /**
     * updateWorkout
     *
     * Updates a workout with a new name
     *
     * @param workout The workout we're updating
     * @param newName The new workout name
     *
     * @return Workout
     */
    public Workout updateWorkoutName(Workout workout, String newName) {
        workout.name = newName;
        workoutDao.update(workout);

        return workout;
    }

    /**
     * startWorkout
     *
     * Starts a workout!
     *
     * @param workout The workout id we're starting
     *
     * @return WorkoutHistory
     */
    public WorkoutHistory startWorkout(Workout workout) {
        WorkoutHistory history = new WorkoutHistory();

        history.workoutId = workout.workoutId;
        history.startTime = LocalDateTime.now();
        history.workoutHistoryId = (int) workoutHistoryDao.insert(history);

        return history;
    }

    /**
     * finishWorkout
     *
     * Finishes a workout!
     *
     * @param workoutHistory The workout history we're stopping
     *
     * @return WorkoutHistory
     */
    public WorkoutHistory finishWorkout(WorkoutHistory workoutHistory) {
        workoutHistory.endTime = LocalDateTime.now();

        workoutHistoryDao.update(workoutHistory);

        return workoutHistory;
    }

    /**
     * addRepSetToActiveWorkout
     *
     * Adds a set for a given workout and movement that uses reps.
     *
     * NOTE: This requires a workoutHistoryId!
     *
     * @param workoutHistory The workout history we're adding the set to
     * @param movement The movement we're adding the info for
     * @param reps Number of reps
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addRepSetToActiveWorkout(WorkoutHistory workoutHistory, Movement movement, float reps) {
        return addSetToActiveWorkout(workoutHistory.workoutHistoryId, movement.movementId, reps, 0.0f, 0.0f);
    }

    /**
     * addRepAndWeightSetToActiveWorkout
     *
     * Adds a set for a given workout and movement that uses reps and weights.
     *
     * NOTE: This requires a workoutHistoryId!
     *
     * @param workoutHistory The workout history we're adding the set to
     * @param movement The movement we're adding the info for
     * @param reps Number of reps
     * @param weight The amount of weight for each rep
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addRepAndWeightSetToActiveWorkout(WorkoutHistory workoutHistory, Movement movement, float reps, float weight) {
        return addSetToActiveWorkout(workoutHistory.workoutHistoryId, movement.movementId, reps, weight, 0.0f);
    }

    /**
     * addTimedSetToActiveWorkout
     *
     * Adds a set for a given workout and movement that uses reps.
     *
     * NOTE: This requires a workoutHistoryId!
     *
     * @param workoutHistory The workout history we're adding the set to
     * @param movement The movement we're adding the info for
     * @param duration Number of reps
     *
     * @return WorkoutMovementHistory
     */
    public WorkoutMovementHistory addTimedSetToActiveWorkout(WorkoutHistory workoutHistory, Movement movement, float duration) {
        return addSetToActiveWorkout(workoutHistory.workoutHistoryId, movement.movementId, 0.0f, 0.0f, duration);
    }

    /**
     * getWorkoutMovements
     *
     * Gets all the movements for a given workout
     *
     * @param workout The workout to lookup movements for
     *
     * @return List<Movement>
     */
    public List<Movement> getWorkoutMovements(Workout workout) {
        WorkoutWithMovements wwm = workoutDao.getWorkoutMovements(workout.workoutId);

        if (wwm == null) { return null; }

        return wwm.movements;
    }

    /**
     * getWorkoutHistories
     *
     * Gets all the histories for a given workout
     *
     * @param workout The workout to lookup histories for
     *
     * @return List<WorkoutHistory>
     */
    public List<WorkoutHistory> getWorkoutHistories(Workout workout) {
        WorkoutWithHistories wwh = workoutDao.getWorkoutHistories(workout.workoutId);

        if (wwh == null) { return null; }

        return wwh.pastWorkouts;
    }

    /**
     * getWorkoutHistory
     *
     * Gets the movements and sets for a single workout history
     *
     * @param workoutHistory The workout history to get all movements and sets for
     *
     * @return WorkoutHistoryWithMovements
     */
    public WorkoutHistoryWithMovements getWorkoutHistory(WorkoutHistory workoutHistory) {
        WorkoutHistoryWithMovements history = new WorkoutHistoryWithMovements();

        history.workoutHistory = workoutHistory;
        history.movementHistory = new ArrayList<>();

        List<Movement> movements = movementDao.getWorkoutMovements(workoutHistory.workoutId);

        for (Movement m : movements) {
            MovementWithWorkoutMovementHistory movementWithHistory = new MovementWithWorkoutMovementHistory();

            movementWithHistory.movement = m;
            movementWithHistory.workoutMovementHistories = workoutMovementHistoryDao.lookupMovementHistoriesWithMovementId(m.movementId);

            history.movementHistory.add(movementWithHistory);
        }

        return history;
    }

    /**
     * getAllPastWorkoutHistories
     *
     * Gets the movements and sets for all workout histories for a given workout.
     *
     * @param workout The workout history to get all movements and sets for
     *
     * @return WorkoutWithHistory
     */
    public WorkoutWithHistory getAllPastWorkoutHistories(Workout workout) {
        WorkoutWithHistory history = new WorkoutWithHistory();
        history.workout = workout;

        history.pastWorkouts = new ArrayList<>();

        // Get all the histories and we'll convert them into histories with movements
        List<WorkoutHistory> histories = workoutHistoryDao.lookupWorkoutHistories(workout.workoutId);

        for (WorkoutHistory h : histories) {
            history.pastWorkouts.add(getWorkoutHistory(h));
        }

        return history;
    }

}
