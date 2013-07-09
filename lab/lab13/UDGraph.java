/* UDGraph.java */

import java.io.*;
import java.util.*;
import list.*;

/**
 *  The UDGraph class represents an unweighted directed graph.
 *  Implemented with an adjacency matrix.
 */

public class UDGraph
{
    /**
     *  adjMatrix references the adjacency matrix of the graph.
     *  vertices is the number of vertices in the graph.
     *  edges is the number of edges in the graph.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    private boolean[][] adjMatrix;
    private int vertices;
    private int edges;                   

    /**
     *  Constructs a graph with n vertices and no edges.
     */
    public UDGraph(int n) {
        vertices = n;
        edges = 0;
        adjMatrix = new boolean[n][n];
        for (int i = 0; i < vertices; i++ ) {
            for (int j = 0; j < vertices; j++ ) {
                adjMatrix[i][j] = false;
            }
        }
    }

    /**
     *  Returns the number of vertices.
     *  @return this graph's vertex count.
     */
    public int getNumVertices() {
        return vertices;
    }

    /**
     *  Returns the number of edges.
     *  @return this graph's edge count.
     */
    public int getNumEdges() {
        return edges;
    }

    /**
     *  Returns true if v is a valid vertex number; false otherwise.
     *  @param v the vertex.
     *  @return boolean indicating existence of vertex number v.
     */
    public boolean validVertex(int v) {
        return (v >= 0) && (v < vertices);
    }

    /**
     *  Returns true if edge (origin, destination) exists; false otherwise.
     *  @param origin the origin vertex.
     *  @param destination the destination vertex.
     *  @return boolean indicating the presence of edge (origin, destination).
     */
    public boolean hasEdge(int origin, int destination) {
        if (validVertex(origin) && validVertex(destination)) {
            return adjMatrix[origin][destination];
        } else {
            return false;
        }
    }

    /**
     *  Creates the edge (origin, destination).  If the edge did not already
     *    exists, increments the edge count.
     *  @param origin the origin vertex.
     *  @param edstination the destination vertex.
     */
    public void addEdge(int origin, int destination) {
        if (validVertex(origin) && validVertex(destination)) {
            if (!adjMatrix[origin][destination]) {
                adjMatrix[origin][destination] = true;
                edges++;
            }
        }    
    }

    /**
     *  Deletes the edge (origin, destination).  If the edge existed, decrements
     *    the edge count.
     *  @param origin the origin vertex.
     *  @param destination the destination vertex.
     */
    public void removeEdge(int origin, int destination) {
        if (validVertex(origin) && validVertex(destination)) {
            if (adjMatrix[origin][destination]) {
                adjMatrix[origin][destination] = false;
                edges--;
            }
        }        
    }

    /**
     *  Returns a new UDGraph with the same vertices as "this" UDGraph.
     *    The new graph has an edge (v, w) if and only if there is a path of
     *    length 2 from v to w in "this" graph.
     *    *** DO NOT CHANGE "this" GRAPH!!! ***
     *  @return the new UDGraph.
     */
    public UDGraph length2Paths() {
        UDGraph newGraph = new UDGraph(vertices);
        LinkedQueue queue = new LinkedQueue();
        int level =2;
        try {
            for(int i=0; i<vertices;i++) {
                // level 1 vertices
                for(int j=0;j<vertices;j++) {
                    if(adjMatrix[i][j]==true) {
                        queue.enqueue(j);
                    }
                }
                // level 2 vertices
                int tmp, qsize;
                for(int l=2; l<=level; l++) {
                    qsize=queue.size();
                    for(int k=0; k< qsize;k++) {
                        tmp = (Integer)queue.dequeue();
                        for(int m=0; m<vertices; m++) {
                            if(adjMatrix[tmp][m]==true) {
                                queue.enqueue(m);
                            }
                        }
                    }
                }
                // add edge to new graph
                while(!queue.isEmpty()) {
                    tmp = (Integer)queue.dequeue();
                    newGraph.addEdge(i,tmp);
                }
            }
        } catch (QueueEmptyException e) {
            System.err.println(e);
        }
        return newGraph;
    }

    /**
     *  Returns a new UDGraph with the same vertices as "this" UDGraph.
     *    The new graph has an edge (v, w) if and only if there is a path of
     *    length "length" from v to w in "this" graph.
     *  @param length the length of paths used to construct the new graph.
     *  @return the new UDGraph.
     */
    public UDGraph paths(int length) {
        if(length<1) {
            System.err.println("Input invalid");
        }
        UDGraph newGraph = new UDGraph(vertices);
        LinkedQueue queue = new LinkedQueue();
        int level =length;
        try {
            for(int i=0; i<vertices;i++) {
                // level 1 vertices
                for(int j=0;j<vertices;j++) {
                    if(adjMatrix[i][j]==true) {
                        queue.enqueue(j);
                    }
                }
                // level 2 vertices
                int tmp, qsize;
                for(int l=2; l<=level; l++) {
                    qsize=queue.size();
                    for(int k=0; k< qsize;k++) {
                        tmp = (Integer)queue.dequeue();
                        for(int m=0; m<vertices; m++) {
                            if(adjMatrix[tmp][m]==true) {
                                queue.enqueue(m);
                            }
                        }
                    }
                }
                // add edge to new graph
                while(!queue.isEmpty()) {
                    tmp = (Integer)queue.dequeue();
                    newGraph.addEdge(i,tmp);
                }
            }
        } catch (QueueEmptyException e) {
            System.err.println(e);
        }
        return newGraph;
    }

