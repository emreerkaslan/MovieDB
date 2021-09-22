package com.erkaslan.moviedb.data;

public class Movie {

    private String title;
    private String year;
    private String director;
    private String imdbID;
    private String poster;
    private String plot;
    private String type;

    public Movie(String title, String year, String poster, String imdbID) {
        this.poster = poster;
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director=director;
    }

    public String getImdbId() {
        return imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot=plot;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
