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

    @SuppressLint({"SetTextI18n", "InflateParams"})
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

        holder.dateView.setText(LocalDateTime.now().toString());

//        WorkoutHistory workoutHistory = _workouthistory.get(position);
//
//        TextView workoutDate = workoutHistory.startTime;
//
//        long workoutSeconds = workoutHistory.getSeconds();
//
//        workoutDate.setText(String.format("%d:%02d:%02d", workoutSeconds / 3600, (workoutSeconds % 3600) / 60, (workoutSeconds % 60)));

        //holder.dateView.setText(workoutHistory.startTime);
        //holder.dateView.setText(_workouthistory.get(position).startTime);

        return convertView;
    }

    static class ViewHolder {
        public TextView dateView;


    }
}
