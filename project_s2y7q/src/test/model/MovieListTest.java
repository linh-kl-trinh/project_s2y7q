package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieListTest {
    private MovieList testMovieList;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @BeforeEach
    void runBefore() {
        testMovieList = new MovieList("Comedies");
        movie1 = new Movie("Horrible Bosses", 2011);
        movie2 = new Movie("Just Like Heaven", 2005);
        movie3 = new Movie("Just Go With It", 2011);
    }

    @Test
    void testConstructor() {
        assertEquals("Comedies", testMovieList.getName());
        ArrayList<Movie> watchlist = testMovieList.getWatchlist();
        assertTrue(watchlist.isEmpty());
    }

    @Test
    void testAddMovie() {
        testMovieList.addMovie(movie1);
        ArrayList<Movie> watchlist = testMovieList.getWatchlist();
        assertEquals(movie1, watchlist.get(0));
        assertEquals(1, watchlist.size());

        testMovieList.addMovie(movie2);
        assertEquals(movie2, watchlist.get(1));
        assertEquals(2, watchlist.size());
    }

    @Test
    void testRemoveMovie() {
        testMovieList.addMovie(movie1);
        ArrayList<Movie> watchlist = testMovieList.getWatchlist();
        testMovieList.removeMovie(movie1);
        assertTrue(watchlist.isEmpty());

        testMovieList.addMovie(movie2);
        testMovieList.addMovie(movie3);
        testMovieList.removeMovie(movie1);
        assertEquals(2, watchlist.size());
        testMovieList.removeMovie(movie2);
        assertEquals(movie3, watchlist.get(0));
        assertEquals(1, watchlist.size());
        testMovieList.removeMovie(movie3);
        assertTrue(watchlist.isEmpty());
    }

    @Test
    void testSortByYear() {
        testMovieList.addMovie(movie1);
        testMovieList.addMovie(movie2);
        testMovieList.addMovie(movie3);

        ArrayList<String> watchlist2005 = testMovieList.sortByYear(2005);
        assertEquals("Just Like Heaven", watchlist2005.get(0));
        assertEquals(1, watchlist2005.size());

        ArrayList<String> watchlist2011 = testMovieList.sortByYear(2011);
        assertEquals("Horrible Bosses", watchlist2011.get(0));
        assertEquals("Just Go With It", watchlist2011.get(1));
        assertEquals(2, watchlist2011.size());

        ArrayList<String> watchlist2022 = testMovieList.sortByYear(2022);
        assertTrue(watchlist2022.isEmpty());
    }

    @Test
    void testToJson() {
        JSONObject json = testMovieList.toJson();
        assertEquals("Comedies", json.getString("name"));
        assertTrue(json.getJSONArray("watchlist").isEmpty());
    }
}