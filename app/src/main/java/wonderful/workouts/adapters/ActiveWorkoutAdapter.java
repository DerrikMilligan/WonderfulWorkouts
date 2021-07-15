package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import wonderful.workouts.R;
import wonderful.workouts.callbacks.ActiveWorkoutAdapterCallback;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.dialogs.AddSetDialog;

public class ActiveWorkoutAdapter extends BaseExpandableListAdapter {
    private final WorkoutHistoryWithMovements _workout;
    private final LayoutInflater layoutInflater;
    private final ActiveWorkoutAdapterCallback _callback;

    public ActiveWorkoutAdapter(Context context, WorkoutHistoryWithMovements workout, ActiveWorkoutAdapterCallback callback) {
        _workout = workout;
        _callback = callback;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        int movementCount = 0;

        // Count all the sets from the workouts
        for (MovementWithWorkoutMovementHistory ignored : _workout.movementHistory) {
            movementCount++;
        }

        return movementCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int movementCount = 0;
        int setCount = 0;

        // Count all the sets from the workouts
        for (MovementWithWorkoutMovementHistory movements : _workout.movementHistory) {
            if (movementCount == groupPosition) {
                for (WorkoutMovementHistory ignored : movements.workoutMovementHistories) {
                    setCount++;
                }

                return setCount;
            }

            movementCount++;
        }

        return setCount;
    }

    @Override
    public MovementWithWorkoutMovementHistory getGroup(int groupPosition) {
        int movementCount = 0;

        // Count all the sets from the workouts
        for (MovementWithWorkoutMovementHistory movement : _workout.movementHistory) {
            if (movementCount == groupPosition) {
                return movement;
            }

            movementCount++;
        }

        return null;
    }

    @Override
    public WorkoutMovementHistory getChild(int groupPosition, int childPosition) {
        int movementCount = 0;
        int setCount = 0;

        // Count all the sets from the workouts
        for (MovementWithWorkoutMovementHistory movements : _workout.movementHistory) {
            if (movementCount == groupPosition) {
                for (WorkoutMovementHistory set : movements.workoutMovementHistories) {

                    if (setCount == childPosition) {
                        return set;
                    }

                    setCount++;
                }
            }

            movementCount++;
        }

        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expandable_active_workout_group, null);
            holder = new GroupViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.active_workout_group_text_workout_name);
            holder.addSetButton = (ImageButton) convertView.findViewById(R.id.btn_add_set);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        MovementWithWorkoutMovementHistory workout = getGroup(groupPosition);

        // This makes the button clickable while leaving the list expandable and took too many hours to figure out.
        holder.addSetButton.setFocusable(false);

        Log.i("ActiveWorkoutAdapter", String.format("Adding set to movement: %s, type: %s", workout.movement.name, workout.movement.type));

        // When we click the button display a dialog to add a set for that movement
        holder.addSetButton.setOnClickListener((view) -> _callback.callback(workout));

        holder.nameView.setText(workout.movement.name);

        Log.i("ActiveWorkoutAdapter", String.format("Movement Name: %s", workout.movement.name));

        return convertView;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expandable_movement_history_item, null);
            holder = new ChildViewHolder();
            holder.setNumberView = (TextView) convertView.findViewById(R.id.movement_history_item_set_number);
            holder.weightView = (TextView) convertView.findViewById(R.id.movement_history_item_weights);
            holder.repAndTimeView = (TextView) convertView.findViewById(R.id.movement_history_item_reps_and_time);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        MovementWithWorkoutMovementHistory workout = getGroup(groupPosition);
        WorkoutMovementHistory history = getChild(groupPosition, childPosition);

        holder.setNumberView.setText(String.format("Set %d", (childPosition + 1)));

        Log.i("ActiveWorkoutAdapter", String.format("Set duration: %.2f, weight: %.2f, reps: %.2f", history.duration, history.weight, history.reps));

        // Adjust the dialog based upon the type
        switch (workout.movement.type) {
            case Movement.Reps:
                holder.repAndTimeView.setText(String.format("%d", Math.round(history.reps)));
                break;

            case Movement.RepsAndWeight:
                holder.repAndTimeView.setText(String.format("%d", Math.round(history.reps)));
                holder.weightView.setText(String.format("%d", Math.round(history.weight)));
                break;

            case Movement.Timed:
                holder.repAndTimeView.setText(String.format("%.2f", history.duration));
                break;
        }

        return convertView;
    }

    static class GroupViewHolder {
        TextView nameView;
        ImageButton addSetButton;
    }

    static class ChildViewHolder {
        TextView setNumberView;
        TextView weightView;
        TextView repAndTimeView;
    }
}
