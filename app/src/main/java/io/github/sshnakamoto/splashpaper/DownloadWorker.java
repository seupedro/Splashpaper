package io.github.sshnakamoto.splashpaper;

import android.app.Notification;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

public class DownloadWorker extends Worker {

    private static final String TAG = "DownloadWorker";
    
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: done"+ " " + Math.random());
        return Result.SUCCESS;
    }
}
