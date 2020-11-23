package crach.stage.game.windows.dialog;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import crach.stage.game.Assets;
import crach.stage.game.CrachGame;
import crach.stage.game.DownloadFiles;
import crach.stage.game.screen.Tools.ProgressUi;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

public abstract class WaitDowload extends DialogGame {
    private ProgressUi progressDowload;
    private DownloadFiles.FileToDownload fileToDownload;
    private boolean isHide;

    public WaitDowload(String FileUrl) {
        super(Assets.jsonStringDialog.getString("dowload"),"special-dialog2",null,false);
        text(new Label("File :" +FileUrl, getSkin(), "font-small", Color.SKY)).bottom().padTop(200).padBottom(50);
        fileToDownload = CrachGame.getDownloadFiles().getFile(FileUrl);
    }
    public void addPrgress(Long Max){
        progressDowload = new ProgressUi(Max, Assets.skinStyle,"B-horizontal",true);
        getContentTable().add(progressDowload).size(200,30).center();
        getContentTable().pack();
    }
    public void update(long Value){
        progressDowload.setValue(Value);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isHide)
            return;

        if(fileToDownload.isLoading()){
            if(fileToDownload.isSuccess()) {
                isHide = true;
                this.hide(Actions.sequence(fadeOut(0.3f, Interpolation.fade),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess();
                    }
                })));
            }
            if(progressDowload == null){
                if(fileToDownload.getTotalByte() > 0)
                     addPrgress(fileToDownload.getTotalByte());
            }
            else {
                progressDowload.setValue(fileToDownload.getBytesTransferred());
            }
        }
        if(fileToDownload.isFailure()){
            this.hide();
            this.onFailure();
        }
    }
    public abstract void onSuccess();
    public abstract void onFailure();

    public DownloadFiles.FileToDownload getFileToDownload() {
        return fileToDownload;
    }
    public FileHandle getFile(){
        return fileToDownload.getFile();
    }
}
