/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.tads.Vertex;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.util.*;


/**
 *
 * @author
 */
public class Tabela extends TableView<Map> {

    public static final String ORIGEM = "Origem";
    public static final String DESTINO = "Destino";
    public static final String CUSTO = "Custo";
    public static final String CAMINHO = "Caminho";

    public Tabela(GestorApp gestor) {
        super();
        super.setPrefSize(500, 200);
        super.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        super.setEditable(true);
        criarColuna();
        super.setItems(generateDataInMap(gestor));
    }

    private void criarColuna() {
        TableColumn<Map, String> colunaOrigem = new TableColumn<>("Origem");
        TableColumn<Map, String> colunaDestinos = new TableColumn<>("Destino");
        TableColumn<Map, String> colunaCustos = new TableColumn<>("Custo Minimo");
        TableColumn<Map, String> colunaCaminhos = new TableColumn<>("Caminho");

        colunaOrigem.setCellValueFactory(new MapValueFactory(ORIGEM));
        colunaDestinos.setCellValueFactory(new MapValueFactory(DESTINO));
        colunaCustos.setCellValueFactory(new MapValueFactory(CUSTO));
        colunaCaminhos.setCellValueFactory(new MapValueFactory(CAMINHO));

        super.getColumns().addAll(colunaOrigem, colunaDestinos, colunaCustos, colunaCaminhos);
        Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = new Callback<TableColumn<Map, String>, TableCell<Map, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
            }
        };
        colunaOrigem.setCellFactory(cellFactoryForMap);
        colunaDestinos.setCellFactory(cellFactoryForMap);
        colunaCustos.setCellFactory(cellFactoryForMap);
        colunaCaminhos.setCellFactory(cellFactoryForMap);
    }

    private ObservableList<Map> generateDataInMap(GestorApp gestor) {
        ObservableList<Map> dados = FXCollections.observableArrayList();
        Vertex<Vertice> vOrigem = gestor.getDiWeightedGraph().getOrigem();
        Map<String, String> caminhos = gestor.getDialog().getCaminhos();
        Map<String, Integer> destinos = gestor.getDialog().getDestinos();
        for (Map.Entry<String, String> caminho : caminhos.entrySet()) {
            for (Map.Entry<String, Integer> destino : destinos.entrySet()) {
                if (caminho.getKey().equals(destino.getKey())) {
                    Map<String, String> dataRow = new HashMap<>();
                    dataRow.put(ORIGEM, vOrigem.element().getCodigo_vertice());
                    dataRow.put(DESTINO, destino.getKey());
                    dataRow.put(CUSTO, String.valueOf(destino.getValue()));
                    dataRow.put(CAMINHO, caminho.getValue());
                    dados.add(dataRow);
                }
            }
        }
        return dados;
    }
}
