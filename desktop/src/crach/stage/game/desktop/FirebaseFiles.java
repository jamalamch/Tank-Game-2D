package crach.stage.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.io.File;
import java.io.IOException;

import crach.stage.game.DownloadFiles;

public class FirebaseFiles implements DownloadFiles {

    @Override
    public FileToDownload getFile(String UrlDowload, final Runnable onSuccess, Runnable onFailure) {
        final FileDowload fileDowload = new FileDowload(Gdx.files.internal("maps/stage/stage1.tmx"));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                fileDowload.isSuccess = true;
                onSuccess.run();
            }
        },2.5f);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(!fileDowload.isLoading){
                    fileDowload.isLoading = true;
                    fileDowload.TotalByte = 10;
                }
                fileDowload.BytesTransferred +=1;
            }
        },0,0.25f,9);
        return fileDowload;
    }

    @Override
    public FileToDownload getFile(String UrlDowload) {
        Runnable runnable0 = new Runnable() {
            @Override
            public void run() { }
        };
        return getFile(UrlDowload,runnable0,runnable0);
    }

    @Override
    public Array<String>  getListeNameFiles() {
        return null;
    }

    public class FileDowload implements FileToDownload{
        private FileHandle fileHandle;
        private boolean isSuccess,isLoading,isFailure;
        private long TotalByte,BytesTransferred;
        public FileDowload(){
            try {
                File file = File.createTempFile("stage", "tmx");
                file.deleteOnExit();
                fileHandle = new FileHandle(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public FileDowload(FileHandle fileHandle){
            this.fileHandle = fileHandle;
        }

        @Override
        public FileHandle getFile() {
            return fileHandle;
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }

        @Override
        public boolean isSuccess() {
            return isSuccess;
        }

        @Override
        public boolean isFailure() {
            return isFailure;
        }

        @Override
        public long getTotalByte() {
            return TotalByte;
        }

        @Override
        public long getBytesTransferred() {
            return BytesTransferred;
        }
    }
}
