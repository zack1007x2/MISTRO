package info.zagama.mistro;

import info.zagama.mistro.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class Welcome extends Activity {

	int count = 0;
	int now = 0;
	private SharedPreferences profile;
	private ImageView imageView1, imageView2;
	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
			animationFadeOutB, animationFadeInC, animationFadeOutC,
			animationFadeInD;
	
	private RelativeLayout llBackgroundPanel = null;
	private BitmapDrawable bmpDrawImg1  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		
		
		
		fnSetBackground();
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);

		
		profile = getSharedPreferences("profile",0);
		Editor editor = profile.edit();
		editor.clear();
		editor.commit();

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

		
		

		imageView1.setImageResource(R.drawable.twinkle1);
		imageView1.startAnimation(animationFadeOutA);
		// imageView2.setImageResource(R.drawable.background_twinkle2);
		// imageView1.setAlpha(1.0f);
		// imageView2.setAlpha(0.0f);
		// imageView1.setAnimation(animationFadeOutB);
		// System.out.println("AAAAAAAAAAAAAAAAAA");
		// imageView2.setAnimation(animationFadeInA);
		// System.out.println("BBBBBBBBBBBBBBBBBBBB");
		mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 5000);
	}
	
	public void fnSetBackground(){
        // ?��?得LinearLayout
        llBackgroundPanel = (RelativeLayout) findViewById(R.id.RelativeLayout0);
        // ?��?該張?��?，並?�置?��??�bmpDrawImg�?
        bmpDrawImg1 = new BitmapDrawable(getResources().openRawResource(R.drawable.wel_background));
        // ???就是設�??��?
        llBackgroundPanel.setBackgroundDrawable(bmpDrawImg1);
    }

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
			imageView2.setImageResource(R.drawable.twinkle2);
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
			imageView1.setImageResource(R.drawable.twinkle3);
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
			imageView2.setImageResource(R.drawable.twinkle1);
			imageView2.startAnimation(animationFadeInA);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}
	};


	private static final int GOTO_MAIN_ACTIVITY = 0;

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case GOTO_MAIN_ACTIVITY:
				Intent gotoPlayer = new Intent();
				gotoPlayer.setClass(Welcome.this, Player.class);
				startActivity(gotoPlayer);
				finish();
				break;

			default:

				break;

			}

		};

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
        System.gc();
    }

}
