import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Created by Alan on 4/14/2016.
 */
public class Roads {
    public static void main(String[] args ) throws FileNotFoundException
    {
       Graph graph = new Graph();
        FileReader fin = new FileReader("tempInput.txt");
        Scanner in = new Scanner(fin);

        while(in.hasNextLine())
        {
            String city,
                    destination;
            Integer distance = 0;

            String[] information = in.nextLine().split(",");
            city = information[0].trim();
            destination = information[1].trim();
            distance = Integer.parseInt(information[2].trim());
            graph.addNode(city,destination,distance);
        }

            for (int i = 0; i < 8; i++) {
                System.out.println(graph.getName());
                graph.printConnections();
                graph.next();
            }
    }
}
