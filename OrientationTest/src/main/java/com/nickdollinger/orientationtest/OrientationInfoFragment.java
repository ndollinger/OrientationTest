package com.nickdollinger.orientationtest;
/* Keeping in mind this is NOT the support library fragment, if we
* Want to run on older devices this will have to be changed to android.support.v4.app.Fragment*/
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * Created by ndolling on 11/21/13.
 * For great justice
 */
public class OrientationInfoFragment extends Fragment {

    String tag = "OrientationInfoFragment";
    OrientationEventListener orientListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        setRetainInstance(true); // no need to recreate this fragment
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        /* Orientation Listener Shows rotation in degrees */
        orientListener = new OrientationEventListener(getActivity()) {
            @Override
            public void onOrientationChanged(int i) {
                //Oh Shit guys, Orientation is changing yo
                // Can I mess with UI elements from here?  Doesn't seem smart
                TextView tOrientation = (TextView)getView().findViewById(R.id.Orientation_Text);
                if(i != ORIENTATION_UNKNOWN){
                    tOrientation.setText(" Orientation is  " + i + " Degrees");
                }
                else{
                    tOrientation.setText(" Orientation Unknown, device is probably flat");
                }

                // When the orientation changes, also check the rotation
                Context context = getActivity();
                Display display = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE))
                        .getDefaultDisplay();
                int orientation = display.getRotation();

                TextView tRotation = (TextView) getView().findViewById(R.id.Rotation_Text);
                switch(orientation){
                    case Surface.ROTATION_0:    tRotation.setText("Rotation is: ROTATION_0");
                        break;
                    case Surface.ROTATION_90:   tRotation.setText("Rotation is: ROTATION_90");
                        break;
                    case Surface.ROTATION_180:  tRotation.setText("Rotation is ROTATION_180");
                        break;
                    case Surface.ROTATION_270:  tRotation.setText("Rotation is ROTATION_270");
                        break;
                    default:                    tRotation.setText("Rotation is UNKNOWN");
                }

                // When orientation changes, measure the screen
                Point size = new Point();
                display.getSize(size);
                TextView tX = (TextView)getView().findViewById(R.id.Screen_Width);
                tX.setText("Width: " + size.x);
                TextView tY = (TextView)getView().findViewById(R.id.Screen_Height);
                tY.setText("Height: " + size.y);
            }
        };
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(tag,"onPause called");
        orientListener.disable();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(tag,"onStop called");
        orientListener.disable();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(tag, "onResume called, orientationListener enabled");
        orientListener.enable();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag, "onDestroyView called");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d(tag, "onDetach called");
        if(orientListener != null){  // Should never be null here
            orientListener.disable();
        }
    }
}