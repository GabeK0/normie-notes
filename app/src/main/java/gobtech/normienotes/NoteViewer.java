package gobtech.normienotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by Gabe K on 1/24/2016.
 */
public class NoteViewer extends FragmentActivity {

    private int type;
    private gNote note;

    private TextNoteFragment textFrag;
    private ImageNoteFragment imageFrag;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteviewer);

        note = (gNote) getIntent().getSerializableExtra("note");
        TextView title = (TextView) findViewById(R.id.specifictitle);
        TextView author = (TextView) findViewById(R.id.specificauthor);
        TextView date = (TextView) findViewById(R.id.specificdate);
        TextView up = (TextView) findViewById(R.id.up);
        TextView down = (TextView) findViewById(R.id.down);
        //TextView text = (TextView) findViewById(R.id.specifictext);

        title.setText(note.getNoteTitle());
        author.setText("By " + note.getAuthor());
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        date.setText(dt.format(note.getDate()));
        up.setText("" + note.getUp());
        down.setText("" + note.getDown());


        type = note.getType();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new NotePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //text.setText(note.getNoteText());
    }

    public void download(View view) {
        if (type == 0)
            Toast.makeText(this, "Can only download a picture note!", Toast.LENGTH_SHORT).show();
        else
            imageFrag.downloadPhoto();

    }

    private class NotePagerAdapter extends FragmentStatePagerAdapter {
        public NotePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (type == 0) {
                textFrag = getTextFragment();
                return textFrag;
            } else if (type == 1) {
                imageFrag = getImageFragment();
                return imageFrag;
            } else {
                if (position == 0) {
                    textFrag = getTextFragment();
                    return textFrag;
                } else {
                    imageFrag = getImageFragment();
                    return imageFrag;
                }
            }
        }

        @Override
        public int getCount() {
            if (type == 2)
                return 2;
            return 1;
        }
    }

    private TextNoteFragment getTextFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("text", note.getNoteText());
        TextNoteFragment temp = new TextNoteFragment();
        temp.setArguments(bundle);
        return temp;
    }

    private ImageNoteFragment getImageFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("url", "" + note.getID());
        ImageNoteFragment temp = new ImageNoteFragment();
        temp.setArguments(bundle);
        return temp;
    }


}
