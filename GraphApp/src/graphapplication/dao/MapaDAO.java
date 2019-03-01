/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.dao;

import graphapplication.model.GestorApp;
import java.io.*;


/**
 *
 */
public interface MapaDAO {

    public void load(GestorApp gestor, File file);

    public void save(GestorApp gestor, File file);

}
