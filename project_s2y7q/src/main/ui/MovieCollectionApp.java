package ui;

import model.Collection;
import model.Movie;
import model.MovieList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Movie collection application
public class MovieCollectionApp extends JFrame implements ActionListener {
    private MovieList waiting;
    private MovieList watching;
    private MovieList finished;
    private MovieList dropped;
    private MovieList currentList;
    private Movie currentMovie;
    private boolean error = false;
    private ArrayList<String> names = new ArrayList<>();
    private Collection collection;
    private static final String JSON_STORE = "./data/watchlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final JLabel prompt = new JLabel("Please select from:");
    private JPanel buttonArea;
    private GroupLayout layout;

    private JButton waitingBtn;
    private JButton watchingBtn;
    private JButton finishedBtn;
    private JButton droppedBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton addBtn;
    private JButton selectBtn;
    private JButton sortBtn;
    private JButton backToCollectionBtn;
    private JButton changeTitleBtn;
    private JButton changeYearBtn;
    private JButton changeRatingBtn;
    private JButton changeStatusBtn;
    private JButton removeBtn;
    private JButton backToListBtn;

    private JLabel titleLabel;
    private JLabel yearLabel;
    private JLabel selectLabel;
    private JLabel yearToSortLabel;
    private JLabel ratingLabel;
    private JLabel statusLabel;

    private JTextField titleTextField;
    private JTextField yearTextField;
    private JTextField yearToSortTextField;
    private JTextField newTitleTextField;
    private JTextField newYearTextField;
    private JTextField ratingTextField;

    private JComboBox<String> movies;
    private JComboBox<String> statuses;

