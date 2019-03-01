/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.views;

import graphapplication.model.BorderComTitulo;
import graphapplication.model.GestorApp;
import graphapplication.model.Vertice;
import graphapplication.tads.Vertex;
import graphapplication.utils.CompararValores;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author
 */
public class ViewEstatistica extends StackPane {

    public ViewEstatistica(BorderPane root, GestorApp gestor) {
        setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 10, 10));
        mostrarGrafico(root, grid, gestor);
        root.setCenter(grid);
    }

    private void mostrarGrafico(BorderPane root, GridPane grid, GestorApp gestor) {

        GridPane gridGrafico = new GridPane();
        gridGrafico.setAlignment(Pos.TOP_LEFT);

        GridPane gridBtn = new GridPane();
        gridBtn.setAlignment(Pos.TOP_LEFT);

        Button btnRetroceder = new Button("Retroceder");
        btnRetroceder.setPrefSize(200, 30);
        btnRetroceder.setOnAction((ActionEvent e) -> {
            ViewPainelPrincipal painelPrincipal = viewPainelPrincipal(root, gestor);
            painelPrincipal.iniciar();
        });

        HBox hboxBtn = new HBox();
        hboxBtn.setAlignment(Pos.TOP_CENTER);
        hboxBtn.getChildren().add(btnRetroceder);

        //criar um painel
        Pane painelBtn = new BorderComTitulo("Operações", hboxBtn);
        painelBtn.getStyleClass().add("titled-address");
        gridBtn.add(painelBtn, 0, 1);
        grid.add(gridBtn, 0, 1);

        BarChart barChart = graficoBarChart(gestor);
        barChart.setPrefSize(1500, 650);

        HBox hboxBarChart = new HBox(10);
        hboxBarChart.getChildren().add(barChart);

        Pane painelBarChart = new BorderComTitulo("Gráfico BarChart", hboxBarChart);
        painelBarChart.getStyleClass().add("titled-address");
        gridGrafico.add(painelBarChart, 0, 1);
        grid.add(gridGrafico, 0, 2);
    }

    public BarChart graficoBarChart(GestorApp gestor) {
        Map<String, Integer> mapa = new HashMap<>();
        CompararValores comparador = new CompararValores(mapa);
        TreeMap<String, Integer> ordenados = new TreeMap<>(comparador);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Vértice");

        NumberAxis yAxis = new NumberAxis(0, 10, 1);
        yAxis.setLabel("Grau");

        BarChart<String, Number> barchart = new BarChart<>(xAxis, yAxis);
        barchart.setTitle("Graus dos Vértices ordenados decrescente");
        barchart.setBarGap(-30);
        barchart.setCategoryGap(0);

        //inserir apenas os graus dos respectivos vertices
        for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
            mapa.put(v.element().getCodigo_vertice(),
                    gestor.getDiWeightedGraph().calcularGrauDeVertice(v));
        }
        //adicionar todos valores de mapa no treemap
        ordenados.putAll(mapa);
        //faz o loop para adicionar os valores no grafico de barras
        for (Map.Entry<String, Integer> entry : ordenados.entrySet()) {
            XYChart.Series totalVertices = new XYChart.Series();
            totalVertices.setName(entry.getKey());
            totalVertices.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
            barchart.getData().addAll(totalVertices);
        }
        return barchart;
    }

    private ViewPainelPrincipal viewPainelPrincipal(BorderPane root, GestorApp gestor) {
        return new ViewPainelPrincipal(root, gestor);
    }

}
