package wonderful.workouts;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import wonderful.workouts.database.AppDatabase;
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
            R.id.navigation_home_page,
            R.id.navigation_profile_page,
            R.id.navigation_movements,
            R.id.navigation_history_page
        ).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // When we navigate using the bottom nav
        binding.navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_bar_profile:
                    navController.navigate(R.id.navigation_profile_page);
                    break;
                case R.id.navigation_bar_home:
                    navController.navigate(R.id.navigation_home_page);
                    break;
                case R.id.navigation_bar_movements:
                    navController.navigate(R.id.navigation_movements);
                    break;
                case R.id.navigation_bar_history:
                    navController.navigate(R.id.navigation_history_page);
                    break;
            }

            return true;
        });

        // This allows us to control some behaviors when the navigation has been changes
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // This is the only way I could manage to get and hide/show the toolbar
            ActionBar supportActionBar = this.getSupportActionBar();

            // This unchecks the bottom nav stuff. It's not perfect for now I'm going to leave it off
            // if (
            //     destination.getId() != R.id.navigation_bar_home &&
            //     destination.getId() != R.id.navigation_bar_dashboard &&
            //     destination.getId() != R.id.navigation_bar_notifications
            // ) {
            //     Menu menu = binding.navView.getMenu();
            //     menu.setGroupCheckable(0, true, false);
            //     for (int i = 0; i < menu.size(); i++) {
            //         menu.getItem(i).setChecked(false);
            //     }
            //     menu.setGroupCheckable(0, true, true);
            // }

            // If the destination is the login page hide the toolbar and the bottom nav
            if (destination.getId() == R.id.navigation_login_page) {
                // supportActionBar.hide();
                navView.setVisibility(View.GONE);
            } else {
                supportActionBar.show();
                navView.setVisibility(View.VISIBLE);
            }
        });

        // Get the instance so we create the database and get the connection ready
        AppDatabase.getInstance(this.getApplicationContext());

        // Helper to clear all tables in development!
        // new Thread(() -> {
        //     AppDatabase.getInstance(this.getApplicationContext()).clearAllTables();
        // }).start();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }


}