package in.creativebucket.weefit.dto;

/**
 * Created by Chandan kumar on 6/27/2015.
 */

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A convenient container for a row of fragments.
 */
public class Row {
    final List<Fragment> columns = new ArrayList<Fragment>();

    public Row(Fragment... fragments) {
        for (Fragment f : fragments) {
            add(f);
        }
    }

    public Row(ArrayList<Fragment> fragments) {
        for (Fragment f : fragments) {
            add(f);
        }
    }

    public void add(Fragment f) {
        columns.add(f);
    }

    public Fragment getColumn(int i) {
        return columns.get(i);
    }

    public int getColumnCount() {
        return columns.size();
    }
}