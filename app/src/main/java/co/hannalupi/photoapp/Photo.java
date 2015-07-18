package co.hannalupi.photoapp;

import android.graphics.Bitmap;

/**
 * Created by hannalupico on 7/18/15.
 */
public class Photo{

    private String timeStamp;
    private Bitmap photoThumbnail;
    private String photoFilePath;

    public Photo(String timeStamp, String filePath, Bitmap thumbnail) {
        this.timeStamp = timeStamp;
        this.photoFilePath = filePath;
        this.photoThumbnail = thumbnail;
    }

    public String getTimeStamp() { return timeStamp; }

    public String getphotoPath() {
        return photoFilePath;
    }

    public Bitmap getPhotoThumbnail() {
        return photoThumbnail;
    }
    
}
