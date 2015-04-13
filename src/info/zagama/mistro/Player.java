package info.zagama.mistro;


import com.google.android.gms.ads.*;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Player extends Activity{
	
	static Activity Player;
	
	private ImageButton btn1,btn2,btn3,btn4;
	private SoundPool soundPool;
	private int alertId;
	private SharedPreferences profile;
	private RelativeLayout llBackgroundPanel = null;
	private BitmapDrawable bmpDrawImg1  = null;
	private int have_play = 0;
	private int press = 0;
	private int ShouldPlay;
	
	private boolean mIsBound = false;
	private MusicService mServ;
	private Intent music;
	
	private ImageView imageView1, imageView2;
	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
			animationFadeOutB, animationFadeInC, animationFadeOutC;
	
	//ADs
	private static final String LOG_TAG = "Player";
	/** Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "a1533936f093bc0";

	  /** The interstitial ad. */
	  private InterstitialAd interstitialAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Player=this;
		SysApplication.getInstance().addActivity(this);  
		fnSetBackground();
		
		// Create the interstitial.
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID);

		 AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

         // Optionally populate the ad request builder.
         adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

         // Set an AdListener.
         interstitialAd.setAdListener(new AdListener() {
             @Override
             public void onAdLoaded() {
                 Toast.makeText(Player.this,
                         "The interstitial is loaded", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onAdClosed() {
                 // Proceed to the next level.

             }
         });

         // Start loading the ad now so that it is ready by the time the user is ready to go to
         // the next level.
         interstitialAd.loadAd(adRequestBuilder.build());
         interstitialAd.setAdListener(new AdListener(){
             public void onAdLoaded(){
                  displayInterstitial();
             }
   });
         ////////////////////////////////////////////////

		
		
		profile = getSharedPreferences("profile",0);
		have_play = profile.getInt("have_play", 0);
		
		
		if(have_play ==0){
		AlertDialogFragment cdf = AlertDialogFragment.newInstance();
		cdf.show(getFragmentManager(), "alertdialog"); 
		
		}
		
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		btn1 = (ImageButton) findViewById(R.id.imageButton1);
		btn2 = (ImageButton) findViewById(R.id.imageButton2);
		btn3 = (ImageButton) findViewById(R.id.imageButton3);
		btn4 = (ImageButton) findViewById(R.id.imageButton4);
		
		btn1.setOnTouchListener(btn1Lis);
		btn2.setOnTouchListener(btn2Lis);
		btn3.setOnTouchListener(btn3Lis);
		btn4.setOnTouchListener(btn4Lis);
		
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
	    alertId = soundPool.load(this, R.raw.click,1);
	    
	    
	    profile = getSharedPreferences("profile",0);
        ShouldPlay =profile.getInt("ShouldPlay", 0);
        music = new Intent();
		music.setClass(this,MusicService.class);
		
		
		
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

		
		imageView1.setImageResource(R.drawable.aa_02_02_stars1_720x1280);
		imageView1.startAnimation(animationFadeOutA);
////////////////////////////////////////////
		
	}
	
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
	     binder) {
		mServ = ((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}
	public void fnSetBackground(){
        // ��敺inearLayout
        llBackgroundPanel = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        // ��閰脣撐��嚗蒂�曄蔭�刻��筮mpDrawImg銝�
        bmpDrawImg1 = new BitmapDrawable(getResources().openRawResource(R.drawable.player_background));
        // ���撠望閮剖���
        llBackgroundPanel.setBackgroundDrawable(bmpDrawImg1);
    }
	
	
	//////////////////////////////////////////ANIM////////////////////////////////////
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
			imageView1.setImageResource(R.drawable.aa_02_02_stars3_720x1280);
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
			imageView2.setImageResource(R.drawable.aa_02_02_stars1_720x1280);
			imageView2.startAnimation(animationFadeInA);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};

	//////////////////////////////////////////////////////////////////////////////////

	private OnTouchListener btn1Lis = new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){ 
					soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
		        	btn1.setImageResource(R.drawable.btn_num1_act);
		        }  
		        if (event.getAction() == MotionEvent.ACTION_UP){ 
		        	btn1.setImageResource(R.drawable.btn_num1_inact);
		        	
		        	profile = getSharedPreferences("profile",0);
		    		profile.edit()
		    		.putInt("num", 1)
		    		.putInt("ShouldPlay", 1).commit();
		    		
		    		Intent gotoRound = new Intent();
		    		gotoRound.setClass(Player.this, Target.class);
					startActivity(gotoRound);
					overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
					finish();
		        }
		        return false;
			}
	};
	
	private OnTouchListener btn2Lis = new OnTouchListener(){
		

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){ 
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
	        	btn2.setImageResource(R.drawable.btn_num2_act);
	        }  
	        if (event.getAction() == MotionEvent.ACTION_UP){ 
	        	btn2.setImageResource(R.drawable.btn_num2_inact);
	        	
	        	profile = getSharedPreferences("profile",0);
	    		profile.edit()
	    		.putInt("num", 2)
	    		.putInt("ShouldPlay", 1).commit();
	    		
	    		Intent gotoRound = new Intent();
	    		gotoRound.setClass(Player.this, Target.class);
				startActivity(gotoRound);
				overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
				finish();
				
	        }
	        
			return false;
		}
	};
	
