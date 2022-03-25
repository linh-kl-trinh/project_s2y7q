package persistence;

import model.Collection;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of collection to file
public class JsonWriter {
    private String destination;
    private PrintWriter writer;
    private static final int TAB = 4;

    // constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    public void write(Collection collection) {
        JSONObject json = collection.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    private void saveToFile(String json) {
        writer.print(json);
    }
}
