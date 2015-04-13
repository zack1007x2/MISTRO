package info.zagama.mistro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Exit extends Activity {

	private ImageButton btExit, btNew;
	private SharedPreferences profile;

	private RelativeLayout llBackgroundPanel = null;
	private BitmapDrawable bmpDrawImg1 = null;

	private SoundPool soundPool;
	private int alertId;

	private boolean mIsBound = false;
	private MusicService mServ;
	private Intent music;
	private int ShouldPlay;
	private boolean servicestart;

	private ImageView imageView1, imageView2;
	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
			animationFadeOutB;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit);

		profile = getSharedPreferences("profile", 0);
		Editor editor = profile.edit();
		editor.clear();
		editor.commit();

		btExit = (ImageButton) findViewById(R.id.imgBexit);
		btNew = (ImageButton) findViewById(R.id.imgBnew);

		llBackgroundPanel = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		bmpDrawImg1 = new BitmapDrawable(getResources().openRawResource(
				R.drawable.aa_06_01_background));
		llBackgroundPanel.setBackgroundDrawable(bmpDrawImg1);

		btExit.setOnTouchListener(btExitLis);
		btNew.setOnTouchListener(btNewLis);

		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
		alertId = soundPool.load(this, R.raw.click, 1);

		profile = getSharedPreferences("profile", 0);
		ShouldPlay = profile.getInt("ShouldPlay", 0);
		music = new Intent();
		music.setClass(this, MusicService.class);

		
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		
		animationFadeOutA = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		animationFadeInA = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animationFadeOutA.setFillEnabled(true);
		animationFadeOutA.setFillAfter(true);
		animationFadeInA.setFillEnabled(true);
		animationFadeInA.setFillAfter(true);
		animationFadeInA.setAnimationListener(animationFadeInAListener);
		animationFadeOutA.setAnimationListener(animationFadeOutAListener);

		animationFadeOutB = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		animationFadeInB = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animationFadeOutB.setFillEnabled(true);
		animationFadeOutB.setFillAfter(true);
		animationFadeInB.setFillEnabled(true);
		animationFadeInB.setFillAfter(true);
		animationFadeInB.setAnimationListener(animationFadeInBListener);
		animationFadeOutB.setAnimationListener(animationFadeOutBListener);

		imageView1.setImageResource(R.drawable.aa_02_02_stars2_720x1280);
		imageView1.startAnimation(animationFadeOutA);
	}

	////////////////////////////////////////ANIM////////////////////////////////////
	AnimationListener animationFadeInAListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			imageView1.startAnimation(animationFadeOutA);
		}
	};

	AnimationListener animationFadeOutAListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			imageView2.setImageResource(R.drawable.aa_02_02_stars2_720x1280);
			imageView2.startAnimation(animationFadeInB);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};

	AnimationListener animationFadeInBListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			imageView2.startAnimation(animationFadeOutB);
		}
	};

	AnimationListener animationFadeOutBListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			imageView1.setImageResource(R.drawable.aa_02_02_stars1_720x1280);
			imageView1.startAnimation(animationFadeInA);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};

	
	////////////////////////////////////////////////////////////////////////////////

	private OnTouchListener btExitLis = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
				btExit.setImageResource(R.drawable.aa_06_03_exit_shine);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				btExit.setImageResource(R.drawable.aa_06_03_exit);

				stopService(music);

				Intent gotoRound = new Intent();
				gotoRound.setClass(Exit.this, ThankU.class);
				startActivity(gotoRound);
				overridePendingTransition(R.anim.long_fade_in,
						R.anim.long_fade_out);
				finish();
			}
			return false;
		}
	};

	private OnTouchListener btNewLis = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
				btNew.setImageResource(R.drawable.aa_06_02_new_shine);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				btNew.setImageResource(R.drawable.aa_06_02_new);

				profile.edit().putInt("ShouldPlay", 1).putInt("have_play", 1)
						.commit();

				Intent gotoRound = new Intent();
				gotoRound.setClass(Exit.this, Player.class);
				startActivity(gotoRound);
				overridePendingTransition(R.anim.long_fade_in,
						R.anim.long_fade_out);
				finish();
			}
			return false;
		}
	};

	private ServiceConnection Scon = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			mServ = ((MusicService.ServiceBinder) binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	};

	void doBindService() {
		bindService(new Intent(this, MusicService.class), Scon,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			unbindService(Scon);
			mIsBound = false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		profile = getSharedPreferences("profile", 0);
		ShouldPlay = profile.getInt("ShouldPlay", 0);

		if (servicestart && ShouldPlay != 1) {
			stopService(music);
			profile.edit().putBoolean("servicestart", false);

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		profile = getSharedPreferences("profile", 0);
		ShouldPlay = profile.getInt("ShouldPlay", 0);
		servicestart = profile.getBoolean("servicestart", false);
		if (!servicestart) {
			startService(music);
			servicestart = true;
			profile.edit().putBoolean("servicestart", true);

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		llBackgroundPanel.getBackground().setCallback(null);
		bmpDrawImg1.getBitmap().recycle();
		bmpDrawImg1 = null;

		
		imageView1.setImageBitmap(null);
        imageView1.destroyDrawingCache();
        
        imageView2.setImageBitmap(null);
        imageView2.destroyDrawingCache();
		System.gc();
	}

}
