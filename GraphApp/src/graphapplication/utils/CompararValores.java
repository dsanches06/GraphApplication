/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphapplication.utils;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author
 */
public class CompararValores implements Comparator<String> {

    private Map<String, Integer> base;

    public CompararValores(Map<String, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
