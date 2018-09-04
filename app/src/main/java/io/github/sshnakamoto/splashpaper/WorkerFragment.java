package io.github.sshnakamoto.splashpaper;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scottyab.rootbeer.RootBeer;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import io.github.sshnakamoto.splashpaper.json.*;
import io.github.sshnakamoto.splashpaper.json.Urls;

import static io.github.sshnakamoto.splashpaper.UrlParams.UrlUtis.queryRandom;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerFragment extends Fragment {

    //TODO: Verify if is LineageOS and display an one time dialog about bug


    private static final String TAG = "WorkerFragment";
    private WorkerTask task;

    public WorkerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_worker, container, false);

        TextView textView = rootView.findViewById(R.id.textView);
        String osVersion = System.getProperty("os.version");
        int sdkInt = Build.VERSION.SDK_INT;     // API Level
        String device = Build.DEVICE;           // Device
        String model = Build.MODEL;             // Model
        String product = Build.PRODUCT;         // Product
        String root = "NO ROOT";

        RootBeer rootBeer = new RootBeer(getContext());
        if (rootBeer.isRooted()) {
            root = "MAYBE";
            if (rootBeer.isRootedWithoutBusyBoxCheck())
                root = "TRUE";
        }

        String deviceInfo = "ROOT" + " " + root + "\n"
                + "SDK" + " " + sdkInt + "\n"
                + "DEVICE" + " " + device + "\n"
                + "MODEL" + " " + model + "\n"
                + "PRODUCT" + " " + product + "\n\n"
                + "OSV.VERSION" + " " + osVersion;

//        textView.setText(deviceInfo);

        final ImageView imageView = rootView.findViewById(R.id.imageView);
//        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        /* AsyncTask */
        task = new WorkerTask(getContext(), imageView, new WallpaperListener() {
            @Override
            public void setWallpaper(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
//        task.execute(queryRandom("nature"));

        /* WorkManager */
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .setRequiresStorageNotLow(false)
                .build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(DownloadWorker.class, 30, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag("wall")
                .build();

        WorkManager.getInstance().enqueue(workRequest);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
