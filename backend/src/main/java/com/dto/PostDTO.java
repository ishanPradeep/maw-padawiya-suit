package com.dto;

import java.io.Serializable;

public class PostDTO  implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String media;
    private Integer category;
    private String front_image;
    private String createdDate;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getFront_image() {
        return front_image;
    }

    public void setFront_image(String front_image) {
        this.front_image = front_image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCategory_id() {
        return category;
    }

    public void setCategory_id(Integer category_id) {
        this.category = category_id;
    }
}
