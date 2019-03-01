/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.views;

import graphapplication.GraphApp;
import graphapplication.graphview.CircularSortedPlacementStrategy;
import graphapplication.graphview.GraphPanel;
import graphapplication.graphview.VertexPlacementStrategy;
import graphapplication.model.Aresta;
import graphapplication.model.BorderComTitulo;
import graphapplication.model.GestorApp;
import graphapplication.model.Tabela;
import graphapplication.model.Vertice;
import graphapplication.singleton.SingletonLogger;
import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Danilson
 */
public class ViewPainelPrincipal implements Observer {

    private final GestorApp gestor;
    private final BorderPane root;
    private final VertexPlacementStrategy strategy;
    private final GraphPanel<Vertice, Aresta> graphPanel;
    private final Scene scene;
    private VBox vboxGraph;
    private SingletonLogger logger = SingletonLogger.getInstance();
    private TextArea loggerText;

    public ViewPainelPrincipal(BorderPane root, GestorApp gestor) {
        this.gestor = gestor;
        this.root = root;
        strategy = new CircularSortedPlacementStrategy();
        graphPanel = new GraphPanel(gestor, gestor.getDiWeightedGraph(), strategy);
        graphPanel.setPrefSize(600, 450);
        graphPanel.setId("graphView");
        scene = new Scene(graphPanel, 450, 400);
        addObserver();
    }

