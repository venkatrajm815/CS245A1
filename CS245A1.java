import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.net.*;

public class CS245A1
{

	static Trie trie;
	static Tree tree;
	static String inputFile, outputFile;
	static ArrayList<String> dictionary, input;
	static BufferedWriter output;
	static Properties property = new Properties();
	static boolean type = true;

	public CS245A1() 
	{
		trie = new Trie();
		tree = new Tree();
		dictionary = new ArrayList<String>();
		input = new ArrayList<String>();
	}
	
	public static void main(String[] args) 
	{
		//finds a1properties.txt file to see what tree to use
		//if not found,sets property to trie, u can change it by making it tree in a1properties.txt
		try
		{
			String filePath = new File("").getAbsolutePath();
			filePath = filePath.concat("/a1properties.txt");
			InputStream file = new FileInputStream(filePath);
			property.load(file);
		} 
		catch (IOException e)
		{
			property.setProperty("storage", "trie");
		}
		//to tell user what tree is being used
		System.out.println("The type of the tree you are using: " + property.getProperty("storage"));

		//sets type to true is trie is being used, false if something else
		if(property.getProperty("storage").equals("trie"))
		{
			type = true;
		} 
		else 
		{
			type = false;
		}

		//Tells user to enter two file names for input and output
		if (args.length != 2) {
			System.out.println("Please enter two file names for the command arguments.");
			return;
		}
		

		CS245A1 assignment1 = new CS245A1();
		//stores args[0] as inputFile
		assignment1.inputFile = args[0];
		//stores args[1] as outputFile
		assignment1.outputFile = args[1];
		//makes the dictionary for trie or search tree
		assignment1.makeDictionary(type);
		//to read the input file and give suggestions in output file
		assignment1.readInput();

		
	}

	public static void makeDictionary(boolean type) 
	{	
		try 
		{
			//Extra Credit: Read directly from github
			URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0"); //find URL 
			Scanner scan = new Scanner(url.openStream());
			long start = System.currentTimeMillis();

			//adds all words in the file and stores in dictionary array
			while (scan.hasNextLine()) 
			{
				String line = scan.nextLine();
				if (!line.isEmpty()) 
				{
					dictionary.add(line);
				}
			}
			
			//if it is trie tree, insert into trie tree
			if(type == true)
			{
				while (scan.hasNextLine()) 
				{
					String line = scan.nextLine();
					trie.insert(line);
				}
				//this is to calculate the run time of inserting into trie tree
				/*
				long end = System.currentTimeMillis();
				float sec = (end-start);
				System.out.println("The time to insert words: "+sec + "ms");
				*/
			}

			//if it is search tree, insert into search tree
			else if(type == false)
			{
				while (scan.hasNextLine()) 
				{
					String line2 = scan.nextLine();
					tree.insert(line2);
				}
				//to make it a balanced tree
				tree.root = tree.balance(tree.root);

				//this is to calculate the run time of inserting into trie tree
				/*
				long end2 = System.currentTimeMillis();
				float sec2 = (end2-start);
				System.out.println("The time to insert words: "+sec2 + "ms");
				*/
			}		
		
		}

		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}


	public static void readInput() 
	{
		try 
		{
			Scanner scan = new Scanner(new File(inputFile));
			//stores all words in inputFile in input array
			while (scan.hasNextLine()) 
			{
				String line = scan.nextLine();
				if (!line.isEmpty()) {
					input.add(line);
				}
				
			}
		}
				
		catch (FileNotFoundException e) 
		{
			System.out.println("Input file not found.");
			return;
		}

		//if it is trie tree
		if(type == true)
		{
			trieSuggest();
		}
		//if it is a search tree
		else
		{
			treeSuggest();
		}

	}

