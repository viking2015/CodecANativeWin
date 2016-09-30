package com.tom.codecanativewin;


import com.tom.codecanativewin.jni.DecodeH264;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class DisplayH264Activity extends Activity {

 
	final public static String TAG = "java_h264";
	private DecodeH264 mH264de = null;

	private SurfaceView mSv = null;
	private SurfaceHolder mSh = null;
	private boolean surfaceCreated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		
		setContentView(R.layout.activity_display_h264);

		mSv = (SurfaceView) findViewById(R.id.viewSurface);
		mSh = mSv.getHolder();
		mSh.setKeepScreenOn(true);
		mSh.addCallback(new SurfaceCallback());

		 
		((Button) findViewById(R.id.bStart)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mH264de == null)
					mH264de = new DecodeH264();
				if (surfaceCreated == false){
					Toast.makeText(DisplayH264Activity.this, "Surface Not Available now", Toast.LENGTH_LONG)
							.show();
				}else{
					mH264de.start( mSh.getSurface() );
				} 
			}
		});
		
	 
		((Button) findViewById(R.id.bStop)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mH264de != null){
					mH264de.release();
				}else{
					Toast.makeText(DisplayH264Activity.this, "DecodeH264 Not Created now", Toast.LENGTH_LONG)
					.show();
				}
			}
		});

	}

	private class SurfaceCallback implements SurfaceHolder.Callback {

		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

			Log.d(TAG, "surfaceChanged " + " arg1 = " + arg1 + " arg2 = " + arg2 + " arg3 = " + arg3);
		}

		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			Log.d(TAG, "surfaceCreated created");
			surfaceCreated = true;
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			Log.d(TAG, "surfaceCreated destroyed");
		}
	}
}
