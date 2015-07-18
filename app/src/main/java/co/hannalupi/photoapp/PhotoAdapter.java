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
 * Created by hannalupico on 7/18/15.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {

    // View lookup cache
    private static class ViewHolder {
        TextView textView;
        ImageView photoView;
    }

    public PhotoAdapter(Context context, ArrayList<Photo> photo) {
        super(context, 0, photo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Photo photo = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

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
        // Populate the data into the template view using the data object
        viewHolder.textView.setText(photo.getTimeStamp());
        viewHolder.photoView.setImageBitmap(photo.getPhotoThumbnail());

        // Return the completed view to render on screen
        return convertView;
    }


}
