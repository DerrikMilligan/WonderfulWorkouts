package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import wonderful.workouts.R;
import wonderful.workouts.database.joiners.WorkoutWithHistory;

public class WorkoutWithHistoryAdapter extends BaseAdapter {
    private final ArrayList<WorkoutWithHistory> _workouts;
    private final LayoutInflater layoutInflater;

    public WorkoutWithHistoryAdapter(Context context, ArrayList<WorkoutWithHistory> workouts) {
        _workouts = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _workouts.size();
    }

    @Override
    public Object getItem(int position) {
        return _workouts.get(position);
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
            holder = new ViewHolder();
            holder.workoutNameView = (TextView) convertView.findViewById(R.id.lvhistory_text_workout_name);
            holder.workoutDateView = (TextView) convertView.findViewById(R.id.lvhistory_text_workout_date);
            holder.setListView     = (ListView) convertView.findViewById(R.id.lvhistory_set_list_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.workoutNameView.setText(_workouts.get(position).workout.name);
        holder.workoutDateView.setText(LocalDateTime.now().toString());
        return convertView;
    }

    static class ViewHolder {
        TextView workoutNameView;
        TextView workoutDateView;
        ListView setListView;
    }
}
