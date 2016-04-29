import java.util.HashMap;
import java.util.Set;

public class Node
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
            connections = new HashMap<String,Integer>();
            visited = false;
        }//end node constructor

        /**
         * @return false if the node has not been visited yet.
         */
        public boolean isVisited()
        {
            return visited;
        }//end isVisited
        
        public void visit()
        {
        	visited = true;
        }

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
       
       public boolean hasConnections()
       {
    	   return !connections.isEmpty();
       }
       
       public Set<String> getConnections()
       {
    	   return connections.keySet(); 
       }
       
       public void printConnections()
       {
           System.out.println(connections.toString());
       }//end printConnections
       public String toString()
       {
    	   return name;
       }
    }//end Node