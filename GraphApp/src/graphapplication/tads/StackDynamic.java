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
public class StackDynamic<E> implements Stack<E> {

    private Node<E> top; // Referência para o no do topo
    private int size; // Número de elementos na pilha

    public StackDynamic() { // Inicializa a pilha
        top = null;
        size = 0;
    }

    @Override
    public int size() { //Retorna o tamanho actual
        return size; // da pilha
    }

    @Override
    public boolean isEmpty() { // Retorna true se a
        return (top == null); // pilha está vazia

    }

    @Override
    public E peek() throws StackEmptyException {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        return top.getElement();
    }

    @Override
    public void push(E element) throws StackFullException {
        Node node = new Node(element, top);
        top = node;
        size++;
    }

    @Override
    public E pop() throws StackEmptyException {
        if (isEmpty()) {
            throw new StackEmptyException();
        }
        E element = peek();
        top = top.getNext();
        size--;
        return element;
    }
}
