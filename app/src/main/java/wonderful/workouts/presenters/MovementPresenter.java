package wonderful.workouts.presenters;

import android.content.Context;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.MovementDao;

public class MovementPresenter {
    private final MovementDao movementDao;

    // Implement the singleton pattern
    private static MovementPresenter INSTANCE = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private MovementPresenter(AppDatabase db) {
        movementDao = db.getMovementDao();
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
}
