package com.cui.video.entity;

import java.io.Serializable;

/**
 * Created by cuiyang on 2016/11/20.
 */

public class PlayerVideoEntity implements Serializable{
    private String title;
    private String name;
    private String url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
