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
public enum BeanTypes {
    
    BASE_MANAGED_BEAN("AbstractBaseMBean"),
    HISTORY_STACK_MANAGED_BEAN("AbstractHistoryStackMBean"),
    MODAL_MANAGED_BEAN("AbstractModalMBean"),
    CONVERSATIONAL_MANAGED_BEAN("AbstractConversationalMBean");
    
    BeanTypes(String type){
        this.name = type;
    }
    private final String name;

    public String getName() {
        return name;
    }
    
    
    public static List<String> getTypes(){
       List<String>retorno = new ArrayList<String>();
        for (BeanTypes type : BeanTypes.values()) {
            retorno.add(type.name);
        }
        return retorno;
    }

    @Override
    public String toString() {
        return  name ;
    }
    
    
    
}
