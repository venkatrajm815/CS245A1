//Trie.java
//This class inserts and searches through the Trie tree


import java.util.*;

//TNode class for each letter of alphabet
class TNode
{
	TNode[] children; 
	boolean isEnd = false ;

	TNode()
	{
		this.children = new TNode[27];
	}
}

public class Trie implements Trees
{
	private TNode root;

	public Trie()
	{
		root = new TNode();
	}

	//insert word into Trie tree
	public void insert(String word) 
	{
		TNode n = root;
		for (int i = 0 ; i < word.length() ; i++) 
		{
			//to check if it is a comma
			if (word.charAt(i) == '\'') 
			{
				//creates a new TNode if not present
				if (n.children[26] == null) 
				{
					n.children[26] = new TNode();
				}
				n = n.children[26];
			}
			//to check if it is a letter
			else 
			{
				//if it is not there, create a TNode
				if (n.children[(int)(word.charAt(i) - 'a')] == null) 
				{
					n.children[(int)(word.charAt(i) - 'a')] = new TNode();
				}
				n = n.children[(int)(word.charAt(i) - 'a')];
			}
		}
		n.isEnd = true;
		// search until the last word in word and make isEnd to true
	}

	//search if word is there in Trie tree
	public boolean search(String word) 
	{
		TNode n = root;
		for (int i = 0 ; i < word.length() ; i++) 
		{
			//if char is a comma
			if (word.charAt(i) == '\'') 
			{
				if (n.children[26] == null) 
				{
				
					return false;
				}
				n = n.children[26];
			}
			else 
			{
				if (n.children[(int)(word.charAt(i) - 'a')] == null) {
				// there's no the specific character
					return false;
				}
				n = n.children[(int)(word.charAt(i) - 'a')];
			}
		}
		return n.isEnd;
	}

}