/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication;

import graphapplication.memento.CareTaker;
import graphapplication.model.GestorApp;
import graphapplication.views.ViewPainelPrincipal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Danilson
 */
public class GraphApp extends Application {
    
     public static Stage stageApp;

    @Override
    public void start(Stage ignored) {

        CareTaker caretaker = new CareTaker();
        GestorApp gestor = new GestorApp(caretaker);
        BorderPane root = new BorderPane();
        ViewPainelPrincipal painel = new ViewPainelPrincipal(root, gestor);
        painel.iniciar();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("/pt/ips/pa/projeto/varianteb/styles/style.css").toExternalForm());

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Aplicação Didática sobre Grafos - Época Recurso – Variante B");
        stage.getIcons().add(new Image("/pt/ips/pa/projeto/varianteb/styles/favicon.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        //para voltar a pagina inicial
        stageApp = stage;
    }

    /**
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
