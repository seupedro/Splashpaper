package io.github.sshnakamoto.splashpaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.parent_view, new WorkerFragment())
                .commit();

        if (BuildConfig.DEBUG) {
            // Calling this from your launcher activity is enough, but I needed a good example spot ;)
            DebugUtils.riseAndShine(this);
        }

    }

    private void workerSetup(){

        /* Pediodic Work */
        PeriodicWorkRequest.Builder workRequest =
                new PeriodicWorkRequest.Builder(DownloadWorker.class, 12, TimeUnit.SECONDS);

        Constraints workConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest workCheck = workRequest.setConstraints(workConstraints).build();

        /* Enqueue */
        WorkManager.getInstance().enqueue(workCheck);

    }
}
