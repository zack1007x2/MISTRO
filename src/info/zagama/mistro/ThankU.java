package info.zagama.mistro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class ThankU extends Activity {

	private Animation animationFadeIn, animationFadeIn1;
	private ImageView background, THX;
	private static final int GOTO_MAIN_ACTIVITY = 0;

	private ImageView imageView1, imageView2;
	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
			animationFadeOutB;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thank_you);
		SysApplication.getInstance().addActivity(this);
		background = (ImageView) findViewById(R.id.IVback);
		THX = (ImageView) findViewById(R.id.IVTHX);

		animationFadeIn = AnimationUtils.loadAnimation(this,R.anim.long_fade_in);
		animationFadeIn1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);

		animationFadeIn1.setAnimationListener(animationFadeInListener);

		background.setImageResource(R.drawable.thank_you2);
		background.startAnimation(animationFadeIn1);

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

		
	}

	// //////////////////////////////////////ANIM////////////////////////////////////
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

	// ////////////////////////////////////////////////////////////////////////////

	AnimationListener animationFadeInListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			THX.setImageResource(R.drawable.aa_07_01_thanku);
			THX.startAnimation(animationFadeIn);
			
			imageView1.setImageResource(R.drawable.aa_02_02_stars1_720x1280);
			imageView1.startAnimation(animationFadeOutA);
			mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 3000);
		}
	};

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case GOTO_MAIN_ACTIVITY:

				SysApplication.getInstance().exit();
				break;

			default:

				break;

			}

		};

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();

		background.setImageBitmap(null);
		background.destroyDrawingCache();

		THX.setImageBitmap(null);
		THX.destroyDrawingCache();

		imageView1.setImageBitmap(null);
		imageView1.destroyDrawingCache();

		imageView2.setImageBitmap(null);
		imageView2.destroyDrawingCache();
		System.gc();
	}

}
