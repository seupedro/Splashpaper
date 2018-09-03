package io.github.sshnakamoto.splashpaper;


import android.graphics.Bitmap;
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
        final ImageView imageView = rootView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        task = new WorkerTask(getContext(), imageView, new WallpaperListener() {
            @Override
            public void setWallpaper(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        task.execute(queryRandom("moviment moviment"));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
