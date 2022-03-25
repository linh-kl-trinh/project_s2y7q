package persistence;


import model.Collection;
import model.Movie;
import model.MovieList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        Collection collection = new Collection();
        addLists(collection, jsonObject);
        return collection;
    }

    // MODIFIES: collection
    // EFFECTS: parses movie lists from JSON object and adds them to collection
    private void addLists(Collection collection, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("lists");
        for (Object json : jsonArray) {
            JSONObject nextList = (JSONObject) json;
            addList(collection, nextList);
        }
    }

    // MODIFIES: collection
    // EFFECTS: parses movie list from JSON object and adds it to collection
    private void addList(Collection collection, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArray = jsonObject.getJSONArray("watchlist");
        MovieList list = new MovieList(name);
        collection.addList(list);
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(nextMovie, list);
        }
    }

    // MODIFIES: list
    // EFFECTS: parses movie from JSON object and adds it to given list
    private void addMovie(JSONObject jsonObject, MovieList list) {
        String title = jsonObject.getString("title");
        int year = jsonObject.getInt("year");
        int rating = jsonObject.getInt("rating");
        Movie movie = new Movie(title, year);
        movie.giveRating(rating);
        list.addMovie(movie);
    }

}
