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
public class StackFullException extends Exception {

    public StackFullException() {
        super("The Stack is Full!");
    }

    public StackFullException(String message) {
        super(message);
    }
}
