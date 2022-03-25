package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a movie list that has a name and watchlist
// of movies
public class MovieList implements Writable {
    private String name;
    private ArrayList<Movie> watchlist;

    // EFFECTS: constructs a movie list with given name
    // and empty watchlist
    public MovieList(String name) {
        this.name = name;
        watchlist = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movie> getWatchlist() {
        return watchlist;
    }

    // MODIFIES: this
    // EFFECTS: adds given movie to watchlist
    public void addMovie(Movie movie) {
        watchlist.add(movie);
    }

    // MODIFIES: this
    // EFFECTS: removes given movie from watchlist
    public void removeMovie(Movie movie) {
        watchlist.remove(movie);
    }

    // REQUIRES: year > 0
    // EFFECTS: sorts out titles of movies in watchlist that
    // are released in given year
    public ArrayList<String> sortByYear(int year) {
        ArrayList<String> sortedList = new ArrayList<>();
        for (Movie next : watchlist) {
            if (next.getYear() == year) {
                sortedList.add(next.getTitle());
            }
        }
        return sortedList;
    }

    @Override
    // EFFECTS: converts this list into JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("watchlist", watchlist);
        return json;
    }
}
