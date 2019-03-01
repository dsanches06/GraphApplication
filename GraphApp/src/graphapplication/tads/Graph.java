/* 
 * The MIT License
 *
 * Copyright 2018 brunomnsilva@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package graphapplication.tads;

/**
 * Interface that describes the behavior of the Graph ADT.
 *
 * @author brunomnsilva
 * @param <E> Type of values stored in edges
 * @param <V> Type of values stored in vertices
 */
public interface Graph<V, E> {

    /**
     * Returns the number of vertices of the graph
     *
     * @return vertex count
     */
    public int numVertices();

    /**
     * Returns the number of edges of the graph
     *
     * @return edge count
     */
    public int numEdges();

    /**
     * Returns the vertices of the graph as an iterable collection
     *
     * @return set of vertices
     */
    public Iterable<Vertex<V>> vertices();

    /**
     * Returns the edges of the graph as an iterable collection.
     *
     * @return set of edges
     */
    public Iterable<Edge<E, V>> edges();

    /**
     * Returns a vertex's incident edges as an iterable collection.
     *
     * @param v
     * @return set of vertices
     */
    public Iterable<Edge<E, V>> incidentEdges(Vertex<V> v)
            throws InvalidEdgeException;

    /**
     * Given a vertex and an edge, returns the opposite vertex.
     *
     * @param v a vertex
     * @param e an edge
     * @return the opposite vertex
     * @exception InvalidVertexException if the vertex is invalid for the graph.
     * @exception InvalidEdgeException if the edge is invalid for the graph.
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e)
            throws InvalidVertexException, InvalidEdgeException;

    /**
     * Tests whether two vertices are adjacent.
     *
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @return true if they are adjacent, false otherwise.
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v)
            throws InvalidVertexException;

    /**
     * Inserts a new vertex with a given element, returning its reference.
     *
     * @param vElement the element to store at the vertex. Cannot be null.
     * @return the reference of the newly created vertex
     */
    public Vertex<V> insertVertex(V vElement);

    /**
     * Inserts a new edge with a given element between two vertices, returning
     * its reference.
     *
     * @param u a vertex (outbound, if digraph)
     * @param v another vertex (inbound, if digraph)
     * @param edgeElement the element to store in the new edge
     * @return the reference for the newly created edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement)
            throws InvalidVertexException;

    /**
     * Inserts a new edge with a given element between two vertices, returning
     * its reference.
     *
     * @param vElement1 element to store in the vertex (outbound, if digraph)
     * @param vElement2 element to store in the another vertex (inbound, if
     * digraph)
     * @param edgeElement the element to store in the new edge
     * @return the reference for the newly created edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement)
            throws InvalidVertexException;

    /**
     * Removes a vertex and all its incident edges and returns the element
     * stored at the removed vertex.
     *
     * @param v vertex to remove
     * @return element from the removed vertex
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public V removeVertex(Vertex<V> v) throws InvalidVertexException;

    /**
     * Removes an edge and return its element.
     *
     * @param e the edge to remove
     * @return the element from the removed edge
     * @exception InvalidVertexException if a vertex is invalid for the graph.
     */
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException;

    /**
     * Replaces the element of a given vertex with a new element and returns the
     * old element.
     *
     * @param v the vertex
     * @param newElement new element to store in v
     * @return the old element previously stored in v
     * @exception InvalidVertexException if the vertex is invalid for the graph.
     */
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException;

    /**
     * Replaces the element of a given edge with a new element and returns the
     * old element.
     *
     * @param e the edge
     * @param newElement new element to store in e
     * @return the old element previously stored in e
     * @exception InvalidEdgeException if the edge is invalid for the graph.
     */
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException;
    

}