    // EFFECTS: runs the movie collection application
    public MovieCollectionApp() {
        super("Movie Collection");
        initializeGraphics();
        setCollectionButtons();
        setListButtons();
        setListTextFields();
        setMovieButtons();
        setMovieTextFields();
        init();
        runCollection();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where this MovieCollectionApp will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 250));
        setLocationRelativeTo(null);
        setVisible(true);
        buttonArea = new JPanel();
        layout = new GroupLayout(buttonArea);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        buttonArea.setLayout(layout);
        add(buttonArea);
    }

    // MODIFIES: this
    // EFFECTS: initializes all JButton to be used in the collection
    private void setCollectionButtons() {
        waitingBtn = new JButton("Waiting List");
        waitingBtn.setActionCommand("waiting");
        waitingBtn.addActionListener(this);

        watchingBtn = new JButton("Watching List");
        watchingBtn.setActionCommand("watching");
        watchingBtn.addActionListener(this);

        finishedBtn = new JButton("Finished List");
        finishedBtn.setActionCommand("finished");
        finishedBtn.addActionListener(this);

        droppedBtn = new JButton("Dropped List");
        droppedBtn.setActionCommand("dropped");
        droppedBtn.addActionListener(this);

        loadBtn = new JButton("Load");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);
        loadBtn.setBackground(new Color(0xD0A324));

        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        saveBtn.setBackground(new Color(0x0CA50C));
    }

    // MODIFIES: this
    // EFFECTS: displays options in the collection
    private void runCollection() {
        LayoutManager curLayout = buttonArea.getLayout();
        if (curLayout != null) {
            buttonArea.removeAll();
        }

        setCollectionLayout();

        pack();
    }

    // MODIFIES: this
    // EFFECTS: displays options in the collection with given message
    private void runCollection(String message) {
        LayoutManager curLayout = buttonArea.getLayout();
        if (curLayout != null) {
            buttonArea.removeAll();
        }

        setCollectionLayout(new JLabel(message));

        pack();
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts of this collection
    private void setCollectionLayout() {
        setCollectionHorizontalLayout();
        setCollectionVerticalLayout();
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts with given message of this collection
    private void setCollectionLayout(JLabel message) {
        setCollectionHorizontalLayout(message);
        setCollectionVerticalLayout(message);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets horizontal layout of this collection
    private void setCollectionHorizontalLayout() {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(prompt)
                                .addComponent(waitingBtn)
                                .addComponent(watchingBtn)
                                .addComponent(finishedBtn)
                                .addComponent(droppedBtn)
                                .addComponent(loadBtn)
                                .addComponent(saveBtn))
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets horizontal layout of this collection
    private void setCollectionHorizontalLayout(JLabel message) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(prompt)
                                .addComponent(waitingBtn)
                                .addComponent(watchingBtn)
                                .addComponent(finishedBtn)
                                .addComponent(droppedBtn)
                                .addComponent(loadBtn)
                                .addComponent(saveBtn)
                                .addComponent(message))
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets vertical layout of this collection
    private void setCollectionVerticalLayout() {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(prompt)
                        .addComponent(waitingBtn)
                        .addComponent(watchingBtn)
                        .addComponent(finishedBtn)
                        .addComponent(droppedBtn)
                        .addComponent(loadBtn)
                        .addComponent(saveBtn)
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets vertical layout of this collection
    private void setCollectionVerticalLayout(JLabel message) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(prompt)
                        .addComponent(waitingBtn)
                        .addComponent(watchingBtn)
                        .addComponent(finishedBtn)
                        .addComponent(droppedBtn)
                        .addComponent(loadBtn)
                        .addComponent(saveBtn)
                        .addComponent(message)
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes all JButton to be used in currentList
    private void setListButtons() {
        addBtn = new JButton("Add");
        addBtn.setActionCommand("add");
        addBtn.addActionListener(this);

        selectBtn = new JButton("Select");
        selectBtn.setActionCommand("select");
        selectBtn.addActionListener(this);

        sortBtn = new JButton("Sort");
        sortBtn.setActionCommand("sort");
        sortBtn.addActionListener(this);

        backToCollectionBtn = new JButton("Back");
        backToCollectionBtn.setActionCommand("backToCollection");
        backToCollectionBtn.addActionListener(this);
        backToCollectionBtn.setBackground(new Color(0x2B2BFF));
    }

    // MODIFIES: this
    // EFFECTS: initializes all JLabel and JTextField to be used in currentList
    private void setListTextFields() {
        titleLabel = new JLabel("Title:");
        titleTextField = new JTextField(5);
        yearLabel = new JLabel("Year:");
        yearTextField = new JTextField(5);
        selectLabel = new JLabel("Select Movie:");
        yearToSortLabel = new JLabel("Sort By Year:");
        yearToSortTextField = new JTextField(5);
    }

    // MODIFIES: this
    // EFFECTS: displays currentList with option menu
    private void runList() {
        LayoutManager curLayout = buttonArea.getLayout();
        if (curLayout != null) {
            buttonArea.removeAll();
        }

        movieDropdown();

        if (error) {
            setListLayout(displayList(), new JLabel("Invalid! Please try again!"));
        } else {
            setListLayout(displayList());
        }

        pack();
    }

    // EFFECTS: makes a dropdown of movies in currentList
    private void movieDropdown() {
        movies = new JComboBox<>();
        movies.addItem("[select]");
        ArrayList<Movie> watchlist = currentList.getWatchlist();
        for (Movie next : watchlist) {
            String rating;
            String title = next.getTitle();
            String year = String.valueOf(next.getYear());
            int index = watchlist.indexOf(next) + 1;
            if (next.getRating() == 0) {
                rating = "no rating";
            } else {
                rating = next.getRating() + "/10";
            }
            movies.addItem(index + ". " + title + " (" + year + ") " + " - " + rating);
        }
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts of currentList
    private void setListLayout(JList listToDisplay) {
        setListHorizontalLayout(listToDisplay);
        setListVerticalLayout(listToDisplay);
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts with given message of currentList
    private void setListLayout(JList listToDisplay, JLabel message) {
        setListHorizontalLayout(listToDisplay, message);
        setListVerticalLayout(listToDisplay, message);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets vertical layout of currentList
    private void setListHorizontalLayout(JList listToDisplay) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(listToDisplay)
                                .addComponent(prompt)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(titleLabel)
                                        .addComponent(titleTextField)
                                        .addComponent(yearLabel)
                                        .addComponent(yearTextField)
                                        .addComponent(addBtn))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(selectLabel)
                                        .addComponent(movies)
                                        .addComponent(selectBtn))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(yearToSortLabel)
                                        .addComponent(yearToSortTextField)
                                        .addComponent(sortBtn))
                                .addComponent(backToCollectionBtn))
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets horizontal layout of currentList
    private void setListHorizontalLayout(JList listToDisplay, JLabel message) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(listToDisplay)
                                .addComponent(prompt)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(titleLabel)
                                        .addComponent(titleTextField)
                                        .addComponent(yearLabel)
                                        .addComponent(yearTextField)
                                        .addComponent(addBtn))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(selectLabel)
                                        .addComponent(movies)
                                        .addComponent(selectBtn))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(yearToSortLabel)
                                        .addComponent(yearToSortTextField)
                                        .addComponent(sortBtn))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(backToCollectionBtn)
                                        .addComponent(message))
                        ));
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets vertical layout of currentList
    private void setListVerticalLayout(JList listToDisplay) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(listToDisplay)
                        .addComponent(prompt)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(titleLabel)
                                .addComponent(titleTextField)
                                .addComponent(yearLabel)
                                .addComponent(yearTextField)
                                .addComponent(addBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(selectLabel)
                                .addComponent(movies)
                                .addComponent(selectBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(yearToSortLabel)
                                .addComponent(yearToSortTextField)
                                .addComponent(sortBtn))
                        .addComponent(backToCollectionBtn)
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets vertical layout of currentList
    private void setListVerticalLayout(JList listToDisplay, JLabel message) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(listToDisplay)
                        .addComponent(prompt)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(titleLabel)
                                .addComponent(titleTextField)
                                .addComponent(yearLabel)
                                .addComponent(yearTextField)
                                .addComponent(addBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(selectLabel)
                                .addComponent(movies)
                                .addComponent(selectBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(yearToSortLabel)
                                .addComponent(yearToSortTextField)
                                .addComponent(sortBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(backToCollectionBtn)
                                .addComponent(message))
        );
    }

    // EFFECTS: saves collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            runCollection("File saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            runCollection("File not found!");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads collection from file
    private void loadCollection() {
        try {
            collection = jsonReader.read();
            ArrayList<MovieList> lists = collection.getLists();
            waiting = lists.get(0);
            watching = lists.get(1);
            finished = lists.get(2);
            dropped = lists.get(3);
            runCollection("File loaded from " + JSON_STORE);
        } catch (IOException e) {
            runCollection("File not found!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes all JButton to be used in currentMovie
    private void setMovieButtons() {
        changeTitleBtn = new JButton("Change");
        changeTitleBtn.setActionCommand("changeTitle");
        changeTitleBtn.addActionListener(this);

        changeYearBtn = new JButton("Change");
        changeYearBtn.setActionCommand("changeYear");
        changeYearBtn.addActionListener(this);

        changeRatingBtn = new JButton("Change");
        changeRatingBtn.setActionCommand("changeRating");
        changeRatingBtn.addActionListener(this);

        changeStatusBtn = new JButton("Change");
        changeStatusBtn.setActionCommand("changeStatus");
        changeStatusBtn.addActionListener(this);

        removeBtn = new JButton("Remove");
        removeBtn.setActionCommand("remove");
        removeBtn.addActionListener(this);
        removeBtn.setBackground(new Color(0xE70A0A));

        backToListBtn = new JButton("Back");
        backToListBtn.setActionCommand("backToList");
        backToListBtn.addActionListener(this);
        backToListBtn.setBackground(new Color(0x2B2BFF));
    }

    // MODIFIES: this
    // EFFECTS: initializes all JLabel and JTextField to be used in currentMovie
    private void setMovieTextFields() {
        newTitleTextField = new JTextField(5);
        newYearTextField = new JTextField(5);
        ratingLabel = new JLabel("Rating:");
        ratingTextField = new JTextField(5);
        statusLabel = new JLabel("Status:");
        statuses = new JComboBox<>();
        statuses.addItem("[select]");
        statuses.addItem("waiting");
        statuses.addItem("watching");
        statuses.addItem("finished");
        statuses.addItem("dropped");
    }

    // MODIFIES: this
    // EFFECTS: displays currentMovie with menu option
    private void runMovie() {
        LayoutManager curLayout = buttonArea.getLayout();
        if (curLayout != null) {
            buttonArea.removeAll();
        }

        statuses.setSelectedIndex(0);

        if (error) {
            setMovieLayout(displayMovie(), new JLabel("Invalid! Please try again!"));
        } else {
            setMovieLayout(displayMovie());
        }

        pack();
    }

    // MODIFIES: this
    // EFFECTS: creates group of JLabel, JTextField, and JButton used to set title
    private GroupLayout.Group createTitleGroup(String type) {
        if (type.equals("sequential")) {
            return layout.createSequentialGroup()
                    .addComponent(titleLabel)
                    .addComponent(newTitleTextField)
                    .addComponent(changeTitleBtn);
        } else {
            return layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(newTitleTextField)
                    .addComponent(changeTitleBtn);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates group of JLabel, JTextField, and JButton used to set year
    private GroupLayout.Group createYearGroup(String type) {
        if (type.equals("sequential")) {
            return layout.createSequentialGroup()
                    .addComponent(yearLabel)
                    .addComponent(newYearTextField)
                    .addComponent(changeYearBtn);
        } else {
            return layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(newYearTextField)
                    .addComponent(changeYearBtn);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates group of JLabel, JTextField, and JButton used to set rating
    private GroupLayout.Group createRatingGroup(String type) {
        if (type.equals("sequential")) {
            return layout.createSequentialGroup()
                    .addComponent(ratingLabel)
                    .addComponent(ratingTextField)
                    .addComponent(changeRatingBtn);
        } else {
            return layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ratingLabel)
                    .addComponent(ratingTextField)
                    .addComponent(changeRatingBtn);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates group of JLabel, JTextField, and JButton used to set status
    private GroupLayout.Group createStatusGroup(String type) {
        if (type.equals("sequential")) {
            return layout.createSequentialGroup()
                    .addComponent(statusLabel)
                    .addComponent(statuses)
                    .addComponent(changeStatusBtn);
        } else {
            return layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabel)
                    .addComponent(statuses)
                    .addComponent(changeStatusBtn);
        }
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts of currentMovie
    private void setMovieLayout(JLabel movieToDisplay) {
        setMovieHorizontalLayout(movieToDisplay);
        setMovieVerticalLayout(movieToDisplay);
    }

    // MODIFIES: this
    // EFFECTS: combines horizontal and vertical layouts with given message of currentMovie
    private void setMovieLayout(JLabel movieToDisplay, JLabel message) {
        setMovieHorizontalLayout(movieToDisplay, message);
        setMovieVerticalLayout(movieToDisplay, message);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets horizontal layout of currentMovie
    private void setMovieHorizontalLayout(JLabel movieToDisplay) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(movieToDisplay)
                                .addComponent(prompt)
                                .addGroup(createTitleGroup("sequential"))
                                .addGroup(createYearGroup("sequential"))
                                .addGroup(createRatingGroup("sequential"))
                                .addGroup(createStatusGroup("sequential"))
                                .addComponent(removeBtn)
                                .addComponent(backToListBtn))
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets horizontal layout of currentMovie
    private void setMovieHorizontalLayout(JLabel movieToDisplay, JLabel message) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(movieToDisplay)
                                .addComponent(prompt)
                                .addGroup(createTitleGroup("sequential"))
                                .addGroup(createYearGroup("sequential"))
                                .addGroup(createRatingGroup("sequential"))
                                .addGroup(createStatusGroup("sequential"))
                                .addComponent(removeBtn)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(backToListBtn)
                                        .addComponent(message))
                        ));

    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets vertical layout of currentMovie
    private void setMovieVerticalLayout(JLabel movieToDisplay) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(movieToDisplay)
                        .addComponent(prompt)
                        .addGroup(createTitleGroup("parallel"))
                        .addGroup(createYearGroup("parallel"))
                        .addGroup(createRatingGroup("parallel"))
                        .addGroup(createStatusGroup("parallel"))
                        .addComponent(removeBtn)
                        .addComponent(backToListBtn)
        );
    }

    // MODIFIES: this
    // EFFECTS: adds all components and given message to layout and sets vertical layout of currentMovie
    private void setMovieVerticalLayout(JLabel movieToDisplay, JLabel message) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(movieToDisplay)
                        .addComponent(prompt)
                        .addGroup(createTitleGroup("parallel"))
                        .addGroup(createYearGroup("parallel"))
                        .addGroup(createRatingGroup("parallel"))
                        .addGroup(createStatusGroup("parallel"))
                        .addComponent(removeBtn)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(backToListBtn)
                                .addComponent(message)
                        ));
    }

    // MODIFIES: this
    // EFFECTS: displays given sorted list
    private void runSortedList(JList sortedListToDisplay) {
        LayoutManager curLayout = buttonArea.getLayout();
        if (curLayout != null) {
            buttonArea.removeAll();
        }

        setSortedListLayout(sortedListToDisplay);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: adds all components to layout and sets layout of given sorted list
    private void setSortedListLayout(JList sortedListToDisplay) {
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(sortedListToDisplay)
                                .addComponent(backToListBtn))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(sortedListToDisplay)
                        .addComponent(backToListBtn)
        );
    }

    // MODIFIES: this
    // EFFECTS: initializes collection
    private void init() {
        collection = new Collection();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        waiting = new MovieList("Waiting List");
        watching = new MovieList("Watching List");
        finished = new MovieList("Finished List");
        dropped = new MovieList("Dropped List");
        names.add("waiting");
        names.add("watching");
        names.add("finished");
        names.add("dropped");
        collection.addList(waiting);
        collection.addList(watching);
        collection.addList(finished);
        collection.addList(dropped);
    }

    // EFFECTS: converts currentList into JList
    private JList displayList() {
        DefaultListModel listModel = new DefaultListModel<>();
        listModel.addElement(currentList.getName());
        ArrayList<Movie> watchlist = currentList.getWatchlist();
        if (watchlist.isEmpty()) {
            listModel.addElement("This list is empty.");
        } else {
            for (Movie next : watchlist) {
                String rating;
                String title = next.getTitle();
                String year = String.valueOf(next.getYear());
                int index = watchlist.indexOf(next) + 1;
                if (next.getRating() == 0) {
                    rating = "no rating";
                } else {
                    rating = next.getRating() + "/10";
                }
                listModel.addElement(index + ". " + title + " (" + year + ") " + " - " + rating);
            }
        }
        JList listToDisplay = new JList(listModel);
        return listToDisplay;
    }

    // EFFECTS: converts currentMovie into JLabel
    private JLabel displayMovie() {
        String rating;
        String title = currentMovie.getTitle();
        String year = String.valueOf(currentMovie.getYear());
        if (currentMovie.getRating() == 0) {
            rating = "no rating";
        } else {
            rating = currentMovie.getRating() + "/10";
        }
        JLabel movieToDisplay = new JLabel(title + " (" + year + ")" + " - " + rating);
        return movieToDisplay;
    }

    // MODIFIES: this
    // EFFECTS: conducts movie addition with given title and year to currentList
    private void doAddMovie(String title, int year) {
        title = title.toLowerCase();
        Movie newMovie = new Movie(title, year);
        currentList.addMovie(newMovie);
    }

    // EFFECTS: conducts sorting of currentList by given year and returns sorted list as JList
    private JList doSortByYear(int year) {
        DefaultListModel listModel = new DefaultListModel<>();
        listModel.addElement("Movies in " + currentList.getName() + " released in " + year + ": ");
        ArrayList<String> sortedList = currentList.sortByYear(year);
        if (sortedList.isEmpty()) {
            listModel.addElement("None.");
        } else {
            for (String next : sortedList) {
                listModel.addElement(next);
            }
        }
        JList sortedListToDisplay = new JList(listModel);
        return sortedListToDisplay;
    }

    // MODIFIES: this
    // EFFECTS: conducts change with given title of currentMovie
    private void doRename(String title) {
        currentMovie.rename(title);
        newTitleTextField = new JTextField(5);
        runMovie();
    }

    // MODIFIES: this
    // EFFECTS: conducts change with given year of currentMovie
    private void doChangeYear(int year) {
        currentMovie.changeYear(year);
        runMovie();
    }

    // MODIFIES: this
    // EFFECTS: conducts removal of currentMovie from currentList
    private void doRemove() {
        currentList.removeMovie(currentMovie);
        runList();
    }

    // MODIFIES: this
    // EFFECTS: conducts change with given status of currentMovie
    private void doChangeStatus(String status) {
        MovieList to;
        if (status.equals("waiting")) {
            to = waiting;
            currentList.removeMovie(currentMovie);
            to.addMovie(currentMovie);
        } else if (status.equals("watching")) {
            to = watching;
            currentList.removeMovie(currentMovie);
            to.addMovie(currentMovie);
        } else if (status.equals("finished")) {
            to = finished;
            currentList.removeMovie(currentMovie);
            to.addMovie(currentMovie);
        } else if (status.equals("dropped")) {
            to = dropped;
            currentList.removeMovie(currentMovie);
            to.addMovie(currentMovie);
        }
        runMovie();
    }

    // MODIFIES: this
    // EFFECTS: conducts change with given rating of currentMovie
    // if rating is invalid, throws NumberFormatException
    private void doGiveRating(int rating) throws NumberFormatException {
        if (rating <= 0 || rating > 10) {
            throw new NumberFormatException();
        } else {
            currentMovie.giveRating(rating);
            runMovie();
        }
    }

    // EFFECTS: selects a movie in currentList
    private void selectMovie(int selection) {
        try {
            currentMovie = currentList.getWatchlist().get(selection - 1);
            runMovie();
        } catch (ArrayIndexOutOfBoundsException e) {
            runList();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (names.contains(command) || command.equals("backToList")) {
            error = false;
            runSelectedList(command);
        } else if (command.equals("load")) {
            loadCollection();
        } else if (command.equals("save")) {
            saveCollection();
        } else if (command.equals("backToCollection")) {
            error = false;
            runCollection();
        } else if (command.equals("add")) {
            tryAddMovieToList();
        } else if (command.equals("select")) {
            selectMovie(movies.getSelectedIndex());
        } else if (command.equals("sort")) {
            trySortByYear();
        } else if (command.equals("remove")) {
            error = false;
            doRemove();
        } else {
            changeMovieInfo(command);
        }
    }

    // EFFECTS: conducts user command in currentMovie
    private void changeMovieInfo(String command) {
        if (command.equals("changeTitle")) {
            doRename(newTitleTextField.getText());
        } else if (command.equals("changeYear")) {
            tryChangeYear();
        } else if (command.equals("changeRating")) {
            tryGiveRating();
        } else if (command.equals("changeStatus")) {
            doChangeStatus((String) statuses.getSelectedItem());
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts user command to select a list
    private void runSelectedList(String command) {
        if (command.equals("waiting")) {
            currentList = waiting;
        } else if (command.equals("watching")) {
            currentList = watching;
        } else if (command.equals("finished")) {
            currentList = finished;
        } else if (command.equals("dropped")) {
            currentList = dropped;
        }

        error = false;
        runList();
    }

    // MODIFIES: this
    // EFFECTS: try adding movie to currentList
    private void tryAddMovieToList() {
        try {
            doAddMovie(titleTextField.getText(), Integer.parseInt(yearTextField.getText()));
            error = false;
        } catch (NumberFormatException e) {
            error = true;
        }

        titleTextField = new JTextField(5);
        yearTextField = new JTextField(5);
        runList();
    }

    // MODIFIES: this
    // EFFECTS: try sorting currentList by year
    private void trySortByYear() {
        try {
            int yearToSort = Integer.parseInt(yearToSortTextField.getText());
            yearToSortTextField = new JTextField(5);
            runSortedList(doSortByYear(yearToSort));
            error = false;
        } catch (NumberFormatException e) {
            error = true;
            yearToSortTextField = new JTextField(5);
            runList();
        }
    }

    // MODIFIES: this
    // EFFECTS: try changing year of currentMovie
    private void tryChangeYear() {
        try {
            int year = Integer.parseInt(newYearTextField.getText());
            newYearTextField = new JTextField(5);
            doChangeYear(year);
            error = false;
        } catch (NumberFormatException e) {
            error = true;
            newYearTextField = new JTextField(5);
            runMovie();
        }
    }

    // MODIFIES: this
    // EFFECTS: try giving rating to currentMovie
    private void tryGiveRating() {
        try {
            int rating = Integer.parseInt(ratingTextField.getText());
            ratingTextField = new JTextField(5);
            doGiveRating(rating);
            error = false;
        } catch (NumberFormatException e) {
            error = true;
            ratingTextField = new JTextField(5);
            runMovie();
        }
    }
}