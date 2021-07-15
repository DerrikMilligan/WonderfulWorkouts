package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;

public class WorkoutHistoryAdapter extends BaseAdapter {
    private final List<WorkoutHistory> _workoutHistory;
    private final LayoutInflater layoutInflater;

    public WorkoutHistoryAdapter(Context context, List<WorkoutHistory> workoutHistory) {
        _workoutHistory = workoutHistory;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _workoutHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return _workoutHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_workout_with_history, null);
            holder = new WorkoutHistoryAdapter.ViewHolder();
            holder.dateView = (TextView) convertView.findViewById(R.id.lvhistory_text_workout_date);
            convertView.setTag(holder);
        } else {
            holder = (WorkoutHistoryAdapter.ViewHolder) convertView.getTag();
        }

        WorkoutHistory workoutHistory = _workoutHistory.get(position);
        LocalDateTime workoutDate = workoutHistory.startTime;
        holder.dateView.setText(String.format(
            "    %s %d, %d - %02d:%02d",
            workoutDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()),
            workoutDate.getDayOfMonth(),
            workoutDate.getYear(),
            workoutDate.getHour(),
            workoutDate.getMinute()
        ));

        return convertView;
    }

    static class ViewHolder {
        public TextView dateView;


    }
}
