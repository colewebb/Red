// Specs
// 
// - due last week of class, for presentations
// - two types of javafx panes                      (done)
// - six types of nodes                             (done)
// - an animation                                   (done)
// - demonstrate events, bindings and listeners     (missing a listener)
//
// Web browser specifics:
// - Address bar, with ability to type a web address and load it        (done)
// - Allow a user to browse a website                                   (done)
// - Dropdown menu for adding favorite sites                            (done)
// - Clicking on a link in dropdown loads it                            (done)
// - Back button with 1-deep history (only load the previous site)
// - Tabbed UI                                                          (done)
// - Save bookmarks to file for permeance                               (done)
// - If string typed in address bar isn't a web address, web search it  (done)

import java.io.File;                        // all the imports
import java.io.FileWriter;                  //
import java.util.ArrayList;                 //
import java.util.Scanner;                   //
import java.util.regex.Matcher;             //
import java.util.regex.Pattern;             //
import javafx.collections.FXCollections;    //
import javafx.collections.ObservableList;   //
import javafx.scene.control.Button;         //
import javafx.scene.control.ComboBox;       //
import javafx.scene.control.Label;          //
import javafx.scene.control.TextField;      //
import javafx.scene.input.KeyCode;          //
import javafx.scene.layout.GridPane;        //
import javafx.scene.layout.HBox;            //
import javafx.scene.web.WebView;            //

public class TabContent extends Red {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button bookmarks = new Button("Bookmark");                  // Bookmarks button
    WebView view = new WebView();                               // new webview, for viewing the web
    String homePage = "https://cs.usu.edu";                     // setting homepage
    ComboBox<String> cbo = new ComboBox<>();                    // new combobox for bookmarks
    ArrayList<String> bookmarkList = new ArrayList<>();         // list of bookmarks
    HBox bottom = new HBox();                                   // bottom bar hbox
    Label title = new Label();                                  // label for page title
    Button about = new Button("About");                         // about button
    String[] history = new String[2];
    Pattern urlCheck = Pattern.compile("^(https?|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");         // make a regex pattern

    public void read () {                           // method to read bookmarks from file and populate local variables
        File open = new File("./bookmarks.txt");    // open the file
        try {                                       // 
            Scanner read = new Scanner(open);       // read the file
            while (read.hasNextLine()) {            // while there's still stuff in file
                bookmarkList.add(read.nextLine());  // read stuff out
            }                                       //
            read.close();                           // close the file
        }                                           //
        catch (Exception e) {                       // catch the exception
            e.printStackTrace();                    //
        }                                           //
    }                                               //

    public void write () {                                              // method to write the bookmarks back to the file
        try {                                                           // try this
            FileWriter fw = new FileWriter ("./bookmarks.txt", false);  // make a new filewriter
            for (int i = 0; i < bookmarkList.size(); i++) {             // for everything in the bookmark list
                fw.write(bookmarkList.get(i) + "\n");                   // write the bookmark and a newline
            }                                                           //
            fw.close();                                                 // close the file when we're done
        }                                                               //
        catch  (Exception e) {                                          // if this doesn't work
            e.printStackTrace();                                        // print the stack trace
        }                                                               //
    }                                                                   //

    public void refresh () {    // refresh the list of bookmarks
        write();                // write to file
        bookmarkList.clear();   // clear the bookmarks list
        read();                 // read from file
    }

    protected void loadTheThing(String url) {                   // method for loading something in the current webview
        view.getEngine().load(url);                             //
    }                                                           //

    public String parseText (String in) {
        Matcher toCheck = urlCheck.matcher(in);                     // regex matcher
        if (toCheck.matches()) {                                    // if the text matches url regex
            return in;                                              // return it
        }                                                           //
        else {                                                      // if the text doesn't match url regex
            String[] search = in.split("\\s+");                     // split the text on whitespace
            String google = "http://www.google.com/search?q=";      // start building a google search url
            for (String i : search) {                               // for each split string
                google = google + i + "+";                          // add the string to the search url with a plus sign
            }                                                       //
            return google;                                          // return search string
        }                                                           //
    }

    public String getLocation () {                  // method for getting current location
        return view.getEngine().getLocation();      //
    }                                               //

    public GridPane getTabContent () {      // get the tab contents
        return p;                           //
    }                                       //
    
    public TabContent () {                                      // start
        read();                                                 // read bookmarks
        ObservableList<String> forCbo =                         // make the list for the combobox
            FXCollections.observableArrayList(bookmarkList);    //
        cbo.setPromptText("Bookmarks");                         // set title of bookmark combobox
        cbo.getItems().addAll(forCbo);                          // put the lsit in the combobox
        address.setMinWidth(700);                               // set the address bar's width
        address.setText(homePage);                              // setting the address bar to read the homepage url
        address.setPromptText("Enter a web address here...");   // setting prompt text
        view.getEngine().load(homePage);                        // loading the homepage
        cbo.setOnAction(e -> {                                  // combobox lambda
            view.getEngine().load(cbo.getValue());              // load the address from the combobox
            address.setText(view.getEngine().getLocation());    // set the address bar text
        });                                                     // end of combobox lambda

        bookmarks.setOnMouseClicked(e -> {                          // bookmark button lambda
            String toBookmark = view.getEngine().getLocation();     // get the current location
            if (bookmarkList.contains(toBookmark)) {                // confirm that the place to bookmark is not already bookmarked
                return;                                             // if so, exit the lambda
            }                                                       // 
            bookmarkList.add(toBookmark);                           // add the location to the list
            refresh();                                              // refresh the list
            cbo.getItems().add(toBookmark);                         // add the current to the combobox
        });                                                         // end bookmark button lambda

        address.setOnKeyPressed(e -> {                                  // address textfield lambda
            if (e.getCode() == KeyCode.ENTER) {                         // if the key pressed is enter
                String toLoad = this.parseText(address.getText());      // parse the text from the address bar
                this.loadTheThing(toLoad);                              // load it
            }                                                           // 
        });                                                             // end of address textfield lambda

        about.setOnMouseClicked(e -> {
            Settings s = new Settings();    // use settings instead of constructing in lambda
            s.start();                      // start settings
        });                                 //

        view.setOnMouseClicked(e -> {                       // link clicked lambda
            String toGo = view.getEngine().getLocation();   // get current location
            address.setText(toGo);                          // set address bar
            
        });                                                 // end of link clicked lambda

        back.setOnMouseClicked(e -> {
            view.getEngine().load(history[0]);
            address.setText(view.getEngine().getLocation());
        });

        view.getEngine().locationProperty().addListener(ov -> {
            if (history[1] == null) {
                if (history[0] == null) {
                    history[0] = view.getEngine().getLocation();
                }
                else {
                    history[1] = view.getEngine().getLocation();
                }
            }
            else {
                history[0] = history[1];
                history[1] = view.getEngine().getLocation();
            }
            System.out.println(history[0] + ", " + history[1]);
        });
        history[0] = view.getEngine().getLocation();
        title.textProperty().bind(view.getEngine().titleProperty());    // bind the title of the webpage to a label
        bottom.getChildren().add(about);                                // add the about button the bottom
        bottom.getChildren().add(title);                                // add the title label to the bottom
        p.add(bookmarks, 0, 0, 1, 1);       // add bookmark button
        p.add(back, 1, 0, 1, 1);            // add the back button
        p.add(address, 2, 0, 5, 1);         // add the address bar
        p.add(cbo, 7, 0, 3, 1);             // add combobox
        p.add(view, 0, 2, 10, 1);           // add the webview
        p.add(bottom, 0, 3, 10, 1);         // add the bottom
    }
}