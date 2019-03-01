/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.memento;

import graphapplication.model.GestorApp;
import graphapplication.singleton.SingletonLogger;
import graphapplication.tads.Stack;
import graphapplication.tads.StackDynamic;
import graphapplication.tads.StackEmptyException;
import graphapplication.tads.StackFullException;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author
 */
public class CareTaker implements IMemento {

    private final Stack<Memento> undoMemento;
    private SingletonLogger instance = SingletonLogger.getInstance();

    public CareTaker() {
        this.undoMemento = new StackDynamic();
    }

    @Override
    public void salvarEstado(GestorApp gestor) {
        Memento memento = gestor.criarMemento();
        try {
            if (memento.getTipoOperacao() != null) {
                instance.writeToLog("Foi criado o memento " + memento.getTipoOperacao());
            } else {
                instance.writeToLog("Foi criado o memento incial");
            }//salva o novo memento
            undoMemento.push(memento);
        } catch (StackFullException ex) {
            Logger.getLogger(CareTaker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void restaurarEstado(GestorApp gestor) {
        if (undoMemento.isEmpty() != true) {
            try {
                Memento memento = undoMemento.pop();
                if (memento.getTipoOperacao() != null) {
                    instance.writeToLog("Foi restaurado para memento " + memento.getTipoOperacao());
                } else {
                    instance.writeToLog("Foi restaurado para memento inicial");
                }//altera para o memento anterior
                gestor.setMemento(memento);
            } catch (StackEmptyException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
