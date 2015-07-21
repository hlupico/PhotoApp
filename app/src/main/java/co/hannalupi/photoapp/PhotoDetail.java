package co.hannalupi.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by hannalupico on 7/19/15.
 */
public class PhotoDetail extends Activity {

    final String TAG = "PHOTODETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);

        ImageView photoDetail = (ImageView) findViewById(R.id.photoDetail);

        Intent newIntent= getIntent();
        Bundle bundle = newIntent.getExtras();

        if(bundle != null){

            String photoFilePath = (String) bundle.get("photoFile");
            Log.v(TAG, "photoFilePath to Decode: " + photoFilePath);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath, options);

            photoDetail.setImageBitmap(bitmap);

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

}
