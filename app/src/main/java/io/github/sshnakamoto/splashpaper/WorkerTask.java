package io.github.sshnakamoto.splashpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkerTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "WorkerTask";
    private WeakReference<Context> weakContext;
    private WeakReference<ImageView> weakImage;
    private Bitmap wallpaper;
    private WallpaperListener wallpaperListener;

    WorkerTask(Context context, ImageView imageView, WallpaperListener listener){
        weakContext = new WeakReference<>(context);
        weakImage = new WeakReference<>(imageView);
        wallpaperListener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        Log.d(TAG, "doInBackground: ");

        if (url[0].isEmpty()){
            throw new NullPointerException("URL Bad formatted");
        }

        /* Network */
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url[0])
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();

            /* Generate Image */
            wallpaper = BitmapFactory.decodeStream(
                    Objects.requireNonNull(response.body()).byteStream());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return wallpaper;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        try {
            WallpaperManager.getInstance(weakContext.get()).setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
