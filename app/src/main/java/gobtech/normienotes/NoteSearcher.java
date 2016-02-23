package gobtech.normienotes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
    JSONArray classes;
    ArrayList<gClass> arrayOfUsers;
    ClassAdapter adapter;


    private StringRequest getClasses() {
        return new StringRequest(Request.Method.GET, "http://web.engr.oregonstate.edu/~braune/NormieNotes/JSON/combos.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // All this will work once the JSON service is set up
                        Log.e("DEBUG", response);
                        try {
                            myObj = new JSONObject(response);
                            classes = myObj.getJSONArray("Combos");
                            for (int i = 0; i < classes.length(); i++) {
                                JSONObject temp = classes.getJSONObject(i);
                                adapter.add(new gClass(temp.optInt("id"), temp.optString("class"), temp.optString("professor")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
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
        arrayOfUsers = new ArrayList<gClass>();

        adapter = new ClassAdapter(this, arrayOfUsers);

        ListView listView = (ListView) findViewById(R.id.mylistview);
        listView.setAdapter(adapter);


        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(getClasses());
    }
}