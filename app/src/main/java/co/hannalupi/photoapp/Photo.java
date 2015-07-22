package co.hannalupi.photoapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Photo object is created to store the file path,
 * time stamp and thumbnail for each photo taken.
 * The Photo class implements the Parceable -
 * allowing the MainActivity to pass the custom object and
 * restore information from previous application sessions.
 */

public class Photo implements Parcelable{

    private String timeStamp;
    private Bitmap photoThumbnail;
    private String photoFilePath;

    // A new instance of Photo() is created from the MainActivity
    // when a photo's timeStamp, photoFilePath and photoThumnail
    // are passed into Photo()
    public Photo(String timeStamp, String filePath, Bitmap thumbnail) {
        this.timeStamp = timeStamp;
        this.photoFilePath = filePath;
        this.photoThumbnail = thumbnail;
    }

    // Getters allow the data saved in individual Photo instances
    // to be accessed in other classes

    public String getTimeStamp() { return timeStamp; }

    public String getPhotoPath() {
        return photoFilePath;
    }

    public Bitmap getPhotoThumbnail() {
        return photoThumbnail;
    }


    /* Implement methods to use Parceable interface */

    // Creates a Parcel object populated with the Photo object's properties & values
    private Photo(Parcel in) {
        timeStamp = in.readString();
        photoThumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        photoFilePath = in.readString();
    }

    // Write Photo object data to the Parcel passed in
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timeStamp);
        dest.writeValue(photoThumbnail);
        dest.writeString(photoFilePath);
    }

    // Used to regenerate your object
    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {

        // Implement createFromParcel() & newArray()
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
