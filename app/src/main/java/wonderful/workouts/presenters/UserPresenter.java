package wonderful.workouts.presenters;

import android.content.Context;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.UserDao;
import wonderful.workouts.database.entities.User;

public class UserPresenter {
    private final UserDao userDao;

    // Implement the singleton pattern
    private static UserPresenter INSTANCE = null;

    // Make the constructor private so we have to use getSingleton to use the presenter
    private UserPresenter(AppDatabase db) {
        userDao = db.getUserDao();
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
     * createNewUser
     *
     * Will create a new user with a given username and password
     *
     * @param username The username for the new account
     * @param password The password for the new account
     *
     * @return User
     */
    public User createNewUser(String username, String password) {
        User newUser = new User();

        newUser.username = username;
        newUser.password = password;

        userDao.insert(newUser);

        return newUser;
    }
}
