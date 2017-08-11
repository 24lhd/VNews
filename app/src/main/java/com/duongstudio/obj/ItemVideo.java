package com.duongstudio.obj;


public class ItemVideo {
    public String idCategory;
    public String idVideo;
    public String title;
    public String image;
    public String linkVideo;
    public String timeVideo;

    public String getTimeVideo() {
        return timeVideo;
    }

    public void setTimeVideo(String timeVideo) {
        this.timeVideo = timeVideo;
    }

    public ItemVideo(String idCategory, String idVideo, String title, String image, String linkVideo, String timeVideo, Date pubDate) {

        this.idCategory = idCategory;
        this.idVideo = idVideo;
        this.title = title;
        this.image = image;
        this.linkVideo = linkVideo;
        this.timeVideo = timeVideo;
        this.pubDate = pubDate;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }


    public Date pubDate;


    @Override
    public String toString() {
        return "ItemVideo{" +
                "idCategory='" + idCategory + '\'' +
                ", idVideo='" + idVideo + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", linkVideo='" + linkVideo + '\'' +
                '}';
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }


}
