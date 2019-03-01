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
 * Data-independent representation of an edge.
 * @author brunomnsilva
 * @param <E> Type of value stored in the edge
 * @param <V> Type of value stored in the vertices that this edge connects.
 */
public interface Edge<E, V> {
    /**
     * Returns the element (object reference) stored in this edge.
     * @return stored element
     */
    public E element();
    
    /**
     * Returns references of both vertices that this edge connects in the form
     * of an array. 
     * @return an array of length 2, i.e., vertices()[0] and vertices()[1]
     */
    public Vertex<V>[] vertices();
}
