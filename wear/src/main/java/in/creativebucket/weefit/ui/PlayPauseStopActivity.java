package in.creativebucket.weefit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import in.creativebucket.weefit.R;
import in.creativebucket.weefit.dto.AppConstants;
import in.creativebucket.weefit.util.PreferencesManager;

/**
 * Created by Chandan kumar on 7/19/2015.
 */
public class PlayPauseStopActivity extends Activity implements AppConstants {

    private ImageView imageVwPlayPause;
    private RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_pause_stop_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                setUIonScreen(stub);
            }
        });
    }

    private void setUIonScreen(WatchViewStub stub) {
        imageVwPlayPause = (ImageView) stub.findViewById(R.id.play_pause_button);
        parentLayout = (RelativeLayout) stub.findViewById(R.id.mainLayout);

        imageVwPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEnablePause = PreferencesManager.getInstance(getApplicationContext()).isEnablePause();
                if (isEnablePause) {
                    Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
                    PreferencesManager.getInstance(getApplicationContext()).setTimerStatus(TIMER_PAUSE);
                    PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(false);

                } else {
                    Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_SHORT).show();
                    PreferencesManager.getInstance(getApplicationContext()).setTimerStatus(TIMER_PLAY);
                    PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
                }
                finish();
                overridePendingTransition(0, R.anim.bottom_out);
            }
        });

        final GestureDetector gdt = new GestureDetector(new GestureListener());

        parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Right to left
//            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Left to right
//            }

            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                PreferencesManager.getInstance(getApplicationContext()).setTimerStatus(TIMER_PLAY);
                PreferencesManager.getInstance(getApplicationContext()).setIsEnablePause(true);
                finish();
                overridePendingTransition(0, R.anim.bottom_out);
                return false; // Bottom to top
            }
            //else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Top to bottom
//            }

//            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
//                return false; // Top to bottom
//            }
            return false;
        }
    }
}


