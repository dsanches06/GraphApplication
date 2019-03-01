/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.utils;

import graphapplication.model.Aresta;
import graphapplication.model.GestorApp;
import graphapplication.model.Vertice;
import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import java.util.*;


/**
 *
 * @author
 */
public class Utils {//para evitar duplicação de codigos

    public static boolean existeVertice(GestorApp gestor, Vertice vertice) {
        if (gestor.getDiWeightedGraph().numVertices() > 0) {
            for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
                if (v.element().equals(vertice)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean existeAresta(GestorApp gestor, Aresta aresta) {
        if (gestor.getDiWeightedGraph().numEdges() > 0) {
            for (Edge<Aresta, Vertice> e : gestor.getDiWeightedGraph().edges()) {
                if (e.element().equals(aresta)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String existeCodigoAresta(GestorApp gestor, String codigo_aresta) {
        if (gestor.getDiWeightedGraph().numEdges() > 0) {
            for (Edge<Aresta, Vertice> e : gestor.getDiWeightedGraph().edges()) {
                String codigo = e.element().getCodigo_aresta().substring(0, 2);
                if (codigo.equals(codigo_aresta)) {
                    return e.element().getCodigo_aresta();
                }
            }
        }
        return null;
    }

    public static Vertex<Vertice> getVertex(GestorApp gestor, String codigo_vertice) {
        Iterable<Vertex<Vertice>> vertices = gestor.getDiWeightedGraph().vertices();
        for (Vertex<Vertice> v : vertices) {
            if (v.element().getCodigo_vertice().equals(codigo_vertice)) {
                return v;
            }
        }
        return null;
    }

    public static boolean containsAresta(HashSet<Aresta> list, Aresta aresta) {
        if (!list.isEmpty()) {
            for (Aresta aux : list) {
                if (aux.equals(aresta)) {
                    return true;
                }
            }
        }
        return false;
    }
}
