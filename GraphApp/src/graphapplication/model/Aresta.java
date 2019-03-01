/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.singleton.SingletonLogger;
import java.io.*;


/**
 *
 * @author
 */
public class Aresta implements Weighted, Serializable {

    private final String codigo_vertice_1;
    private final String codigo_vertice_2;
    private String codigo_aresta;
    private final int valor_aresta;
    private SingletonLogger instance = SingletonLogger.getInstance();

    public Aresta(String codigo_vertice_1, String codigo_vertice_2, String codigo_aresta, int valor_aresta) {
        this.codigo_vertice_1 = codigo_vertice_1;
        this.codigo_vertice_2 = codigo_vertice_2;
        this.codigo_aresta = codigo_aresta;
        this.valor_aresta = valor_aresta;
        instance.writeToLog("Foi criado uma nova aresta: " + getMensagem());
    }

    public String getCodigo_aresta() {
        return codigo_aresta;
    }

    public void setCodigo_aresta(String codigo_aresta) {
        this.codigo_aresta = codigo_aresta;
    }

    @Override
    public int getValor_aresta() {
        return valor_aresta;
    }

    public String getCodigo_vertice_1() {
        return codigo_vertice_1;
    }

    public String getCodigo_vertice_2() {
        return codigo_vertice_2;
    }

    @Override
    public String toString() {
        return "{" + codigo_aresta + "," + valor_aresta + "}";
    }

    private String getMensagem() {
        return toString();
    }

}
