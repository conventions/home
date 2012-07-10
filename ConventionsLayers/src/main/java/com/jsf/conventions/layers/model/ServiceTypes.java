/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.layers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author rmpestano Oct 28, 2011 10:47:55 PM
 */
public enum ServiceTypes {
    
    STATELESS("Stateless"),
    STATEFUL("Stateful");
    
     ServiceTypes(String type){
        this.name = type;
    }
    private final String name;

    public String getName() {
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static List<String> getTypes(){
       List<String>retorno = new ArrayList<String>();
        retorno.add(STATELESS.name);
        retorno.add(STATEFUL.name);
        return retorno;
    }

}
