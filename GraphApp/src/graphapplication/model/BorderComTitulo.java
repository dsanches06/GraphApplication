/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author Danilson
 */
public class BorderComTitulo extends StackPane {

    private final String titulo;
    private final Node node;

    public BorderComTitulo(String titulo, Node node) {
        this.titulo = titulo;
        this.node = node;
        mostrarBorder();
    }

    private void mostrarBorder() {
        Label title = new Label("  " + titulo + "  ");
        title.getStyleClass().add("bordered-titled-title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        StackPane contentPane = new StackPane();
        node.getStyleClass().add("bordered-titled-content");
        contentPane.getChildren().add(node);
        contentPane.setAlignment(Pos.CENTER);

        this.getStyleClass().add("bordered-titled-border");
        this.getChildren().addAll(title, contentPane);
    }

}
