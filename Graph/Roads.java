<<<<<<< HEAD
package Graph;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Created by Alan on 4/14/2016.
 */
public class Roads
{
    public static void main(String[] args) throws FileNotFoundException
    {
    	Graph graph = new Graph();
        FileReader fin = new FileReader("/home/loki/Documents/Roads/Roads/src/txt.txt");
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

        traversal(graph,graph.getCurrent());
      //  try
       // {
         //   for (Node n : graph) 
          //  {
           //     System.out.println(graph.getName());
            //    n.printConnections();
            //}
        //}
        //catch(NoSuchElementException x)
        //{
        //	
        //}
        in.close();
    }
    
    public static void traversal(Graph g, Node n)
    {
    	ArrayList<Node> visited = new ArrayList<Node>();
    	try
    	{
	    	for(Node x : g)// goes through graph
	    	{
	    		if(!x.isVisited())//checks if the node has been visited
	    		{
	    			x.visit();//visits node
	    			visited.add(x);
	    			if(x.hasConnections())//gets connections
	    			{
	    				for(String c : x.getConnections())//gets a set of connections iterates through them
	    				{
	    					if(g.contains(c))//makes sure the graph contains the element
	    					{
	    						if(!g.get(c).isVisited())//makes sure its not visited
	    						{
	    							g.get(c).visit();    //if not visited then visit
	    							visited.add(g.get(c));
	    						}
	    					}
	    					else//if graph not contain the String c from set then somethin' broke
	    					{
	    						System.out.println("ERROR");
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	catch(NoSuchElementException x)
    	{
    		
    	}
    	System.out.println(visited);
    }
}
=======
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Created by Alan on 4/14/2016.
 */
public class Roads
{
    public static void main(String[] args) throws FileNotFoundException
    {
    	Graph graph = new Graph();
        FileReader fin = new FileReader("tempInput.txt");
        Scanner in = new Scanner(fin);

        while(in.hasNextLine())
        {
            String city,
                    destination;
            Double distance = 0.0;

            String[] information = in.nextLine().split(",");
            city = information[0].trim();
            destination = information[1].trim();
            distance = Double.parseDouble(information[2].trim());
            graph.addNode(city,destination,distance);
        }

        traversal(graph,graph.getCurrent());

        System.out.print(1);
        minimumSpanningTree(graph,"Arkadelphia");
        System.out.print(2);
        minimumSpanningTree(graph,"Malvern");
        System.out.print(3);
        minimumSpanningTree(graph,"Hot Springs");
        System.out.print(4);
        minimumSpanningTree(graph,"Hope");
        System.out.print(5);
        minimumSpanningTree(graph,"Camden");
        System.out.print(6);
        minimumSpanningTree(graph,"Prescott");
        System.out.print(7);
        minimumSpanningTree(graph,"Pine Bluff");
        System.out.print(8);
        minimumSpanningTree(graph,"Little Rock");




        in.close();
    }
    
    public static void traversal(Graph g, Node n)
    {
    	ArrayList<Node> visited = new ArrayList<Node>();
    	try
    	{
	    	for(Node x : g)// goes through graph
	    	{
	    		if(!x.isVisited())//checks if the node has been visited
	    		{
	    			x.visit();//visits node
	    			visited.add(x);
	    			if(x.hasConnections())//gets connections
	    			{
	    				for(String c : x.getConnections())//gets a set of connections iterates through them
	    				{
	    					if(g.contains(c))//makes sure the graph contains the element
	    					{
	    						if(!g.get(c).isVisited())//makes sure its not visited
	    						{
	    							g.get(c).visit();    //if not visited then visit
	    							visited.add(g.get(c));
	    						}
	    					}
	    					else//if graph not contain the String c from set then somethin' broke
	    					{
	    						System.out.println("ERROR");
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	catch(NoSuchElementException x)
        {}
    	System.out.println(visited);
    }//end traversal

    /**
     * Takes a start city and finds the smallest connection between all of the currently visited nodes. When a node
     * is determined to be part of an edge that has the smallest value, it becomes part of the visited nodes. The
     * process repeats for one less than the total number of nodes in the graph since the first node is visited with no
     * value.
     * 
     * @param g the graph being used
     * @param startCity the city that is being used as the start point for the minimum tree.
     * @return
     */
    public static double minimumSpanningTree(Graph g, String startCity )
    {
        double currentMin = 0;
        String minCity = "";
        double total = 0;
        ArrayList<String> visited =  new ArrayList<>();
        visited.add(startCity);

        //
        for(int i=0; i< g.counter-1;i++) {
            for (String cityName : visited) {
                Node node = g.get(cityName);
                String city = "";
                double minimum = currentMin;

                Set<String> connections = node.connections.keySet();
                for (String entry : connections) {
                    if (minimum == 0 && !visited.contains(entry))//sets the minimum as first element in set.
                    {
                        minimum = node.connections.get(entry);
                        currentMin = minimum;
                        minCity = entry;
                    } else if (minimum > node.connections.get(entry) && !visited.contains(entry))//checks for new minimum
                    {
                        minimum = node.connections.get(entry);
                        city = entry;
                        currentMin = minimum;
                        minCity = city;

                    }
                }//end iteration
            }
            visited.add(minCity);
            total  += currentMin;

            currentMin = 0;

        }
        System.out.println("Current total: "+total+"\n");
        return total;
    }//end minimumSpanningTree

}//end Roads
>>>>>>> refs/remotes/origin/master
