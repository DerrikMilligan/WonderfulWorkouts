package wonderful.workouts.presenters;

import android.content.Context;

import java.util.List;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.MovementDao;
import wonderful.workouts.database.daos.WorkoutDao;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.User;

public class MovementPresenter {
    private final MovementDao movementDao;
    private final WorkoutDao workoutDao;

    // Implement the singleton pattern
    private static MovementPresenter INSTANCE = null;

    private static Movement currentMovement = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private MovementPresenter(AppDatabase db) {
        movementDao = db.getMovementDao();
        workoutDao = db.getWorkoutDao();
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

    public List<String> getCategoryList(User user) {
        return movementDao.getCategoryList(user.userId);
    }

    public List<String> getEquipmentList(User user) {
        return movementDao.getEquipmentList(user.userId);
    }

    public List<Movement> getCategoryMovements(String category) {
        return movementDao.getCategoryMovements(category);
    }

}
