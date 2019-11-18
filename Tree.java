import java.util.*;

//TreeNode class that stores left and right TreeNode
class TreeNode
{
	String data;
	TreeNode left, right;

	public TreeNode(String s) 
	{
		data = s;
		left = right = null;
	}
}

public class Tree implements Trees
{
	TreeNode root;

	//calls recursive insert function
	public void insert(String word) 
	{
		root = insert(root, word);
	}

	//inserts word in Tree
	public TreeNode insert(TreeNode t, String word) 
	{
		if (t == null) 
		{
			return new TreeNode(word);
		}

		if (word.compareTo(t.data) > 0) 
		{
			t.right = insert(t.right, word);
		}

		else 
		{
			t.left = insert(t.left, word);
		}
		return t;
	}

	//calls recursive search function
	public boolean search(String word) 
	{
		return search(root, word);
	}

	//searches word in Tree
	public boolean search(TreeNode t, String word) 
	{
		if (t == null)
		{
			return false;
		}

		if (t.data.equals(word)) 
		{
			return true;
		}

		if (word.compareTo(t.data) < 0)
		{
			return search(t.left, word);
		}

		else 
		{
			return search(t.right, word);
		}
	}

	//creates arralist to store Nodes and calls recursive balance function
	public TreeNode balance(TreeNode t) 
	{
		ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
		storeNodes(t, nodes);
		return balance(nodes, 0, nodes.size()-1);
	}

	//balances Tree by balancing start to mid-1, then mid-1 to end and returns the arraylist
	public TreeNode balance(ArrayList<TreeNode> n, int start, int end) 
	{
		if (start > end) 
		{
			return null;
		}
		int mid = (start+end)/2; 
		TreeNode node = n.get(mid);
		node.left = balance(n, start, mid-1);
		node.right = balance(n, mid+1, end);
		return node;
	}

	//function to store the TreeNodes
	public void storeNodes(TreeNode t, ArrayList<TreeNode> n) 
	{
		if (t == null) 
		{
			return;
		}
		storeNodes(t.left, n);
		n.add(t);
		storeNodes(t.right, n);
	}

}