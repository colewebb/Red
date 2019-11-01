// Specs
// 
// - due last week of class, for presentations
// - two types of javafx panes
// - six types of nodes
// - an animation
// - demonstrate events, bindings and listeners
// - server -> client or client -> client communication
//      - No extra component for web browser
//
// Web browser specifics:
// - Address bar, with ability to type a web address and load it
// - Allow a user to browse a website
// - Dropdown menu for adding favorite sites
// - Clicking on a link in dropdown loads it
// - Back button with 1-deep history (only load the previous site)

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class Red extends Application {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button bookmarks = new Button("Bookmarks");                 // Bookmarks button
    WebView view = new WebView();                               // new webview, for viewing the web
    ArrayList<String> history = new ArrayList<String>();        // new arraylist for tracking history
    String homePage = "https://cs.usu.edu";                     // setting homepage

    protected void loadTheThing(String url) {                   // method for loading something in the current webview
        view.getEngine().load(url);                             //
    }
    protected String getLocation () {                           // method for getting current location
        return view.getEngine().getLocation();                  //
    }
    public void start(Stage st) {                               // start
        address.setMinWidth(700);                               // set the address bar's width
        history.add(homePage);                                  // adding the homepage to history
        address.setText(homePage);                              // setting the address bar to read the homepage url
        address.setPromptText("Enter a web address here...");   // setting prompt text
        view.getEngine().load(homePage);                        // loading the homepage
        bookmarks.setOnMouseClicked(e -> {
            Bookmarks b = new Bookmarks();
            b.start();
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

        p.add(bookmarks, 0, 0, 1, 1);   // add the bookmarks button
        p.add(back, 1, 0, 1, 1);        // add the back button
        p.add(address, 2, 0, 8, 1);     // add the address bar
        p.add(view, 0, 1, 10, 1);       // add the webview
        Scene sc = new Scene(p);        // new scene
        st.setWidth(750);               // setting the stage's width
        st.setScene(sc);                // set the scene
        st.setTitle("Red Web Browser"); // set the title
        st.show();                      // show everything
    }
}