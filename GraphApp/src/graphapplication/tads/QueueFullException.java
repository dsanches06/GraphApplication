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
public class QueueFullException extends RuntimeException {

    public QueueFullException() {
        super("A fila está cheia!");
    }
}
