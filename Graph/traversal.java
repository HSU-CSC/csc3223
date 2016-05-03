import java.util.ArrayList;

public class traversal
{

public void traversal(Graph g, Node n, ArrayList<Node> visited)
    {
    	//System.out.println(n+ " 1 ");
		if(!n.isVisited())//checks if the node has been visited
		{
			n.visit();//visits node
			visited.add(n);
			if(n.hasConnections())//gets connections
			{
				
				for(String c : n.getConnections())//gets a set of connections iterates through them
				{
					if(g.contains(c))//makes sure the graph contains the element
					{
						if(!g.get(c).isVisited())//makes sure its not visited
						{
							//System.out.println(g.get(c));
							traversal(g,g.get(c),visited);   //if not visited then visit
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
