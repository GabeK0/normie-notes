package gobtech.normienotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Gabe K on 3/7/2016.
 */
public class TextNoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.textnote, container, false);

        TextView noteText = (TextView) rootView.findViewById(R.id.specifictext);
        noteText.setText(getArguments().getString("text"));

        return rootView;
    }
}
