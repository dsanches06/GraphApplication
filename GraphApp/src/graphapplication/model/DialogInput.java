/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import graphapplication.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;



/**
 *
 * @author
 */
public class DialogInput {

    private String nomeVertice1;
    private String nomeVertice2;
    private final GestorApp gestor;
    private final Map<Vertice, String> path;
    private int custo;
    private final List<Edge<Aresta, Vertice>> percursoAtual;
    private Map<Vertex<Vertice>, Vertex<Vertice>> predecessors;
    private Vertice origem = null;
    private Map<String, String> caminhos;
    private Map<String, Integer> destinos;

    public DialogInput(GestorApp gestor) {
        this.gestor = gestor;
        this.path = new HashMap<>();
        this.percursoAtual = new ArrayList<>();
        this.predecessors = new HashMap<>();
        this.caminhos = new HashMap<>();
        this.destinos = new HashMap<>();
    }

    public boolean dialogCriarNovoVertice() {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Criar Novo Vertice");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Nome");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().isEmpty()) {
                Vertice vertice = new Vertice(result.get());
                if (Utils.existeVertice(gestor, vertice) != true) {
                    gestor.getDiWeightedGraph().insertVertex(vertice);
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "Este vertice já existe!");
                }
            } else {
                DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                        "O nome do vertice tem que estar preenchido");
            }
        }
        return result.isPresent();
    }

    public boolean dialogRemoverVertice() {

        List<String> vertices = new ArrayList<>();
        for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
            vertices.add(v.element().getCodigo_vertice());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, vertices);
        dialog.setTitle("Remover um vertice");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Escolha o vertice");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof String) {
                for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
                    if (v.element().getCodigo_vertice().equals(result.get())) {
                        gestor.getDiWeightedGraph().removeVertex(v);
                    }
                }
            }
        }
        return result.isPresent();
    }

    public boolean dialogCriarNovaAresta() {

        Dialog<Aresta> dialog = new Dialog<>();
        dialog.setTitle("Criar Nova Aresta");
        dialog.setHeaderText("");
        dialog.setResizable(false);

        Label label1 = new Label("1º");
        Label label2 = new Label("2º");
        Label labelCusto = new Label("Custo");

        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.setValue("vertice");
        comboBox1.setPrefSize(120, 30);
        comboBox1.setItems(FXCollections.observableArrayList(getVertices()));
        comboBox1.setOnAction((ActionEvent e) -> {
            nomeVertice1 = comboBox1.getSelectionModel().getSelectedItem();
        });

        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.setValue("vertice");
        comboBox2.setPrefSize(120, 30);
        comboBox2.setItems(FXCollections.observableArrayList(getVertices()));
        comboBox2.setOnAction((ActionEvent e) -> {
            nomeVertice2 = comboBox2.getSelectionModel().getSelectedItem();
        });

        TextField textCusto = new TextField();
        textCusto.getStyleClass().add("textfield");
        textCusto.setPrefSize(120, 30);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(label1, 1, 1);
        grid.add(comboBox1, 2, 1);
        grid.add(label2, 1, 3);
        grid.add(comboBox2, 2, 3);
        grid.add(labelCusto, 1, 5);
        grid.add(textCusto, 2, 5);

        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                if (nomeVertice1 != null) {
                    if (nomeVertice2 != null) {
                        if (!nomeVertice1.equals(nomeVertice2)) {
                            if (!textCusto.getText().isEmpty()) {
                                String codigo_aresta = nomeVertice1.concat(nomeVertice2);
                                String codigoObtido = Utils.existeCodigoAresta(gestor, codigo_aresta);
                                if (codigoObtido != null) {
                                    int numero = Integer.valueOf(codigoObtido.substring(2, 3));
                                    codigo_aresta += ++numero;
                                } else {
                                    codigo_aresta += 1;
                                }
                                return new Aresta(nomeVertice1, nomeVertice2, codigo_aresta, Integer.valueOf(textCusto.getText()));
                            } else {
                                DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                                        "O custo tem que estar preenchido");
                            }
                        } else {
                            DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                                    "2º vertice tem que ser diferente do 1º vertice");
                        }
                    } else {
                        DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                                "2º vertice não deve ser nulo");
                    }
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "1º vertice não deve ser nulo");
                }
            }
            return null;
        });

        Optional<Aresta> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof Aresta) {
                Aresta aresta = ((Aresta) result.get());
                Vertex<Vertice> v1 = Utils.getVertex(gestor, aresta.getCodigo_vertice_1());
                Vertex<Vertice> v2 = Utils.getVertex(gestor, aresta.getCodigo_vertice_2());
                if (Utils.existeAresta(gestor, aresta) != true) {
                    gestor.getDiWeightedGraph().insertEdge(v1, v2, aresta);
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "Esta aresta já existe!");
                }
            }
        }
        return result.isPresent();
    }

    public boolean dialogRemoverAresta() {
        List<Aresta> choices = new ArrayList<>();
        for (Edge<Aresta, Vertice> e : gestor.getDiWeightedGraph().edges()) {
            choices.add(e.element());
        }

        ChoiceDialog<Aresta> dialog = new ChoiceDialog<>(null, choices);
        dialog.setTitle("Remover uma Aresta");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Escolha a aresta");

        Optional<Aresta> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof Aresta) {
                Aresta aresta = result.get();
                for (Edge<Aresta, Vertice> edge : gestor.getDiWeightedGraph().edges()) {
                    if (edge.element().equals(aresta)) {
                        gestor.getDiWeightedGraph().removeEdge(edge);
                    }
                }
            }
        }
        return result.isPresent();
    }

    public boolean dialogBuscaEmLargura() {

        List<String> listaVertices = getVertices();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, listaVertices);
        dialog.setTitle("Busca em largura");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Escolha o vertice");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof String) {
                Vertex<Vertice> v = Utils.getVertex(gestor, result.get());
                if ((v.element() != null)
                        && (gestor.getDiWeightedGraph().calcularGrauDeVertice(v) >= 1)) {
                    origem = v.element();
                    custo = 0;
                    List<Vertice> lista = gestor.getDiWeightedGraph().buscaEmLargura(v);
                    path.clear();
                    for (Vertice local : lista) {
                        //adicionar novos caminhos
                        if (!local.equals(origem)) {
                            path.put(local, local.getCodigo_vertice());
                        }
                    }
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "Este vertice tem grau zero, ou seja,\n"
                            + " não tem vertices adjacentes");
                }
            }
        }
        return result.isPresent();
    }

    public boolean dialogBuscaEmProfundidade() {

        List<String> listaVertices = getVertices();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, listaVertices);
        dialog.setTitle("Busca em profundidade");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Escolha o vertice");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof String) {
                Vertex<Vertice> v = Utils.getVertex(gestor, result.get());
                if ((v.element() != null)
                        && (gestor.getDiWeightedGraph().calcularGrauDeVertice(v) >= 1)) {
                    origem = v.element();
                    custo = 0;
                    List<Vertice> lista = gestor.getDiWeightedGraph().buscaEmProfundidade(v);
                    path.clear();
                    for (Vertice local : lista) {
                        //adicionar novos caminhos
                        if (!local.equals(origem)) {
                            path.put(local, local.getCodigo_vertice());
                        }
                    }
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "Este vertice tem grau zero, ou seja,\n"
                            + " não tem vertices adjacentes");
                }
            }
        }

        return result.isPresent();
    }

    public boolean dialogCalcularDijkstra() {

        List<String> listaVertices = getVertices();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, listaVertices);
        dialog.setTitle("Algoritmo Dijkstra");
        dialog.setHeaderText("");
        dialog.setResizable(false);
        dialog.setContentText("Escolha o vertice");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() instanceof String) {
                Vertex<Vertice> vOrigem = Utils.getVertex(gestor, result.get());
                if ((vOrigem.element() != null)
                        && (gestor.getDiWeightedGraph().calcularGrauDeVertice(vOrigem) >= 1)) {
                    origem = vOrigem.element();
                    List<Vertex<Vertice>> caminho = new ArrayList<>();
                    List<Edge<Aresta, Vertice>> conexoes = new ArrayList<>();
                    //obter osvertices sem a vertice origem
                    List<Vertex<Vertice>> verticesAux = new ArrayList<>();
                    for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
                        if (!vOrigem.element().equals(v.element())) {
                            if (gestor.getDiWeightedGraph().calcularGrauDeVertice(v) >= 1) {
                                verticesAux.add(v);
                            }
                        }
                    }
                    //executa o dijstra
                    for (Vertex<Vertice> vDestino : verticesAux) {
                        int custoTotal = gestor.getDiWeightedGraph().minimumCostPath(vOrigem, vDestino, caminho, conexoes);
                        destinos.put(vDestino.element().getCodigo_vertice(), custoTotal);
                        //apagar os caminhos anteriores
                        path.clear();
                        //obter os caminhos 
                        for (Vertex<Vertice> vertex : caminho) {
                            //adicionar novos caminhos
                            path.put(vertex.element(), vertex.element().getCodigo_vertice());
                            caminhos.put(vDestino.element().getCodigo_vertice(), obterPercursoCalculado());
                        }
                        //apagar os percursos anteriores
                        percursoAtual.clear();
                        //obter os percurso
                        for (Edge<Aresta, Vertice> e : conexoes) {
                            //adicionar novos percursos
                            percursoAtual.add(e);
                        }
                    }
                } else {
                    DialogAlert.mostrarInf(Alert.AlertType.ERROR, "Erro",
                            "Este vertice tem grau zero, ou seja,\n"
                            + " não tem vertices adjacentes");
                }
            }
        }

        return result.isPresent();
    }

    public String obterPercursoCalculado() {
        StringBuilder str = new StringBuilder();
        if (origem != null) {
            str.append(origem.getCodigo_vertice());
            for (String caminho : path.values()) {
                str.append(" -> ").append(caminho);
            }
        }
        return str.toString();
    }

    public List<Edge<Aresta, Vertice>> getPercursosAtuais() {
        if (percursoAtual == null) {
            return new ArrayList<>();
        } else {
            return percursoAtual; //copia da lista?
        }
    }

    public List<Edge<Aresta, Vertice>> getCaminhoDeBusca() {
        List<Edge<Aresta, Vertice>> lista = new ArrayList<>();
        if (!gestor.getDiWeightedGraph().getPath().isEmpty()) {
            for (Edge<Aresta, Vertice> edge : gestor.getDiWeightedGraph().getPath()) {
                if (!lista.contains(edge)) {
                    lista.add(edge);
                }
            }
        }
        return lista;
    }

    public List<Edge<Aresta, Vertice>> getVerticeSelecionado(Vertex<Vertice> v) {
        List<Edge<Aresta, Vertice>> lista = new ArrayList<>();
        if (v != null) {
            if (gestor.getDiWeightedGraph().calcularGrauDeVertice(v) >= 1) {
                for (Edge<Aresta, Vertice> edge : gestor.getDiWeightedGraph().incidentEdges(v)) {
                    lista.add(edge);
                }
            }
        }
        return lista;
    }

    private List<String> getVertices() {
        List<String> lista = new ArrayList<>();
        for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
            lista.add(v.element().getCodigo_vertice());
        }
        return lista;
    }

    public int getCusto() {
        return custo;
    }

    public Map<String, String> getCaminhos() {
        return caminhos;
    }

    public void setCaminhos(Map<String, String> caminhos) {
        this.caminhos = caminhos;
    }

    public Map<String, Integer> getDestinos() {
        return destinos;
    }

    public void setDestinos(Map<String, Integer> destinos) {
        this.destinos = destinos;
    }

}
