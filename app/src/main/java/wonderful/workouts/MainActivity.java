package wonderful.workouts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import wonderful.workouts.database.entities.User;
import wonderful.workouts.databinding.ActivityMainBinding;
import wonderful.workouts.presenters.UserPresenter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView navView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_login_page,
            R.id.navigation_home_page
        ).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // This allows us to control some behaviors when the navigation has been changes
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // This is the only way I could manage to get and hide/show the toolbar
            ActionBar supportActionBar = this.getSupportActionBar();

            // If the destination is the login page hide the toolbar and the bottom nav
            if (destination.getId() == R.id.navigation_login_page) {
                // supportActionBar.hide();
                navView.setVisibility(View.GONE);
            } else {
                supportActionBar.show();
                navView.setVisibility(View.VISIBLE);
            }
        });

        // navController.navigate(R.id.home_page);

        // Intent intent = new Intent(this, Profile.class);
        // startActivity(intent);

        testUserPresenter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    private void testUserPresenter() {
        new Thread(() -> {
            UserPresenter userPresenter = UserPresenter.getInstance(this.getApplicationContext());

            Log.i("MainActivity", String.format("Does the user 'derrik' exist? %s", userPresenter.usernameExists("Emily") ? "yes" : "no"));

            User derrik = userPresenter.createNewUser("Emily", "bilbo");

            Log.i("MainActivity", String.format("New user: id: '%d' username: '%s', password: '%s'", derrik.userId, derrik.username, derrik.password));

            Log.i("MainActivity", String.format("Does the user 'derrik' exist? %s", userPresenter.usernameExists("Emily") ? "yes" : "no"));
        }).start();
    }

}