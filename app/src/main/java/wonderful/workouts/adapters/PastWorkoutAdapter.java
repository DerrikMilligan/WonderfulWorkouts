package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutMovementHistory;
import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;
import wonderful.workouts.database.joiners.WorkoutHistoryWithMovements;
import wonderful.workouts.database.joiners.WorkoutWithHistory;

public class PastWorkoutAdapter extends BaseExpandableListAdapter {
    private final WorkoutWithHistory _workouts;
    private final LayoutInflater layoutInflater;

    public PastWorkoutAdapter(Context context, WorkoutWithHistory workouts) {
        _workouts = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        int movementCount = 0;

        // Count all the sets from the workouts
        for (WorkoutHistoryWithMovements histories : _workouts.pastWorkouts) {
            for (MovementWithWorkoutMovementHistory ignored : histories.movementHistory) {
                movementCount++;
            }
        }

        return movementCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int movementCount = 0;
        int setCount = 0;

        // Count all the sets from the workouts

            for (WorkoutHistoryWithMovements histories : _workouts.pastWorkouts) {
                for (MovementWithWorkoutMovementHistory movements : histories.movementHistory) {
                    if (movementCount == groupPosition) {
                        for (WorkoutMovementHistory ignored : movements.workoutMovementHistories) {
                            setCount++;
                        }

                        return setCount;
                    }

                    movementCount++;
                }
            }


        return setCount;
    }

    @Override
    public MovementWithWorkoutMovementHistory getGroup(int groupPosition) {
        int movementCount = 0;

        // Count all the sets from the workouts

            for (WorkoutHistoryWithMovements histories : _workouts.pastWorkouts) {
                for (MovementWithWorkoutMovementHistory movement : histories.movementHistory) {
                    if (movementCount == groupPosition) {
                        return movement;
                    }

                    movementCount++;
                }
            }


        return null;
    }

    @Override
    public WorkoutMovementHistory getChild(int groupPosition, int childPosition) {
        int movementCount = 0;
        int setCount = 0;

        // Count all the sets from the workouts

            for (WorkoutHistoryWithMovements histories : _workouts.pastWorkouts) {
                for (MovementWithWorkoutMovementHistory movements : histories.movementHistory) {
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
            holder.nameView = (TextView) convertView.findViewById(R.id.workout_history_group_text_workout_name);
            holder.dateView = (TextView) convertView.findViewById(R.id.workout_history_group_text_workout_date);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        MovementWithWorkoutMovementHistory workout = getGroup(groupPosition);

        holder.nameView.setText(workout.movement.name);
        // holder.dateView.setText(LocalDateTime.now().toString());

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
