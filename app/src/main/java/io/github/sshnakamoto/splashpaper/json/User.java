
package io.github.sshnakamoto.splashpaper.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("twitter_username")
    @Expose
    private Object twitterUsername;

    @SerializedName("portfolio_url")
    @Expose
    private Object portfolioUrl;

    @SerializedName("bio")
    @Expose
    private Object bio;

    @SerializedName("location")
    @Expose

    private Object location;
    @SerializedName("links")
    @Expose

    private UserLinks links;
    @SerializedName("profile_image")
    @Expose

    private ProfileImage profileImage;
    @SerializedName("instagram_username")
    @Expose

    private Object instagramUsername;
    @SerializedName("total_collections")
    @Expose

    private Integer totalCollections;
    @SerializedName("total_likes")
    @Expose
    private Integer totalLikes;

    @SerializedName("total_photos")
    @Expose
    private Integer totalPhotos;

}
