package edu.union.adt.graph.tests.burkek;
import java.util.*;
import java.io.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.adt.graph.Graph;
import edu.union.adt.graph.GraphFactory;

@RunWith(JUnit4.class)
public class kevinsTests
{
    private Graph<String> g;

    @Before
    public void setUp()
    {
        g = GraphFactory.<String>createGraph();
    }

    @After
    public void tearDown()
    {
        g = null;
    }

    @Test
    public void addEdge_noRepeats(){
      g.addEdge("A", "B");
      g.addEdge("A", "B");

      assertEquals("Degree should not increase when adding duplicate edge", 1, g.degree("A"));
    }

    //removeVertex when there is an edge connecting to and from the
    //vertex we want to remove
    @Test
    public void removeVertex_edgeToandFrom(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.removeVertex("B");

      assertFalse("Vertex is no longer in the graph", g.contains("B"));

      assertEquals("Removing a vertex caused the number vertices to decrease by 1", 2, g.numVertices());

      assertFalse("Graph should no longer contain toRemove as an edge", g.hasEdge("A", "B"));

      //assertEquals("Removing this vertex removes two edges",0, g.numEdges());
    }

    //removeEdge when the To vertex doesn't exist
    @Test
    public void removeEdge_noToVertex()
    {

      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.removeEdge("A", "E");

      assertEquals("Making sure the number of vertices did not change", 3, g.numVertices());

      assertEquals("Making sure the number of edges did not change", 2, g.numEdges());

      assertEquals("Making sure the degree of From node doesn't change", 1, g.degree("A"));

    }

    @Test
    public void hasPath_oneVertex()
    {

      g.addVertex("A");
      assertTrue("hasPath when graph has one vertex", g.hasPath("A", "A"));

    }

    @Test
    public void hasPath_noPathBetweenVertices(){
      g.addEdge("A", "B");
      g.addEdge("B","C");
      g.addVertex("H");

      assertFalse("Checking hasPath when both vertices exist but no path between them", g.hasPath("A", "H"));

    }

    @Test
    public void hasPath_repeatNode(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.addEdge("C", "D");
      g.addEdge("C", "A");
      g.addEdge("D", "G");
      g.addEdge("G", "H");

      assertTrue("Checking hasPath for when a later node points to start node", g.hasPath("A", "H"));

    }

    @Test
    public void pathLength_twoShortestPaths(){
      g.addEdge("A", "B");
      g.addEdge("A", "E");
      g.addEdge("B", "D");
      g.addEdge("E", "D");

      assertEquals("Two cases where shortest path is 2", 2, g.pathLength("A", "D"));

    }


    @Test
    public void pathLength_repeatNode(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.addEdge("C", "D");
      g.addEdge("C", "A");
      g.addEdge("D", "G");
      g.addEdge("G", "H");

      assertEquals("Path where a later node points to start node", 5, g.pathLength("A", "H"));

    }

    //getPath when the To vertex does not exist
    @Test
    public void getPath_noTovertex(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.addEdge("C", "D");
      LinkedList<String> l = new LinkedList<>();
      assertEquals("Checking that the method returns empty iterable when the graph's To vertex does not exist",
      l, g.getPath("A", "F"));
      
    }

    @Test
    public void getPath_repeatNode(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.addEdge("C", "D");
      g.addEdge("C", "A");
      g.addEdge("D", "G");
      g.addEdge("G", "H");

      LinkedList<String> l = new LinkedList<>();
      l.add("A");
      l.add("B");
      l.add("C");
      l.add("D");
      l.add("G");
      l.add("H");

      assertEquals("Path where a later node points to start node", l, g.getPath("A", "H"));

    }


}
