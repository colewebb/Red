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
// - If string typed in address bar isn't a web address, web search it

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;            // pane 1
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;                // pane 2
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;      // node 1
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;                  // node 2
import javafx.scene.control.Button;         // node 3
import javafx.scene.control.ComboBox;       // node 4
import javafx.scene.control.Label;          // node 5
import javafx.scene.web.WebView;            // node 6

public class Red extends Application {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button bookmarks = new Button("Bookmark");                  // Bookmarks button
    WebView view = new WebView();                               // new webview, for viewing the web
    String homePage = "https://cs.usu.edu";                     // setting homepage
    ComboBox<String> cbo = new ComboBox<>();                    // new combobox for bookmarks
    ArrayList<String> bookmarkList = new ArrayList<>();         // list of bookmarks
    HBox bottom = new HBox();
    Label title = new Label();
    Button about = new Button("About");
    String[] history = new String[2];
    Pattern urlCheck = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
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
    }

    public void write () {                                              // method to write the bookmarks back to the file
        try {                                                           //
            FileWriter fw = new FileWriter ("./bookmarks.txt", false);  //
            for (int i = 0; i < bookmarkList.size(); i++) {             //
                fw.write(bookmarkList.get(i) + "\n");                   //
            }                                                           //
            fw.close();                                                 //
        }                                                               //
        catch  (Exception e) {                                          //
            e.printStackTrace();                                        //
        }                                                               //
    }

    public void refresh () {
        write();
        bookmarkList.clear();
        read();
    }

    protected void loadTheThing(String url) {                   // method for loading something in the current webview
        view.getEngine().load(url);                             //
    }

    public void onPageLoad () {
        String location = view.getEngine().getLocation();
        for (String newString : history) {
            if (location.equals(newString)) {
                return;
            }
        }
        if (history[1] != null) {
            history[0] = history[1];
            history[1] = location;
        }
        else {
            history[1] = location;
        }
    }

    protected String getLocation () {                           // method for getting current location
        return view.getEngine().getLocation();                  //
    }
    
    public void start(Stage st) {                               // start
        read();                                                 //
        bottom.setAlignment(Pos.CENTER_LEFT);                   // 
        ObservableList<String> forCbo =                         //
            FXCollections.observableArrayList(bookmarkList);    //
        cbo.getItems().addAll(forCbo);                          //
        address.setMinWidth(700);                               // set the address bar's width
        address.setText(homePage);                              // setting the address bar to read the homepage url
        address.setPromptText("Enter a web address here...");   // setting prompt text
        view.getEngine().load(homePage);                        // loading the homepage

        cbo.setOnAction(e -> {                                  // combobox lambda
            view.getEngine().load(cbo.getValue());              // load the address from the combobox
            address.setText(view.getEngine().getLocation());    // set the address bar text
        });                                                     // end of combobox lambda

        st.widthProperty().addListener(ov -> {
            cbo.setMaxWidth(st.getWidth() / 8);
        });

        bookmarks.setOnMouseClicked(e -> {                          // bookmark button lambda
            String toBookmark = view.getEngine().getLocation();     // get the current location
            bookmarkList.add(toBookmark);                           // add the location to the list
            refresh();                                              // refresh the list
            cbo.getItems().add(toBookmark);                         // add the current to the combobox
        });                                                         // end bookmark button lambda

        back.setOnMouseClicked(e -> {                               // back button lambda TODO: broken back button
            this.onPageLoad();
            view.getEngine().load(history[0]);
        });                                                         // end of back button lambda

        address.setOnKeyPressed(e -> {                          // address textfield lambda
            if (e.getCode() == KeyCode.ENTER) {                 // 
                Matcher toCheck = urlCheck.matcher(address.getText());
                if (toCheck.matches()) {
                    view.getEngine().load(address.getText());   //
                }
                else {
                    String[] search = address.getText().split("\\s+");
                    String google = "http://www.google.com/search?q=";
                    for (String i : search) {
                        google = google + i + "+";
                    }
                    view.getEngine().load(google);
                }
                
            }                                                   // 
        });                                                     // end of address textfield lambda
        about.setOnMouseClicked(e -> {
            Stage about = new Stage();                              // new stage
            Pane aboutPane = new Pane();                            // new pane
            VBox vb = new VBox();                                   // new vbox
            Circle icon = new Circle(75);
            FadeTransition ft = new 
                FadeTransition(Duration.millis(3000), icon);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
            icon.setFill(Color.RED);
            vb.getChildren().add(icon);
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
            this.onPageLoad();
            address.setText(toGo);                          // set address bar
        });                                                 // end of link clicked lambda
        title.textProperty().bind(view.getEngine().titleProperty());    // bind the title of the webpage to a label
        bottom.getChildren().add(about);                                // add the about button the bottom
        bottom.getChildren().add(title);                                // add the title label to the bottom
        // cbo.setMaxWidth(150);           // set the width of the combobox
        p.add(bookmarks, 0, 0, 1, 1);   // add bookmark button
        p.add(back, 1, 0, 1, 1);        // add the back button
        p.add(address, 2, 0, 5, 1);     // add the address bar
        p.add(cbo, 7, 0, 3, 1);         // add combobox
        p.add(view, 0, 1, 10, 1);       // add the webview
        p.add(bottom, 0, 2, 10, 1);     // add the bottom
        Scene sc = new Scene(p);        // new scene
        st.setWidth(750);               // setting the stage's width
        st.setScene(sc);                // set the scene
        st.setTitle("Red Web Browser"); // set the title
        st.show();                      // show everything
    }
}