    /**
     *  Returns a String representing the adjacency matrix, including the number
     *    of vertices and edges.
     *  @return a String representing the adjacency matrix.
     */
    public String toString() {
        int i, j;
        String s = vertices + " vertices and " + edges + " edges\n";
        for (i = 0; i < vertices; i++) {
            for (j = 0; j < vertices - 1; j++) {
                s = s + (adjMatrix[i][j] ? "t" : ".")  + " ";
            }
            s = s + (adjMatrix[i][j] ? "t" : ".")  + "\n";
        }
        return s;
    }


    public static void main(String[] args) {

        System.out.println("\n *** Lab 12:  " + 
                "Square the unweighted directed graph! *** \n");

        // Create an 11-vertex graph.
        System.out.println("Creating a graph with 11 vertices");
        UDGraph graph = new UDGraph(11);
        graph.addEdge(0, 8);
        graph.addEdge(1, 0);
        graph.addEdge(1, 3);
        graph.addEdge(2, 0);
        graph.addEdge(3, 2);
        graph.addEdge(3, 5);
        graph.addEdge(4, 2);
        graph.addEdge(4, 5);
        graph.addEdge(5, 7);
        graph.addEdge(5, 9);
        graph.addEdge(6, 4);
        graph.addEdge(6, 7);
        graph.addEdge(8, 4);
        graph.addEdge(8, 6);
        graph.addEdge(8, 10);
        graph.addEdge(9, 1);
        graph.addEdge(10, 6);

        boolean goodJob = true;

        String t1String = "11 vertices and 17 edges\n. . . . . . . . t . .\n" +
            "t . . t . . . . . . .\nt . . . . . . . . . .\n. . t . . t . . . . .\n" +
            ". . t . . t . . . . .\n. . . . . . . t . t .\n. . . . t . . t . . .\n" +
            ". . . . . . . . . . .\n. . . . t . t . . . t\n. t . . . . . . . . .\n" +
            ". . . . . . t . . . .\n";
        System.out.println("\nThe original graph is\n" + graph);
        if (!t1String.equals(graph.toString())) {
            System.out.println("Error:  the original graph should be\n" +
                    t1String);
            goodJob = false;
        } else {
            System.out.println("PASSED t1 TEST");
        }

        // Do length-2 paths work?
        String t2String = "11 vertices and 25 edges\n. . . . t . t . . . t\n" +
            ". . t . . t . . t . .\n. . . . . . . . t . .\nt . . . . . . t . t .\n" +
            "t . . . . . . t . t .\n. t . . . . . . . . .\n. . t . . t . . . . .\n" +
            ". . . . . . . . . . .\n. . t . t t t t . . .\nt . . t . . . . . . .\n" +
            ". . . . t . . t . . .\n";
        System.out.println("Testing length-2 paths.");
        System.out.println("The graph of length-2 paths is\n" +
                graph.length2Paths());
        if (!t2String.equals(graph.length2Paths().toString())) {
            System.out.println("Error:  the length-2 path graph should be\n" +
                    t2String);
            goodJob = false;
        } else {
            System.out.println("PASSED t2 TEST");
        }

        // Do length-3 paths work?
        String t3String = "11 vertices and 34 edges\n. . t . t t t t . . .\n" +
            "t . . . t . t t . t t\n. . . . t . t . . . t\n. t . . . . . . t . .\n" +
            ". t . . . . . . t . .\nt . . t . . . . . . .\nt . . . . . . t . t .\n" +
            ". . . . . . . . . . .\nt . t . t t . t . t .\n. . t . . t . . t . .\n" +
            ". . t . . t . . . . .\n";
        System.out.println("Testing length-3 paths.");
        System.out.println("The graph of length-3 paths is\n" +
                graph.paths(3));
        if (!t3String.equals(graph.paths(3).toString())) {
            System.out.println("Error:  the length-3 path graph should be\n" +
                    t3String);
            goodJob = false;
        } else {
            System.out.println("PASSED t3 TEST");
        }

        // Do length-4 paths work?
        String t4String = "11 vertices and 49 edges\nt . t . t t . t . t .\n" +
            ". t t . t t t t t . .\n. . t . t t t t . . .\nt . . t t . t . . . t\n" +
            "t . . t t . t . . . t\n. . t . . t . . t . .\n. t . . . . . . t . .\n" +
            ". . . . . . . . . . .\nt t t . . t . t t t .\nt . . . t . t t . t t\n" +
            "t . . . . . . t . t .\n";
        System.out.println("Testing length-4 paths.");
        System.out.println("The graph of length-4 paths is\n" +
                graph.paths(4));
        if (!t4String.equals(graph.paths(4).toString())) {
            System.out.println("Error:  the length-4 path graph should be\n" +
                    t4String);
            goodJob = false;
        } else {
            System.out.println("PASSED t4 TEST");
        }


        // Do length-5 paths work?
        String t5String = "11 vertices and 63 edges\nt t t . . t . t t t .\n" +
            "t . t t t t t t . t t\nt . t . t t . t . t .\n. . t . t t t t t . .\n" +
            ". . t . t t t t t . .\nt . . . t . t t . t t\nt . . t t . t . . . t\n" +
            ". . . . . . . . . . .\nt t . t t . t t t t t\n. t t . t t t t t . .\n" +
            ". t . . . . . . t . .\n";
        System.out.println("Testing length-5 paths.");
        System.out.println("The graph of length-5 paths is\n" +
                graph.paths(5));
        if (!t5String.equals(graph.paths(5).toString())) {
            System.out.println("Error:  the length-5 path graph should be\n" +
                    t5String);
            goodJob = false;
        } else {
            System.out.println("PASSED t5 TEST");
        }


        if (goodJob) {
            System.out.println(" *** Good Job! *** \n");
        }
    }
}
