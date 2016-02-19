package gobtech.normienotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by gabrielk on 2/11/16.
 */
public class MainMenu extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    }


    public void searchClasses(View view) {
        startActivity(new Intent(this, NoteSearcher.class));
    }
}
