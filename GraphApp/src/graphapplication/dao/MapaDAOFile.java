/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.dao;

import graphapplication.model.Aresta;
import graphapplication.model.GestorApp;
import graphapplication.model.Vertice;
import graphapplication.singleton.SingletonLogger;
import graphapplication.tads.Edge;
import graphapplication.tads.Vertex;
import graphapplication.utils.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author
 */
public class MapaDAOFile implements MapaDAO {

    private SingletonLogger instance = SingletonLogger.getInstance();

    public MapaDAOFile() {
    }

    @Override
    public void save(GestorApp gestor, File file) {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            int totalVertices = gestor.getDiWeightedGraph().numVertices();
            printWriter.println(totalVertices);

            for (Vertex<Vertice> v : gestor.getDiWeightedGraph().vertices()) {
                int grau = gestor.getDiWeightedGraph().calcularGrauDeVertice(v);
                //se grau for zero
                if (grau == 0) {
                    //o vertice é isolado
                    printWriter.println(v.element().getCodigo_vertice().concat("_ISOLADO"));
                } else {
                    printWriter.println(v.element().getCodigo_vertice());
                }
            }

            int totalArestas = gestor.getDiWeightedGraph().numEdges();
            printWriter.println(totalArestas);

            for (Edge<Aresta, Vertice> e : gestor.getDiWeightedGraph().edges()) {
                printWriter.println(e.element().getCodigo_vertice_1() + "," + e.element().getCodigo_vertice_2()
                        + "," + e.element().getCodigo_aresta() + "," + e.element().getValor_aresta());
            }
            printWriter.flush();
            printWriter.close();
            instance.writeToLog("Foi salvado os dados no ficheiro " + file.getName());
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public void load(GestorApp gestor, File file) {
        int i = 0;
        int j = 0;
        try {
            Scanner sc = new Scanner(file);
            instance.writeToLog("Foi carregado o ficheiro " + file.getName());

            int totalVertices = Integer.parseInt(sc.nextLine().trim());

            while (sc.hasNextLine() && i < totalVertices) {
                String codigo_vertice = sc.nextLine().trim();
                //se o vertice for isolado
                if (codigo_vertice.endsWith("_ISOLADO")) {
                    //obter o primeiro digito do codigo
                    codigo_vertice = codigo_vertice.substring(0, 1);
                }
                Vertice v = new Vertice(codigo_vertice);
                if (Utils.existeVertice(gestor, v) != true) {
                    gestor.getDiWeightedGraph().insertVertex(v);
                    i++;
                }
            }
            if (i == totalVertices) {
                int totalArestas = Integer.parseInt(sc.nextLine().trim());
                while (sc.hasNextLine() && j < totalArestas) {
                    String[] campos = sc.nextLine().split(",");
                    Vertex<Vertice> v1 = Utils.getVertex(gestor, campos[0].trim());
                    Vertex<Vertice> v2 = Utils.getVertex(gestor, campos[1].trim());
                    String codigo_aresta = campos[2].trim();
                    int custo = Integer.parseInt(campos[3].trim());
                    Aresta aresta = new Aresta(v1.element().getCodigo_vertice(),
                            v2.element().getCodigo_vertice(), codigo_aresta, custo);
                    if (Utils.existeAresta(gestor, aresta) != true) {
                        gestor.getDiWeightedGraph().insertEdge(v1, v2, aresta);
                        j++;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }
}
