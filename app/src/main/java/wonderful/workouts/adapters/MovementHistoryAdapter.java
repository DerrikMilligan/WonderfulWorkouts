package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

public class MovementHistoryAdapter extends BaseExpandableListAdapter {
    private final Map<WorkoutHistory, List<WorkoutMovementHistory>> _workouts;
    private final LayoutInflater layoutInflater;

    public MovementHistoryAdapter(Context context, Map<WorkoutHistory, List<WorkoutMovementHistory>> workouts) {
        _workouts = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return _workouts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Get the group we're expanding
        WorkoutHistory currentHistory = getGroup(groupPosition);

        if (currentHistory == null)
            return 0;

        for (Map.Entry<WorkoutHistory, List<WorkoutMovementHistory>> entry : _workouts.entrySet()) {
            if (entry.getKey().workoutHistoryId == currentHistory.workoutHistoryId) {
                return entry.getValue().size();
            }
        }

        return 0;
    }

    @Override
    public WorkoutHistory getGroup(int groupPosition) {
        return (WorkoutHistory) _workouts.keySet().toArray()[groupPosition];
    }

    @Override
    public WorkoutMovementHistory getChild(int groupPosition, int childPosition) {
        WorkoutHistory workoutHistory = getGroup(groupPosition);

        return _workouts.get(workoutHistory).get(childPosition);
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

    @SuppressLint("DefaultLocale")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expandable_movement_history_group, null);
            holder = new GroupViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.workout_history_group_text_workout_name);
            holder.dateView = (TextView) convertView.findViewById(R.id.workout_history_group_text_workout_date);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        WorkoutHistory workoutHistory = getGroup(groupPosition);

        // holder.nameView.setText(workout.workout.name);
        holder.dateView.setText(String.format(
            "%s %d, %d - %d:%d",
            workoutHistory.startTime.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()),
            workoutHistory.startTime.getDayOfMonth(),
            workoutHistory.startTime.getYear(),
            workoutHistory.startTime.getHour(),
            workoutHistory.startTime.getMinute()
        ));

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

        WorkoutMovementHistory history = getChild(groupPosition, childPosition);
        holder.setNumberView.setText(String.format("Set %d", (childPosition + 1)));

        if (history.duration > 0) {
            holder.weightView.setText("");
            holder.repAndTimeView.setText(String.format("%.2f", history.duration));
        } else if (history.weight > 0) {
            holder.weightView.setText(String.format("%d", Math.round(history.weight)));
            holder.repAndTimeView.setText(String.format("%d", Math.round(history.reps)));
        } else {
            holder.weightView.setText("");
            holder.repAndTimeView.setText(String.format("%d", Math.round(history.reps)));
        }

        return convertView;
    }

    static class GroupViewHolder {
        TextView nameView;
        TextView dateView;
    }

    static class ChildViewHolder {
        TextView setNumberView;
        TextView weightView;
        TextView repAndTimeView;
    }
}
