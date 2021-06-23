package wonderful.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
            .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);

        testUserPresenter();
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