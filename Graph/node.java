	public class Node
	{
		public String connection;
		private String parent;
		public int distance;
		private boolean visited;
		public Node next;
		
		public Node(String nName,String nconnection, int nDistance)
		{
			connection = nconnection;
			parent = nName;
			distance = nDistance;
			visited = false;
			next = null;
		}
		public Node()
		{
			connection = "";
			parent = "";
			distance = 0;
			visited = false;
			next = null;
		}
		public void visit()
		{
			visited = true;
		}
		
		public boolean isVisited()
		{
			return visited;
		}
		
		public boolean hasNext()
		{
			if(next== null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		
		public String getParent()
		{
			return parent;
		}
		
		public void setParent(String nP)
		{
			parent = nP;
		}

		
		public String toString()
		{
			if(next != null)
			{
				return parent +" "+connection+" "+distance+" ";
			}
			else
			{
				return parent +" "+ connection +" "+ distance;
			}
		}
	}
