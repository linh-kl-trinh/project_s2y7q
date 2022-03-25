package persistence;

import model.Movie;
import model.MovieList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkList(String name, ArrayList<Movie> list, MovieList movieList) {
        assertEquals(name, movieList.getName());
        ArrayList<Movie> watchlist = movieList.getWatchlist();
        for (int i = 0; i < watchlist.size(); i++) {
            Movie expectedMovie = list.get(i);
            Movie actualMovie = watchlist.get(i);
            assertEquals(expectedMovie.getTitle(), actualMovie.getTitle());
            assertEquals(expectedMovie.getYear(), actualMovie.getYear());
            assertEquals(expectedMovie.getRating(), actualMovie.getRating());
        }
    }
}
