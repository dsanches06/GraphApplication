/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import graphapplication.model.Aresta;
import graphapplication.model.GestorApp;
import graphapplication.model.Vertice;
import graphapplication.singleton.SingletonLogger;
import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import graphapplication.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class MapaDAOOneJSON implements MapaDAO {

    private SingletonLogger instance = SingletonLogger.getInstance();

    public MapaDAOOneJSON() {

    }

    private HashSet<Aresta> selectAll(File file) {
        if (file.exists()) {
            System.out.println("file existe: " + file);
        } else {
            System.out.println("file not exist: " + file);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
            Gson gson = new GsonBuilder().create();
            HashSet<Aresta> list = gson.fromJson(br,
                    new TypeToken<HashSet<Aresta>>() {
                    }.getType());
            return list;
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return new HashSet<>();
    }

    @Override
    public void save(GestorApp gestor, File file) {
        FileWriter writer;
        try {
            HashSet<Aresta> list = selectAll(file);
            for (Edge<Aresta, Vertice> edge : gestor.getDiWeightedGraph().edges()) {
                Aresta aresta = ((Aresta) edge.element());
                if (Utils.containsAresta(list, aresta) != true) {
                    list.add(aresta);
                }
            }
            writer = new FileWriter(file);
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
            instance.writeToLog("Foi salvado os dados no ficheiro " + file.getName());
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void load(GestorApp gestor, File file) {
        HashSet<Aresta> list = selectAll(file);
        if (list != null) {
            instance.writeToLog("Foi carregado o ficheiro " + file.getName());
            for (Aresta aresta : list) {
                //obter os vertices
                Vertex<Vertice> v1 = Utils.getVertex(gestor, aresta.getCodigo_vertice_1());
                Vertex<Vertice> v2 = Utils.getVertex(gestor, aresta.getCodigo_vertice_2());

                if (Utils.existeVertice(gestor, v1.element()) != true) {
                    gestor.getDiWeightedGraph().insertVertex(v1.element());
                }

                if (Utils.existeVertice(gestor, v2.element()) != true) {
                    gestor.getDiWeightedGraph().insertVertex(v2.element());
                }

                if (Utils.existeAresta(gestor, aresta) != true) {
                    gestor.getDiWeightedGraph().insertEdge(v1, v2, aresta);
                }
            }
        }
    }
}
