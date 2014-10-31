package com.multimedia.busy;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.VideoView;

public class SplashScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		try {
			VideoView videodisplay = new VideoView(this);
			setContentView(videodisplay);
			Uri video = Uri.parse("android.resource://" + getPackageName()
					+ "/" + R.raw.m_movie);
			videodisplay.setVideoURI(video);

			videodisplay.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					skip();
				}

			});
			videodisplay.start();
		} catch (Exception ex) {
			skip();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		skip();
		return true;
	}

	private void skip() {
		// it is safe to use this code even if you
		// do not intend to allow users to skip the splash
		if (isFinishing())
			return;
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
