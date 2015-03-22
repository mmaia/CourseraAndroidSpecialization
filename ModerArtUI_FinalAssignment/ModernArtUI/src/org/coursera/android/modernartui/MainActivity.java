package org.coursera.android.modernartui;

import java.math.BigDecimal;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	//moma address url
	private final static String MOMA_SITE = "http://www.moma.org";

	//need to change colors of all non-white/gray views
	private View leftTop;
	private View leftBottom;
	private View rightTop;
	private View rightBottom;
	
	//used by random color generator impl.
	Random randomColorGen = new Random();
	
	
	//used by saturation impl.
	private ColorMatrix colorMatrix = new ColorMatrix();
	private ColorMatrixColorFilter cmFilter = new ColorMatrixColorFilter(colorMatrix);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "entering onCreate");
		setContentView(R.layout.activity_main);
		
		//get references to views that need to change collors @ the slide bar
		leftTop = findViewById(R.id.left_top);
		leftBottom = findViewById(R.id.left_bottom);
		rightTop = findViewById(R.id.right_top);
		rightBottom = findViewById(R.id.right_bottom);
		
		//get reference to the slide Bar 
		SeekBar theSlider = (SeekBar) findViewById(R.id.slider);
		//sets value from 0 - 100
		theSlider.setMax(100);
		theSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            	Log.i(TAG, "SeekBar.onProgressChanged called, progress so far: " + progress);
            	changeCollor(progress, leftTop, rightTop, leftBottom, rightBottom);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            	Log.i(TAG, "SeekBar.onStartTrackingTouch called, not being used by now");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            	Log.i(TAG, "SeekBar.onPStopTrackingTouch called, not being used by now");
            }
        });
		
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogFragment mDialog = AlertDialogFragment.newInstance();
        mDialog.show(getFragmentManager(), "Alert");
        return true;
    }

    public static class AlertDialogFragment extends DialogFragment {
        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.dialog_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.visit_moma_message,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_SITE));
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Nah, later!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    AlertDialogFragment.this.dismiss();
                                }
                            })
                    .create();
        }
    }
	
	//method that change the collors of the views using BigDecimal
	private void changeCollor(int colorIncrementer, View... views ){
//		Log.i(TAG, "SeekBar.changeCollor called");

		//implementation using random colors being generated.
//		for (View view : views) {
//            ((ColorDrawable) view.getBackground()).setColor(0xFF000000 + randomColorGen.nextInt(0xFFFFFF));
//            
//        }
		
		
		//implementation using opacity
		for(View view: views){
			int currentOpacity = view.getBackground().getOpacity();
//			Log.i(TAG, "current opacity: " + currentOpacity);
			int baseColor = 100 - colorIncrementer;
			BigDecimal bd = new BigDecimal(baseColor);
			BigDecimal bdNewOpacity = bd.divide(new BigDecimal(100));
			float newOpacity = bdNewOpacity.floatValue();
//			Log.i(TAG, "new opacity: " + newOpacity);
			view.setAlpha((float)newOpacity);
		}
		
		//Implementation using color saturation
		//http://www.41post.com/4837/programming/android-changing-image-color-saturation
//		for (View view : views) {
//            
//            
//        }
	}
}
