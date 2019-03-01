/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.tads.Edge;
import graphapplication.tads.Graph;
import graphapplication.tads.Vertex;
import java.util.*;


/**
 *
 * @author @param <V>
 * @param <E>
 */
public interface DiWeightedGraph<V, E extends Weighted> extends Graph<V, E> {

    public int minimumCostPath(Vertex<V> vOrigem, Vertex<V> vDestino,
            List<Vertex<V>> path, List<Edge<E, V>> conexoes);

    public List<V> buscaEmProfundidade(Vertex<V> v);

    public List<V> buscaEmLargura(Vertex<V> v);

    public int calcularGrauDeVertice(Vertex<V> v);

    public List<Edge<E, V>> getPath();

    public Vertex<V> getOrigem();

}
