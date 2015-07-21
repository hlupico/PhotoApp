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


public class MainActivity extends ListActivity {

    static final String TAG = "PhotoApp";
    private static final String PHOTOLIST = "photoList";
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int THUMB_DIM = 100;
    private Uri fileUri;
    private File newPhoto;
    private PhotoAdapter mAdapter;
    private static File mediaStorageDir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Entered onCreate");

        //Pass bundle
        if(savedInstanceState != null){
            mPhotoList = savedInstanceState.getParcelableArrayList(PHOTOLIST);
        }

        // Create the adapter to convert the array to views
        mAdapter = new PhotoAdapter(this, mPhotoList);

        //Reference footerview in layoutfile
        TextView footerView = (TextView) findViewById(R.id.footerView);

        //Attach Listener to FooterView
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "Entered footerView.setOnClickListener()");

                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                Log.v(TAG, "FileUri toString: " + fileUri.toString());

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Attach Listener to items in listview

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Photo photoDetail = mAdapter.getPhotoItem(position);

                String photoUri = photoDetail.getPhotoPath();

                Toast.makeText(getApplicationContext(), photoUri, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), PhotoDetail.class);
                intent.putExtra("photoFile", photoUri);

                startActivity(intent);


            }
        });

        // Attach the adapter to a ListView
        setListAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG,"Entered onActivityResult()");

        // Check result code and request code
        // TODO - If user submitted a new photo
        // Create a new PhotoItem from photo Intent
        // and then add it to the adapter

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                updatePhotoList();

            }else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "User Cancelled Image Capture", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();

            }
        }

    }


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

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

        Log.v(TAG, "Entered onSaveInstanceState : fileUri = " + fileUri.toString());
        outState.putParcelableArrayList(PHOTOLIST, mPhotoList);

        super.onSaveInstanceState(outState);
    }


    // Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }


    // Create a File for saving an image or video
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        Environment.getExternalStorageState();

        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PhotoApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("PhotoApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE){

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");

        } else {
            return null;
        }

        Log.v(TAG, "Media File: " + mediaFile.toString());

        return mediaFile;
    }


    private void getPhotos(ArrayList<Photo> photoList) {

        if (photoList != null && mediaStorageDir.exists()) {


        //TODO - Try to get the last photofile from the directory and
        //add that file information to photoList

        newPhoto = lastFileModified(mediaStorageDir.toString());

        Log.v(TAG, "FILE PATH TO NEWEST FILE WITHIN DIRECTORY: " + newPhoto.toString());


            Date date = new Date(newPhoto.lastModified());
            String time = date.toString();

            photoList.add(
                    new Photo(
                            time
                            , newPhoto.getAbsolutePath()
                            , getPhotoThumbnail(newPhoto.getAbsolutePath())
                    )
            );

        }
    }

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
        return choice;
    }


    private Bitmap getPhotoThumbnail(String photoPath) {
        Log.i(TAG, "Get Photo Thumbnails");
        // Get the dimensions of the View
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

    private void updatePhotoList() {
        getPhotos(mPhotoList);
        mAdapter.notifyDataSetChanged();
    }

}
