package co.hannalupi.photoapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    static final String TAG = "PhotoApp";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
//    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO - Implement OnClick().
                Log.i(TAG, "Entered footerView.setOnClickListener()");

                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//              fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//              intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG,"Entered onActivityResult()");

        // Check result code and request code
        // TODO - If user submitted a new photo
        // Create a new PhotoItem from photo Intent
        // and then add it to the adapter

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "Photo Result Returned", Toast.LENGTH_LONG).show();

//                PhotoItem mPhotoItem = new PhotoItem(data);
//                mAdapter.add(mPhotoItem);
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

//    public Uri getOutputMediaFileUri() {
//        public static final int MEDIA_TYPE_IMAGE = 1;
//        public static final int MEDIA_TYPE_VIDEO = 2;
//
//        /** Create a file Uri for saving an image or video */
//        private static Uri getOutputMediaFileUri(int type) {
//
//            return Uri.fromFile(getOutputMediaFile(type));
//
//        }
//
//        /**
//        * Create a File for saving an image or video
//        */
//        private static File getOutputMediaFile(int type) {
//            //TODO : CHECK THAT SDCad is mounted
//            //To be safe, you should check that the SDCard is mounted
//            // using Environment.getExternalStorageState() before doing this.
//
//            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), "MyCameraApp");
//            // This location works best if you want the created images to be shared
//            // between applications and persist after your app has been uninstalled.
//
//            // Create the storage directory if it does not exist
//            if (!mediaStorageDir.exists()) {
//                if (!mediaStorageDir.mkdirs()) {
//                    Log.d("MyCameraApp", "failed to create directory");
//                    return null;
//                }
//            }
//
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "IMG_" + timeStamp + ".jpg");
//        }else {
//            return null;
//        }
//
//        return mediaFile;
//
//        }
//    }
}
