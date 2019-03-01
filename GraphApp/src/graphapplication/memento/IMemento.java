/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.memento;

import graphapplication.model.GestorApp;



/**
 *
 * @author
 */
public interface IMemento {

    public void salvarEstado(GestorApp gestor);
    public void restaurarEstado(GestorApp gestor);
}
