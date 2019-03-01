/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.model;

import graphapplication.singleton.SingletonLogger;
import java.io.*;
import java.util.*;


/**
 *
 * @author
 */
public class Vertice implements Serializable {

    private String codigo_vertice;
    private SingletonLogger instance = SingletonLogger.getInstance();

    public Vertice(String codigo_vertice) {
        this.codigo_vertice = codigo_vertice;
        instance.writeToLog("Foi criado um novo vertice: " + getMensagem());
    }

    public String getCodigo_vertice() {
        return codigo_vertice;
    }

    public void setCodigo_vertice(String codigo_vertice) {
        this.codigo_vertice = codigo_vertice;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.codigo_vertice);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertice other = (Vertice) obj;
        if (!Objects.equals(this.codigo_vertice, other.codigo_vertice)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + codigo_vertice + "}";
    }

    private String getMensagem() {
        return toString();
    }

}
