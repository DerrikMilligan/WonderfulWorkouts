package wonderful.workouts.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

import wonderful.workouts.R;

public class MeasurementDialog extends AppCompatDialogFragment {
    MeasurementCallback _callbacks;

    public MeasurementDialog(MeasurementCallback callbacks) {
        _callbacks = callbacks;
    }

    @NonNull
    @NotNull
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_measurement, null);

        TextView weightInput = view.findViewById(R.id.measurement_dialog_edit_weight);

        builder.setView(view)
            .setTitle("Update Weight")
            .setNegativeButton("cancel", (dialog, which) -> {})
            .setPositiveButton("ok", (dialog, which) -> _callbacks.onComplete(weightInput.getText().toString()));

        return builder.create();
    }

    // @Override
    // public void onAttach(Context context) {
    //     super.onAttach(context);
    //
    //     try {
    //         listener = (MeasurementDialogListener) context;
    //     } catch (ClassCastException e) {
    //         throw new ClassCastException(context.toString() + "must implement MeasurementDialogListener");
    //     }
    // }

    // public interface MeasurementDialogListener {
    //     void applyText(String weight);
    // }
}
