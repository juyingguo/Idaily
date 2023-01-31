package com.liuguangqiang.idaily.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.liuguangqiang.idaily.adapter.StoriesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 15/6/8.
 */
public class Story extends BaseEntity implements Parcelable {

    public int id;

    public String title;

    public int type = 0;

    public List<String> images = new ArrayList<>();

    public String image;

    public String body;

    public List<String> css;
    public List<String> js;

    public String share_url;

    public String hint;

    public List<String> getCss() {
        return css;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Story() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", images=" + images +
                ", image='" + image + '\'' +
                ", body='" + body + '\'' +
                ", css=" + css +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeStringList(this.images);
        dest.writeString(this.image);
        dest.writeString(this.body);
        dest.writeStringList(this.css);
        dest.writeString(this.share_url);
    }

    protected Story(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.type = in.readInt();
        this.images = in.createStringArrayList();
        this.image = in.readString();
        this.body = in.readString();
        this.css = in.createStringArrayList();
        this.share_url = in.readString();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public int getItemType() {
        return StoriesAdapter.ITEM_STORY;
    }
}
