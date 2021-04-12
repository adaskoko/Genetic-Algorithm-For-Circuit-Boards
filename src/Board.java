import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Board implements Serializable {
    protected int width;
    protected int height;

    protected ArrayList<Pair> pairs;

    public Board(){
        pairs = new ArrayList<>();
    }

    @Override
    public String toString(){
        String out = "Wymiary: ";
        out += width + "x" + height;
        out += "\nPary do polaczenia:";
        for(Pair pair : pairs){
            out += "\n" + pair;
        }

        return out;
    }

    public void readFromFile(String filename){
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\D");

            width = Integer.parseInt(scanner.next());
            height = Integer.parseInt(scanner.next());

            while(scanner.hasNext()){
                int x1, y1, x2, y2;
                x1 = Integer.parseInt(scanner.next());
                y1 = Integer.parseInt(scanner.next());
                x2 = Integer.parseInt(scanner.next());
                y2 = Integer.parseInt(scanner.next());

                pairs.add(new Pair(new Point(x1, y1), new Point(x2, y2)));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
