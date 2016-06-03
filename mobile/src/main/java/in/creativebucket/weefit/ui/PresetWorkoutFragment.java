package in.creativebucket.weefit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.creativebucket.weefit.MainActivity;
import in.creativebucket.weefit.R;

/**
 * Created by Chandan Kumar on 10/3/2015.
 */
public class PresetWorkoutFragment extends Fragment {

    private Bundle bundle;
    private String title;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("category_name");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preset_layout_fragment, container, false);
        return rootView;
    }

}
