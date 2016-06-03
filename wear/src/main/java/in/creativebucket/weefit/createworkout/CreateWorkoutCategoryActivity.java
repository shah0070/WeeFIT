package in.creativebucket.weefit.createworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.adapter.WearListAdapter;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.dto.CategoryItem;
import in.creativebucket.weefit.util.PreferencesManager;

public class CreateWorkoutCategoryActivity extends Activity implements AppConstants {
    private ListView listView;
    private List<CategoryItem> categoryItemList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_workout_category_fragment);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                listView = (ListView) stub.findViewById(R.id.wearListView);
                loadAdapter();
            }
        });
    }

    private void loadAdapter() {
        categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem(R.drawable.rightarrow, getString(R.string.category_one)));
        categoryItemList.add(new CategoryItem(R.drawable.rightarrow, getString(R.string.category_two)));
        categoryItemList.add(new CategoryItem(R.drawable.rightarrow, getString(R.string.category_three)));
        categoryItemList.add(new CategoryItem(R.drawable.rightarrow, getString(R.string.category_four)));
        categoryItemList.add(new CategoryItem(R.drawable.rightarrow, getString(R.string.category_five)));

        WearListAdapter wearListAdapter = new WearListAdapter(this, categoryItemList);
        listView.setAdapter(wearListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PreferencesManager.getInstance(getApplicationContext()).setNextExercisePos(0);
                PreferencesManager.getInstance(getApplicationContext()).setNextCreatedWorkoutPos(0);
                Intent intent = new Intent(CreateWorkoutCategoryActivity.this, CreateExerciseSelectionActivity.class);
                intent.putExtra(CATEGORY_NAME, categoryItemList.get(i).getCategoryName());
                intent.putExtra(IS_FROM_QUEUE, false);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ex_add_msg), Toast.LENGTH_LONG).show();
            }
        });
    }
}
