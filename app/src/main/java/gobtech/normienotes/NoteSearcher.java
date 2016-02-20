package gobtech.normienotes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;



import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.StringRequest;import com.android.volley.toolbox.Volley;import java.util.ArrayList;

/**
 * Created by gabrielk on 2/18/16.
 */

public class NoteSearcher extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesearcher);

        // Construct the data source
        ArrayList<gClass> arrayOfUsers = new ArrayList<gClass>();

        ClassAdapter adapter = new ClassAdapter(this, arrayOfUsers);

        ListView listView = (ListView) findViewById(R.id.mylistview);
        listView.setAdapter(adapter);



        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
