package co.hannalupi.photoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * PhotoAdapter answered two questions:
 *      (1) Which array of information, or Photo Objects in this case,
 *          needs to be displayed and
 *      (2) Where should the data be displayed?
 * The PhotoAdapter binds the information from the photos taken
 * with the PhotoApp to individual views in the ListView.
 */

public class PhotoAdapter extends ArrayAdapter<Photo> {

    private ArrayList<Photo> mPhotoList;

    // Construct the instance of PhotoAdapter which is created in the MainActivity
    // The ArrayList<Photo> containing the saved Photo objects
    // is passed to PhotoAdapter to bind the information in ArrayList<Photo> to the listView
    public PhotoAdapter(Context context, ArrayList<Photo> photo) {
        super(context, 0, photo);
        this.mPhotoList = photo;
    }

    // A ViewHolder patter will be used to optimize memory resources
    // By creating a view template -
    // the adapter can cache views instead of continually fetching new ones
    private static class ViewHolder {
        TextView textView;
        ImageView photoView;
    }

    // Get the PhotoItem() at a specific position in the adapter
    // Use getPhotoItem() in MainActivity to retrieve photo file path
    public Photo getPhotoItem(int position)
    {
        return mPhotoList.get(position);
    }


    // Implement the ViewHolder pattern in getView() to optimize memory resources
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position to be rendered
        Photo photo = getItem(position);

        // viewHolder will hold caches view data
        ViewHolder viewHolder;

        // If convertView is empty create a new view, otherwise reuse a view that
        // has already been created
        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.photo_listview, parent, false);

            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.photoView = (ImageView) convertView.findViewById(R.id.photoView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Add data into template view using from the object's
        viewHolder.textView.setText(photo.getTimeStamp());
        viewHolder.photoView.setImageBitmap(photo.getPhotoThumbnail());

        // Return the completed view to render on screen
        return convertView;
    }

}
