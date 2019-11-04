// Specs
// 
// - due last week of class, for presentations
// - two types of javafx panes
// - six types of nodes
// - an animation
// - demonstrate events, bindings and listeners
//
// Web browser specifics:
// - Address bar, with ability to type a web address and load it        (done)
// - Allow a user to browse a website                                   (done)
// - Dropdown menu for adding favorite sites                            (done)
// - Clicking on a link in dropdown loads it                            (done)
// - Back button with 1-deep history (only load the previous site)

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebView;

public class Red extends Application {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button bookmarks = new Button("Bookmark");                  // Bookmarks button
    WebView view = new WebView();                               // new webview, for viewing the web
    ArrayList<String> history = new ArrayList<String>();        // new arraylist for tracking history
    String homePage = "https://cs.usu.edu";                     // setting homepage
    ComboBox<String> cbo = new ComboBox<>();
    ArrayList<String> bookmarkList = new ArrayList<>();
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
    protected String getLocation () {                           // method for getting current location
        return view.getEngine().getLocation();                  //
    }
    public void start(Stage st) {                               // start
        read();                                                 //
        // cbo.getItems().add("Bookmarks");
        ObservableList<String> forCbo =                         //
            FXCollections.observableArrayList(bookmarkList);    //
        cbo.getItems().addAll(forCbo);                          //
        address.setMinWidth(700);                               // set the address bar's width
        history.add(homePage);                                  // adding the homepage to history
        address.setText(homePage);                              // setting the address bar to read the homepage url
        address.setPromptText("Enter a web address here...");   // setting prompt text
        view.getEngine().load(homePage);                        // loading the homepage
        cbo.setOnAction(e -> {
            // if (cbo.getValue().equals("Bookmarks")) {
            //     Bookmarks b = new Bookmarks();
            //     b.start();
            // }
            // else {
                view.getEngine().load(cbo.getValue());
                address.setText(view.getEngine().getLocation());
            // }
        });
        // TODO: bind address bar to current location in the webview
        bookmarks.setOnMouseClicked(e -> {
            String toBookmark = view.getEngine().getLocation();
            bookmarkList.add(toBookmark);
            refresh();
            cbo.getItems().add(toBookmark);
        });
        
        back.setOnMouseClicked(e -> {                               // back button lambda TODO: broken back button
            String current = view.getEngine().getLocation();        // get the current position
            System.out.println(history.size());                     // print debugging info
            for (int i = 0; i < history.size(); i++) {              // search history for the current location
                if (current.equals(history.get(i))) {               // when you find it
                    view.getEngine().load(history.get(i - 1));      // load the address we were at before
                    address.setText(history.get(i - 1));            // and set the address bar accordingly
                }                                                   //
            }                                                       //
        });                                                         // end of back button lambda

        address.setOnKeyPressed(e -> {                          // address textfield lambda
            if (e.getCode() == KeyCode.ENTER) {                 // 
                view.getEngine().load(address.getText());       //
                history.add(view.getEngine().getLocation());    //
            }                                                   // 
        });                                                     // end of address textfield lambda

        view.setOnMouseClicked(e -> {                       // link clicked lambda TODO: don't add the address more than once in a row
            String toGo = view.getEngine().getLocation();   // get current location
            history.add(toGo);                              // update history
            address.setText(toGo);                          // set address bar
        });                                                 // end of link clicked lambda
        p.add(bookmarks, 0, 0, 1, 1);
        p.add(back, 1, 0, 1, 1);        // add the back button
        p.add(address, 2, 0, 5, 1);     // add the address bar
        p.add(cbo, 7, 0, 3, 1);
        p.add(view, 0, 1, 10, 1);       // add the webview
        Scene sc = new Scene(p);        // new scene
        st.setWidth(750);               // setting the stage's width
        st.setScene(sc);                // set the scene
        st.setTitle("Red Web Browser"); // set the title
        st.show();                      // show everything
    }
}