package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMovie = new Movie("Horrible Bosses", 2011);
    }

    @Test
    void testConstructor() {
        assertEquals("Horrible Bosses", testMovie.getTitle());
        assertEquals(2011, testMovie.getYear());
        assertEquals(0, testMovie.getRating());
    }

    @Test
    void testRename() {
        testMovie.rename("Terrible Bosses");
        assertEquals("Terrible Bosses", testMovie.getTitle());

        testMovie.rename("Wonderful Bosses");
        assertEquals("Wonderful Bosses", testMovie.getTitle());
    }

    @Test
    void changeYear() {
        testMovie.changeYear(2005);
        assertEquals(2005, testMovie.getYear());

        testMovie.changeYear(1990);
        assertEquals(1990, testMovie.getYear());
    }

    @Test
    void testGiveRating() {
        testMovie.giveRating(3);
        assertEquals(3, testMovie.getRating());

        testMovie.giveRating(1);
        assertEquals(1, testMovie.getRating());
    }

    @Test
    void testToJson() {
        JSONObject json = testMovie.toJson();
        assertEquals("Horrible Bosses", json.getString("title"));
        assertEquals(2011, json.getInt("year"));
        assertEquals(0, json.getInt("rating"));
    }

}