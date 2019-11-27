import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Bookmarks extends Red {
    private ArrayList<String> urls = new ArrayList<String>();
    public Bookmarks () {   // constructor: reads in bookmarks from file and fills in local arraylists
        read();             // read in bookmarks from file
    }                       //

    public void read () {                           // method to read bookmarks from file and populate local variables
        File open = new File("./bookmarks.txt");    // open the file
        try {                                       // 
            Scanner read = new Scanner(open);       // read the file
            while (read.hasNextLine()) {            // while there's still stuff in file
                urls.add(read.nextLine());          // read the URLs out
            }                                       //
            read.close();                           // close the file
        }                                           //
        catch (Exception e) {                       // catch the exception
            e.printStackTrace();                    //
        }                                           //
    }                                               //

    public void write () {                                              // method to write the bookmarks back to the file
        try {                                                           //
            FileWriter fw = new FileWriter ("./bookmarks.txt", false);  //
            for (int i = 0; i < urls.size(); i++) {                     //
                fw.write(urls.get(i)+"\n");                             //
            }                                                           //
            fw.close();                                                 //
        }                                                               //
        catch  (Exception e) {                                          //
            e.printStackTrace();                                        //
        }                                                               //
    }                                                                   //

    public void refresh () {    // refreshes the bookmark file
        write();                //
        urls.clear();           //
        read();                 //
    }                           //

    public void add (String url) throws FileAlreadyExistsException {   // method to add a bookmark
        for (int i = 0; i < urls.size(); i++) {                        // 
            if (url.equals(urls.get(i))) {                             // check to be sure that a bookmark with the given name doesn't already exist
                throw new FileAlreadyExistsException(url);             // throw FileAlreadyExists exception if it does
            }                                                          //
        }                                                              //
        urls.add(url);                                                 // add the address
    }                                                                  //

    public String[] getAllURLs () {                     // returns a list of all the addresses
        String[] toReturn = new String[urls.size()];    //
        for (int i = 0; i < urls.size(); i++) {         //
            toReturn[i] = urls.get(i);                  //
        }                                               //
        return toReturn;                                //
    }                                                   //

    public boolean delete (String url) {            // deleting a bookmark, given its name (returns true if successful deletion, false otherwise)
        for (int i = 0; i < urls.size(); i++) {     // 
            if (url.equals(urls.get(i))) {          //
                urls.remove(i);                     //
                return true;                        //
            }                                       //
        }                                           //
        return false;                               //
    }                                               //

    public void onAdd() {                                       // method for the add button
        String toBookmarkURL = super.getLocation();             // grab current location
        try {                                                   //
            this.add(toBookmarkURL);                            // add the bookmark to the file
        }                                                       //
        catch (Exception e) {                                   //
            e.printStackTrace();                                //
        }                                                       //
        finally {                                               //
            refresh();                                          // refresh when done
        }                                                       //                            
    }

    public void onDelete(String url) {     // method for the delete button
        for (String i : urls) {
            if (i.equals(url)) {
                urls.remove(i);
            }
        }
        refresh();
    }

    public void onGo (String url) {        // method for the go button
        super.newTab(url);
    }

    public void start() {
        refresh();
        Stage st = new Stage();                         // new stage
        BorderPane bookmarkPane = new BorderPane();     // borderpane
        ListView<String> list = new ListView<String>(   // ListView for bookmarks
            FXCollections.observableArrayList(          //
                this.getAllURLs()));                    // 
        Button add = new Button("Add");                 // add button
        add.setOnMouseClicked(e -> {                    // add button lambda
            this.onAdd();                               //
        });                                             // end add button lambda
        Button delete = new Button("Delete");           // delete button
        delete.setOnMouseClicked(e -> {                 // delete button lambda
            onDelete(list.getSelectionModel().getSelectedItem());
            list.getItems().remove(list.getSelectionModel().getSelectedItem());
        });                                             // end delete button lambda
        Button go = new Button ("Go!");                 // go button
        go.setOnMouseClicked(e -> {                     // go button lambda
            onGo(list.getSelectionModel().getSelectedItem());
        });                                             //end go button lambda
        HBox bottomBar = new HBox(add, delete, go);     // hbox for bottom bar
        bottomBar.setAlignment(Pos.CENTER);             // setting bottom bar alignment
        Scene bookmarkScene = new Scene(bookmarkPane);  // scene
        bookmarkPane.setCenter(list);                   // add list to pane
        bookmarkPane.setBottom(bottomBar);              // add bottom bar to pane
        bookmarkPane.setMaxSize(300, 400);              // setting the window size
        st.setScene(bookmarkScene);                     // setting scene
        st.setTitle("Red Bookmarks");                   // setting title
        st.setAlwaysOnTop(true);                        // making window always on top
        st.show();                                      // showing the stage
    }                                                   // 
}