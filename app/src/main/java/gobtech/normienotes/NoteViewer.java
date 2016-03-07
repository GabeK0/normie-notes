package gobtech.normienotes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Gabe K on 1/24/2016.
 */
public class NoteViewer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteviewer);

        gNote note = (gNote) getIntent().getSerializableExtra("note");
        TextView title = (TextView) findViewById(R.id.specifictitle);
        TextView author = (TextView) findViewById(R.id.specificauthor);
        TextView date = (TextView) findViewById(R.id.specificdate);
        //TextView text = (TextView) findViewById(R.id.specifictext);

        title.setText(note.getNoteTitle());
        author.setText("By " + note.getAuthor());
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        date.setText(dt.format(note.getDate()));
        //text.setText(note.getNoteText());
    }
}
