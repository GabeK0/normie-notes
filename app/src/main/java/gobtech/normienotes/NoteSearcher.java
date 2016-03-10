package gobtech.normienotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gabrielk on 2/18/16.
 */

public class NoteSearcher extends Activity {

    JSONObject myObj;
    //JSONArray classes;
    ArrayList<gClass> arrayOfClasses;
    ArrayList<gNote> arrayOfNotes;

    private LinearLayout whyJustWhy;
    private ListView listView;
    private DrawerLayout drawer;
    ClassAdapter classAdapter;
    NoteAdapter noteAdapter;
    private Boolean modeSpecific;
    private EditText searchBox;

    private StringRequest getClasses() {
        return new StringRequest(Request.Method.GET, "http://web.engr.oregonstate.edu/~braune/NormieNotes/JSON/combos.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // All this will work once the JSON service is set up
                        //Log.e("DEBUG", response);
                        try {
                            myObj = new JSONObject(response);
                            JSONArray classes = myObj.getJSONArray("Combos");
                            for (int i = 0; i < classes.length(); i++) {
                                JSONObject temp = classes.getJSONObject(i);
                                classAdapter.add(new gClass(temp.optInt("id"), temp.optString("class"), temp.optString("professor")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        classAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private StringRequest getNotes() {
        return new StringRequest(Request.Method.GET, "http://web.engr.oregonstate.edu/~braune/NormieNotes/JSON/notes.php?id="
                + getIntent().getExtras().getInt("id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // All this will work once the JSON service is set up
                        //Log.e("DEBUG", response);
                        try {
                            myObj = new JSONObject(response);
                            JSONArray notes = myObj.getJSONArray("Classes");
                            for (int i = 0; i < notes.length(); i++) {
                                JSONObject temp = notes.getJSONObject(i);
                                noteAdapter.add(new gNote(temp.optInt("id"), temp.optString("class"), temp.optString("professor"),
                                        temp.optString("title"), temp.optString("user"), temp.optString("note"), temp.optInt("ups"), temp.optInt("downs"), temp.optLong("timeVal"), temp.optInt("type")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        noteAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesearcher);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        whyJustWhy = (LinearLayout) findViewById(R.id.whyjustwhy);
        TextView headerText = (TextView) findViewById(R.id.headertext);
        searchBox = (EditText) findViewById(R.id.searchbox);
        // Construct the data source
        listView = (ListView) findViewById(R.id.mylistview);
        RequestQueue queue = Volley.newRequestQueue(this);

        if (getIntent().getExtras().getBoolean("specificClass")) {
            modeSpecific = true;
            headerText.setText("Search notes...");
            arrayOfNotes = new ArrayList<gNote>();
            noteAdapter = new NoteAdapter(this, arrayOfNotes);
            listView.setAdapter(noteAdapter);
            queue.add(getNotes());
        } else {
            modeSpecific = false;
            headerText.setText("Search classes...");
            arrayOfClasses = new ArrayList<gClass>();
            classAdapter = new ClassAdapter(this, arrayOfClasses);
            listView.setAdapter(classAdapter);
            queue.add(getClasses());
        }

    }

    public void classSelected(View view) {
        Intent intent;

        if (!drawer.isDrawerOpen(whyJustWhy)) {
            if (modeSpecific) {
                intent = new Intent(this, NoteViewer.class);
                intent.putExtra("note", arrayOfNotes.get((int) view.getTag()));
            } else {
                intent = new Intent(this, NoteSearcher.class);
                intent.putExtra("specificClass", true);
                intent.putExtra("id", (int) view.getTag());
            }
            startActivity(intent);
        } else {
            drawer.closeDrawer(whyJustWhy);
            hideKeyboard(this);
        }
    }

    public void searchEntered(View view) {
        drawer.closeDrawer(whyJustWhy);
        hideKeyboard(this);
        if (!searchBox.getText().toString().equals("")) {
            String term = searchBox.getText().toString();
            if (modeSpecific) {
                ArrayList<gNote> searchNotes = new ArrayList<gNote>();
                for (int i = 0; i < arrayOfNotes.size(); i++) {
                    if (arrayOfNotes.get(i).getNoteTitle().contains(term) || arrayOfNotes.get(i).getAuthor().contains(term))
                        searchNotes.add(arrayOfNotes.get(i));
                }
                noteAdapter = new NoteAdapter(this, searchNotes);
                noteAdapter.notifyDataSetChanged();
                listView.setAdapter(noteAdapter);
            } else {
                ArrayList<gClass> searchClasses = new ArrayList<gClass>();
                for (int i = 0; i < arrayOfClasses.size(); i++) {
                    if (arrayOfClasses.get(i).className.contains(term) || arrayOfClasses.get(i).professor.contains(term))
                        searchClasses.add(arrayOfClasses.get(i));
                }
                classAdapter = new ClassAdapter(this, searchClasses);
                classAdapter.notifyDataSetChanged();
                listView.setAdapter(classAdapter);
            }
        } else {
            refresh(new View(this));
        }


    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void toggleDrawer(View view) {
        if (drawer.isDrawerOpen(whyJustWhy))
            drawer.closeDrawer(whyJustWhy);
        else
            drawer.openDrawer(whyJustWhy);
    }

    public void refresh(View view) {
        if (modeSpecific) {
            noteAdapter = new NoteAdapter(this, arrayOfNotes);
            listView.setAdapter(noteAdapter);
        } else {
            classAdapter = new ClassAdapter(this, arrayOfClasses);
            listView.setAdapter(classAdapter);
        }
    }
}