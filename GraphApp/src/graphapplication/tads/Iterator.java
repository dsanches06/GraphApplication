/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.tads;

/**
 *
 *
 * @param <E> O tipo genérico do iterador.
 */
public interface Iterator<E> {

    public boolean hasNext();

    public E next();
}
