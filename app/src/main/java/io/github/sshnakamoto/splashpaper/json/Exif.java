
package io.github.sshnakamoto.splashpaper.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exif {

    @SerializedName("make")
    @Expose
    private String make;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("exposure_time")
    @Expose
    private String exposureTime;

    @SerializedName("aperture")
    @Expose
    private String aperture;

    @SerializedName("focal_length")
    @Expose
    private String focalLength;

    @SerializedName("iso")
    @Expose
    private Integer iso;

}
