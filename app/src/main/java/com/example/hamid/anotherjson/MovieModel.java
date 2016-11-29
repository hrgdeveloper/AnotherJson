package com.example.hamid.anotherjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hamid on 12/09/2015.
 */
public class MovieModel {

    int year;
    float rating;
    String duration;
    String director;
    String tagline;
@SerializedName("cast")
    List<Cast> castlist;
    String image;
    String story;
    String movie;

    public MovieModel(int year,int rating,String duration,String director,String tagline,String image,String story,String movie){
        this.year=year;
        this.rating=rating;
        this.duration=duration;
        this.director=director;
        this.tagline=tagline;
        this.image=image;
        this.story=story;
        this.movie=movie;

    }



    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Cast> getCastlist() {
        return castlist;
    }

    public void setCastlist(List<Cast> castlist) {
        this.castlist = castlist;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }


    static class Cast{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;

    }
}
