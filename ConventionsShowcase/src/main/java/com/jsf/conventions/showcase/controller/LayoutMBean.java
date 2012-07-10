/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jsf.conventions.showcase.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.component.menuitem.MenuItem;

/**
 *
 * @author rmpestano Mar 19, 2012 at 8:15:33 PM
 */
@Named
@ViewAccessScoped
public class LayoutMBean implements Serializable{

      private List<MenuItem> tabs = null;  
  
    public List<MenuItem> getLayoutTabs() {  
        if (tabs == null) {  
            tabs = new ArrayList<MenuItem>();  
  
            MenuItem mi = new MenuItem();  
            mi.setUrl("/home.faces");  
            mi.setIcon("ui-icon-showcase");  
            mi.setValue("Showcase");  
            tabs.add(mi);  
  
            mi = new MenuItem();  
            mi.setUrl("/guide/userguide.faces");  
            mi.setIcon("ui-icon-doc");  
            mi.setValue("Documentation");  
            tabs.add(mi);  
  
        }  
  
        return tabs;  
    }  
    
}
