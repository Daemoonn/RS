package com.example.rs.domain;

import java.io.Serializable;

public class MovieDetail implements Serializable {
    private long movieId;
    private String url_id;
    private String en_title;
    private String cn_title;
    private String genres;
    private String published_year;
    private String img_link;
    private String page_link;
    private String summary;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getUrl_id() {
        return url_id;
    }

    public void setUrl_id(String url_id) {
        this.url_id = url_id;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getCn_title() {
        return cn_title;
    }

    public void setCn_title(String cn_title) {
        this.cn_title = cn_title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPublished_year() {
        return published_year;
    }

    public void setPublished_year(String published_year) {
        this.published_year = published_year;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getPage_link() {
        return page_link;
    }

    public void setPage_link(String page_link) {
        this.page_link = page_link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "MovieDetail{" +
                "movieId=" + movieId +
                ", url_id='" + url_id + '\'' +
                ", en_title='" + en_title + '\'' +
                ", cn_title='" + cn_title + '\'' +
                ", published_year='" + published_year + '\'' +
                ", img_link='" + img_link + '\'' +
                ", page_link='" + page_link + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
