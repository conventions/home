/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.model;

import com.jsf.conventions.bean.state.State;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 *
 * @author rmpestano Jul 4, 2011 12:10:45 AM
 */
public enum ShowcaseState  implements State{
    FRIEND("friend");

    private final String stateName;
    
    ShowcaseState(String stateName){
        this.stateName = stateName;
    }

    @Override
    public String getStateName() {
        return this.stateName;
    }

    public static boolean isFriendState(State state){
        return FRIEND.equals(state);
    } 

    @Produces
    @Named
    public State friendState(){
        return ShowcaseState.FRIEND;
    }
}
