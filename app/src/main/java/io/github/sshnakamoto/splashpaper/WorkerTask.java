package io.github.sshnakamoto.splashpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Objects;

import io.github.sshnakamoto.splashpaper.json.RootJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkerTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "WorkerTask";
    private WeakReference<Context> weakContext;
    private WeakReference<ImageView> weakImage;
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

        /* API over HTTPS */
        Request jsonRequest = new Request.Builder()
                .url(url[0])
                .build();

        OkHttpClient client = new OkHttpClient();
        Response jsonResponse = null;
        try {
            jsonResponse = client.newCall(jsonRequest).execute();
            if (jsonResponse.isSuccessful()) {

                /* Parse JSON to obj */
                Gson gson = new Gson();
                RootJson rootJson = gson.fromJson(jsonResponse.body().string(), RootJson.class);
                String imageUrl = rootJson.getUrls().getFull();

                /* API over HTTPS */
                Request imageResquest = new Request.Builder()
                        .url(imageUrl)
                        .build();

                Response imageResponse = client.newCall(imageResquest).execute();
                if (imageResponse.isSuccessful()) {

                    /* Converting bytes to file */
                    return BitmapFactory.decodeStream(imageResponse.body().byteStream());
                } else {
                    Log.d(TAG, "doInBackground: Error #130");
                }
            } else {
                Log.d(TAG, "doInBackground: Error #120");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsonResponse != null) {
                jsonResponse.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap wallpaper) {
        super.onPostExecute(wallpaper);

        if (weakContext.get() != null || wallpaper != null) {
            try {
                /* Applying on User UI */
                WallpaperManager.getInstance(weakContext.get()).setBitmap(wallpaper);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(weakContext.get(), "done", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(weakContext.get(), "Error #110: Problem to Download Image", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute: Error #110");
        }
    }

}
