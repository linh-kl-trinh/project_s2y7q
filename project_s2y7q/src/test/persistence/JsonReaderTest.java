package persistence;

import model.Collection;
import model.Movie;
import model.MovieList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            Collection collection = reader.read();
            assertEquals(0, collection.numLists());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWatchlist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");
        try {
            Collection collection = reader.read();
            ArrayList<MovieList> lists = collection.getLists();
            assertEquals(3, lists.size());
            ArrayList<Movie> waiting = new ArrayList<>();
            Movie run = new Movie("run", 2020);
            Movie theNextThreeDays = new Movie("the next three days", 2010);
            waiting.add(run);
            waiting.add(theNextThreeDays);
            ArrayList<Movie> finished = new ArrayList<>();
            ArrayList<Movie> dropped = new ArrayList<>();
            Movie theWave = new Movie("the wave", 2008);
            theWave.giveRating(5);
            dropped.add(theWave);
            checkList("Waiting List", waiting, lists.get(0));
            checkList("Finished List", finished, lists.get(1));
            checkList("Dropped List", dropped, lists.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
