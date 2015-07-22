package co.hannalupi.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Displays the full-sized image of an image from the PhotoApp.
 * When a photo is selected from the ListView,
 * its file path is passed to PhotoDetail.
 */
public class PhotoDetail extends Activity {

    final String TAG = "PHOTODETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the photo_detail
        // photo_detail contains an imageView
        setContentView(R.layout.photo_detail);

        // Create ImageView, photoDetail, from layout item in photo_detail
        ImageView photoDetail = (ImageView) findViewById(R.id.photoDetail);

        // Create a newIntent to receive the intent sent by MainActivity
        // Assign bundle to the extra information, the photoUri, from the intent
        Intent newIntent= getIntent();
        Bundle bundle = newIntent.getExtras();

        // If the intent contains a non-null bundle then proceed
        if(bundle != null){

            // Get the "photoFile" information from the bundle
            String photoFilePath = (String) bundle.get("photoFile");

            // Set options.inSampleSize save memory
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;

            // Create a bitmap from the photoFilePath passed to PhotoDetail
            // taking the BitmapFactory.Options into account
            Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, options);

            // Apply the bitmap image to the imageView
            photoDetail.setImageBitmap(bitmap);

        }
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
