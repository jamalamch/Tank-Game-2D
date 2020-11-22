package crach.stage.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.api.services.drive.DriveScopes;
import java.util.Collections;
import crach.stage.game.CrachGame;
import crach.stage.game.IActivityRequestHandler;
import de.golfgl.gdxgamesvcs.GpgsClient;

public class DesktopLauncher {
	private final String App_Id = "crach.stage.game";
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 700;
		config.height =700;
		config.y=40;
		config.fullscreen=false;
		new LwjglApplication(new CrachGame(new ActivityRequest()) {
	        @Override
	        public void create() {
	            setGsClient(new GpgsClient().initialize("Crach Game th 1979",Gdx.files.internal("gpgs-client_secret.json"),false));
	            setDownloadFiles(new FirebaseFiles());
	            super.create();
	        }

			@Override
			public void dispose() {
	        	update();
				super.dispose();
			}
		}, config);
	}

	
	private static class ActivityRequest implements  IActivityRequestHandler{
		protected Runnable VidioRewarded;
		protected Runnable VidioRewardedFiald;

		@Override
		public void showVideosAds(Runnable Rewarded,Runnable RewardedFaild) {
			VidioRewarded = Rewarded;
			VidioRewardedFiald = RewardedFaild;
			VidesoAdsWatchedFails();
		}
		
		@Override
		public void showAds(boolean show) {	
		}
		public void VidesoAdsWatched() {
			VidioRewarded.run();
		}

		public void VidesoAdsWatchedFails() {
			VidioRewardedFiald.run();
		}
	}

}


