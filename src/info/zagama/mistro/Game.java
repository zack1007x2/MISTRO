package info.zagama.mistro;

import info.zagama.mistro.GestureUtils.Screen;

import java.util.Arrays;

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
import android.os.Handler;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Game extends Activity {

	static Activity Game;
	Animation animation1;
	Animation animation2;
	private SharedPreferences profile;
	private ImageView score1, score2, score3, player1, player2, player3,
			player4;
	private ViewFlipper mViewFlipper, mViewFlipper01, mViewFlipper02;
	private int mSpeed, mSpeed01, mSpeed02;
	private int mCount, mCount01, mCount02;
	private int mFactor, mFactor01, mFactor02;

	private GestureDetector gestureDetector;
	private Screen screen;
	private int allow = 0;
	private int time = 0;
	private int count,target;
	private int[] remain_cycle;
	private int remain_round;
	private int[] cur3_score;
	private int[] accumulate_score;
	private int[] challenge_palyer;
	private int playernum;
	private int cur_player;
	private int press = 0;

	private RelativeLayout Background = null;
	private BitmapDrawable bmpDrawImg0 = null;

	private RelativeLayout player_chg = null;
	private BitmapDrawable bmpDrawImg1 = null;

	private boolean mIsBound = false;
	private MusicService mServ;
	private Intent music;
	private int ShouldPlay;
	private boolean servicestart;
	private SoundPool slot, success, successlow;
	private int slotId, successId, successlowId;
	private int rons;
	
//	private ImageView imageView1, imageView2;
//	private Animation animationFadeInA, animationFadeOutA, animationFadeInB,
//			animationFadeOutB, animationFadeInC, animationFadeOutC,
//			animationFadeInD, animationFadeOutD;
	
	int earlyEnd= 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Game = this;
		SysApplication.getInstance().addActivity(this);
		player_chg = (RelativeLayout) findViewById(R.id.RL_player);
		Background = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		bmpDrawImg0 = new BitmapDrawable(getResources().openRawResource(
				R.drawable.game));
		Background.setBackgroundDrawable(bmpDrawImg0);

		bmpDrawImg1 = new BitmapDrawable(getResources().openRawResource(
				R.drawable.aa_04_02_topfram_01_1080x283));
		player_chg.setBackgroundDrawable(bmpDrawImg1);

		mViewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper00);
		mViewFlipper01 = (ViewFlipper) findViewById(R.id.ViewFlipper01);
		mViewFlipper02 = (ViewFlipper) findViewById(R.id.ViewFlipper02);

		player1 = (ImageView) findViewById(R.id.player1);
		player2 = (ImageView) findViewById(R.id.player2);
		player3 = (ImageView) findViewById(R.id.player3);
		player4 = (ImageView) findViewById(R.id.player4);

		score1 = (ImageView) findViewById(R.id.score1);
		score2 = (ImageView) findViewById(R.id.score2);
		score3 = (ImageView) findViewById(R.id.score3);

		gestureDetector = new GestureDetector(this, onGestureListener);
		//抓取螢幕
		screen = GestureUtils.getScreenPix(this);

		profile = getSharedPreferences("profile",0);
		playernum = profile.getInt("num", 0);
		target = profile.getInt("target", 0);
		
		slot = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
		success = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
		successlow = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
		
		successId = success.load(this, R.raw.success, 2);
		successlowId = successlow.load(this, R.raw.success_low, 2);
		slotId = slot.load(this, R.raw.flip, 2);

		accumulate_score = new int[playernum];
		challenge_palyer = new int[playernum];
		
		//設定分數初始值
		switch(target){
		
			case 27:
		
				if (playernum == 1) {
					player1.setImageResource(R.drawable.a27);
					accumulate_score[0] = 27;
				} else if (playernum == 2) {
					player1.setImageResource(R.drawable.a27);
					player2.setImageResource(R.drawable.a27);
					accumulate_score[0] = 27;
					accumulate_score[1] = 27;
				} else if (playernum == 3) {
					player1.setImageResource(R.drawable.a27);
					player2.setImageResource(R.drawable.a27);
					player3.setImageResource(R.drawable.a27);
					accumulate_score[0] = 27;
					accumulate_score[1] = 27;
					accumulate_score[2] = 27;
				} else if (playernum == 4) {
					player1.setImageResource(R.drawable.a27);
					player2.setImageResource(R.drawable.a27);
					player3.setImageResource(R.drawable.a27);
					player4.setImageResource(R.drawable.a27);
					accumulate_score[0] = 27;
					accumulate_score[1] = 27;
					accumulate_score[2] = 27;
					accumulate_score[3] = 27;
				}
				
				break;
			case 54:
				if (playernum == 1) {
					player1.setImageResource(R.drawable.a54);
					accumulate_score[0] = 54;
				} else if (playernum == 2) {
					player1.setImageResource(R.drawable.a54);
					player2.setImageResource(R.drawable.a54);
					accumulate_score[0] = 54;
					accumulate_score[1] = 54;
				} else if (playernum == 3) {
					player1.setImageResource(R.drawable.a54);
					player2.setImageResource(R.drawable.a54);
					player3.setImageResource(R.drawable.a54);
					accumulate_score[0] = 54;
					accumulate_score[1] = 54;
					accumulate_score[2] = 54;
				} else if (playernum == 4) {
					player1.setImageResource(R.drawable.a54);
					player2.setImageResource(R.drawable.a54);
					player3.setImageResource(R.drawable.a54);
					player4.setImageResource(R.drawable.a54);
					accumulate_score[0] = 54;
					accumulate_score[1] = 54;
					accumulate_score[2] = 54;
					accumulate_score[3] = 54;
				}
				break;
			case 81:
				if (playernum == 1) {
					player1.setImageResource(R.drawable.a81);
					accumulate_score[0] = 81;
				} else if (playernum == 2) {
					player1.setImageResource(R.drawable.a81);
					player2.setImageResource(R.drawable.a81);
					accumulate_score[0] = 81;
					accumulate_score[1] = 81;
				} else if (playernum == 3) {
					player1.setImageResource(R.drawable.a81);
					player2.setImageResource(R.drawable.a81);
					player3.setImageResource(R.drawable.a81);
					accumulate_score[0] = 81;
					accumulate_score[1] = 81;
					accumulate_score[2] = 81;
				} else if (playernum == 4) {
					player1.setImageResource(R.drawable.a81);
					player2.setImageResource(R.drawable.a81);
					player3.setImageResource(R.drawable.a81);
					player4.setImageResource(R.drawable.a81);
					accumulate_score[0] = 81;
					accumulate_score[1] = 81;
					accumulate_score[2] = 81;
					accumulate_score[3] = 81;
				}
				break;
			case 108:
				if (playernum == 1) {
					player1.setImageResource(R.drawable.a108);
					accumulate_score[0] = 108;
				} else if (playernum == 2) {
					player1.setImageResource(R.drawable.a108);
					player2.setImageResource(R.drawable.a108);
					accumulate_score[0] = 108;
					accumulate_score[1] = 108;
				} else if (playernum == 3) {
					player1.setImageResource(R.drawable.a108);
					player2.setImageResource(R.drawable.a108);
					player3.setImageResource(R.drawable.a108);
					accumulate_score[0] = 108;
					accumulate_score[1] = 108;
					accumulate_score[2] = 108;
				} else if (playernum == 4) {
					player1.setImageResource(R.drawable.a108);
					player2.setImageResource(R.drawable.a108);
					player3.setImageResource(R.drawable.a108);
					player4.setImageResource(R.drawable.a108);
					accumulate_score[0] = 108;
					accumulate_score[1] = 108;
					accumulate_score[2] = 108;
					accumulate_score[3] = 108;
				}
				break;
				

		
		}
		remain_cycle = new int[playernum];
		cur3_score = new int[4];
		
		music = new Intent();
		music.setClass(this, MusicService.class);

		profile.edit().putInt("ShouldPlay", 0).commit();
		
		

	}
	

	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();

			float x_limit = screen.widthPixels / 6;
			float y_limit = screen.heightPixels / 6;
			float x_abs = Math.abs(x);
			float y_abs = Math.abs(y);
			if (x_abs >= y_abs) {
				// gesture left or right
				if (x > x_limit || x < -x_limit) {

				}
			} else {
				// gesture down or up
				if (y > y_limit || y < -y_limit) {
					if (y > 0 && allow == 0) {
						// down
						// /////////結�?Player & Round

						if (!Player.Player.isFinishing()) {
							Player.Player.finish();
						}


						if (time == 0) {
							score1.setImageBitmap(null);
							score2.setImageBitmap(null);
							score3.setImageBitmap(null);
						}

						// ///////////////////////////////////////////////////
						allow = 1;
						mCount = (int) (Math.random() * 10) + 30;
						mFactor = (int) 150 / mCount;
						mSpeed = mFactor;
						Handler h1 = new Handler();
						h1.postDelayed(r1, mSpeed);

						mCount01 = (int) (Math.random() * 25) + 30;
						mFactor01 = (int) 150 / mCount01;
						mSpeed01 = mFactor01;
						Handler h2 = new Handler();
						h2.postDelayed(r2, mSpeed01);

						mCount02 = (int) (Math.random() * 40) + 30;
						mFactor02 = (int) 150 / mCount02;
						mSpeed02 = mFactor02;
						Handler h3 = new Handler();
						h3.postDelayed(r3, mSpeed02);

						Handler check = new Handler();
						check.postDelayed(r4, 1000);
						// ////////////////////////////////////////////////////
					}
				}
			}
			return true;
		}

	};

	private Runnable r1 = new Runnable() {

		@Override
		public void run() {
			down();
			slot.play(slotId, 1.0F, 1.0F, 0, 0, 1.0F);
			if (mCount < 1) {
				
			} else {
				Handler h1 = new Handler();
				h1.postDelayed(r1, mSpeed);
			}
		}

	};
	private Runnable r2 = new Runnable() {

		@Override
		public void run() {
			down01();
			slot.play(slotId, 1.0F, 1.0F, 0, 0, 1.0F);
			if (mCount01 < 1) {
				
			} else {
				Handler h2 = new Handler();
				h2.postDelayed(r2, mSpeed01);
			}
		}

	};
	private Runnable r3 = new Runnable() {

		@Override
		public void run() {
			down02();
			slot.play(slotId, 1.0F, 1.0F, 0, 0, 1.0F);
			if (mCount02 < 1) {
				
			} else {
				Handler h3 = new Handler();
				h3.postDelayed(r3, mSpeed02);
			}
		}

	};

	private Runnable r4 = new Runnable() {

		@Override
		public void run() {

			if (mCount < 1 && mCount01 < 1 && mCount02 < 1) {
				cur_player = count % playernum;
				profile = getSharedPreferences("profile",0);
				Editor editor = profile.edit();
				
				cur3_score[time] = mViewFlipper.getDisplayedChild()
						+ mViewFlipper01.getDisplayedChild()
						+ mViewFlipper02.getDisplayedChild();
				
				
				System.out.println("cur3_score[time] = " + cur3_score[time]
						+ "AAAAAAA" + time);
				
//				if(challenge_palyer[cur_player]!=1){
				switch (time) {
				case 0:
					// System.out.println(getResources().getIdentifier("number"+cur3_score[time],
					// "drawable", getPackageName()));
					score1.setImageResource(getResources().getIdentifier(
							"number" + cur3_score[time], "drawable",
							getPackageName()));
					break;
				case 1:
					score2.setImageResource(getResources().getIdentifier(
							"number" + cur3_score[time], "drawable",
							getPackageName()));
					break;
				case 2:
					score3.setImageResource(getResources().getIdentifier(
							"number" + cur3_score[time], "drawable",
							getPackageName()));
					break;
				}

				earlyEnd = accumulate_score[cur_player]-cur3_score[0]-cur3_score[1]-cur3_score[2];
				if(earlyEnd==0){
					accumulate_score[cur_player]=0;
					editor.putInt("winner", cur_player);
					editor.commit();
					win();
				}else if(earlyEnd<0){
					time=3;
				}else{
					time++;
				}
				
//				}
//				else{
//					score1.setImageResource(getResources().getIdentifier(
//							"number" + cur3_score[time], "drawable",
//							getPackageName()));
//					cur3_score[1]=0;
//					cur3_score[2]=0;
//					time = 3;
//				}
				
				//CHANGE PLAYER
				if (time == 3) {
					
					if(cur_player==(playernum-1)){
						rons++;
					}
					
					successlow.play(successlowId, 1.0F, 1.0F, 0, 0, 1.0F);
					allow=1;
					animation1 = new AlphaAnimation(1.0f, 0.0f);
					 animation1.setDuration(500);
					 animation1.setStartOffset(500);
					 

				     animation2 = new AlphaAnimation(0.0f, 1.0f);
				     animation2.setDuration(500);
				     animation2.setStartOffset(1000);
				     
				     Background.setAnimation(animation1);
				     animation1.setAnimationListener(new AnimationListener(){

				         @Override
				         public void onAnimationEnd(Animation arg0) {
				        	 Background.setAnimation(animation2);
				         }

				         @Override
				         public void onAnimationRepeat(Animation arg0) {
				             // TODO Auto-generated method stub

				         }

				         @Override
				         public void onAnimationStart(Animation arg0) {
				             // TODO Auto-generated method stub
				        	 allow=1;
				         }

				     });
				     animation2.setAnimationListener(new AnimationListener(){

				         @Override
				         public void onAnimationEnd(Animation arg0) {
				        	 allow= 0;
				         }

				         @Override
				         public void onAnimationRepeat(Animation arg0) {
				             // TODO Auto-generated method stub

				         }

				         @Override
				         public void onAnimationStart(Animation arg0) {
				             // TODO Auto-generated method stub

				         }

				     });  
					
					cur3_score[3] = cur3_score[0] + cur3_score[1]
							+ cur3_score[2];
					if((accumulate_score[cur_player]-cur3_score[3])>=0){
						accumulate_score[cur_player] -= cur3_score[3];
					}
					

					if(accumulate_score[cur_player]<10){
						challenge_palyer[cur_player]=1;
					}

					switch (count % playernum) {
					case 0:
						player1.setImageResource(getResources().getIdentifier(
								"a" + accumulate_score[cur_player], "drawable",
								getPackageName()));
						
						if(accumulate_score[cur_player]==0){
							editor.putInt("winner", 0);
							editor.commit();
							
							win();
						}
						if (playernum > 1) {
							bmpDrawImg1.getBitmap().recycle();
							bmpDrawImg1 = null;
							bmpDrawImg1 = new BitmapDrawable(
									getResources()
											.openRawResource(
													R.drawable.aa_04_02_topfram_02_1080x283));
							player_chg.setBackgroundDrawable(bmpDrawImg1);
						}
						break;
					case 1:
						player2.setImageResource(getResources().getIdentifier(
								"a" + accumulate_score[cur_player], "drawable",
								getPackageName()));
						
						if(accumulate_score[cur_player]==0){
							editor.putInt("winner", 1);
							editor.commit();
							
							win();
						}

						bmpDrawImg1.getBitmap().recycle();
						bmpDrawImg1 = null;

						if (playernum == 2) {
							bmpDrawImg1 = new BitmapDrawable(
									getResources()
											.openRawResource(
													R.drawable.aa_04_02_topfram_01_1080x283));
							player_chg.setBackgroundDrawable(bmpDrawImg1);
						} else {
							bmpDrawImg1 = new BitmapDrawable(
									getResources()
											.openRawResource(
													R.drawable.aa_04_02_topfram_03_1080x283));
							player_chg.setBackgroundDrawable(bmpDrawImg1);
						}
						break;
					case 2:
						player3.setImageResource(getResources().getIdentifier(
								"a" + accumulate_score[cur_player], "drawable",
								getPackageName()));
						
						if(accumulate_score[cur_player]==0){
							editor.putInt("winner", 2);
							editor.commit();
							win();
						}

						bmpDrawImg1.getBitmap().recycle();
						bmpDrawImg1 = null;

						if (playernum == 3) {
							bmpDrawImg1 = new BitmapDrawable(
									getResources()
											.openRawResource(
													R.drawable.aa_04_02_topfram_01_1080x283));
							player_chg.setBackgroundDrawable(bmpDrawImg1);
						} else {
							bmpDrawImg1 = new BitmapDrawable(
									getResources()
											.openRawResource(
													R.drawable.aa_04_02_topfram_04_1080x283));
							player_chg.setBackgroundDrawable(bmpDrawImg1);
						}

						break;
					case 3:
						player4.setImageResource(getResources().getIdentifier(
								"a" + accumulate_score[cur_player], "drawable",
								getPackageName()));
						
						if(accumulate_score[cur_player]==0){
							editor.putInt("winner", 3);
							editor.commit();
							win();
						}

						bmpDrawImg1.getBitmap().recycle();
						bmpDrawImg1 = null;
						bmpDrawImg1 = new BitmapDrawable(
								getResources()
										.openRawResource(
												R.drawable.aa_04_02_topfram_01_1080x283));
						player_chg.setBackgroundDrawable(bmpDrawImg1);
						break;
					}
					for (int i = 0; i < 4; i++) {
						cur3_score[i] = 0;
					}
					count++;
					time = 0;
					if(rons>10){
						secondaryWin();
					}
				
				}
				allow = 0;
				// cur_score =
				// mViewFlipper.getDisplayedChild()+mViewFlipper01.getDisplayedChild()+mViewFlipper02.getDisplayedChild();

			} else {
				Handler check = new Handler();
				check.postDelayed(r4, 1000);
			}
		}

	};
	
	private void win(){
		success.play(successId, 1.0F, 1.0F, 0, 0, 1.0F);
		profile = getSharedPreferences("profile",0);
		Editor editor = profile.edit();
		editor.putInt("ShouldPlay", 1);
		editor.commit();

		for (int i = 0; i < playernum; i++) {
			profile.edit()
					.putInt("player" + (i + 1),
							accumulate_score[i]).commit();
		}
		
		Intent gotoAward = new Intent();
		gotoAward.setClass(Game.this, Award.class);
		startActivity(gotoAward);
		overridePendingTransition(R.anim.long_fade_in,
				R.anim.long_fade_out);
		finish();
	}
	
	private void secondaryWin(){
		success.play(successId, 1.0F, 1.0F, 0, 0, 1.0F);
		int temp = accumulate_score[0];
		int winner[] = new int[4];
		int win_num = 0;
		for (int i = 0; i < accumulate_score.length; i++) {
			temp = Math.min(temp, accumulate_score[i]); // 利用Math.max()
		}
		int Min = temp;

		Editor editor = profile.edit();
		for (int i = 0; i < accumulate_score.length; i++) {
			editor.putInt("winner" + (i + 1), -1);
			if (accumulate_score[i] == Min) {
				editor.putInt("winner" + (i + 1), i + 1);
				win_num++;
			}
		}

		editor.putInt("win_num", win_num);
		editor.putInt("winner_score", Min);
		editor.putInt("ShouldPlay", 1);
		editor.commit();

		for (int i = 0; i < playernum; i++) {
			profile.edit()
					.putInt("player" + (i + 1),
							accumulate_score[i]).commit();
		}
		
		Intent gotoAward = new Intent();
		gotoAward.setClass(Game.this, Award.class);
		startActivity(gotoAward);
		overridePendingTransition(R.anim.long_fade_in,
				R.anim.long_fade_out);
		finish();
		
//		profile = getSharedPreferences("profile",0);
//		Editor editor = profile.edit();
//		editor.putInt("ShouldPlay", 1);
//		editor.commit();
//
//		for (int i = 0; i < playernum; i++) {
//			profile.edit()
//					.putInt("player" + (i + 1),
//							accumulate_score[i]).commit();
//		}
//		
//		Intent gotoAward = new Intent();
//		gotoAward.setClass(Game.this, Award.class);
//		startActivity(gotoAward);
//		overridePendingTransition(R.anim.long_fade_in,
//				R.anim.long_fade_out);
//		finish();
	}

	private void down() {
		mCount--;
		mSpeed += mFactor;
		Animation outToBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.2f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		outToBottom.setDuration(mSpeed);
		Animation inFromTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.2f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		inFromTop.setDuration(mSpeed);
		mViewFlipper.clearAnimation();
		mViewFlipper.setInAnimation(inFromTop);
		mViewFlipper.setOutAnimation(outToBottom);
		if (mViewFlipper.getDisplayedChild() == 0) {
			mViewFlipper.setDisplayedChild(3);
		} else {
			mViewFlipper.showPrevious();
		}
	}

	private void down01() {
		mCount01--;
		mSpeed01 += mFactor01;
		Animation outToBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		outToBottom.setDuration(mSpeed01);
		Animation inFromTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		inFromTop.setDuration(mSpeed01);
		mViewFlipper01.clearAnimation();
		mViewFlipper01.setInAnimation(inFromTop);
		mViewFlipper01.setOutAnimation(outToBottom);
		if (mViewFlipper01.getDisplayedChild() == 0) {
			mViewFlipper01.setDisplayedChild(3);
		} else {
			mViewFlipper01.showPrevious();
		}
	}

	private void down02() {
		mCount02--;
		mSpeed02 += mFactor02;
		Animation outToBottom = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		outToBottom.setDuration(mSpeed02);
		Animation inFromTop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		inFromTop.setDuration(mSpeed02);
		mViewFlipper02.clearAnimation();
		mViewFlipper02.setInAnimation(inFromTop);
		mViewFlipper02.setOutAnimation(outToBottom);
		if (mViewFlipper02.getDisplayedChild() == 0) {
			mViewFlipper02.setDisplayedChild(3);
		} else {
			mViewFlipper02.showPrevious();
		}
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
		Background.getBackground().setCallback(null);
		player_chg.getBackground().setCallback(null);
		bmpDrawImg0.getBitmap().recycle();
		bmpDrawImg0 = null;
		bmpDrawImg1.getBitmap().recycle();
		bmpDrawImg1 = null;

		score1.setImageBitmap(null);
		score1.destroyDrawingCache();
		score2.setImageBitmap(null);
		score2.destroyDrawingCache();
		score3.setImageBitmap(null);
		score3.destroyDrawingCache();

		player1.setImageBitmap(null);
		player1.destroyDrawingCache();
		player2.setImageBitmap(null);
		player2.destroyDrawingCache();
		player3.setImageBitmap(null);
		player3.destroyDrawingCache();
		player4.setImageBitmap(null);
		player4.destroyDrawingCache();
		
		
//		imageView1.setImageBitmap(null);
//        imageView1.destroyDrawingCache();
//        
//        imageView2.setImageBitmap(null);
//        imageView2.destroyDrawingCache();

		System.gc();
	}

	void leave() {
		Intent gotoRound = new Intent();
		gotoRound.setClass(Game.this, ThankU.class);
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
