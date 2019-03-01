/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.tads.Edge;
import graphapplication.tads.GraphEdgeList;
import graphapplication.tads.InvalidEdgeException;
import graphapplication.tads.InvalidVertexException;
import graphapplication.tads.Queue;
import graphapplication.tads.QueueEstatico;
import graphapplication.tads.Stack;
import graphapplication.tads.StackDynamic;
import graphapplication.tads.StackEmptyException;
import graphapplication.tads.StackFullException;
import graphapplication.tads.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author @param <V>
 * @param <E>
 */
public class DiWeightedGraphImp<V, E extends Weighted> extends GraphEdgeList<V, E> implements DiWeightedGraph<V, E> {

    private final List<Edge<E, V>> path;
    private Vertex<V> origem;

    /**
     * Creates a empty graph
     */
    public DiWeightedGraphImp() {
        super();
        this.path = new ArrayList<>();
        this.origem = null;
    }

    /**
     * Creates graph for memento
     *
     * @param diWeightedGraphImp
     */
    public DiWeightedGraphImp(DiWeightedGraphImp diWeightedGraphImp) {
        super(diWeightedGraphImp);
        this.path = diWeightedGraphImp.getPath();
        this.origem = diWeightedGraphImp.getOrigem();
    }

    @Override
    public int minimumCostPath(Vertex<V> vOrigem, Vertex<V> vDestino,
            List<Vertex<V>> path, List<Edge<E, V>> conexoes) {
        HashMap<Vertex<V>, Vertex<V>> parents = new HashMap();
        HashMap<Vertex<V>, Integer> distances = new HashMap();
        HashMap<Vertex<V>, Edge<E, V>> pathedges = new HashMap();
        path.clear();
        try {
            executeDijkstra(vOrigem, parents, distances, pathedges);
        } catch (InvalidEdgeException | InvalidVertexException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        int custo = distances.get(vDestino);
        Vertex<V> vertex = vDestino;
        do {
            path.add(0, vertex);
            conexoes.add(0, pathedges.get(vertex));
            vertex = parents.get(vertex);
        } while (vertex != vOrigem);

        setOrigem(vOrigem);
        return custo;
    }

    //Grau de um vértice - É o número de arestas incidentes no vértice
    @Override
    public int calcularGrauDeVertice(Vertex<V> v) {
        int grau = 0;
        if (v != null) {
            Iterable<Edge<E, V>> incidentEdges = incidentEdges(v);
            if (incidentEdges != null) {
                for (Edge<E, V> e : incidentEdges) {
                    if (e != null) {
                        grau += 1;
                    }
                }
            }
        }
        return grau;
    }

    @Override
    public List<V> buscaEmProfundidade(Vertex<V> v) {
        List<V> lista = new ArrayList<>();
        Iterable<V> it = DFS(v);
        for (V v1 : it) {
            lista.add(v1);
        }
        setOrigem(v);
        return lista;
    }

    //Busca em Profundidade
    private Iterable<V> DFS(Vertex<V> v) {
        path.clear();
        List<V> caminho = new ArrayList<>();
        Set<Vertex<V>> visitado = new HashSet<>();
        Stack<Vertex> pilha = new StackDynamic<>();
        visitado.add(v);
        try {
            pilha.push(v);
        } catch (StackFullException ex) {
            Logger.getLogger(DiWeightedGraphImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!pilha.isEmpty()) {
            try {
                Vertex<V> vLook = pilha.pop();
                caminho.add(vLook.element());
                for (Vertex<V> vAdj : getAdjacents(vLook)) {//ver isso
                    if (!visitado.contains(vAdj)) {
                        visitado.add(vAdj);
                        pilha.push(vAdj);
                    }
                }
            } catch (StackEmptyException | StackFullException ex) {
                Logger.getLogger(DiWeightedGraphImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return caminho;
    }

    @Override
    public List<V> buscaEmLargura(Vertex<V> v) {
        List<V> lista = new ArrayList<>();
        Iterable<V> it = BFS(v);
        for (V v1 : it) {
            lista.add(v1);
        }
        setOrigem(v);
        return lista;
    }

    //Busca em Largura
    private Iterable<V> BFS(Vertex<V> v) {
        path.clear();
        List<V> caminho = new ArrayList<>();
        Set<Vertex<V>> visitado = new HashSet<>();
        Queue<Vertex<V>> fila = new QueueEstatico<>();
        visitado.add(v);
        fila.enqueue(v);
        while (!fila.isEmpty()) {
            Vertex<V> vLook = fila.dequeue();
            caminho.add(vLook.element());
            for (Vertex<V> vAdj : getAdjacents(vLook)) {//ver isso
                if (!visitado.contains(vAdj)) {
                    visitado.add(vAdj);
                    fila.enqueue(vAdj);
                }
            }
        }
        return caminho;
    }

    private Iterable<Vertex<V>> getAdjacents(Vertex<V> vLook) {
        List<Vertex<V>> vAdj = new ArrayList<>();
        for (Edge<E, V> e : edges()) {
            if (areAdjacent(e.vertices()[0], vLook)) {
                vAdj.add(e.vertices()[0]);
                path.add(e);
            }
            if (areAdjacent(e.vertices()[1], vLook)) {
                vAdj.add(e.vertices()[1]);
                path.add(e);
            }
        }
        return vAdj;
    }

    private Vertex<V> minCost(Set<Vertex<V>> unvisited,
            HashMap<Vertex<V>, Integer> distancia) {
        int min = Integer.MAX_VALUE;
        Vertex<V> minCostVertex = null;
        for (Vertex<V> vertex : unvisited) {
            if (distancia.get(vertex) <= min) {
                minCostVertex = vertex;
                min = distancia.get(vertex);
            }
        }
        return minCostVertex;
    }

    private void executeDijkstra(Vertex<V> origem,
            HashMap<Vertex<V>, Vertex<V>> parents,
            HashMap<Vertex<V>, Integer> distancias,
            HashMap<Vertex<V>, Edge<E, V>> pathEdges) {
        Set<Vertex<V>> naoVisitado;
        naoVisitado = new HashSet<>();
        for (Vertex<V> v : vertices()) {
            naoVisitado.add(v);
            distancias.put(v, Integer.MAX_VALUE);
            parents.put(v, null);
        }
        distancias.put(origem, 0);

        while (!naoVisitado.isEmpty()) {
            Vertex<V> vCustoMinimo = minCost(naoVisitado, distancias);
            naoVisitado.remove(vCustoMinimo);
            for (Edge<E, V> edge : incidentEdges(vCustoMinimo)) {
                Vertex<V> oposto = opposite(vCustoMinimo, edge);
                if (naoVisitado.contains(oposto)) {
                    int dist = edge.element().getValor_aresta() + distancias.get(vCustoMinimo);
                    if (distancias.get(oposto) > dist) {
                        distancias.put(oposto, dist);
                        parents.put(oposto, vCustoMinimo);
                        pathEdges.put(oposto, edge);
                    }
                }
            }
        }
    }

    @Override
    public List<Edge<E, V>> getPath() {
        return path;
    }

    @Override
    public Vertex<V> getOrigem() {
        return origem;
    }

    public void setOrigem(Vertex<V> origem) {
        this.origem = origem;
    }

}
