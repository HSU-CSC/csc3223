import java.util.ArrayList;

public class traversal
{

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
     * @param nnode
     * @param visited
     */

public void traversal(Graph g, Node n, ArrayList<Node> visited)
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
}
