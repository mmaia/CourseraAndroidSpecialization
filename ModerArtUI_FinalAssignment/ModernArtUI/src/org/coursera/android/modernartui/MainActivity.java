package org.coursera.android.modernartui;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	//moma address url
	private final String MOMA_SITE = "www.moma.org";

	//need to change colors of all non-white/gray views
	private View leftTop;
	private View leftBottom;
	private View rightTop;
	private View rightBottom;
	
	
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
	
	//method that change the collors of the views using BigDecimal + View.setAlpha of the view who sets opacity.
	private void changeCollor(int colorIncrementer, View... views ){
		Log.i(TAG, "SeekBar.changeCollor called");
		for(View view: views){
			int currentOpacity = view.getBackground().getOpacity();
			Log.i(TAG, "current opacity: " + currentOpacity);
			int baseColor = 100 - colorIncrementer;
			BigDecimal bd = new BigDecimal(baseColor);
			BigDecimal bdNewOpacity = bd.divide(new BigDecimal(100));
			float newOpacity = bdNewOpacity.floatValue();
			Log.i(TAG, "new opacity: " + newOpacity);
			view.setAlpha((float)newOpacity);
		}
	}
}
