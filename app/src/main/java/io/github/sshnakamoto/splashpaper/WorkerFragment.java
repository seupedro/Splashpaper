package io.github.sshnakamoto.splashpaper;


import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerFragment extends Fragment {

    private static final String TAG = "WorkerFragment";

    String UrlApi = "https://images.unsplash.com/photo-1511736515797-8aab81ec7e35?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=966f3c728ebb930b49192fdbf14b55e0&auto=format&fit=crop&w=1534&q=80";
//    String UrlApi = "https://images.unsplash.com/photo-1535632788826-78ca9d09d2e7?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bb651ad5967f2074c98b8c30ae2fc442&auto=format&fit=crop&w=695&q=80";

    public WorkerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_worker, container, false);

        final ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView textView = rootView.findViewById(R.id.textView);

        WorkerTask task = new WorkerTask(getContext(), imageView, new WallpaperListener() {
            @Override
            public void setWallpaper(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        task.execute(UrlApi);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
