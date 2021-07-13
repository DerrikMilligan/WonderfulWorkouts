package wonderful.workouts.dialogs;

import android.app.AlertDialog;

import androidx.fragment.app.FragmentActivity;

import wonderful.workouts.callbacks.DialogHelperCallback;

public class DialogHelper {
    public static void displayAlertFromFragmentThread(
        FragmentActivity activity,
        String title,
        String message,
        DialogHelperCallback callback
    ) {
        // Let the user know that the password was invalid
        activity.runOnUiThread(() -> {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialog, which) -> {
                // Cant think of why we wouldn't want to dismiss the dialog always
                dialog.dismiss();

                // If we have a callback, call it
                callback.onOkayClicked(dialog, which);
            });
            alertDialog.show();
        });
    }
}
