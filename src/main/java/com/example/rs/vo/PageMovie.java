package com.example.rs.vo;

import java.io.Serializable;

public class PageMovie implements Serializable {
    private long movieId;
    private String en_title;
    private String genres;
    private double avg2;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getAvg2() {
        return avg2;
    }

    public void setAvg2(double avg2) {
        this.avg2 = avg2;
    }
}
