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
public class StackEmptyException extends Exception {

    public StackEmptyException() {
        super("The Stack is Empty!");
    }

    public StackEmptyException(String message) {
        super(message);
    }
}
