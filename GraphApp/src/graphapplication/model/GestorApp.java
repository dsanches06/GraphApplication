/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.dao.MapaDAO;
import graphapplication.dao.MapaFactory;
import graphapplication.memento.CareTaker;
import graphapplication.memento.Memento;
import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import java.io.File;
import java.util.Iterator;
import java.util.Observable;





/**
 *
 * @author
 */
public class GestorApp extends Observable {

    private DiWeightedGraph<Vertice, Aresta> diWeightedGraph;
    private DialogInput dialog;
    private String tipoOperacao;
    private Vertex<Vertice> verticeSelecionado;
    private CareTaker caretaker;

    public GestorApp(CareTaker caretaker) {
        this.diWeightedGraph = new DiWeightedGraphImp();
        this.dialog = new DialogInput(this);
        this.tipoOperacao = null;
        this.verticeSelecionado = null;
        this.caretaker = caretaker;
    }

    public Memento criarMemento() {
        return new Memento(caretaker, tipoOperacao, diWeightedGraph);
    }

    public void setMemento(Memento memento) {
        caretaker = memento.getCaretaker();
        diWeightedGraph = memento.getDiWeightedGraph();
        tipoOperacao = memento.getTipoOperacao();
    }

    private void notificarObservadores() {
        //notificar observadores
        setChanged();
        notifyObservers();
    }

    public void load(File file, String type) {
        MapaDAO mapa = MapaFactory.createFileDao(type);
        if (mapa != null) {
            resetarGraph();//reseta os elementos no grafo
            //salva o estado
            caretaker.salvarEstado(this);
            //faz load dos novos elementos no grafo
            mapa.load(this, file);
            tipoOperacao = "load";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void save(File file, String type) {
        MapaDAO mapa = MapaFactory.createFileDao(type);
        if (mapa != null) {
            mapa.save(this, file);
            tipoOperacao = "save";
        }
    }

    public void dialogCriarNovoVertice() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogCriarNovoVertice() == true) {
            tipoOperacao = "dialogCriarNovoVertice";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void dialogRemoverVertice() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogRemoverVertice() == true) {
            tipoOperacao = "dialogRemoverVertice";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void dialogCriarNovaAresta() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogCriarNovaAresta() == true) {
            tipoOperacao = "dialogCriarNovaAresta";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void dialogRemoverAresta() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogRemoverAresta() == true) {
            tipoOperacao = "dialogRemoverAresta";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void buscaEmLargura() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogBuscaEmLargura() == true) {
            tipoOperacao = "bfs";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void buscaEmProfundidade() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogBuscaEmProfundidade() == true) {
            tipoOperacao = "dfs";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void dijkstra() {
        //salva o estado
        caretaker.salvarEstado(this);
        if (dialog.dialogCalcularDijkstra() == true) {
            tipoOperacao = "dijkstra";
            //notificar observadores
            notificarObservadores();
        }
    }

    public void setVerticeSelecionado(Vertex<Vertice> verticeSelecionado) {
        //salva o estado
        caretaker.salvarEstado(this);
        if (verticeSelecionado != null) {
            this.verticeSelecionado = verticeSelecionado;
            tipoOperacao = "select";
            //notificar observadores
            notificarObservadores();
        }

    }

    public void undo() {
        caretaker.restaurarEstado(this);
        //notificar observadores
        notificarObservadores();
    }

    private void resetarGraph() {
        Iterator<Vertex<Vertice>> vertices = this.getDiWeightedGraph().vertices().iterator();
        if (vertices != null) {
            while (vertices.hasNext()) {
                this.getDiWeightedGraph().removeVertex(vertices.next());
                if (this.getDiWeightedGraph().numVertices() == 0) {
                    break;
                }
            }
        }
        Iterator<Edge<Aresta, Vertice>> arestas = this.getDiWeightedGraph().edges().iterator();
        if (arestas != null) {
            while (arestas.hasNext()) {
                this.getDiWeightedGraph().removeEdge(arestas.next());
                if (this.getDiWeightedGraph().numEdges() == 0) {
                    break;
                }
            }
        }
    }

    public DiWeightedGraph<Vertice, Aresta> getDiWeightedGraph() {
        return diWeightedGraph;
    }

    public DialogInput getDialog() {
        return dialog;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public Vertex<Vertice> getVerticeSelecionado() {
        return verticeSelecionado;
    }

    public CareTaker getCaretaker() {
        return caretaker;
    }

}
