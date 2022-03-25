package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a movie that has a title, year of release,
// and rating (out of 10)
public class Movie implements Writable {
    private String title;
    private int year;
    private int rating;

    // REQUIRES: year > 0
    // EFFECTS: constructs a movie with given title, year
    // of release, and no rating
    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
        rating = 0;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
        return rating;
    }

    // MODIFIES: this
    // EFFECTS: changes title of this movie
    public void rename(String title) {
        this.title = title;
    }

    // REQUIRES: year > 0
    // MODIFIES: this
    // EFFECTS: changes year of release of this movie
    public void changeYear(int year) {
        this.year = year;
    }

    // REQUIRES: 0 < rating <= 10
    // MODIFIES: this
    // EFFECTS: gives the movie a rating from 0 to 10
    public void giveRating(int rating) {
        this.rating = rating;
    }

    @Override
    // EFFECTS: converts this movie into JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("year", year);
        json.put("rating", rating);
        return json;
    }
}
