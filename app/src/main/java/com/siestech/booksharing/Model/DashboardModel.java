package com.siestech.booksharing.Model;

public class DashboardModel {

    int profile, postImage, save;
    String name, about, comment;

    public DashboardModel(int profile, int postImage, int save, String name, String about, String comment) {
        this.profile = profile;
        this.postImage = postImage;
        this.save = save;
        this.name = name;
        this.about = about;
        this.comment = comment;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
