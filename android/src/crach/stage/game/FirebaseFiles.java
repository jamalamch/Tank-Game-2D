package crach.stage.game;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FirebaseFiles implements DownloadFiles{
    StorageReference storageRef;
    AndroidLauncher androidLauncher;
    public FirebaseFiles(AndroidLauncher androidLauncher){
         storageRef = FirebaseStorage.getInstance().getReference();
         this.androidLauncher = androidLauncher;
    }
    @Override
    public FileToDownload getFile(String UrlDowload, final Runnable onSuccess, final Runnable onFailure) {
        final FileDowload fileDowload = new FileDowload();

        if(!androidLauncher.isNetworkAvaible()){
            fileDowload.isFailure = true;
            Gdx.app.log("Firebase","No Connection");
            return fileDowload;
        }
        StorageReference fileReference = storageRef.child(UrlDowload);
        fileReference.getFile(fileDowload.fileHandle.file()).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    onSuccess.run();
                    fileDowload.isSuccess = true;
                    Gdx.app.log("Firebase","File is Download");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    onFailure.run();
                    fileDowload.isFailure = true;
                    Gdx.app.log("Firebase","File is NOT Download");
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(!fileDowload.isLoading){
                        if((fileDowload.TotalByte = taskSnapshot.getTotalByteCount())>0)
                             fileDowload.isLoading = true;
                    }
                    fileDowload.BytesTransferred = taskSnapshot.getBytesTransferred();
                }
            });
        return fileDowload;
    }

    @Override
    public FileToDownload getFile(String UrlDowload) {
        Runnable Run0 = new Runnable() {
            @Override
            public void run() {}
        };
            return getFile(UrlDowload,Run0,Run0);
    }

    @Override
    public Array<String> getListeNameFiles() {
        if(!androidLauncher.isNetworkAvaible()){
            Gdx.app.log("Firebase","No Connection");
            return null;
        }
        Array<String> listFiLe = new Array<>();
        List<StorageReference> ListReference= storageRef.listAll().getResult().getItems();
        for(StorageReference reference : ListReference)
            listFiLe.add(reference.getName());

        return listFiLe;
    }

    public class FileDowload implements FileToDownload{
        private FileHandle fileHandle;
        private boolean isSuccess,isLoading,isFailure;
        private long TotalByte,BytesTransferred;
        public FileDowload(){
            try {
                    File file = File.createTempFile("stage", ".tmx");
                    file.deleteOnExit();
                    fileHandle = new FileHandle(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//        } else {
//            mAuth.signInAnonymously().addOnSuccessListener(new  OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    Gdx.app.log("Firebase","onSuccess");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Gdx.app.log("Firebase","onFailure");
//                }
//            });