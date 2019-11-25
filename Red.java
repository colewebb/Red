// Specs
// 
// - due last week of class, for presentations
// - two types of javafx panes                      (done)
// - six types of nodes                             (done)
// - an animation                                   (done)
// - demonstrate events, bindings and listeners     (done)
//
// Web browser specifics:
// - Address bar, with ability to type a web address and load it        (done)
// - Allow a user to browse a website                                   (done)
// - Dropdown menu for adding favorite sites                            (done)
// - Clicking on a link in dropdown loads it                            (done)
// - Back button with 1-deep history (only load the previous site)
// - Tabbed UI
// - Save bookmarks to file for permeance                               (done)
// - If string typed in address bar isn't a web address, web search it  (done)

import java.io.File;                        // all the imports
import java.io.FileWriter;                  //
import java.util.ArrayList;                 //
import java.util.Scanner;                   //
import java.util.regex.Matcher;             //
import java.util.regex.Pattern;             //
import javafx.animation.FadeTransition;     //
import javafx.animation.Timeline;           //
import javafx.application.Application;      //
import javafx.collections.FXCollections;    //
import javafx.collections.ObservableList;   //
import javafx.stage.Stage;                  //
import javafx.util.Duration;                //
import javafx.scene.layout.GridPane;        //
import javafx.scene.layout.HBox;            //
import javafx.scene.layout.Pane;            //
import javafx.scene.layout.VBox;            //
import javafx.scene.paint.Color;            //
import javafx.scene.shape.Circle;           //
import javafx.scene.control.TextField;      //
import javafx.scene.input.KeyCode;          //
import javafx.scene.Scene;                  //
import javafx.scene.control.Button;         //
import javafx.scene.control.ComboBox;       //
import javafx.scene.control.Label;          //
import javafx.scene.web.WebView;            //

public class Red extends Application {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button bookmarks = new Button("Bookmark");                  // Bookmarks button
    WebView view = new WebView();                               // new webview, for viewing the web
    String homePage = "https://cs.usu.edu";                     // setting homepage
    ComboBox<String> cbo = new ComboBox<>();                    // new combobox for bookmarks
    ArrayList<String> bookmarkList = new ArrayList<>();         // list of bookmarks
    ArrayList<Tab> tabs = new ArrayList<Tab>();                 // list of tabs
    HBox tabContainer = new HBox();                             // holder for the tabs
    Button newTab = new Button(" + ");                          // add tab button
    Button closeTab = new Button(" x ");                        // close tab button
    HBox bottom = new HBox();                                   // bottom bar hbox
    Label title = new Label();                                  // label for page title
    Button about = new Button("About");                         // about button
    ArrayList<String> history = new ArrayList<>();              // history
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

    // public void onPageLoad () {                             // run on a page load
    //     String location = view.getEngine().getLocation();   // get location
    //     if (history.contains(location) == false) {          // if the history hasn't seen this location before
    //         history.add(location);
    //     }
    // }                                                       //

    protected String getLocation () {                           // method for getting current location
        return view.getEngine().getLocation();                  //
    }                                                           //
    
