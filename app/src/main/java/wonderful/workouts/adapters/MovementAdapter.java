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
import wonderful.workouts.database.entities.Movement;

public class MovementAdapter  extends BaseAdapter {
    private final List<Movement> _movements;
    private final LayoutInflater layoutInflater;

    public MovementAdapter(Context context, List<Movement> movements) {
        _movements = movements;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()  {
        return _movements.size();
    }

    @Override
    public Object getItem(int position)  {
        return _movements.get(position);
    }

    @Override
    public long getItemId(int position)  {
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovementAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_movement, null);
            holder = new MovementAdapter.ViewHolder();
            // holder.idView = (TextView) convertView.findViewById(R.id.list_view_movement_id);
            holder.nameView = (TextView) convertView.findViewById(R.id.list_view_movement_name);
            convertView.setTag(holder);
        } else {
            holder = (MovementAdapter.ViewHolder) convertView.getTag();
        }

        // holder.idView.setText(_movements.get(position).movementId + ": ");
        holder.nameView.setText(_movements.get(position).name);
        return convertView;
    }


    static class ViewHolder {
        TextView idView;
        TextView nameView;
    }
}
