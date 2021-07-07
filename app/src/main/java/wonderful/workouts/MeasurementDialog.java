package wonderful.workouts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

public class MeasurementDialog extends AppCompatDialogFragment {
    private EditText editWeight;
    // private MeasurementDialogListener listener;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.measurement_dialog, null);
        builder.setView(view)
            .setTitle("Update Weight")
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            })
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String weight = editWeight.getText().toString();
                    //listener.applyText(weight);
                }
            });

        editWeight = (EditText) view.findViewById(R.id.editWeight);
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
