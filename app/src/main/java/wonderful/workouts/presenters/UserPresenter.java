package wonderful.workouts.presenters;

import android.content.Context;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.List;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.MeasurementDao;
import wonderful.workouts.database.daos.MovementDao;
import wonderful.workouts.database.daos.UserDao;
import wonderful.workouts.database.daos.WorkoutDao;
import wonderful.workouts.database.daos.WorkoutMovementCrossRefDao;
import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.joiners.UserWithWorkouts;

public class UserPresenter {
    private final UserDao                    userDao;
    private final MeasurementDao             measurementDao;
    private final MovementDao                movementDao;
    private final WorkoutDao                 workoutDao;
    private final WorkoutMovementCrossRefDao workoutMovementCrossRefDao;

    private static User currentUser = null;

    // Implement the singleton pattern
    private static UserPresenter INSTANCE = null;

    // Make the constructor private so we have to use getInstance to use the presenter
    private UserPresenter(AppDatabase db) {
        userDao                    = db.getUserDao();
        movementDao                = db.getMovementDao();
        workoutDao                 = db.getWorkoutDao();
        measurementDao             = db.getMeasurementDao();
        workoutMovementCrossRefDao = db.getWorkoutMovementCrossRefDao();
    }

    // Here's the way we get our singleton instance for use
    public static UserPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            // Yeah the synchronized block ensures that when we create the database that can only ever be done once and
            // to wait until its done if we happen to have race conditions. Read more here: https://www.baeldung.com/java-synchronized
            synchronized (UserPresenter.class) {
                // get the database for the singleton
                AppDatabase db = AppDatabase.getInstance(context);

                // Create the singleton if it doesn't exist
                INSTANCE = new UserPresenter(db);
            }
        }

        return INSTANCE;
    }

    // Public methods to get information from the database

    /**
     * setCurrentUser
     *
     * Set's a user in the singleton to track who's logged in
     *
     * @param user The user we're storing that's logged into the application
     */
    public void setCurrentUser(User user) { currentUser = user; }

    /**
     * getCurrentUser
     *
     * Gets the logged in user
     *
     * @return User
     */
    public User getCurrentUser() { return currentUser; }

    /**
     * getUser
     *
     * Gets a user from their info
     *
     * @param username
     * @param password
     *
     * @return User
     */
    public User getUser(String username, String password) { return userDao.getUser(username, password); }

    /**
     * getUser
     *
     * Gets a user from their id
     *
     * @param userId
     *
     * @return User
     */
    public User getUser(int userId) { return userDao.getUser(userId); }

    /**
     * usernameExists
     *
     * Will return true if the username already exists and false if it doesn't exist
     *
     * @param username The username you want to search against
     *
     * @return boolean
     */
    public boolean usernameExists(String username) {
        User user = userDao.getFromUsername(username);

        // If the user is null then we failed to find one
        return user != null;
    }

    /**
     * validatePassword
     *
     * Will return true if the username already exists and false if it doesn't exist
     *
     * @param username The username you want to search against
     *
     * @return boolean
     */
    public boolean passwordIsValid(String username, String password) {
        User user = userDao.getUser(username, password);

        // If the user is null then we failed to find one
        return user != null;
    }

    /**
     * createNewUser
     *
     * Will create a new user with a given username and password
     *
     * @param username The username for the new account
     * @param password The password for the new account
     * @param generateDefaults Whether or not to generate default workouts
     *
     * @return User
     */
    public User createNewUser(String username, String password, boolean generateDefaults) {
        User newUser = new User();

        newUser.username = username;
        newUser.password = password;
        newUser.userId = (int) userDao.insert(newUser);

        if (generateDefaults) {
            this.createDefaultWorkouts(newUser);
            this.createUserMeasurements(newUser);
        }

        return newUser;
    }

    /**
     * getMeasurements
     *
     * Get's all the current measurements for a given user
     *
     * @param user The user we're updating the measurement for
     *
     * @return List<Measurement>
     */
    public List<Measurement> getMeasurements(User user) {
        return measurementDao.getUserMeasurements(user.userId);
    }

    /**
     * updateMeasurement
     *
     * Updates a measurements for a user
     *
     * @param user The user we're updating the measurement for
     * @param measurementName Make sure to use the static tpyes on Measurment
     * @param value The new measurment value
     *
     * @return Measurement
     */
    public Measurement updateMeasurement(User user, String measurementName, float value) {
        Measurement measurement = measurementDao.lookupMeasurement(user.userId, measurementName);

        if (measurement == null) {
            Log.e("UserPresenter", "We have a big problem chief!");
            return null;
        }

        measurement.value = value;
        measurement.lastUpdated = LocalDateTime.now();

        measurementDao.update(measurement);

        return measurement;
    }

    /**
     * createUserMeasurements
     *
     * Creates all the initial measurements for a user
     *
     * @param user The user we're creating the initial measurements for
     */
    public void createUserMeasurements(User user) {
        Measurement weight = new Measurement();
        weight.type   = Measurement.weight;
        weight.userId = user.userId;
        weight.value  = 0.0f;
        measurementDao.insert(weight);

        Measurement biceps = new Measurement();
        biceps.type   = Measurement.biceps;
        biceps.userId = user.userId;
        biceps.value  = 0.0f;
        measurementDao.insert(biceps);

        Measurement chest = new Measurement();
        chest.type    = Measurement.chest;
        chest.userId  = user.userId;
        chest.value   = 0.0f;
        measurementDao.insert(chest);

        Measurement thighs = new Measurement();
        thighs.type   = Measurement.thighs;
        thighs.userId = user.userId;
        thighs.value  = 0.0f;
        measurementDao.insert(thighs);

        Measurement waist = new Measurement();
        waist.type    = Measurement.waist;
        waist.userId  = user.userId;
        waist.value   = 0.0f;
        measurementDao.insert(waist);

        Measurement hips = new Measurement();
        hips.type     = Measurement.hips;
        hips.userId   = user.userId;
        hips.value    = 0.0f;
        measurementDao.insert(hips);

        Measurement neck = new Measurement();
        neck.type     = Measurement.neck;
        neck.userId   = user.userId;
        neck.value    = 0.0f;
        measurementDao.insert(neck);

        Measurement calves = new Measurement();
        calves.type   = Measurement.calves;
        calves.userId = user.userId;
        calves.value  = 0.0f;
        measurementDao.insert(calves);
    }

    /**
     * createDefaultWorkouts
     *
     * Creates all the default workouts and movements for a user
     *
     * @param user The user we're creating the workouts for
     */
    public void createDefaultWorkouts(User user) {
        // Chest/Tricep day
        Movement bench            = movementDao.lookupOrCreateMovement("Benchpress",        Movement.RepsAndWeight, "Chest", "Barbell");
        Movement pushups          = movementDao.lookupOrCreateMovement("Pushups",           Movement.Reps,          "Chest", "Body weight");
        Movement flys             = movementDao.lookupOrCreateMovement("Flys",              Movement.RepsAndWeight, "Chest", "Dumbbells");
        Movement tricepExtensions = movementDao.lookupOrCreateMovement("Tricep Extensions", Movement.RepsAndWeight, "Triceps", "Dumbbells");
        Movement dips             = movementDao.lookupOrCreateMovement("Dips",              Movement.RepsAndWeight, "Triceps", "Body Weight");

        // create workout and add movements
        Workout chestAndTriceps = workoutDao.lookupOrCreateWorkout(user.userId, "Chest & Triceps");
        workoutMovementCrossRefDao.addMovementToWorkout(chestAndTriceps, bench);
        workoutMovementCrossRefDao.addMovementToWorkout(chestAndTriceps, pushups);
        workoutMovementCrossRefDao.addMovementToWorkout(chestAndTriceps, flys);
        workoutMovementCrossRefDao.addMovementToWorkout(chestAndTriceps, tricepExtensions);
        workoutMovementCrossRefDao.addMovementToWorkout(chestAndTriceps, dips);

        // Back/Bicep day
        Movement deadLift     = movementDao.lookupOrCreateMovement("Deadlift",      Movement.RepsAndWeight, "Back", "Barbell");
        Movement latPulldowns = movementDao.lookupOrCreateMovement("Lat Pulldowns", Movement.RepsAndWeight, "Back", "Machine");
        Movement rows         = movementDao.lookupOrCreateMovement("Rows",          Movement.RepsAndWeight, "Back", "Machine");
        Movement pullups      = movementDao.lookupOrCreateMovement("Pullups",       Movement.Reps,          "Back", "Pull-up bar");
        Movement bicepCurls   = movementDao.lookupOrCreateMovement("Bicep Curls",   Movement.RepsAndWeight, "Biceps", "Dumbbells");
        Movement curlBar      = movementDao.lookupOrCreateMovement("Curl Bar",      Movement.RepsAndWeight, "Biceps", "Barbell");

        // create workout and add movements
        Workout backAndBiceps = workoutDao.lookupOrCreateWorkout(user.userId, "Back & Biceps");
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, deadLift);
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, latPulldowns);
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, rows);
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, pullups);
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, bicepCurls);
        workoutMovementCrossRefDao.addMovementToWorkout(backAndBiceps, curlBar);

        // Leg Day
        Movement squat          = movementDao.lookupOrCreateMovement("Squats",          Movement.RepsAndWeight, "Legs", "Barbell");
        Movement legPress       = movementDao.lookupOrCreateMovement("Leg Press",       Movement.RepsAndWeight, "Legs", "Machine");
        Movement calfRaises     = movementDao.lookupOrCreateMovement("Calf Raises",     Movement.RepsAndWeight, "Legs", "Machine");
        Movement hamstringCurls = movementDao.lookupOrCreateMovement("Hamstring Curls", Movement.RepsAndWeight, "Legs", "Machine");
        Movement legCurls       = movementDao.lookupOrCreateMovement("Leg Curls",       Movement.RepsAndWeight, "Legs", "Machine");

        // create workout and add movements
        Workout legs = workoutDao.lookupOrCreateWorkout(user.userId, "Leg Day");
        workoutMovementCrossRefDao.addMovementToWorkout(legs, squat);
        workoutMovementCrossRefDao.addMovementToWorkout(legs, legPress);
        workoutMovementCrossRefDao.addMovementToWorkout(legs, calfRaises);
        workoutMovementCrossRefDao.addMovementToWorkout(legs, hamstringCurls);
        workoutMovementCrossRefDao.addMovementToWorkout(legs, legCurls);

        // Abs
        Movement crunches      = movementDao.lookupOrCreateMovement("Crunches",       Movement.Reps,  "Abs", "Body weight");
        Movement situps        = movementDao.lookupOrCreateMovement("Situps",         Movement.Reps,  "Abs", "Body weight");
        Movement russianTwists = movementDao.lookupOrCreateMovement("Russian Twists", Movement.Reps,  "Abs", "Body weight");
        Movement planks        = movementDao.lookupOrCreateMovement("Planks",         Movement.Timed, "Abs", "Body weight");
        Movement legLifts      = movementDao.lookupOrCreateMovement("Leg Lifts",      Movement.Reps,  "Abs", "Body weight");

        // create workout and add movements
        Workout abs = workoutDao.lookupOrCreateWorkout(user.userId, "Ab Day");
        workoutMovementCrossRefDao.addMovementToWorkout(abs, crunches);
        workoutMovementCrossRefDao.addMovementToWorkout(abs, situps);
        workoutMovementCrossRefDao.addMovementToWorkout(abs, russianTwists);
        workoutMovementCrossRefDao.addMovementToWorkout(abs, planks);
        workoutMovementCrossRefDao.addMovementToWorkout(abs, legLifts);
    }

}
