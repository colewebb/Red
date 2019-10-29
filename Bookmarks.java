import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Bookmarks {
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> urls = new ArrayList<String>();
    public Bookmarks () {   // constructor: reads in bookmarks from file and fills in local arraylists
        read();             // read in bookmarks from file
    }                       //
    public void read () {                           // method to read bookmarks from file and populate local variables
        File open = new File("./bookmarks.txt");    // open the file
        try {                                       // 
            Scanner read = new Scanner(open);       // read the file
            while (read.hasNextLine()) {            // while there's still stuff in file
                names.add(read.nextLine());         // line n is the name
                urls.add(read.nextLine());          // and line n+1 is the address
            }                                       //
            read.close();                           // close the file
        }                                           //
        catch (Exception e) {                       // catch the exception
            e.printStackTrace();                    //
        }                                           //
    }
    public void write () {              // method to write the bookmarks back to the file

    }
    public void refresh () {    // refreshes the bookmark file
        write();                //
        read();                 //
    }
    public void add (String name, String url) throws FileAlreadyExistsException {   // method to add a bookmark
        for (int i = 0; i < names.size(); i++) {                                    // 
            if (name.equals(names.get(i))) {                                        // check to be sure that a bookmark with the given name doesn't already exist
                throw new FileAlreadyExistsException(name);                         // throw FileAlreadyExists exception if it does
            }                                                                       //
        }                                                                           //
        names.add(name);                                                            // add the name
        urls.add(url);                                                              // add the address
    }
    public String get (String name) throws FileNotFoundException {      // gets the address given the name
        for (int i = 0; i < names.size(); i++) {                        // basic find
            if (name.equals(names.get(i))) {                            //
                return urls.get(i);                                     //
            }                                                           //
        }                                                               //
        throw new FileNotFoundException(name);                          // throws FileNotFound exception if the bookmark isn't found
    }
    public String[] getAllNames () {                    // returns a list of all the names
        String[] toReturn = new String[names.size()];   //
        for (int i = 0; i < names.size(); i++) {        //
            toReturn[i] = names.get(i);                 //
        }                                               //
        return toReturn;                                //
    }
    public String[] getAllURLs () {                     // returns a list of all the addresses
        String[] toReturn = new String[urls.size()];    //
        for (int i = 0; i < urls.size(); i++) {         //
            toReturn[i] = urls.get(i);                  //
        }                                               //
        return toReturn;                                //
    }
    public boolean delete (String name) {           // deleting a bookmark, given its name (returns true if successful deletion, false otherwise)
        for (int i = 0; i < names.size(); i++) {    // 
            if (name.equals(names.get(i))) {        //
                names.remove(i);                    //
                urls.remove(i);                     //
                return true;                        //
            }                                       //
        }                                           //
        return false;                               //
    }
}