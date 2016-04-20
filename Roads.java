import java.util.Scanner;

/**
 * Created by Alan on 4/14/2016.
 */
public class Roads {
    public static void main(String[] args )
    {

       Graph graph = new Graph();
        graph.addNode("Hot Springs");
        graph.addNode("Malvern");
        graph.addNode("Pine Bluff");
        graph.addNode("Little Rock");

        for(int i= 0; i< 10; i++)
        {
            System.out.println(graph.getName());
            graph.next();
        }
    }
}
