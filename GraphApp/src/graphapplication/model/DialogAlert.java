/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

/**
 *
 * @author
 */
public class DialogAlert {
    public static void mostrarInf(AlertType tipo, String titulo, String mensagem) {
        Alert dialogo = new Alert(tipo);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensagem);
        dialogo.showAndWait();
    }
}
