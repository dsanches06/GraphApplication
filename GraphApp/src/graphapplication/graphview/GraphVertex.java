/* 
 * The MIT License
 *
 * Copyright 2018 brunomnsilva@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package graphapplication.graphview;

import graphapplication.model.GestorApp;
import graphapplication.model.Vertice;
import graphapplication.tads.Vertex;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;


/**
 * A draggable graph vertex representation in the form of a circle.
 *
 * @author brunomnsilva
 */
public class GraphVertex extends Circle {

    private final GestorApp gestor;
    private final Vertex<Vertice> vertex;

    /**
     * GraphVertex constructor that receives initial values for the circle.
     *
     * @param x
     * @param y
     * @param radius
     * @param gestor
     * @param vertex
     */
    public GraphVertex(double x, double y, double radius, GestorApp gestor, Vertex<Vertice> vertex) {
        super(x, y, radius);
        this.gestor = gestor;
        this.vertex = vertex;
        enableDrag();
    }

    // make a node movable by dragging it around with the mouse.
    private void enableDrag() {
        final Delta dragDelta = new Delta();
        setOnMousePressed((MouseEvent mouseEvent) -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = getCenterX() - mouseEvent.getX();
            dragDelta.y = getCenterY() - mouseEvent.getY();
            getScene().setCursor(Cursor.MOVE);
        });
        setOnMouseReleased((MouseEvent mouseEvent) -> {
            getScene().setCursor(Cursor.HAND);
        });
        setOnMouseDragged((MouseEvent mouseEvent) -> {
            double newX = mouseEvent.getX() + dragDelta.x;
            if (newX > 0 && newX < getScene().getWidth()) {
                setCenterX(newX);
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < getScene().getHeight()) {
                setCenterY(newY);
            }
        });
        setOnMouseEntered((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });

        setOnMouseClicked((MouseEvent mouseEvent) -> {
            gestor.setVerticeSelecionado(vertex);
        });

    }

    // records relative x and y co-ordinates.
    private class Delta {

        double x, y;
    }
}
