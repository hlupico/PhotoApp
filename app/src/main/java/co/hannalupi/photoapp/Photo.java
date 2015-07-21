package co.hannalupi.photoapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hannalupico on 7/18/15.
 */
public class Photo implements Parcelable{

    private String timeStamp;
    private Bitmap photoThumbnail;
    private String photoFilePath;

    public Photo(String timeStamp, String filePath, Bitmap thumbnail) {
        this.timeStamp = timeStamp;
        this.photoFilePath = filePath;
        this.photoThumbnail = thumbnail;
    }

    private Photo(Parcel in) {
        timeStamp = in.readString();
        photoThumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        photoFilePath = in.readString();
    }

    public String getTimeStamp() { return timeStamp; }

    public String getPhotoPath() {
        return photoFilePath;
    }

    public Bitmap getPhotoThumbnail() {
        return photoThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timeStamp);
        dest.writeValue(photoThumbnail);
        dest.writeString(photoFilePath);
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };


}
