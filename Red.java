// Wrapper for TabContent.java with tab support

import javafx.application.Application;      // all the imports
import javafx.collections.ObservableList;   //
import javafx.scene.Node;                   //
import javafx.scene.Scene;                  //
import javafx.scene.control.Tab;            //
import javafx.scene.control.TabPane;        //
import javafx.scene.layout.GridPane;        //
import javafx.scene.web.WebView;            //
import javafx.stage.Stage;                  //

public class Red extends Application {

    TabPane pane = new TabPane ();      // new tabpane
    
    public String getLocation () {                                                  // get the location of the current webview
        Tab current = pane.getSelectionModel().getSelectedItem();                   // get the current tab
        GridPane contents = (GridPane) current.getContent();                        // from the tab, get the pane
        ObservableList<Node> nodes = contents.getChildren();                        // from the pane, get the children
        String toReturn = "";                                                       // initialize a return string
        for (Node i : nodes) {                                                      // for each node
            if (GridPane.getColumnIndex(i) == 0 && GridPane.getRowIndex(i) == 2) {  // if the node is where the webview should be
                WebView view = (WebView) i;                                         // cast the node to webview
                toReturn = view.getEngine().getLocation();                          // get the location
                break;                                                              // break
            }                                                                       //
        }                                                                           //
        return toReturn;                                                            // return
    }                                                                               //
    
    public void newTab () {                                                             // make a new tab
        Tab nextTab = new Tab();                                                        // make the tab
        TabContent nextTabContent = new TabContent();                                   // make the tabcontent object
        nextTab.textProperty().bind(nextTabContent.view.getEngine().titleProperty());   // bind the title of the webview to the title of the tab
        nextTab.setContent(nextTabContent.p);                                           // move the content to the tab
        pane.getTabs().add(nextTab);                                                    // add the tab to the tab pane
        pane.getSelectionModel().selectLast();                                          // move current tab
    }                                                                                   //
    
    public void start (Stage st) {              // start
        Tab newTab = new Tab(" + ");            // make the newtab button (tab technically)
        newTab.setOnSelectionChanged(e -> {     // when the newtab tab becomes active
            newTab();                           // make a new tab (which is then switched to by newtab())
        });                                     //
        newTab.setClosable(false);              // remove newtab's close button
        pane.getTabs().add(newTab);             // add the newtab tab to the tabpane
        Scene sc = new Scene(pane);             // new scene
        st.setTitle("Red Web Browser");         // set window title
        st.setWidth(1000);                      // set window width
        st.setScene(sc);                        // set scene
        st.show();                              // show
    }
}