    public void start(Stage st) {                               // start
        read();                                                 // read bookmarks
        tabContainer.getChildren().addAll(newTab, closeTab);    // add tab control buttons
        ObservableList<String> forCbo =                         // make the list for the combobox
            FXCollections.observableArrayList(bookmarkList);    //
        cbo.getItems().add("Bookmarks");                        //
        cbo.getItems().addAll(forCbo);                          // put the lsit in the combobox
        address.setMinWidth(700);                               // set the address bar's width
        address.setText(homePage);                              // setting the address bar to read the homepage url
        address.setPromptText("Enter a web address here...");   // setting prompt text
        view.getEngine().load(homePage);                        // loading the homepage

        cbo.setOnAction(e -> {                                  // combobox lambda
            if (cbo.getValue() == "Bookmarks") {                // if the combobox is for the Bookmarks window
                Bookmarks b = new Bookmarks();                  // open up the Bookmarks window
                b.start();                                      //
            }                                                   //
            view.getEngine().load(cbo.getValue());              // load the address from the combobox
            address.setText(view.getEngine().getLocation());    // set the address bar text
        });                                                     // end of combobox lambda

        st.widthProperty().addListener(ov -> {      // set the width of the combobox
            cbo.setMaxWidth(st.getWidth() / 8);     // to the width of the window, divided by 8
        });                                         //

        bookmarks.setOnMouseClicked(e -> {                          // bookmark button lambda
            String toBookmark = view.getEngine().getLocation();     // get the current location
            if (bookmarkList.contains(toBookmark)) {                // confirm that the place to bookmark is not already bookmarked
                return;                                             // if so, exit the lambda
            }                                                       // 
            bookmarkList.add(toBookmark);                           // add the location to the list
            refresh();                                              // refresh the list
            cbo.getItems().add(toBookmark);                         // add the current to the combobox
        });                                                         // end bookmark button lambda

        back.setOnMouseClicked(e -> {               // back button lambda TODO: broken back button
            for (String i : history) {
                System.out.println(i);
            }
            // this.onPageLoad();                      // run onPageLoad
            view.getEngine().load(history.get(0));      // load the history
        });                                         // end of back button lambda

        address.setOnKeyPressed(e -> {                                      // address textfield lambda
            if (e.getCode() == KeyCode.ENTER) {                             // 
                String toLoad = this.parseText(address.getText());
                this.loadTheThing(toLoad);
            }                                                               // 
        });                                                                 // end of address textfield lambda

        about.setOnMouseClicked(e -> {
            Stage about = new Stage();                              // new stage
            Pane aboutPane = new Pane();                            // new pane
            VBox vb = new VBox();                                   // new vbox
            Circle icon = new Circle(75);                           // new circle
            FadeTransition ft = new                                 // making a fade transition, for the icon
                FadeTransition(Duration.millis(3000), icon);        //
            ft.setFromValue(1.0);                                   // fades from opaque
            ft.setToValue(0.0);                                     // to transparent
            ft.setCycleCount(Timeline.INDEFINITE);                  // run the animation forever
            ft.setAutoReverse(true);                                // reverse it
            ft.play();                                              // play the animation
            icon.setFill(Color.RED);                                // set the fill on the icon
            vb.getChildren().add(icon);                             // add the icon
            vb.getChildren().add(new Label("Red Web Browser"));     // label
            vb.getChildren().add(new Label("'Pathetic Penguin'"));  // label
            vb.getChildren().add(new Label("This is free software, distributed without any promise of \nsupport or fitness for a specific task. Licensed under the \nGNU Public License v3."));
            aboutPane.getChildren().add(vb);                        // add the vbox to the pane
            Scene aboutScene = new Scene(aboutPane);                // new scene
            about.setScene(aboutScene);                             // set scene
            about.setTitle("About");                                // set title
            about.setMinWidth(250);                                 // set width
            about.setMinHeight(125);                                // set height
            about.show();                                           // show
        });

        view.setOnMouseClicked(e -> {                       // link clicked lambda TODO: don't add the address more than once in a row
            String toGo = view.getEngine().getLocation();   // get current location
            // this.onPageLoad();                              // run the pageLoad
            address.setText(toGo);                          // set address bar
        });                                                 // end of link clicked lambda

        newTab.setOnMouseClicked(e -> {
            Tab toAdd = new Tab();
            toAdd.load(homePage);
            toAdd.begin();
            tabContainer.getChildren().add(toAdd.getButton());
        });

        title.textProperty().bind(view.getEngine().titleProperty());    // bind the title of the webpage to a label
        bottom.getChildren().add(about);                                // add the about button the bottom
        bottom.getChildren().add(title);                                // add the title label to the bottom
        p.add(bookmarks, 0, 0, 1, 1);       // add bookmark button
        p.add(back, 1, 0, 1, 1);            // add the back button
        p.add(address, 2, 0, 5, 1);         // add the address bar
        p.add(cbo, 7, 0, 3, 1);             // add combobox
        p.add(tabContainer, 0, 1, 10, 1);   // add the tab container
        p.add(view, 0, 2, 10, 1);           // add the webview
        p.add(bottom, 0, 3, 10, 1);         // add the bottom
        Scene sc = new Scene(p);            // new scene
        st.setWidth(750);                   // setting the stage's width
        st.setScene(sc);                    // set the scene
        st.setTitle("Red Web Browser");     // set the title
        st.show();                          // show everything
    }
}