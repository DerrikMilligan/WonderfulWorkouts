package wonderful.workouts.presenters;

import android.content.Context;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.WorkoutDao;

public class WorkoutPresenter {
    private final WorkoutDao workoutDao;

    // Implement the singleton pattern
    private static WorkoutPresenter INSTANCE = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private WorkoutPresenter(AppDatabase db) {
        workoutDao = db.getWorkoutDao();
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
}
