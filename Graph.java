import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Alan on 4/14/2016.
 *
 * The graph is made with the concept of a circularly linked list in mind. There are nodes that are the cities that
 * contain the city name, whether it has been visited, a list of all the connections it has in the graph, and a
 * reference to the next node. This allows the traversal of the upper level of the graph making selection of any
 * particular node easier. Without a beginning or end to the list, the end point is determined by making a temp
 * Node called top and making sure that the next node is not the one that was set to top.
 */
public class Graph implements Iterable<Node>
{
    public Node current;
    public int counter;

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
    public void addNode(String cityName, String connection, Integer distance)
    {
        Node newNode = new Node(cityName);
        Node otherNode =  new Node(connection);
        newNode.addConnection(connection,distance);
        otherNode.addConnection(cityName,distance);
        Node top = current;
        //Adds to the front if there are no current nodes.
        if(current == null)
        {

            //makes the new node
            current = newNode;
            counter++;

            //sets its next reference to the other city making the bidirectional edge
            current.next = otherNode;
            otherNode.next = current;
            counter++;
        }else{
            //finds the end of the list and adds the new node if its not there.
            if(!contains(newNode.name))
            {
                for(int i = 0; i< counter-1;i++)
                {
                    next();
                }//end for
                current.next = newNode;
                newNode.next = top;
                counter++;
            }else{
                find(cityName);
                current.addConnection(connection,distance);
            }

            //adds the reverse node if it doesn't exist.
            if(!contains(otherNode.name))
            {
                current = top;
                for(int i = 0; i< counter-1;i++)
                {
                    next();
                }//end for
                current.next = otherNode;
                otherNode.next = top;
                counter++;
            }else{
                find(connection);
                current.addConnection(cityName,distance);
            }
        }
    }//end addNode

    public boolean contains(String city)
    {
        boolean found = false;
        for(int i = 0; i< counter; i++){
            if(current.name.equalsIgnoreCase(city))
            {
                found = true;
            }
            next();
        }

        return found;
    }

    /**
     * Goes to the next node in the graph.
     * @return 
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

    public boolean find(String searchItem)
    {
        boolean found = false;
        int count = 0;
        while(!found && count <counter)
        {
            next();
            if(current.name.equalsIgnoreCase(searchItem))
            {
                found = true;
                return true;
            }
        }
        return false;
    }
    
    public Node get(String searchItem)
    {
    	boolean found = false;
        int count = 0;
        while(!found && count <counter)
        {
            next();
            if(current.name.equalsIgnoreCase(searchItem))
            {
            found =true;
            return current;
            }
        }
        return null;
    }
    
    public Node getCurrent()
    {
    	return current;
    }
    
    public int size()
    {
    	return counter;
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
			if(current.next != null)
			{
				return true;
			}
			return false;
		}
		
		public Node next()
		{
			//String nodeName = current.next.name;
			if(hasNext() && tracker < counter-1)
			{
				tracker++;
				current = current.next;
				return current;
			}
			throw new NoSuchElementException();
			
		}

		public void remove() 
		{
			// TODO Auto-generated method stub
			
		}
		
		
		
		
	}
}//end Graph﻿