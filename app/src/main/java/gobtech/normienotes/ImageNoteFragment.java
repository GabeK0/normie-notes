package gobtech.normienotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Gabe K on 3/8/2016.
 */
public class ImageNoteFragment extends Fragment {

    NetworkImageView image;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.imagenote, container, false);

        String url = "http://web.engr.oregonstate.edu/~braune/NormieNotes/getImage.php?id=" + getArguments().getString("url");
        Log.e("DEBUG", url);
        //url = "http://i.imgur.com/ms3z5ca.jpg";
        ImageLoader imageLoader;
        imageLoader = MySingleton.getInstance(getContext()).getImageLoader();
        image = (NetworkImageView) rootView.findViewById(R.id.specificimage);
        image.setImageUrl(url, imageLoader);





        return rootView;
    }

//    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm,
//                                    Bitmap.CompressFormat format, int quality) {
//
//        File imageFile = new File(dir, fileName);
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(imageFile);
//
//            bm.compress(format, quality, fos);
//
//            fos.close();
//
//            return true;
//        } catch (IOException e) {
//            Log.e("app", e.getMessage());
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        return false;
//    }


    public void downloadPhoto() {

//        Drawable drawable = image.getDrawable();
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//
//        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "Download");
//        boolean doSave = true;
//        if (!dir.exists()) {
//            doSave = dir.mkdirs();
//        }
//
//        if (doSave) {
//            saveBitmapToFile(dir, "NormieNote" + ".png", bitmap, Bitmap.CompressFormat.PNG, 100);
//            //toast.cancel();
//            Toast.makeText(getActivity(), "Picture successfully saved to Download folder!", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e("DEBUG", "Couldn't create target directory.");
//        }

        image.buildDrawingCache();

        Bitmap bmp = image.getDrawingCache();

        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, "NormieNote" + getArguments().getString("url") + ".jpg");

        try{
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            scanFile(getContext(), Uri.fromFile(file));
            Toast.makeText(getActivity(), "Picture successfully saved to Pictures folder!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private static void scanFile(Context context, Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }
}
