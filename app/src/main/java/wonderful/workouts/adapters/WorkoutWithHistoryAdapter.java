package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithWorkoutMovementHistories;
import wonderful.workouts.database.joiners.WorkoutWithHistory;

public class WorkoutWithHistoryAdapter extends BaseExpandableListAdapter {
    private final ArrayList<WorkoutWithHistory> _workouts;
    private final LayoutInflater layoutInflater;

    public WorkoutWithHistoryAdapter(Context context, ArrayList<WorkoutWithHistory> workouts) {
        _workouts = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return _workouts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int setCount = 0;

        // Get the group we're expanding
        WorkoutWithHistory currentHistory = _workouts.get(groupPosition);

        // Count all the sets from the workouts
        for (WorkoutHistoryWithWorkoutMovementHistories histories : currentHistory.pastWorkouts) {
            for (MovementWithWorkoutMovementHistory movements : histories.movementHistory) {
                for (WorkoutMovementHistory ignored : movements.workoutMovementHistories) {
                    setCount++;
                }
            }
        }

        return setCount;
    }

    @Override
    public WorkoutWithHistory getGroup(int groupPosition) {
        return _workouts.get(groupPosition);
    }

    @Override
    public WorkoutMovementHistory getChild(int groupPosition, int childPosition) {
        int setIndex = 0;

        WorkoutWithHistory currentHistory = _workouts.get(groupPosition);

        // Count all the sets from the workouts
        for (WorkoutHistoryWithWorkoutMovementHistories histories : currentHistory.pastWorkouts) {
            for (MovementWithWorkoutMovementHistory movements : histories.movementHistory) {
                for (WorkoutMovementHistory set : movements.workoutMovementHistories) {
                    if (setIndex == childPosition) {
                        return set;
                    }

                    setIndex++;
                }
            }
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
            convertView = layoutInflater.inflate(R.layout.expandable_movement_history_group, null);
            holder = new GroupViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.movement_history_group_text_workout_name);
            holder.dateView = (TextView) convertView.findViewById(R.id.movement_history_group_text_workout_date);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        WorkoutWithHistory workout = _workouts.get(groupPosition);

        holder.nameView.setText(workout.workout.name);
        holder.dateView.setText(LocalDateTime.now().toString());

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
        holder.weightView.setText(String.format("%d", Math.round(history.weight)));
        holder.repAndTimeView.setText(String.format("%d", Math.round(history.reps)));

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
