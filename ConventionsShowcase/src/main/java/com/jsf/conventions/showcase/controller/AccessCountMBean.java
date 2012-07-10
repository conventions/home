/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author rmpestano Dec 6, 2011 7:19:24 AM
 */

@ApplicationScoped
@Named(value="accessCountMBean")
public class AccessCountMBean {
    
    private AtomicLong accessCount;

    public AccessCountMBean() {
        accessCount = new AtomicLong();
    }

    
    public AtomicLong getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(AtomicLong accessCount) {
        this.accessCount = accessCount;
    }

    
    protected void increment(){
        accessCount.set(accessCount.incrementAndGet());
    }
    
    
}
