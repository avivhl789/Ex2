# **Pokemon Game**

A pokemon game based on directed weighted graphs that makes the trainers catch the pokemon using the shortest possbile path.

## **Description**
------
You start the game by picking one of  24 available levels from which to create directed weighted graphs.

Once picked, the algorithm should construct the info send by the server in Json form into a graph made of nodes and weighted directed edges.

The goal of this project is to try and catch the most pokemon with in a given amount of time, while not overloading the server with the `move()` function which moves the trainers around the graph.

The program uses the Breadth-First-Search and Dijkstra algorithms.

### **Terminology**
------
Node - a single point on the graph on which the trainers spawn.

Edge - a line which connects two between two distinct nodes and on which the pokemons randomly spawn

Path - a sequence of nodes and edges starting at a source node and ending at the chosen destination.

Weight - the cost it takes to traverse any given edge.

Dijkstra - is an algorithm that finds the shortest path between a starting node and an end node, taking in to account the weights between the nodes. 

Breadth-First-Search (BFS) -  

## **Installing**
------
All you need to do is clone this repository and you'll have all the needed files that implement Dijkstra's algorithm including a JAR file that will run for you the server.

Here are the command lines you need to run in order to clone and use this repository:
```
git clone https://github.com/avivhl789/Ex2.git
cd Ex2
```
In order to run the code type in:
```
java -jar Ex2.jar
```

You're all done!

Now just pick a game level between 0 - 23 and see them go!



### **Links:**
------
[Directed Weighted Graphs](https://en.wikipedia.org/wiki/Directed_graph)

[Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)

[Breadth-First-Search (ie. BFS)](https://en.wikipedia.org/wiki/Breadth-first_search)
