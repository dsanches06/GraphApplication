/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.tads;





/**
 *
 * @author
 */
public interface Queue<E> {//Interface Fila

    /* Obtém o tamanho da fila */
    public int size();

    /* Informa se a fila está vazia */
    public boolean isEmpty();

    /* Devolve o primeiro elemento da fila */
    public E peek() throws QueueEmptyException;

    /* Insere um elemento na fila */
    public void enqueue(E element) throws QueueFullException;

    /* Remove um elemento da fila */
    public E dequeue() throws QueueEmptyException;

    /* iterador para percorrer a fila */
    public Iterator<E> getIterator();
}