	// searches the inputs using trie and gives suggestions
	public static void trieSuggest() 
	{	
		try 
		{
			File secondFile = new File(outputFile);
			output = new BufferedWriter(new FileWriter(secondFile));

			for (int i = 0 ; i < input.size() ; i++) 
			{
				HashMap<String, Integer> levenshteinDistance = new HashMap<String, Integer>();
				// levenshteinDistance is a map from Strings in dictionary and their levenshtein distance with the input word
				String inWord = input.get(i);
				if (trie.search(inWord.toLowerCase())) 
				{
					output.write(inWord + "\n");
					// if the input word is in the dictionay then write it into the output file directly
				}
				else 
				{
					// this loop stores all the strings in dictionary and their levenshtein distance with the words from input array
					for (int j = 0 ; j < dictionary.size() ; j++) 
					{
						String word = dictionary.get(j);
						//stores the word and distance in the HashMap 
						levenshteinDistance.put(word, levenshtein(inWord, word, inWord.length(), word.length()));
						
					}
					//this sorts it by the value of the levenshtein distance
					levenshteinDistance = levenshteinDistance.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
					
					Iterator<String> iterator = levenshteinDistance.keySet().iterator();

					//this is to give 3 suggestions of the input word
					for (int j=0 ; j < 3 ; j++) 
					{
						output.write(iterator.next() + " ");
					}
					// writes the first three keys in levenshteinDistance (with the three least levenshtein distance)
					output.write("\n");
				}
			}
			output.close();
		}
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
	}

	// searches the inputs using tree and gives suggestions
	public static void treeSuggest() 
	{
		try 
		{
			output = new BufferedWriter(new FileWriter(outputFile));
			for (int i = 0 ; i < input.size() ; i++) 
			{
				HashMap<String, Integer> levenshteinDistance = new HashMap<String, Integer>();
				// levenshteinDistance is a map from Strings in dictionary and their levenshtein distance with the input word
				String inWord = input.get(i);
				if (tree.search(inWord.toLowerCase())) 
				{
					output.write(inWord + "\n");
					// if the input word is in the dictionay then write it into the output file directly
				}
				else 
				{
					// this loop stores all the strings in dictionary and their levenshtein distance with the words from input array
					for (int j = 0 ; j < dictionary.size() ; j++) 
					{
						String word = dictionary.get(j);
						//stores the word and distance in the HashMap 
						levenshteinDistance.put(word, levenshtein(inWord, word, inWord.length(), word.length()));
					}
					//this sorts it by the value of the levenshtein distance
					levenshteinDistance = levenshteinDistance.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
					
					Iterator<String> iterator = levenshteinDistance.keySet().iterator();
					
					//this is to give 3 suggestions of the input word
					for (int j = 0 ; j < 3 ; j++) 
					{
						output.write(iterator.next() + " ");
					}
					// writes the first three keys in levenshteinDistance (with the three least levenshtein distance)
					output.write("\n");
				}
			}
			output.close();
		}
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}	
	}

	

	public static int levenshtein(String word1, String word2, int leven1, int leven2) 
	{
		int[][] length = new int[leven1+1][leven2+1];
		for(int i = 0 ; i <= leven1 ; i++)
		{
			for(int j = 0 ; j <= leven2 ; j++)
			{
				if(i == 0)
				{
					length[i][j] = j; 
				} 
				else if (j == 0)
				{
					length[i][j] = i;
				} 
				else 
				{
					length[i][j] = min(length[i - 1][j - 1] 
                 + substituteCost(word1.charAt(i - 1), word2.charAt(j - 1)), 
                  length[i - 1][j] + 1, 
                  length[i][j - 1] + 1);
				}
			}
		}
		return length[leven1][leven2];
	}

	//returns the substitute cost of a and b
	public static int substituteCost(char a, char b)
	{
		return a==b ? 0:1;
	}

	//returns the minimum 
	public static int min(int ... lengths)
	{
		return Arrays.stream(lengths).min().orElse(Integer.MAX_VALUE);
	}

	

}


