package in.creativebucket.weefit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.dto.CategoryItem;

/**
 * Created by Chandan kumar on 6/27/2015.
 */
public class WearListAdapter extends BaseAdapter {

    private final Context context;
    private final List<CategoryItem> items;

    public WearListAdapter(Context context, List<CategoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.create_workout_category_item, parent, false);
            holder = new ViewHolder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.categoryName.setText(items.get(position).getCategoryName());

        return convertView;
    }

    class ViewHolder {
        TextView categoryName;
    }

}