import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Bookmarks {
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> urls = new ArrayList<String>();
    public Bookmarks () {                           // constructor: reads in bookmarks from file and fills in local arraylists
        File open = new File("./bookmarks.txt");    //
        try {                                       //
            Scanner read = new Scanner(open);       //
            while (read.hasNextLine()) {            //
                names.add(read.nextLine());         //
                urls.add(read.nextLine());          //
            }                                       //
            read.close();                           //
        }                                           //
        catch (Exception e) {                       //
            e.printStackTrace();                    //
        }                                           //
    }                                               //
    public void write () {

    }
    public void add (String name, String url) throws FileAlreadyExistsException {   // 
        for (int i = 0; i < names.size(); i++) {
            if (name.equals(names.get(i))) {
                throw new FileAlreadyExistsException(name);
            }
        }
        names.add(name);
        urls.add(url);
    }
    public String get (String name) throws FileNotFoundException {
        for (int i = 0; i < names.size(); i++) {
            if (name.equals(names.get(i))) {
                return urls.get(i);
            }
        }
        throw new FileNotFoundException(name);
        
    }
    public String[] getAllNames () {
        String[] toReturn = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            toReturn[i] = names.get(i);
        }
        return toReturn;
    }
    public String[] getAllURLs () {
        String[] toReturn = new String[urls.size()];
        for (int i = 0; i < urls.size(); i++) {
            toReturn[i] = urls.get(i);
        }
        return toReturn;
    }
    public boolean delete (String name) {
        for (int i = 0; i < names.size(); i++) {
            if (name.equals(names.get(i))) {
                names.remove(i);
                urls.remove(i);
                return true;
            }
        }
        return false;
    }
}