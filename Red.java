// Wrapper for TabContent.java with tab support

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Red extends Application {

    TabPane pane = new TabPane ();
    
    public String getLocation () {
        Tab current = pane.getSelectionModel().getSelectedItem();
        GridPane contents = (GridPane) current.getContent();
        ObservableList<Node> nodes = contents.getChildren();
        String toReturn = "";
        for (Node i : nodes) {
            if (GridPane.getColumnIndex(i) == 0 && GridPane.getRowIndex(i) == 2) {
                WebView view = (WebView) i;
                toReturn = view.getEngine().getLocation();
                break;
            }
        }
        return toReturn;
    }
    
    public void newTab () {
        Tab nextTab = new Tab();
        TabContent nextTabContent = new TabContent();
        nextTab.textProperty().bind(nextTabContent.view.getEngine().titleProperty());
        nextTab.setContent(nextTabContent.p);
        pane.getTabs().add(nextTab);
        pane.getSelectionModel().selectLast();
    }
    
    public void start (Stage st) {
        Tab newTab = new Tab(" + ");
        newTab.setOnSelectionChanged(e -> {
            newTab();
        });
        newTab.setClosable(false);
        pane.getTabs().add(newTab);
        Scene sc = new Scene(pane);
        st.setTitle("Red Web Browser");
        st.setWidth(1000);
        st.setScene(sc);
        st.show();
    }
}