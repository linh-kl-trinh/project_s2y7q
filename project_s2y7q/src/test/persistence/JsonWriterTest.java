package persistence;

import model.Collection;
import model.Movie;
import model.MovieList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWatchlist() {
        try {
            Collection collection = new Collection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            collection = reader.read();
            assertEquals(0, collection.numLists());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWatchlist() {
        try {
            Collection collection = new Collection();
            collection.addList(new MovieList("Waiting List"));
            collection.addList(new MovieList("Finished List"));
            collection.addList(new MovieList("Dropped List"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWatchlist.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWatchlist.json");
            collection = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
