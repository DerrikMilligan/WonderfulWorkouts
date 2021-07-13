package wonderful.workouts.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import wonderful.workouts.R;
import wonderful.workouts.callbacks.AddSetDialogCallback;
import wonderful.workouts.database.entities.Movement;

public class AddSetDialog extends AppCompatDialogFragment {
    String _title;
    String _setType;
    AddSetDialogCallback _callbacks;

    public AddSetDialog(String title, String setType, AddSetDialogCallback callbacks) {
        _title = title;
        _setType = setType;
        _callbacks = callbacks;
    }

    @NonNull
    @NotNull
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_new_set, null);

        TextView weightInput   = view.findViewById(R.id.dialog_set_weight);
        TextView repsInput     = view.findViewById(R.id.dialog_set_reps);
        TextView durationInput = view.findViewById(R.id.dialog_set_duration);

        Log.i("AddSetDialog", String.format("Set type: %s", _setType));
        Log.i("AddSetDialog", String.format("RepsAndWeight: %s", Movement.RepsAndWeight));

        // Adjust the dialog based upon the type
        switch (_setType) {
            case Movement.Reps:
                weightInput.setVisibility(View.GONE);
                durationInput.setVisibility(View.GONE);
                break;

            case Movement.RepsAndWeight:
                Log.i("AddSetDialog", "Hiding duration");
                durationInput.setVisibility(View.GONE);
                break;

            case Movement.Timed:
                weightInput.setVisibility(View.GONE);
                repsInput.setVisibility(View.GONE);
                break;
        }

        builder.setView(view)
            .setTitle(String.format("Adding set for %s", _title))
            .setNegativeButton("cancel", (dialog, which) -> {})
            .setPositiveButton("ok", (dialog, which) -> {
                float weight   = 0.0f;
                float reps     = 0.0f;
                float duration = 0.0f;

                // Attempt to convert the strings. If we get errors it's fine we have a default value anyways
                try { weight   = Float.parseFloat(weightInput.getText().toString());   } catch (Exception e) {}
                try { reps     = Float.parseFloat(repsInput.getText().toString());     } catch (Exception e) {}
                try { duration = Float.parseFloat(durationInput.getText().toString()); } catch (Exception e) {}

                _callbacks.callback(weight, reps, duration);
            });

        return builder.create();
    }
}
