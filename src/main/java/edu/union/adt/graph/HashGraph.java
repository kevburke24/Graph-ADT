package edu.union.adt.graph;
import java.util.*;
import java.io.*;

/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass
 * @version 1
 */
public class HashGraph<V> implements Graph<V>
{
    private HashMap <V, ArrayList<V>> graph;
    /**
     * Create an empty graph.
     */
    public HashGraph()
    {
        graph = new HashMap <>();
    }

    //Returns the edges connected to a certain vertex
    public ArrayList<V> getEdges(V vertex){
      return (ArrayList<V>) graph.get(vertex);
    }

    //Returns a vertex connected to the passed vertex
    private V getConnectedVertex(V vertex){
      return vertex;
    }

    /**
     * @return the number of vertices in the graph.
     */
     @Override
    public int numVertices()
    {
      return graph.size();
    }

    /**
     * @return the number of edges in the graph.
     */
     @Override
    public int numEdges()
    {
        int count = 0;
        for (ArrayList<V> value : graph.values()){
            count = count + value.size();
        }
        return count;

    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
     @Override
    public int degree(V vertex)
    {
        if (!contains(vertex)){
            throw new RuntimeException("RuntimeException");
        }
        else{
            Iterator graphIterator = graph.entrySet().iterator();
            Map.Entry graphElement = (Map.Entry) graphIterator.next();
            V vert = (V)graphElement.getKey();
            while (graphIterator.hasNext() && vert != vertex){
                graphElement = (Map.Entry) graphIterator.next();
                vert = (V)graphElement.getKey();
            }
            ArrayList<V> edges = graph.get(vert);
            return edges.size();
	}
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
     @Override
    public void addEdge(V from, V to)
    {
        if (contains(from)){

	       ArrayList <V> edges = getEdges(from);

    	   if (!edges.contains(to)){
    		    edges.add(to);
            graph.put(from, edges);
    	   }
	     }

    	if (!contains(from)){
    	   ArrayList <V> newEdge = new ArrayList<>();
    	   newEdge.add(to);
    	   graph.put(from, newEdge);
    	}

    	if (!contains(to)){
    	   ArrayList<V> e = new ArrayList<>();
    	   graph.put(to, e);
    	}


    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    @Override
    public void addVertex(V vertex)
    {
        ArrayList <V> edges = new ArrayList<>();
	      graph.put(vertex, edges);

    }

    /**
     * @return an iterable collection for the set of vertices of
     * the graph.
     */
    public Iterable<V> getVertices()
    {
        ArrayList <V> vertices = new ArrayList<>();
        for (V vertex : graph.keySet()){
		        vertices.add(vertex);
	      }
    	Iterable <V> v = vertices;
    	return v;
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    @Override
    public Iterable<V> adjacentTo(V from)
    {
       Iterator graphIterator = graph.entrySet().iterator();
    	 Map.Entry graphElement = (Map.Entry) graphIterator.next();
    	 V vert = (V)graphElement.getKey();
    	 while (graphIterator.hasNext() && vert != from){
      		graphElement = (Map.Entry) graphIterator.next();
      		vert = (V)graphElement.getKey();
    	 }
    	 ArrayList <V> edges = graph.get(vert);
    	 //Iterable <V> e = edges;
    	 return edges;
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
     @Override
    public boolean contains(V vertex)
    {
        return graph.containsKey(vertex);
    }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
     @Override
    public boolean hasEdge(V from, V to)
    {
        if (!contains(from)){
            return false;
        }
        else{
            Iterator graphIterator = graph.entrySet().iterator();
            Map.Entry graphElement = (Map.Entry) graphIterator.next();
            V vert = (V) graphElement.getKey();
            while (graphIterator.hasNext() && vert != from){
                graphElement = (Map.Entry) graphIterator.next();
                vert = (V)graphElement.getKey();
            }
	    ArrayList <V> edges = graph.get(vert);
	    return edges.contains(to);
	    }
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
     @Override
    public String toString()
    {
        String builder = new String();
    	for (V vertex : graph.keySet()){
    		String strVertex = vertex.toString();
    		builder = builder + strVertex + ": ";
    		ArrayList <V> edges = graph.get(vertex);
    		int degree = edges.size();
    		int i = 0;
    		if (degree > 0){
    			while (i < degree -1){
    				String strEdge = edges.get(i).toString();
    				builder = builder + strEdge + ", ";
    				i++;
    			}
    		String strEdge = edges.get(i).toString();
    		builder = builder + strEdge + "\n";

    		}
    		else{
    			strVertex = vertex.toString();
    			builder = builder + strVertex + ": \n";
    		}

    	}
        	return builder;
    }

  /**
  Overrides the Object equals method to determine equivalence between two
  Graph objects
  */
  public boolean equals(Object o){
        if (o == null){
	        return false;
        }
	else if (o == this){
	   return true;
	}
	else if (o.getClass() != this.getClass()){
	   return false;
	}
	HashGraph otherGraph = (HashGraph)o;
	return otherGraph.graph.equals(this.graph);

  }

    /**
     * Removes a vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove){
      if(contains(toRemove)){
        graph.remove(toRemove);
      }
      for (V vertex : graph.keySet()){
        if (graph.get(vertex).contains(toRemove)){
          ArrayList<V> edges = graph.get(vertex);
          edges.remove(toRemove);
          graph.put(vertex, edges);
        }
      }
    }

    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to){
    }

    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff A and B are in the
     * graph and there exists a sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * It therefore follows that, if vertex A is in the graph, there
     * is a path from A to A.
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    public boolean hasPath(V from, V to){

    }

    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol>
     * <li>If from = to, the shortest path has length 0
     * <li>Otherwise, the shortest path length is the length of the shortest
     * possible path connecting from to to.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    public int pathLength(V from, V to) {
    
    }

    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices are be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable includeS the source and
     * destination vertices. If there is no path from 'from' to 'to'
     * in the graph (e.g. if the vertices are not in the graph),
     * returns an empty Iterable collection of vertices.
     */
    public Iterable<V> getPath(V from, V to){
      ArrayList<V> arr = new ArrayList<>();
      return arr;
    }
}