    private void addObserver() {
        gestor.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof GestorApp) {
            GestorApp gestorApp = ((GestorApp) o);

            //cria um novo graph
            GraphPanel<Vertice, Aresta> graphPane = new GraphPanel(gestorApp, gestorApp.getDiWeightedGraph(), strategy);
            graphPane.setPrefSize(450, 450);
            graphPane.setId("graphView");

            //cria um novo scene
            Scene newScene = new Scene(graphPane, 600, 370);
            //romeve todos os elementos
            vboxGraph.getChildren().clear();
            graphPane.plotGraph();
            vboxGraph.getChildren().add(graphPane);//insere o graph
            graphPane.resetColorsToDefault();
            //mostra a mensagem
            loggerText.setText(logger.getMensagens());

            //obter operação
            String tipoOperacao = gestorApp.getTipoOperacao();
            if (tipoOperacao != null) {
                if ((!tipoOperacao.equals("load"))
                        && (!tipoOperacao.equals("save"))) {
                    //obter o percurso atual
                    List<Edge<Aresta, Vertice>> percursoAtual = getPercurso(gestorApp, tipoOperacao);
                    //se for vazio ou não
                    if (!percursoAtual.isEmpty()) {
                        //obter os valores
                        int custo = gestorApp.getDialog().getCusto();
                        String caminho = gestorApp.getDialog().obterPercursoCalculado();
                        Vertex<Vertice> vOrigem = gestor.getDiWeightedGraph().getOrigem();
                        List<Edge<Aresta, Vertice>> edges = getPercurso(gestorApp, tipoOperacao);
                        //obter resultados textual
                        StringBuilder str = obterResultados(tipoOperacao, vOrigem, custo, caminho, edges);

                        TextArea txtArea = new TextArea();

                        HBox hboxResultado = new HBox();

                        Pane painelResultado = new BorderComTitulo("Resultados", hboxResultado);
                        painelResultado.getStyleClass().add("titled-address");

                        if (str.length() != 0) {
                            txtArea.setText(str.toString());
                            hboxResultado.getChildren().add(txtArea);
                            vboxGraph.getChildren().add(painelResultado);
                        } else {
                            TableView<Map> tabela = tabela(gestorApp);
                            tabela.refresh();
                            hboxResultado.getChildren().add(tabela);
                            vboxGraph.getChildren().add(painelResultado);
                        }
                        for (Edge<Aresta, Vertice> e : percursoAtual) {
                            if (e != null) {
                                graphPane.setEdgeColor(e, GraphPanel.GRAPH_EDGE_DRAW_SELECTED, 1);
                            }

                        }
                    }
                }
            }
        }
    }

    public void iniciar() {
        menuIniciar();
        painelCentral();
    }

    private void menuIniciar() {
        //barra de menu     
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu-bar");

        //menu ficheiro
        Menu menuFicheiro = new Menu("Ficheiro");
        //menu item sair
        MenuItem sair = new MenuItem("Sair", null);
        sair.setId("menu-item");
        sair.setMnemonicParsing(true);
        sair.setOnAction((ActionEvent e) -> {
            Platform.exit();
        });

        Menu menuImportar = new Menu("Importar");
        menuImportar.setId("menu-item");

        //menu item importar ficheiro de texto
        MenuItem importarFile = new MenuItem("file", null);
        importarFile.setId("menu-item");
        importarFile.setMnemonicParsing(true);
        importarFile.setOnAction((ActionEvent e) -> {
            //cria uma instancia filechooser
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Importar ficheiro", "file");
            //mostrar dialog abrir/carregar
            File file = fileChooser.showOpenDialog(GraphApp.stageApp);
            //se houver ficheiro
            if (file != null) {
                gestor.load(file, "file");
            }
        });

        //menu item importar ficheiro json
        MenuItem importarJson = new MenuItem("json", null);
        importarJson.setId("menu-item");
        importarJson.setMnemonicParsing(true);
        importarJson.setOnAction((ActionEvent e) -> {
            //cria uma instancia filechooser
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Exportar ficheiro", "json");
            //mostrar dialog abrir/carregar
            File file = fileChooser.showOpenDialog(GraphApp.stageApp);
            //se houver ficheiro
            if (file != null) {
                gestor.load(file, "json");

            }
        });

        Menu menuExportar = new Menu("Exportar");
        menuExportar.setId("menu-item");

        //menu item exportar ficheiro de texto
        MenuItem exportarFile = new MenuItem("file", null);
        exportarFile.setId("menu-item");
        exportarFile.setMnemonicParsing(true);
        exportarFile.setOnAction((ActionEvent e) -> {
            //cria uma instancia filechooser
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Exportar ficheiro", "file");
            //mostrar dialog abrir/gravar
            File file = fileChooser.showSaveDialog(GraphApp.stageApp);
            //se houver ficheiro
            if (file != null) {
                gestor.save(file, "file");
            }
        });

        //menu item importar ficheiro json
        MenuItem exportarJson = new MenuItem("json", null);
        exportarJson.setId("menu-item");
        exportarJson.setMnemonicParsing(true);
        exportarJson.setOnAction((ActionEvent e) -> {
            //cria uma instancia filechooser
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser, "Exportar ficheiro", "json");
            //mostrar dialog abrir/gravar
            File file = fileChooser.showSaveDialog(GraphApp.stageApp);
            //se houver ficheiro
            if (file != null) {
                gestor.save(file, "json");
            }
        });

        //menu estatistica
        Menu menuEstatitica = new Menu("Estatistica");
        //menu item estatistica para visualizar grafico
        MenuItem estatistica = new MenuItem("visualizar", null);
        estatistica.setId("menu-item");
        estatistica.setMnemonicParsing(true);
        estatistica.setOnAction((ActionEvent e) -> {
            viewEstatistica(root, gestor);
        });

        menuImportar.getItems().addAll(importarFile, importarJson);
        menuExportar.getItems().addAll(exportarFile, exportarJson);
        menuFicheiro.getItems().addAll(menuImportar, menuExportar, sair);
        menuEstatitica.getItems().addAll(estatistica);
        menuBar.getMenus().addAll(menuFicheiro, menuEstatitica);
        root.setTop(menuBar);
    }

    private void painelCentral() {

        GridPane gridGraph = new GridPane();
        gridGraph.setAlignment(Pos.TOP_CENTER);
        gridGraph.setHgap(10);
        gridGraph.setVgap(10);

        vboxGraph = new VBox(10);
        vboxGraph.setPrefSize(500, 565);
        gridGraph.add(vboxGraph, 0, 1);//insere o graph

        GridPane gridOperacao = new GridPane();
        gridOperacao.setAlignment(Pos.TOP_CENTER);
        gridOperacao.setHgap(10);
        gridOperacao.setVgap(10);

        Button btn1 = new Button("Criar");
        btn1.setPrefSize(90, 30);
        btn1.setOnAction((ActionEvent e) -> {
            gestor.dialogCriarNovoVertice();
        });

        Button btn2 = new Button("Remover");
        btn2.setPrefSize(90, 30);
        btn2.setOnAction((ActionEvent e) -> {
            gestor.dialogRemoverVertice();
        });

        HBox hbtn1 = new HBox(15);
        hbtn1.getChildren().addAll(btn1, btn2);

        //criar um painel
        Pane painelVbtn1 = new BorderComTitulo("Operações com Vertices", hbtn1);
        painelVbtn1.getStyleClass().add("titled-address");

        Button btn3 = new Button("Criar");
        btn3.setPrefSize(90, 30);
        btn3.setOnAction((ActionEvent e) -> {
            gestor.dialogCriarNovaAresta();
        });

        Button btn4 = new Button("remover");
        btn4.setPrefSize(90, 30);
        btn4.setOnAction((ActionEvent e) -> {
            gestor.dialogRemoverAresta();
        });
        HBox hbtn2 = new HBox(15);
        hbtn2.getChildren().addAll(btn3, btn4);

        //criar um painel
        Pane painelVbtn2 = new BorderComTitulo("Operaçoes com Arestas", hbtn2);
        painelVbtn2.getStyleClass().add("titled-address");

        Button btn5 = new Button("Largura");
        btn5.setPrefSize(90, 30);
        btn5.setOnAction((ActionEvent e) -> {
            gestor.buscaEmLargura();
        });

        Button btn6 = new Button("Dijkstra");
        btn6.setPrefSize(90, 30);
        btn6.setOnAction((ActionEvent e) -> {
            gestor.dijkstra();
        });
        HBox hbtn3 = new HBox(15);
        hbtn3.getChildren().addAll(btn5, btn6);

        Button btn7 = new Button("Profundidade");
        btn7.setPrefSize(90, 30);
        btn7.setOnAction((ActionEvent e) -> {
            gestor.buscaEmProfundidade();
        });

        Button btn8 = new Button("Undo");
        btn8.setPrefSize(90, 30);
        btn8.setOnAction((ActionEvent e) -> {
            gestor.undo();
        });
        HBox hbtn4 = new HBox(15);
        hbtn4.getChildren().addAll(btn7, btn8);

        VBox vbtn1 = new VBox(10);
        vbtn1.getChildren().addAll(hbtn3, hbtn4);

        //criar um painel
        Pane painelVbtn3 = new BorderComTitulo("Percorrer o grafo", vbtn1);
        painelVbtn3.getStyleClass().add("titled-address");

        VBox painelVbtn = new VBox(10);
        painelVbtn.getChildren().addAll(painelVbtn1, painelVbtn2, painelVbtn3);

        loggerText = new TextArea("Logger ");
        loggerText.setPrefSize(250, 100);

        //separator
        Separator separador1 = new Separator();
        separador1.setOrientation(Orientation.HORIZONTAL);
        separador1.setHalignment(HPos.CENTER);

        VBox vboxOperacao = new VBox(20);
        vboxOperacao.getChildren().addAll(painelVbtn, separador1, loggerText);
        gridOperacao.add(vboxOperacao, 0, 1);//insere o digraph

        //separator
        Separator separador2 = new Separator();
        separador2.setOrientation(Orientation.VERTICAL);
        separador2.setHalignment(HPos.CENTER);

        vboxGraph.getChildren().clear();
        graphPanel.plotGraph();
        vboxGraph.getChildren().add(graphPanel);//insere o graph

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(gridGraph, separador2, gridOperacao);
        root.setCenter(hBox);
    }

    private void configureFileChooser(FileChooser fileChooser, String titulo, String type) {
        fileChooser.setTitle(titulo);
        switch (type) {
            case "file":
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            case "json":
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
                break;
        }
    }

    private StackPane viewEstatistica(BorderPane root, GestorApp gestor) {
        return new ViewEstatistica(root, gestor);
    }

    private StringBuilder obterResultados(String tipo, Vertex<Vertice> vOrigem,
            int custo, String caminho, List<Edge<Aresta, Vertice>> edges) {
        StringBuilder str = new StringBuilder();
        if (!tipo.equals("select")) {
            if (!tipo.equals("dijkstra")) {
                if (vOrigem != null) {
                    str.append("Operação: ")
                            .append(tipo)
                            .append(" ")
                            .append(vOrigem)
                            .append("\nGrau: ")
                            .append(String.valueOf(gestor.getDiWeightedGraph().calcularGrauDeVertice(vOrigem)));
                    str.append("\nCusto: ")
                            .append(custo);
                    switch (tipo) {
                        case "dfs":
                        case "bfs":
                            str.append("\nCaminho: ")
                                    .append(caminho)
                                    .append("\nArestas: ");
                            for (Edge<Aresta, Vertice> edge : edges) {
                                str.append(" -> ")
                                        .append(edge.element());
                            }
                            break;
                    }
                }
            }
        } else {
            str.append(getVerticeSelecionado(tipo, edges));
        }
        return str;
    }

    private String getVerticeSelecionado(String tipo, List<Edge<Aresta, Vertice>> edges) {
        StringBuilder str = new StringBuilder();
        str.append("Operação: ")
                .append(tipo)
                .append(" ")
                .append(gestor.getVerticeSelecionado().element().toString())
                .append("\nGrau: ")
                .append(String.valueOf(gestor.getDiWeightedGraph().calcularGrauDeVertice(gestor.getVerticeSelecionado())))
                .append("\nArestas Incidentes: ")
                .append(gestor.getVerticeSelecionado().element().toString());
        if (gestor.getDiWeightedGraph().calcularGrauDeVertice(gestor.getVerticeSelecionado()) >= 1) {
            for (Edge<Aresta, Vertice> edge : edges) {
                str.append(" -> ")
                        .append(edge.element());
            }
        } else {
            str.append("N/A");
        }

        return str.toString();
    }

    private List<Edge<Aresta, Vertice>> getPercurso(GestorApp gestor, String tipo) {
        switch (tipo) {
            case "dijkstra":
                return gestor.getDialog().getPercursosAtuais();
            case "dfs":
            case "bfs":
                return gestor.getDialog().getCaminhoDeBusca();
            case "select":
                return gestor.getDialog().getVerticeSelecionado(gestor.getVerticeSelecionado());
        }
        return new ArrayList<>();
    }

    private Tabela tabela(GestorApp gestor) {
        return new Tabela(gestor);
    }

}
