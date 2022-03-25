package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectionTest {
    private Collection testCollection;

    @BeforeEach
    void runBefore() {
        testCollection = new Collection();
    }

    @Test
    void testConstructor() {
        ArrayList<MovieList> lists = testCollection.getLists();
        assertTrue(lists.isEmpty());
    }

    @Test
    void testAddList() {
        MovieList comedy = new MovieList("Comedy");
        MovieList action = new MovieList("Action");

        testCollection.addList(comedy);
        assertEquals(1,testCollection.numLists());

        testCollection.addList(action);
        assertEquals(2,testCollection.numLists());
    }
}
