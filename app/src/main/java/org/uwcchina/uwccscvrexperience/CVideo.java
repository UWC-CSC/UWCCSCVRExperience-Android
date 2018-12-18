package org.uwcchina.uwccscvrexperience;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CVideo implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("thumbnail")
    private String thumbnailUri;
    private Pair<Integer, Integer> resolution;
    private long size;
    private String uri;

    public CVideo(int id, String title, String description, String thumbnailUri) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setThumbnailUri(thumbnailUri);
    }

    public CVideo(int id, String title, String description, String thumbnailUri,
                  Pair<Integer, Integer> resolution, long size, String uri) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setThumbnailUri(thumbnailUri);
        setResolution(resolution);
        setSize(size);
        setUri(uri);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public Pair<Integer, Integer> getResolution() {
        return resolution;
    }

    public void setResolution(Pair<Integer, Integer> resolution) {
        this.resolution = resolution;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
