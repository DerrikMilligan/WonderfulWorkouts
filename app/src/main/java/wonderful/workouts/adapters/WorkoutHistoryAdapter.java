package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;

public class WorkoutHistoryAdapter extends BaseAdapter {
    private final ArrayList<WorkoutHistory> _workouthistory;
    private final LayoutInflater layoutInflater;

    public WorkoutHistoryAdapter(Context context, ArrayList<WorkoutHistory> workoutHistory) {
        _workouthistory = workoutHistory;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _workouthistory.size();
    }

    @Override
    public Object getItem(int position) {
        return _workouthistory.get(position);
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

        WorkoutHistory workoutHistory = _workouthistory.get(position);
        LocalDateTime workoutDate = workoutHistory.startTime;
        holder.dateView.setText(String.format("%d:%02d:%02d", workoutDate.getHour(), workoutDate.getMinute(), workoutDate.getSecond()));

        return convertView;
    }

    static class ViewHolder {
        public TextView dateView;


    }
}