private OnTouchListener btn3Lis = new OnTouchListener(){
		

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){ 
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
	        	btn3.setImageResource(R.drawable.btn_num3_act);
	        }  
	        if (event.getAction() == MotionEvent.ACTION_UP){ 
	        	btn3.setImageResource(R.drawable.btn_num3_inact);
	        	
	        	profile = getSharedPreferences("profile",0);
	    		profile.edit()
	    		.putInt("num", 3)
	    		.putInt("ShouldPlay", 1).commit();
	    		
	    		Intent gotoRound = new Intent();
	    		gotoRound.setClass(Player.this, Target.class);
				startActivity(gotoRound);
				overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
				finish();
	        }
	        
			return false;
		}
	};
	
private OnTouchListener btn4Lis = new OnTouchListener(){
		

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN){  
				soundPool.play(alertId, 1.0F, 1.0F, 0, 0, 1.0F);
	        	btn4.setImageResource(R.drawable.btn_num4_act);
	        }  
	        if (event.getAction() == MotionEvent.ACTION_UP){ 
	        	btn4.setImageResource(R.drawable.btn_num4_inact);
	        	
	        	profile = getSharedPreferences("profile",0);
	    		profile.edit()
	    		.putInt("num", 4)
	    		.putInt("ShouldPlay", 1).commit();
	    		
	    		Intent gotoRound = new Intent();
	    		gotoRound.setClass(Player.this, Target.class);
				startActivity(gotoRound);
				overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
				finish();
	        }
	        
			return false;
		}
	};
	
	@Override
    protected void onPause() {
        super.onPause(); 
        profile = getSharedPreferences("profile",0);
        ShouldPlay = profile.getInt("ShouldPlay", 0);
        if(ShouldPlay ==0){
        	stopService(music);
        }
        
    }
	@Override
	protected void onResume() {
		super.onResume();
		profile = getSharedPreferences("profile",0);
		ShouldPlay = profile.getInt("ShouldPlay", 0);

		if(ShouldPlay ==0){
		startService(music);
		}
		
		profile.edit()
		.putInt("ShouldPlay", 0).commit();
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
	
	void leave(){
		Intent gotoRound = new Intent();
		gotoRound.setClass(Player.this, ThankU.class);
		startActivity(gotoRound);
		overridePendingTransition(R.anim.long_fade_in, R.anim.long_fade_out);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		        if(keyCode == KeyEvent.KEYCODE_MENU){
		        	MenuDialogFragment MenuDialog = MenuDialogFragment.newInstance();
		        	MenuDialog.show(getFragmentManager(), "menudialog"); 
		        }
		        else if(keyCode == KeyEvent.KEYCODE_BACK){
		        	press++;
		        	MenuDialogFragment MenuDialog = MenuDialogFragment.newInstance();
		        	MenuDialog.show(getFragmentManager(), "menudialog");
		        	Toast.makeText(this, "按第二次退出", Toast.LENGTH_LONG).show();
		        	if(press>=2)
		        		leave();
//		        	SysApplication.getInstance().exit(); 
		        }
				return false;
	}
	
	// Invoke displayInterstitial() when you are ready to display an interstitial.
	  public void displayInterstitial() {
	    if (interstitialAd.isLoaded()) {
	    	interstitialAd.show();
	    }
	  }
}
