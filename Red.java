import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class Red extends Application {
    GridPane p = new GridPane();                                // new GridPane
    TextField address = new TextField();                        // new text field, for address
    Button back = new Button(" < ");                            // new back button
    Button forward = new Button(" > ");                         // new forward button
    WebView view = new WebView();                               // new webview, for viewing the web
    ArrayList<String> history = new ArrayList<String>();        // new arraylist for tracking history
    public void start(Stage st) {                               // start
        address.setMinWidth(700);                               // set the address bar's width
        String homePage = "https://google.com";                 // setting homepage
        history.add(homePage);                                  // adding the homepage to history
        address.setText(homePage);                              // setting the address bar to read the homepage url
        view.getEngine().load(homePage);                        // loading the homepage

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

        forward.setOnMouseClicked(e -> {                        // forward button lambda TODO: broken forward button
            String current = view.getEngine().getLocation();    // get the current url
            for (int i = 0; i < history.size() - 1; i++) {      // search for the current url in history
                if (current.equals(history.get(i))) {           // when you find it
                    view.getEngine().load(history.get(i + 1));  // load the next url
                    address.setText(history.get(i + 1));        // and set the address bar accordingly TODO: make sure this doesn't crash if there is no forward
                }                                               //
            }                                                   //
        });                                                     // end of forward lambda

        view.setOnMouseClicked(e -> {                       // link clicked lambda TODO: don't add the address more than once in a row
            String toGo = view.getEngine().getLocation();   // get current location
            history.add(toGo);                              // update history
            address.setText(toGo);                          // set address bar
        });                                                 // end of link clicked lambda

        p.add(back, 0, 0, 1, 1);        // add the back button
        p.add(forward, 1, 0, 1, 1);     // add the forward button
        p.add(address, 2, 0, 8, 1);     // add the address bar
        p.add(view, 0, 1, 10, 1);       // add the webview
        Scene sc = new Scene(p);        // new scene
        st.setWidth(750);               // setting the stage's width
        st.setScene(sc);                // set the scene
        st.setTitle("Red Web Browser"); // set the title
        st.show();                      // show everything
    }
}