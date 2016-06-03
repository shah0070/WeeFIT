package in.creativebucket.weefit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.creativebucket.weefit.MainActivity;
import in.creativebucket.weefit.R;

/**
 * Created by Chandan Kumar on 10/3/2015.
 */
public class HomeFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getActivity().getResources().getString(R.string.category));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        LinearLayout presetWorkoutLayout = (LinearLayout) rootView.findViewById(R.id.preset_workout_layout);
        presetWorkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresetWorkoutFragment presetWorkoutFragment = new PresetWorkoutFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category_name", getActivity().getResources().getString(R.string.preset_workout));
                presetWorkoutFragment.setArguments(bundle);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.container, presetWorkoutFragment, "preset_workout");
                ft.commit();
            }
        });

        LinearLayout createWorkoutLayout = (LinearLayout) rootView.findViewById(R.id.create_workout_layout);
        createWorkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
