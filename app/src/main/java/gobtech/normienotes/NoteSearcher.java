package gobtech.normienotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
    ClassAdapter classAdapter;
    NoteAdapter noteAdapter;

    private StringRequest getClasses() {
        return new StringRequest(Request.Method.GET, "http://web.engr.oregonstate.edu/~braune/NormieNotes/JSON/combos.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // All this will work once the JSON service is set up
                        Log.e("DEBUG", response);
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
                        Log.e("DEBUG", response);
                        try {
                            myObj = new JSONObject(response);
                            JSONArray notes = myObj.getJSONArray("Notes");
                            for (int i = 0; i < notes.length(); i++) {
                                JSONObject temp = notes.getJSONObject(i);
                                noteAdapter.add(new gNote(temp.optString("class"), temp.optString("professor"),
                                        temp.optString("title"), "Cool Guy", temp.optString("note"), temp.optLong("timeVal")));
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

        // Construct the data source
        ListView listView = (ListView) findViewById(R.id.mylistview);
        RequestQueue queue = Volley.newRequestQueue(this);

        if (getIntent().getExtras().getBoolean("specificClass")) {
            arrayOfNotes = new ArrayList<gNote>();
            noteAdapter = new NoteAdapter(this, arrayOfNotes);
            listView.setAdapter(noteAdapter);
            queue.add(getNotes());
        } else {

            arrayOfClasses = new ArrayList<gClass>();
            classAdapter = new ClassAdapter(this, arrayOfClasses);
            listView.setAdapter(classAdapter);
            queue.add(getClasses());
        }

    }

    public void classSelected(View view) {
        Intent intent = new Intent(this, NoteSearcher.class);
        intent.putExtra("specificClass", true);
        intent.putExtra("id", (int) view.getTag());
        startActivity(intent);
    }
}