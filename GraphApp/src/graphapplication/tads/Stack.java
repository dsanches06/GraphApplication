/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.tads;

import java.util.List;

/**
 *
 * @author 
 * @param <E> 
 */
public interface Stack<E> {
    public int size();
    public boolean isEmpty();
    public E peek() throws StackEmptyException;
    public void push(E elem) throws StackFullException;
    public E pop() throws StackEmptyException;   
}
