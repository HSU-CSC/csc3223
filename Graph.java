import java.util.HashMap;

/**
 * Created by Alan on 4/14/2016.
 *
 * The graph is made with the concept of a circularly linked list in mind. There are nodes that are the cities that
 * contain the city name, whether it has been visited, a list of all the connections it has in the graph, and a
 * reference to the next node. This allows the traversal of the upper level of the graph making selection of any
 * particular node easier. Without a beginning or end to the list, the end point is determined by making a temp
 * Node called top and making sure that the next node is not the one that was set to top.
 */
public class Graph {
    private Node current;

    public Graph()
    {
        current = null;
    }

    public String getName()
    {
        return current.name;
    }

    //TODO: Change this to add connections from the string the data file gives you with the city name and distance.
    /**
     * Adds a new city to the overall list of cities in the map. If there are no cities in the map currently,
     * a node without a reference to another is made. If there are two nodes, the second node will reference the first
     * one making a circular linked list.
     */
    public void addNode(String cityName)
    {
        Node newNode = new Node(cityName);
        Node top = current;

        //Adds to the front if there are no current nodes.
        if(current == null)
        {
            current = newNode;
        }else{
            if(hasNext())
            {
                //finds the end of the list
                while(current.next != top)
                {
                    next();
                }//end while
                newNode.next = top;
                current.next = newNode;
            }else{
                current.next = newNode;
                newNode.next = current;
            }
        }
    }//end addNode

    /**
     * Goes to the next node in the graph.
     */
    public void next()
    {
        current = current.next;
    }

    /**
     * @return true if the next node is not null.
     */
    public boolean hasNext()
    {
        boolean value = false;

        if(current.next != null)
            value = true;

        return value;
    }//end hasNext;

    protected class Node
    {
        protected String name;
        protected boolean visited;
        protected HashMap<String, Integer> connections;
        protected Node next;

        /**
         * Constructs an unvisited Node with the specified name.
         *
         * @param name the name of the city that will be represented in the Node.
         */
        public Node(String name)
        {
            this.name = name;
            visited = false;
        }//end node constructor

        /**
         * @return false if the node has not been visited yet.
         */
        public boolean isVisited()
        {
            return visited;
        }//end isVisited

        /**
         * This adds a connection to the current Node's HashMap containing another city's name and the distance
         * from the current city to the next city.
         *
         * @param cityName the city that it is being connected to.
         * @param distance the distance to the other city.
         */
        public void addConnection(String cityName, Integer distance) {

            //needs to check that the hash map doesn't already contain that city's name before adding it in.
            connections.put(cityName, distance);
        }//end addConnections

        /**
         * Prints out the adjacent cities and their distance to the node
         */
        public void printConnections()
        {
            System.out.println(connections.toString());
        }//end printConnections
    }//end Node
}//end Graph
