package gobtech.normienotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by gabrielk on 2/23/16.
 */
public class NoteAdapter extends ArrayAdapter<gNote> {

    SimpleDateFormat dt;

    public NoteAdapter(Context context, ArrayList<gNote> notes) {
        super(context, 0, notes);
        dt = new SimpleDateFormat("MM-dd-yyyy hh:mm");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        gNote thisNote = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.noterow, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.notetitle);
        TextView author = (TextView) convertView.findViewById(R.id.noteauthor);
        TextView date = (TextView) convertView.findViewById(R.id.notedate);
        // Populate the data into the template view using the data object
        title.setText(thisNote.getNoteTitle());
        author.setText("By " + thisNote.getAuthor());
        date.setText(dt.format(thisNote.getDate()));
        // Return the completed view to render on screen
        return convertView;

    }




}
