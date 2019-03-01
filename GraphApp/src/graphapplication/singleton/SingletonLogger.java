/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.singleton;

import java.io.*;
import java.util.*;

/**
 *
 * @author
 */
public final class SingletonLogger {

    private static final String LOGGERFILE = "logger.txt";
    private PrintStream printStreamFile;
    private static SingletonLogger instance = new SingletonLogger();
    private final StringBuilder mensagens;

    private SingletonLogger() {
        connect();
        mensagens = new StringBuilder(20480);
    }

    public static SingletonLogger getInstance() {
        return instance;
    }

    private boolean connect() {
        if (printStreamFile == null) {
            try {
                printStreamFile = new PrintStream(new FileOutputStream(LOGGERFILE), true);
            } catch (FileNotFoundException ex) {
                printStreamFile = null;
                return false;
            }
            return true;
        }
        return true;
    }

    public void writeToLog(String str) throws LoggerException {
        if (printStreamFile == null) {
            throw new LoggerException("Connection fail");
        }//para ficheiro logger
        printStreamFile.println(new Date().toString() + "  " + str);
        addMensagem(str);

    }

    private void addMensagem(String mensagem) {
        if (mensagens.length() > 0) {
            mensagens.append('\n');
        }//append nova mensagem
        mensagens.append(mensagem);
    }

    public String getMensagens() {
        return mensagens.toString();
    }

}
