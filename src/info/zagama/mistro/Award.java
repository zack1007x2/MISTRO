package info.zagama.mistro;

import info.zagama.mistro.R;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Award extends Activity {

	static Activity Award;
	private ImageView winner_img, player1, player2, player3, player4,
			score_imgbt;
	private ImageButton imageButtonC;
	private int Fwinner, winner1, winner2, winner3, winner4, winner_score, win_num;
	private int[] player;
	private int[] winner;
	private Gallery mGallery;
	private SharedPreferences profile;
	private RelativeLayout llBackgroundPanel = null;
	private BitmapDrawable bmpDrawImg1 = null;
	private int press = 0;
	private Timer mTimer;
	private TimerTask mTimerTask;
	private Handler mHandler = new Handler();
	private int number;

	private boolean enableRefresh;

	private boolean mIsBound = false;
	private MusicService mServ;
	private Intent music;
	private int ShouldPlay;
	private boolean servicestart;
	
	private SoundPool soundPool;
	private int alertId;

	private ImageView imageView1, imageView2;
	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
			animationFadeOutB, animationFadeInC, animationFadeOutC,
			animationFadeInD, animationFadeOutD;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.award);

		Award = this;
		SysApplication.getInstance().addActivity(this);
		llBackgroundPanel = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		bmpDrawImg1 = new BitmapDrawable(getResources().openRawResource(
				R.drawable.award_background));
		llBackgroundPanel.setBackgroundDrawable(bmpDrawImg1);

		player1 = (ImageView) findViewById(R.id.player1);
		player2 = (ImageView) findViewById(R.id.player2);
		player3 = (ImageView) findViewById(R.id.player3);
		player4 = (ImageView) findViewById(R.id.player4);

		winner_img = (ImageView) findViewById(R.id.winner_img);
		score_imgbt = (ImageView) findViewById(R.id.score_imgbt);
		imageButtonC = (ImageButton) findViewById(R.id.imageButtonC);

		imageButtonC.setOnTouchListener(imageButtonCbtLis);
		profile = getSharedPreferences("profile", 0);
		Fwinner = profile.getInt("winner", 0);
		win_num = profile.getInt("win_num", 0);
		winner_score = profile.getInt("winner_score", 0);
		
		
		winner = new int[4];
		winner[0] = profile.getInt("winner1", -1);
		winner[1] = profile.getInt("winner2", -1);
		winner[2] = profile.getInt("winner3", -1);
		winner[3] = profile.getInt("winner4", -1);
		
		
		// score
		player = new int[4];
		player[0] = profile.getInt("player1", -1);
		player[1] = profile.getInt("player2", -1);
		player[2] = profile.getInt("player3", -1);
		player[3] = profile.getInt("player4", -1);
		
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
		alertId = soundPool.load(this, R.raw.click, 1);

		for (int i = 0; i < 4; i++) {

			if (player[i] != -1) {
				switch (i) {
				case 0:
					player1.setImageResource(getResources().getIdentifier(
							"a" + player[0], "drawable", getPackageName()));
					break;
				case 1:
					player2.setImageResource(getResources().getIdentifier(
							"a" + player[1], "drawable", getPackageName()));
					break;
				case 2:
					player3.setImageResource(getResources().getIdentifier(
							"a" + player[2], "drawable", getPackageName()));
					break;
				case 3:
					player4.setImageResource(getResources().getIdentifier(
							"a" + player[3], "drawable", getPackageName()));
					break;
				}
			}
		}
		
		mGallery = ((Gallery) findViewById(R.id.winner_ga));

		if (win_num > 1) {
			winner_img.setVisibility(8);
			for (int i = 0; i < 4; i++) {

				if (winner[i] != -1 && winner1 == 0) {
					winner1 = winner[i];
				} else if (winner[i] != -1 && winner2 == 0) {
					winner2 = winner[i];
				} else if (winner[i] != -1 && winner3 == 0) {
					winner3 = winner[i];
				} else if (winner[i] != -1 && winner4 == 0) {
					winner4 = winner[i];
				}
			}
			switch (win_num) {

			case 2:
				int[] Pics1 = {
						getResources().getIdentifier("winner_player" + winner1,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner2,
								"drawable", getPackageName()) };
				mGallery.setAdapter(new ImageAdapter(this, Pics1));
				break;
			case 3:

				int[] Pics2 = {
						getResources().getIdentifier("winner_player" + winner1,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner2,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner3,
								"drawable", getPackageName()) };
				mGallery.setAdapter(new ImageAdapter(this, Pics2));
				break;
			case 4:

				int[] Pics3 = {
						getResources().getIdentifier("winner_player" + winner1,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner2,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner3,
								"drawable", getPackageName()),
						getResources().getIdentifier("winner_player" + winner4,
								"drawable", getPackageName()) };
				mGallery.setAdapter(new ImageAdapter(this, Pics3));
				break;
			}

			mGallery.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						return true;
					}
					return false;
				}

			});
			mGallery.setUnselectedAlpha(255);
			mGallery.setFadingEdgeLength(0);
			mGallery.setSpacing(0);
			mGallery.setSelection(Integer.MAX_VALUE / 2);
			mGallery.setAnimationDuration(1000);
		}

		winner_img.setImageResource(getResources().getIdentifier(
				"winner_player" + (Fwinner + 1), "drawable", getPackageName()));

		score_imgbt.setImageResource(getResources().getIdentifier(
				"a" + winner_score, "drawable", getPackageName()));

		profile = getSharedPreferences("profile", 0);
		ShouldPlay = profile.getInt("ShouldPlay", 0);
		music = new Intent();
		music.setClass(this, MusicService.class);

		// anim

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);

		// animation
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

		animationFadeOutC = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		animationFadeInC = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animationFadeOutC.setFillEnabled(true);
		animationFadeOutC.setFillAfter(true);
		animationFadeInC.setFillEnabled(true);
		animationFadeInC.setFillAfter(true);
		animationFadeInC.setAnimationListener(animationFadeInCListener);
		animationFadeOutC.setAnimationListener(animationFadeOutCListener);

		animationFadeOutD = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		animationFadeInD = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animationFadeOutD.setFillEnabled(true);
		animationFadeOutD.setFillAfter(true);
		animationFadeInD.setFillEnabled(true);
		animationFadeInD.setFillAfter(true);
		animationFadeInD.setAnimationListener(animationFadeInDListener);
		animationFadeOutD.setAnimationListener(animationFadeOutDListener);
		imageView1.setImageResource(R.drawable.aa_05_03_stars1_1080x1920);
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
			imageView2.setImageResource(R.drawable.aa_05_03_stars2_1080x1920);
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
			imageView1.setImageResource(R.drawable.aa_05_03_stars3_1080x1920);
			imageView1.startAnimation(animationFadeInC);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
		}
	};

	AnimationListener animationFadeInCListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			imageView1.startAnimation(animationFadeOutC);
		}
	};

	AnimationListener animationFadeOutCListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			imageView2.setImageResource(R.drawable.aa_05_03_stars4_1080x1920);
			imageView2.startAnimation(animationFadeInD);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};

	AnimationListener animationFadeInDListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			imageView2.startAnimation(animationFadeOutD);
		}
	};

	AnimationListener animationFadeOutDListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

			imageView1.setImageResource(R.drawable.aa_05_03_stars1_1080x1920);
			imageView1.startAnimation(animationFadeInA);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};
	// ////////////////////////////////////////////////////////////////////////////////

	private OnTouchListener imageButtonCbtLis = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
				imageButtonC
						.setImageResource(R.drawable.aa_05_06_continue_active);
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				imageButtonC
						.setImageResource(R.drawable.aa_05_06_continue_inactive);

				profile.edit().putInt("ShouldPlay", 1).commit();
				Intent gotoExit = new Intent();
				gotoExit.setClass(Award.this, Exit.class);
				startActivity(gotoExit);
				overridePendingTransition(R.anim.long_fade_in,
						R.anim.long_fade_out);
				finish();
			}
			return false;
		}
	};

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

		winner_img.setImageBitmap(null);
		winner_img.destroyDrawingCache();
		score_imgbt.setImageBitmap(null);
		score_imgbt.destroyDrawingCache();

		player1.setImageBitmap(null);
		player1.destroyDrawingCache();
		player2.setImageBitmap(null);
		player2.destroyDrawingCache();
		player3.setImageBitmap(null);
		player3.destroyDrawingCache();
		player4.setImageBitmap(null);
		player4.destroyDrawingCache();

		System.gc();
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

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
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
		
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}

		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// scroll 3
						mGallery.onScroll(null, null, 3, 0);
						mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					}
				});
			}
		};

		mTimer.schedule(mTimerTask, 3000, 3000);

	}

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

	void leave() {
		Intent gotoRound = new Intent();
		gotoRound.setClass(Award.this, ThankU.class);
		startActivity(gotoRound);
		overridePendingTransition(R.anim.long_fade_in, R.anim.long_fade_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			MenuDialogFragment MenuDialog = MenuDialogFragment.newInstance();
			MenuDialog.show(getFragmentManager(), "menudialog");
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			press++;
			Toast.makeText(this, "按第二次退出程式", Toast.LENGTH_LONG).show();
			MenuDialogFragment MenuDialog = MenuDialogFragment.newInstance();
			MenuDialog.show(getFragmentManager(), "menudialog");
			if (press >= 2)

				leave();
			// SysApplication.getInstance().exit();
		}
		return false;
	}

}
