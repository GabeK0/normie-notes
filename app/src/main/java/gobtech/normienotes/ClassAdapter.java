package gobtech.normienotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gabrielk on 2/18/16.
 */
public class ClassAdapter extends ArrayAdapter<gClass> {

    public ClassAdapter(Context context, ArrayList<gClass> classes) {
        super(context, 0, classes);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        gClass thisClass = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.classrow, parent, false);
        }
        // Lookup view for data population
        TextView className = (TextView) convertView.findViewById(R.id.classname);
        TextView professor = (TextView) convertView.findViewById(R.id.professorname);
        // Populate the data into the template view using the data object
        className.setText(thisClass.className);
        professor.setText(thisClass.professor);
        // Return the completed view to render on screen
        return convertView;

    }
}
