package wonderful.workouts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wonderful.workouts.R;
import wonderful.workouts.database.entities.Workout;

public class WorkoutAdapter extends BaseAdapter {
    private final List<Workout> _workouts;
    private final LayoutInflater layoutInflater;

    public WorkoutAdapter(Context context, List<Workout> workouts) {
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
            convertView = layoutInflater.inflate(R.layout.list_view_workout, null);
            holder = new ViewHolder();
            holder.idView = (TextView) convertView.findViewById(R.id.list_view_workout_id);
            holder.nameView = (TextView) convertView.findViewById(R.id.list_view_workout_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Workout workout = _workouts.get(position);

        holder.idView.setText(workout.workoutId + ": ");
        holder.nameView.setText(workout.name);
        return convertView;
    }

    static class ViewHolder {
        TextView idView;
        TextView nameView;
    }
}
