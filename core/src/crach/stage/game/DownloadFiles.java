package crach.stage.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public interface DownloadFiles {
    FileToDownload getFile(String urlDowload,Runnable onSuccess,Runnable onFailure);
    FileToDownload getFile(String urlDowload);
    Array<String> getListeNameFiles();
    interface FileToDownload{
        FileHandle getFile();
        boolean isSuccess();
        boolean isFailure();
        boolean isLoading();
        long getTotalByte();
        long getBytesTransferred();
    }
}
