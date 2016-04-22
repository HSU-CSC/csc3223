import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Graph implements Iterable<Node>
{
	
	ArrayList<Node> graph;
	ArrayList<String> parents;
	
	public Graph()
	{
		graph = new ArrayList<Node>();
	    parents = new ArrayList<String>();
	}
	
	
	public void add(String name, String connect, int distance)
	{
		
		Node newNode = new Node(name.toUpperCase(),connect.toUpperCase(),distance);
		//System.out.println(graph + " 1 ");
		//System.out.println(parents);
		
		if(graph.size() == 0)
		{
			graph.add(newNode);
			parents.add(newNode.getParent().toUpperCase());
			//System.out.println(graph+" 2 ");
			
		}
		
		else if(parents.contains(name.toUpperCase()))
			{
				Node start = graph.get(parents.indexOf(name));
				//System.out.println(" 3 "+ start);
				
				if(start.next == null)
				{
					start.next = newNode;
					start.next.setParent(name);
					//System.out.println(" 4 ");
				}
				
				else
				{
					//System.out.println(" 5 ");
					while(start.next != null)
					{
						start = start.next;
						//System.out.println(" 6 ");
					}	
					
					start.next = new Node(name.toUpperCase(),connect.toUpperCase(),distance);
				}
				//System.out.println(" 7 ");
				
			}
			
			else 
			{
				graph.add(newNode);
				parents.add(name);
				//System.out.println(" 8 ");
			}
		//System.out.println(graph);
			
		}
	
	public int size()
	{
		return graph.size();
	}
	
	public Node get(int i)
	{
		return graph.get(i);
	}
		
	
	public Iterator<Node> iterator()
	{
		return new GraphIterator();
	}
	
	public class GraphIterator implements Iterator<Node>
	{
		private int tracker;
		public GraphIterator()
		{
			tracker = 0;
		}
		
		public boolean hasNext()
		{
			if(graph.size() > tracker)
			{
				return true;
			}
			return false;
		}
		
		public Node next()
		{
			if(this.hasNext())
			{
				int current = tracker;
				tracker++;
				return graph.get(current);
			}
			throw new NoSuchElementException();
		}

		public void remove() 
		{
			// TODO Auto-generated method stub
			
		}
	}
	
}
