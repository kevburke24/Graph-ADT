package edu.union.adt.graph.tests.burkek;

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
public class SimpleGraphTests
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

    //removeVertex when there is an edge connecting to and from the
    //vertex we want to remove
    @Test
    public void removeVertex_edgeToandFrom(){
      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.removeVertex("B");

      assertFalse("Vertex is no longer in the graph", g.contains("B"));

      assertEquals("Removing a vertex caused the number vertices to decrease by 1", 2, g.numVertices());

      assertEquals("Removing this vertex removes two edges",0, g.numEdges());

    }

    //removeEdge when the two nodes aren't connected
    @Test
    public void removeEdge_bothNodesStillInGraph()
    {

      g.addEdge("A", "B");
      g.removeEdge("A", "B");

      assertTrue("Making sure A is still in the graph", g.contains("A"));

      assertTrue("Making sure B is still in the graph", g.contains("B"));

      assertEquals("Making sure the number of vertices did not change", 2, g.numVertices());

    }

    //removeEdge when the To vertex doesn't exist
    @Test
    public void removeEdge_noToVertex()
    {

      g.addEdge("A", "B");
      g.addEdge("B", "C");
      g.removeEdge("A", "E");

      assertEquals("Making sure the number of vertices did not change", 2, g.numVertices());

      assertEquals("Making sure the number of edges did not change", 1, g.numEdges());

      assertEquals("Making sure the degree of From node doesn't change", 1, g.degree("A"));
    }

    //hasPath when a graph has one vertex
    @Test
    public void hasPath_oneVertex()
    {

      g.addVertex("A");
      assertTrue("hasPath when graph has one vertex", g.hasPath("A", "A"));

    }

    //hasPath when the graph has one vertex
    @Test
    public void hasPath_noVertices(){

      assertFalse("hasPath when the graph has no vertices", g.hasPath("A", "B"));

    }

    //pathLength when there are no vertices
    @Test
    public void pathLength_noVertices(){

      assertEquals("pathLength when there are no vertices", Integer.MAX_VALUE, g.pathLength("A", "B"));

    }

    //pathLength when the From vertex does not exist
    @Test
    public void pathLength_noFromVertex(){
      g.addEdge("A", "B");
      g.addEdge("B","C");
      g.addVertex("H");
      assertEquals("pathLength when the From vertex does not exist", Integer.MAX_VALUE, g.pathLength("M", "H"));

    }

    // getPath when there are no vertices
    @Test
    public void getPath_noVertices(){
      Iterable<String> empty = new Iterable<>();
      assertEquals("getPath when there are no vertices", empty, g.getPath("A", "B"));

    }

    //getPath when the To vertex does not exist
    @Test
    public void getPath_noTovertex(){
      g.addEdge("A", "B");
      g.addVertex("C");
      Iterable<String> empty = new Iterable<>();
      assertEquals("getPath when the To vertex does not exist", empty, g.getPath("A", "D"));

    }
}
