/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.tads;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class StackArray<E> implements Stack<E> {

    public static final int MAX = 100;
    //tamanho maxima da pilha
    private int size;
    //indice do topo da pilha
    private int topo;
    //array para armazenar os elementos
    private E[] array;

    public StackArray(int tamanho) {
        this.size = tamanho;
        array = (E[]) new Object[tamanho];
        topo = -1;
    }

    public StackArray() {
        this(MAX);
    }

    @Override
    public int size() {
        return (topo + 1);
    }

    @Override
    public boolean isEmpty() {
        return (topo == -1);
    }

    @Override
    public E peek() throws StackEmptyException {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        return array[topo];
    }

    @Override
    public void push(E element) throws StackFullException {
        if (size() == size) {
            throw new StackFullException();
        }
        array[++topo] = element;
    }

    @Override
    public E pop() throws StackEmptyException {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        return array[topo--];
    }

}
