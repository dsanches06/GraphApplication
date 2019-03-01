/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.memento;

import graphapplication.model.Aresta;
import graphapplication.model.DiWeightedGraph;
import graphapplication.model.DiWeightedGraphImp;
import graphapplication.model.Vertice;



/**
 *
 * @author
 */
public class Memento {

    private CareTaker caretaker;
    private DiWeightedGraph<Vertice, Aresta> diWeightedGraph;
    private String tipoOperacao;

    public Memento(CareTaker caretaker, String tipoOperacao,
            DiWeightedGraph<Vertice, Aresta> diWeightedGraph) {
        this.caretaker = caretaker;
        this.diWeightedGraph = new DiWeightedGraphImp((DiWeightedGraphImp) diWeightedGraph);;
        this.tipoOperacao = tipoOperacao;
    }

    public CareTaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(CareTaker caretaker) {
        this.caretaker = caretaker;
    }

    public DiWeightedGraph<Vertice, Aresta> getDiWeightedGraph() {
        return diWeightedGraph;
    }

    public void setDiWeightedGraph(DiWeightedGraph<Vertice, Aresta> diWeightedGraph) {
        this.diWeightedGraph = diWeightedGraph;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }
}
