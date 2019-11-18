README File/ Analysis Report
CS 245 Assignment 1: Spell Checker

Name: Venkatraj Mohan
Date/Time Completed: 10:45pm Novembe 17th, 2019

List of Files and their purposes:

	CS245A1.java
		-Main Class
		-This file takes in Trie.java and Tree.java to store the lines from dictionary into them
		-Takes in a1properties.txt to know which tree to use
		-Inserts the words into Trie or Tree(Search tree)
		-Takes in one input file and one output file as command arguments to spell check and give suggestions
		-Creates a HashMap to store the word and the levenshtein distance and gives 3 suggestions in the output file

	Trees.java
		-Parent class of Tree.java and Trie.java that is implemented by both of them

	Trie.java
		-This file inserts and searches the Trie using Nodes

	Tree.java
		-This files inserts and searches using Binary search tree implementation with Nodes

	a1properties.txt
		-Text file that stores what tree to use
		Ex. storage=tree   //uses binary search tree
			storage=trie.  //uses trie

	input.txt
		-input file that a sample line of words

	output.txt
		-initially blank text file that stores suggestions of the words in input.txt 

	input file with line of words is needed
	output file will be created if not present in same directory already
	Both of these are to be enter are command arguments when calling main class


Analysis Report:

	To determine which strategy is the best for inserting words, I used System.currentTimeMillis(); to calculate the difference in time between before and after inserting words.
	On average, the trie takes around 280ms.
	On average, the search tree takes around 260ms.
	However, this is average running time case.
	Keep in mind that this is to insert the words.
	As we are using a balanced search tree, it is faster to insert into it compared to the trie.
	Lets, take a look at the worst case running time.

	For the trie, there are two functions being called: trie.insert() and trie.search().
	The worst case running time for these functions is O(l) where l is the length of the longest word in dictionary.
	The function insert() runs through each line of the dictionary.
	The function search() runs through the size of input array.
	Therefore, the total running time is n*O(l).

	For the search tree, there are three functions being called: tree.insert(),tree.search(),tree.balance().
	The worst case running time of insert() is O(n) where n is the number of words in dictionary
	The worst case run time for search() is O(logn) as the number of the elements it checks halves every recursive call.
	The worst case for balance() is also O(n) as it has to go through each word to balance it. 
	Therefore, the total running time is O(n).

	So we can conclude that based on worst case running time, trie takes less time than the search tree.

Extra Credit:
	-I did part 1 where it reads directly from GitHub using the URL.
	-I did part 2 using the levenshtein distance to get good suggestions and spell checking.
	-I didnt do part 3 which is active spell checking.














