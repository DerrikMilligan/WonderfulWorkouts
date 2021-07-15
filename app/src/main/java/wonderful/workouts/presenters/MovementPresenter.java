package wonderful.workouts.presenters;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import wonderful.workouts.database.joiners.WorkoutWithHistory;

public class MovementPresenter {
    private final MovementDao movementDao;
    private final WorkoutDao workoutDao;
    private final WorkoutHistoryDao workoutHistoryDao;
    private final WorkoutMovementHistoryDao movementHistoryDao;

    // Implement the singleton pattern
    private static MovementPresenter INSTANCE = null;

    private static Movement currentMovement = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private MovementPresenter(AppDatabase db) {
        movementDao = db.getMovementDao();
        workoutDao = db.getWorkoutDao();
        workoutHistoryDao = db.getWorkoutHistoryDao();
        movementHistoryDao = db.getWorkoutMovementHistoryDao();
    }

    // Here's the way we get our singleton instance for use
    public static MovementPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            // Yeah the synchronized block ensures that when we create the database that can only ever be done once and
            // to wait until its done if we happen to have race conditions. Read more here: https://www.baeldung.com/java-synchronized
            synchronized (UserPresenter.class) {
                // get the database for the singleton
                AppDatabase db = AppDatabase.getInstance(context);

                // Create the singleton if it doesn't exist
                INSTANCE = new MovementPresenter(db);
            }
        }

        return INSTANCE;
    }

    // ------------------------------------------------------------------------
    // Public methods
    // ------------------------------------------------------------------------

    /**
     * setCurrentMovement
     *
     * Set's a current movement in the singleton to track which movement is being viewed
     *
     * @param movement The workout we're storing
     */
    public void setCurrentMovement(Movement movement) { currentMovement = movement; }

    /**
     * getCurrentMovement
     *
     * Gets the current movement
     *
     * @return Movement
     */
    public Movement getCurrentMovement() { return currentMovement; }

    /**
     * getUserMovement
     *
     * Gets all the movements for a user and their workouts
     *
     * @return List<Movement>
     */
    public List<Movement> getUserMovements(User user) {
        return movementDao.getUserMovements(user.userId);
    }

    /**
     * getCategoryList
     *
     * Gets all categories for a given user
     *
     * @param user
     *
     * @return List<String>
     */
    public List<String> getCategoryList(User user) {
        return movementDao.getCategoryList(user.userId);
    }

    /**
     * updateMovement
     *
     * Updates a movement
     *
     * @param movement
     */
    public void updateMovement(Movement movement) {
        movementDao.update(movement);
    }

    /**
     * getEquipmentList
     *
     * Gets all equipment for a given user
     *
     * @param user
     *
     * @return List<String>
     */
    public List<String> getEquipmentList(User user) {
        return movementDao.getEquipmentList(user.userId);
    }

    public List<Movement> getCategoryMovements(User user, String category) {
        return movementDao.getCategoryMovements(user.userId, category);
    }

    public List<Movement> getEquipmentMovements(User user, String equipment) {
        return movementDao.getEquipmentMovements(user.userId, equipment);
    }

    public Movement lookupOrCreateMovement(String movementName, String type, String category, String equipment) {
        return movementDao.lookupOrCreateMovement(movementName, type, category, equipment);
    }
  
    /**
     * getCategoryMovements
     *
     * Gets all movements for a given category
     *
     * @param category
     *
     * @return List<Movement>
     */
    public List<Movement> getCategoryMovements(int userId, String category) {
        return movementDao.getCategoryMovements(userId, category);
    }

    public Map<WorkoutHistory, List<WorkoutMovementHistory>> getMovementHistory(Movement movement) {
        Map<WorkoutHistory, List<WorkoutMovementHistory>> histories = new HashMap<>();

        List<WorkoutMovementHistory> movements = movementHistoryDao.lookupMovementHistoriesWithMovementId(movement.movementId);

        for (WorkoutMovementHistory wmh : movements) {
            WorkoutHistory workoutHistory = workoutHistoryDao.lookupWorkoutHistory(wmh.workoutHistoryId);

            // Lookup the array
            List<WorkoutMovementHistory> sets = null;

            for (Map.Entry<WorkoutHistory, List<WorkoutMovementHistory>> entry : histories.entrySet()) {
                if (entry.getKey().workoutHistoryId == workoutHistory.workoutHistoryId) {
                    sets = entry.getValue();
                    break;
                }
            }

            // If we don't have an array list ready create one and add the key to the hash map
            if (sets == null) {
                Log.i("MovementPresenter", String.format("Adding key for workoutHistoryId: %d", workoutHistory.workoutHistoryId));
                histories.put(workoutHistory, new ArrayList<>());
                for (Map.Entry<WorkoutHistory, List<WorkoutMovementHistory>> entry : histories.entrySet()) {
                    if (entry.getKey().workoutHistoryId == workoutHistory.workoutHistoryId) {
                        sets = entry.getValue();
                        break;
                    }
                }
            }

            // Add the set to it
            Log.i("MovementPresenter", "Adding movement to list");
            sets.add(wmh);
        }

        return histories;
    }

    /**
     * createNewMovement
     *
     * Creates a movement with the given information
     *
     * @param name
     * @param type
     * @param category
     * @param equipment
     *
     * @return Movement
     */
    public Movement createNewMovement(String name, String type, String category, String equipment) {
        Movement m = new Movement();

        m.name      = name;
        m.type      = type;
        m.category  = category;
        m.equipment = equipment;

        m.movementId = (int) movementDao.insert(m);

        return m;
    }
}
