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