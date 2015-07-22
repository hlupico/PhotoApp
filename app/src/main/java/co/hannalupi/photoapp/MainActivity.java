package co.hannalupi.photoapp;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * The MainActivity is responsible for setting up the main UI,
 * handling the interactions the user has with the application,
 * creating files within the PhotoApp subdirectory to store new photos and
 * updating the ListView to display new photo information.
 */

public class MainActivity extends ListActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int THUMB_DIM = 100;
    private static final String TAG = "PhotoApp";
    private static final String PHOTOLIST = "photoList";

    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();
    private Uri fileUri;
    private File newPhoto;
    private PhotoAdapter mAdapter;
    private static File mediaStorageDir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Pass information saved from previous application sessions to superclass
        super.onCreate(savedInstanceState);

        // Set the layout for the MainActivity to be activity_main
        // activity_main contains a listView and textView
        setContentView(R.layout.activity_main);

        // If photo information from previous application sessions was saved,
        // fill the empty ArrayList<Photo> with the saved array
        // See onSavedInstanceState() below for more information regarding
        // the information saved before the application is stopped
        if(savedInstanceState != null){
            mPhotoList = savedInstanceState.getParcelableArrayList(PHOTOLIST);
        }

        // Create an adapter to convert the array to views
        // that will populate the listView
        mAdapter = new PhotoAdapter(this, mPhotoList);

        // Create the footerView from the textView within activity_main.xml
        TextView footerView = (TextView) findViewById(R.id.footerView);

        // Set Listener to footerView to handle click events
        // If the footerView is 'clicked' the user is brought to an
        // camera application that already exists on the device
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* The process for starting the camera application is based on the
                    procedure outlined by the Android Developer API Guides

                    http://developer.android.com/guide/topics/media/camera.html
                */

                // An Intent is used to start the camera application
                // Create new Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Create a file to save the image
                // See getOutputMediaFileUri() helper method below
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                // Set the image file name
                // Because MediaStore.EXTRA_OUTPUT is used, when a user navigates to the MainActivity
                // from the camera application, a null intent will be returned
                // However the photo will be saved to the Uri sent with the intent
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                // Start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Attach Listener to items in listview


        // Set Listener for items within the listView to handle click events
        // If an item in the listView is 'clicked' the user is brought to the
        // PhotoDetail Acitivity which displays the full-sized photo
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Set photoDetail to the Photo object associated with the listView item selected
                Photo photoDetail = mAdapter.getPhotoItem(position);

                // Get the file path for the photo object selected
                String photoUri = photoDetail.getPhotoPath();

                // An Intent is used to start the PhotoDetail Activity
                // Create a new intent, passing the file path from the photo
                // object to the new activity, PhotoDetail
                Intent intent = new Intent(getApplicationContext(), PhotoDetail.class);
                intent.putExtra("photoFile", photoUri);

                // Start the intent - show the full-sized photo view
                startActivity(intent);
            }
        });

        // Attach the adapter to a ListView
        // Bind the data available to the adapter
        setListAdapter(mAdapter);
    }


    // When the user navigates back to the MainActivity
    // from the camera application onActivityResult is called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Pass parameters to superclass
        super.onActivityResult(requestCode, resultCode, data);

        // Check that result code and request code are returned from camera activity
        // If the result code and request code were returned - updatePhotoList()
        // to show the new photo thumbnail and timestamp information in the listView
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // See updatePhotoList() helper method below
                updatePhotoList();

            // Display message for user if the activity was canceled
            }else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "User Cancelled Image Capture", Toast.LENGTH_LONG).show();

            // Display message for user if the activity failed
            } else {
                Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();

            }
        }
    }

    // Photo objects that are currently stored in mPhotoList are put into the
    // Bundle in onSaveInstanceState() which is called before the application's onStop()
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelableArrayList(PHOTOLIST, mPhotoList);
        super.onSaveInstanceState(outState);
    }

    /* The procedure for creating and saving image files while using a camera application
        (within the getOutputMediaFileUri() & getOutputMediaFile() helper methods)
        was adopted from the procedure outlined by the Android Developer API Guides

        See http://developer.android.com/guide/topics/media/camera.html#saving-media
    */

    // Create a file Uri for saving an image or video
    // Called when a user 'clicks' the footerView
    // the file Uri returned to the photo is associated withe
    // the photo that is to be taken
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    // Create a File for saving an image or video by
    // creating (1) the storage directory (2) the individual file name
    private static File getOutputMediaFile(int type){

        // Return the primary external storage directory.
        Environment.getExternalStorageState();

        // (1) Create storage directory, PhotoApp, within: Primary External Storage > Pictures
        // This will enable the user to access the photos even if the application is uninstalled.
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PhotoApp");

        // Check that the mediaStorageDir was created
        // Create the storage directory if it does not exist
        // Notify the user if the application could not create the directory
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("PhotoApp", "failed to create directory");
                return null;
            }
        }

        // (2) Create a media file name
        // The mediaFile is created within the PhotoApp subdirectory
        // and is identified by its timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        // return the file name created for the photo to be taken to getOutputMediaFileUri()
        return mediaFile;
    }

    // Called from onActivityResult() if the correct result code and request codes
    // were returned from the camera application
    private void updatePhotoList() {

        // Get the most current ArrayList<Photos>
        // See the helper method, getPhotos(), below
        getPhotos(mPhotoList);

        // Notify the adapter that new information has been added to the ArrayList<Photos>
        mAdapter.notifyDataSetChanged();
    }

    // Called from when updatePhotoList() is needed
    private void getPhotos(ArrayList<Photo> photoList) {

        // Check that Arraylist<Photo> and PhotoApp subdirectory exist
        // before creating new Photo objects and adding them to the photoList
        if (photoList != null && mediaStorageDir.exists()) {

            // Create new file from the lastFileModified() in the PhotoApp subdirectory
            // See the lastFileModified() helper method below
            newPhoto = lastFileModified(mediaStorageDir.toString());

            // Get the timestamp for the newPhoto that was created
            Date date = new Date(newPhoto.lastModified());
            String time = date.toString();

            // Create a new Photo object by passing the
            // timestamp, absolutefilePath, and PhotoThumbnail to the Photo class
            // Add the new Photo object to the ArrayList<Photo>, photoList
            // See getPhotoThumbnail() helper method below
            photoList.add(
                    new Photo(
                            time
                            , newPhoto.getAbsolutePath()
                            , getPhotoThumbnail(newPhoto.getAbsolutePath())
                    )
            );

        }
    }

    // Search for the lastFileModified within the PhotoApp subdirectory
    public static File lastFileModified(String dir) {

        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });

        long lastMod = Long.MIN_VALUE;
        File choice = null;

        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }

        // Return the lastFileModified to the getPhotos() so that the methods knows
        // which file is to be used to create a Photo object
        return choice;
    }

    // Called to from updatePhotos() to construct a thumbnail Bitmap for the
    // most recent photo taken
    private Bitmap getPhotoThumbnail(String photoPath) {

        // Get the dimensions of the ImageView for the photo thumbnail
        // Using THUMB_DIM = 100
        int targetW = THUMB_DIM;
        int targetH = THUMB_DIM;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    /* Default Android methods below */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
