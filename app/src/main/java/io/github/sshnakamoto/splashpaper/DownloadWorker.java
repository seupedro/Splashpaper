package io.github.sshnakamoto.splashpaper;

import android.app.Notification;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import androidx.work.Worker;
import io.github.sshnakamoto.splashpaper.json.RootJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static io.github.sshnakamoto.splashpaper.UrlParams.UrlUtis.queryRandom;

public class DownloadWorker extends Worker {

    private static final String TAG = "DownloadWorker";

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Work started");

        /* API over HTTPS */
        Request jsonRequest = new Request.Builder()
                .url(queryRandom("nature"))
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
                jsonResponse.close();

                /* API over HTTPS */
                Request imageResquest = new Request.Builder()
                        .url(imageUrl)
                        .build();

                Response imageResponse = client.newCall(imageResquest).execute();
                if (imageResponse.isSuccessful()) {

                    /* Converting bytes to file */
                    Bitmap wallpaper = BitmapFactory.decodeStream(imageResponse.body().byteStream());
                    WallpaperManager.getInstance(getApplicationContext()).setBitmap(wallpaper);

                    imageResponse.close();
                    return Result.SUCCESS;
                } else {
                    Log.d(TAG, "doInBackground: Error #130");
                    imageResponse.close();
                    return Result.RETRY;
                }
            } else {
                Log.d(TAG, "doInBackground: Error #120");
                jsonResponse.close();
                return Result.RETRY;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.RETRY;
        }

    }
}
