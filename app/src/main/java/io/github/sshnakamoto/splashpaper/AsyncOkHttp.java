package io.github.sshnakamoto.splashpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncOkHttp extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "AsyncOkHttp";
    private final WeakReference<Activity> weakReference;
    private final WeakReference<ImageView> weakReferenceImage;
    private Response response;


    public AsyncOkHttp(Activity activity, ImageView image){
        weakReference = new WeakReference<>(activity);
        weakReferenceImage = new WeakReference<>(image);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        /* Requisition */
        Request request = new Request.Builder()
                .url(strings[0])
                .build();

        /* Client */
        OkHttpClient client = new OkHttpClient();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (weakReference.get() != null){
            try {
                WallpaperManager.getInstance(weakReference.get()).setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (weakReferenceImage.get() != null){
                ImageView imageView = weakReferenceImage.get();

                if (imageView != null){
                    imageView.setImageBitmap(bitmap);

                }
            }
        }

    }
}
