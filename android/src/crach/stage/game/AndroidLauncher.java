package crach.stage.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import crach.stage.game.Screen.Tools.ViewBanerAds;
import de.golfgl.gdxgamesvcs.GpgsClient;

public class AndroidLauncher extends AndroidApplication {


	protected AdView adView;
	protected RewardedVideoAd AdVideosView;
	protected Runnable VidioRewarded,VidioRewardFaild;

	protected GpgsClient gpgsClient;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode=true;

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);
		// Do the stuff that initialize() would do for you
		CrachGame crachGame = new CrachGame(new IActivityReques());
		crachGame.setGsClient( gpgsClient =  new GpgsClient().initialize(this, false));
		crachGame.setDownloadFiles(new FirebaseFiles(this));
		MobileAds.initialize(getApplicationContext(), "ca-app-pub-9299941173277338~3704821421");

		// Create the libgdx View
		View gameView = initializeForView(crachGame, config);

		//creat Game service

		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-9299941173277338/9981457684"); // Put in your secret key here
		adView.setAdListener(new BanerAdListener());

		AdRequest.Builder adRequest = new AdRequest.Builder();
		//adRequest.addTestDevice("D9A687854D4CF448B462FD12DAD00827");
		adView.loadAd(adRequest.build());
		//AdVideosView
		AdVideosView =  MobileAds.getRewardedVideoAdInstance(this);
		AdVideosView.setRewardedVideoAdListener(new VideoAdListener());
		loadRewardedVideoAd();


		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		adParams.bottomMargin = 5;
		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
	}
	private void loadRewardedVideoAd() {
		AdRequest.Builder ads = new AdRequest.Builder();
		//ads.addTestDevice("D9A687854D4CF448B462FD12DAD00827");
		AdVideosView.loadAd("ca-app-pub-9299941173277338/5064691479", ads.build());
	}
	@Override
	public void onResume() {
		AdVideosView.resume(this);
		super.onResume();
	}
	@Override
	public void onPause() {
	    CrachGame.update();
		AdVideosView.pause(this);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		AdVideosView.destroy(this);
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (gpgsClient != null)
			gpgsClient.onGpgsActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode,resultCode,data);
	}

	private class IActivityReques implements IActivityRequestHandler{
		private final int SHOW_ADS = 1;
		private final int HIDE_ADS = 0;

		@SuppressLint("HandlerLeak")
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case SHOW_ADS: {
						adView.setVisibility(View.VISIBLE);
						break;
					}
					case HIDE_ADS: {
						adView.setVisibility(View.GONE);
						break;
					}
				}
			}
		};
		// This is the callback that posts a message for the handler
		@Override
		public void showAds ( boolean show){
			handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
		}
		@Override
		public void showVideosAds(Runnable Rewarded,Runnable RewardedFaild) {
			VidioRewarded = Rewarded;
			VidioRewardFaild = RewardedFaild;
			runOnUiThread(new Runnable() {
				public void run() {
					if (AdVideosView.isLoaded()) {
						AdVideosView.show();
					} else {
						AdVideosView.getRewardedVideoAdListener().onRewardedVideoAdClosed();
					}
				}
			});
		}
	}
	protected class VideoAdListener implements  RewardedVideoAdListener{
		@Override
		public void onRewardedVideoAdLoaded() {}

		@Override
		public void onRewardedVideoAdOpened() {}

		@Override
		public void onRewardedVideoStarted() {}

		@Override
		public void onRewardedVideoAdClosed() {
			if(VidioRewardFaild != null) {
				VidioRewardFaild.run();
				Gdx.app.log("Gdx Ads","Closed");
			}
			loadRewardedVideoAd();
			VidioRewarded =VidioRewardFaild = null;
		}

		@Override
		public void onRewarded(RewardItem rewardItem) {
			if(VidioRewarded != null)
				VidioRewarded.run();
			VidioRewardFaild =VidioRewarded= null;
			Gdx.app.log("Gdx Ads","True");
		}
		@Override
		public void onRewardedVideoAdLeftApplication() {}

		@Override
		public void onRewardedVideoAdFailedToLoad(int i) {
			if(VidioRewarded != null) {
				VidioRewardFaild.run();
				Gdx.app.log("Gdx Ads","Failed");
			}
			VidioRewarded = VidioRewardFaild = null;
		}
		@Override
		public void onRewardedVideoCompleted() {}
	}
	protected  class BanerAdListener extends AdListener{
		@Override
		public void onAdLoaded() {
			ViewBanerAds.setIsAdsLoad(true);
			Gdx.app.log("Gdx Ads","banner Load");
		}

		public void onAdOpened() {
			Gdx.app.log("Gdx Ads","banner Opened ");
		}
		public void onAdClosed() {
			//ViewBanerAds.setIsAdsLoad(true);
			Gdx.app.log("Gdx Ads","banner Closed");
		}
		public void onAdFailedToLoad(int var1){
			ViewBanerAds.setIsAdsLoad(false);
			Gdx.app.log("Gdx Ads","banner Faild");
		}
	}
	public boolean isNetworkAvaible(){
		ConnectivityManager Cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		return Cm.getActiveNetworkInfo() != null && Cm.getActiveNetworkInfo().isConnected();
	}
}