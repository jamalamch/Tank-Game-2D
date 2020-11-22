package crach.stage.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonWriter;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookCallback;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookError;
import de.tomgrill.gdxfacebook.core.GDXFacebookGraphRequest;
import de.tomgrill.gdxfacebook.core.GDXFacebookSystem;
import de.tomgrill.gdxfacebook.core.JsonResult;
import de.tomgrill.gdxfacebook.core.SignInMode;
import de.tomgrill.gdxfacebook.core.SignInResult;

public  class GdxRequest {
    private static GDXFacebook gdxFacebook;
    private static final String TAG = "gdxFacebook";
    private static final String FB_WALL_MESSAGE = " Play with us  ";
    private static final String FB_WALL_LINK = "http://play.google.com/store/apps/details?id=crach.stage.game";
    private static final String FB_WALL_CAPTION = "CRACH GAME";

    public static void partage(){
        if(gdxFacebook == null)
            gdxFacebook = GDXFacebookSystem.install(new MyFacebookConfig());

        if(gdxFacebook.isSignedIn()) {
            GDXFacebookGraphRequest request = new GDXFacebookGraphRequest().setNode("me/feed").useCurrentAccessToken();
            request.setMethod(Net.HttpMethods.POST);
            request.putField("message", FB_WALL_MESSAGE);
            request.putField("link", FB_WALL_LINK);
            request.putField("caption", FB_WALL_CAPTION);
            gdxFacebook.graph(request, new GDXFacebookCallback<JsonResult>() {
                @Override
                public void onFail(Throwable t) {
                    Gdx.app.error(TAG, "Exception occured while trying to post to user wall.");
                    t.printStackTrace();
                }
                @Override
                public void onCancel() {
                    Gdx.app.debug(TAG, "Post to user wall has been cancelled.");
                }
                @Override
                public void onSuccess(JsonResult result) {
                    Gdx.app.debug(TAG, "Posted to user wall successful.");
                    Gdx.app.debug(TAG, "Response: " + result.getJsonValue().prettyPrint(JsonWriter.OutputType.json, 1));
                }
                @Override
                public void onError(GDXFacebookError error) {
                    Gdx.app.error(TAG, "An error occured while trying to post to user wall:" + error.getErrorMessage());
                }
            });
        }
        else{
             Array<String> permissionsPublish = new Array<String>();
            permissionsPublish.add("publish_actions");
            gdxFacebook.signIn(SignInMode.PUBLISH, permissionsPublish, new GDXFacebookCallback<SignInResult>() {
                @Override
                public void onSuccess(SignInResult result) {
                    Gdx.app.debug(TAG, "SIGN IN (publish permissions): User logged in successfully.");
                }
                @Override
                public void onCancel() {
                    Gdx.app.debug(TAG, "SIGN IN (publish permissions): User canceled login process");
                }
                @Override
                public void onFail(Throwable t) {
                    Gdx.app.error(TAG, "SIGN IN (publish permissions): Technical error occured:");
                    logout();
                    t.printStackTrace();
                }
                @Override
                public void onError(GDXFacebookError error) {
                    Gdx.app.error(TAG, "SIGN IN (publish permissions): Error login: " + error.getErrorMessage());
                    logout();
                }
            });
        }
    }
    private static void logout() {
        gdxFacebook.signOut();
    }
    private static  class MyFacebookConfig extends GDXFacebookConfig{
        public MyFacebookConfig() {
            APP_ID = "2535589819836193";
            PREF_FILENAME = ".fbSession";
        }
    }
}
