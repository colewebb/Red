import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class Tab extends Red {
    WebView view = new WebView();       // webview
    String history;                     // 1 deep history
    Button tabButton = new Button();    // button
    boolean current = false;            // 
    public Tab () {                                                         // no argument constructor
        view.getEngine().load(homePage);                                    // load the homepage
        tabButton.textProperty().bind(view.getEngine().titleProperty());    // bind the page title to the button text
        tabButton.setMaxWidth(150);                                         // set the max width
    }                                                                       //
    
    public void flipCurrent () {    // flip the current boolean
        if (current) {              // 
            current = false;        //
        }                           //
        else {                      //
            current = true;         //
        }                           //
    }                               //
    
    public void setCurrent (boolean set) {  // current setter
        current = set;                      //
    }                                       //
    
    public WebView getView () {     // get the webview
        return view;                //
    }                               //
    
    public String getHistory () {   // get the history
        return history;             //
    }                               //
    
    public Button getButton () {    // get the button
        return tabButton;           //
    }                               //
}