/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.dao;

/**
 *
 * @author
 */
public class MapaFactory {

    public static MapaDAO createFileDao(String type) {
        switch (type) {
            case "file":
                return new MapaDAOFile();
            case "json":
                return new MapaDAOOneJSON();
        }
        return null;
    }

}
