package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a collection of movie lists
public class Collection implements Writable {
    private ArrayList<MovieList> lists;

    // EFFECTS: constructs a collection with no movie list
    public Collection() {
        lists = new ArrayList<>();
    }

    public ArrayList<MovieList> getLists() {
        return lists;
    }

    @Override
    // EFFECTS: converts this collection into JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("lists", lists);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: adds given list to this collection
    public void addList(MovieList list) {
        lists.add(list);
    }

    // EFFECTS: returns number of lists in this collection
    public int numLists() {
        return lists.size();
    }
}
