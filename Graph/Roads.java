import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * Created by Alan on 4/14/2016.
 */
public class Roads
{
    Node start;
    Graph graph;
    String destination;
    double distance;
    boolean found;
    
    public Roads(Graph nGraph, String nDest)
    {
        graph = nGraph;
        destination = nDest;
        distance = 0;
        found = false;
    }

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

        //traversal(graph,graph.getCurrent());

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

    /**
     * Traversal Method for depth 1st. Takes a graph,and a starting node, the arrayList
     * is only to track the visited nodes and for debugging purposes.
     *
     * Starts at the 1st node and check if visited if not then visit & add to arrayList.
     * Then checks for connections if has connections. Then for-each connection
     * check is visited if not visited call traversal again. Repeat until there are no more
     * unvisited nodes then begin to reverse and finish the for-each loops for each call
     * looking for unvisited nodes. Rinse & Repeat.
     *
     * @param graph
     * @param node
     * @param visited
     */

    public static void traversal(Graph graph, Node node, ArrayList<Node> visited)
    {
        if(!node.isVisited())//checks if the node has been visited
        {
            node.visit();//visits node
            visited.add(node);

            if(node.hasConnections())//gets connections
            {

                for(String c : node.getConnections())//gets a set of connections iterates through them
                {
                    if(!graph.get(c).isVisited())//makes sure its not visited
                    {
                        traversal(graph,graph.get(c),visited);//if not visited then visit
                    }//end if
                }//end for-each
            }//end if
        }//end if
    }

    /**
     *
     * @param node
     * @param dist
     * @return
     */
    public void shortestPath(Node node,double dist)
    {
        if(found!= true)
        {
            if(node.connections.containsKey(destination))
            {
                found = true;
                this.distance += node.connections.get(destination);
                return;

            }

            else
            {
                for(double k : node.connections.values())
                {
                    if(k <= dist)
                    {
                        dist = k;
                    }
                }

                for(String y :node.connections.keySet())
                {
                    if(node.connections.get(y) == dist)
                    {
                        if(!graph.get(y).isVisited()&& found!=true)
                        {
                            graph.get(y).visit();
                            this.distance += node.connections.get(y);
                            shortestPath(graph.get(y),1000);
                        }
                    }

                }
            }

        }

    }

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
        //System.out.println("Current total: "+total+"\n");
        return total;
    }//end minimumSpanningTree

}//end Roads