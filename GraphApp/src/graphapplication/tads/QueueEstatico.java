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
public class QueueEstatico<E> implements Queue<E> {//Fila Estatico

    private final E[] fila;
    private int inicio;
    private int fim;
    private static final int MAX_CAPACIDADE = 100;

    public QueueEstatico(int capacidade) {
        this.fila = (E[]) new Object[capacidade];
        this.inicio = 0;
        this.fim = 0;
    }

    public QueueEstatico() {
        this(MAX_CAPACIDADE);
    }

    @Override
    public int size() {
        return fim - inicio;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private boolean isFull() {
        return size() == fila.length;
    }

    @Override
    public E peek() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException();
        }
        return fila[inicio];
    }

    @Override
    public void enqueue(E element) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException();
        }
        fila[fim % fila.length] = element;
        fim++;
    }

    @Override
    public E dequeue() throws QueueEmptyException {
        E element = peek();
        inicio++;
        if (inicio >= fila.length) {
            inicio -= fila.length;
            fim -= fila.length;
        }
        return element;
    }

    public E[] getFila() {
        return fila;
    }

    @Override
    public Iterator<E> getIterator() {
        return new IteratorQueue();
    }

    private class IteratorQueue implements Iterator<E> {

        private int pos;

        public IteratorQueue() {
            pos = size() - 1;
        }

        @Override
        public boolean hasNext() {
            return pos >= 0;
        }

        @Override
        public E next() {
            return fila[pos--];
        }
    }
}
