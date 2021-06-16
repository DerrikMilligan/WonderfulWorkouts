package wonderful.workouts.presenters;

import android.content.Context;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

import wonderful.workouts.database.AppDatabase;
import wonderful.workouts.database.daos.UserDao;
import wonderful.workouts.database.entities.User;

public class UserPresenter {
    private AppDatabase _db = null;
    private UserDao userDao = null;

    // Implement the singleton pattern
    private static UserPresenter INSTANCE = null;

    // Make the constructor private so we have to use getSingleton to use the presenter
    private UserPresenter(AppDatabase db) {
        _db = db;
        userDao = _db.getUserDao();
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
     * @param String username
     *
     * @return boolean
     */
    public boolean usernameExists(String username) {
        Maybe<User> userLookup = userDao.getFromUsername(username);

        // Get the response from the database
        User user = userLookup.blockingGet();

        // If the user is null then we failed to find one
        return user != null;
    }

    /**
     * createNewUser
     *
     * Will create a new user with a given username and password
     *
     * @param String username
     * @param String password
     *
     * @return User
     */
    public User createNewUser(String username, String password) {
        User newUser = new User();

        newUser.username = username;
        newUser.password = password;

        Completable userInsert = userDao.insert(newUser);

        // Wait until the insert is complete
        userInsert.blockingAwait();

        return newUser;
    }
}
