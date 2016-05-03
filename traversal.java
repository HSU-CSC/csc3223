import java.util.ArrayList;

public class traversal
{

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
