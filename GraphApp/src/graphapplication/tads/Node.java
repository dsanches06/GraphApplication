/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.tads;

/**
 *
 * @author 
 */
public class Node<E> {

    private E element;
    private Node<E> next;
    private Node<E> previous;

  
    public Node(E element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    public Node(E element, Node next) {
        this.element = element;
        this.next = next;
        this.previous = null;
    }

  
    public Node(E element, Node<E> prev, Node<E> next) {
        this.element = element;
        this.next = next;
        this.previous = prev;
    }


    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }

}
