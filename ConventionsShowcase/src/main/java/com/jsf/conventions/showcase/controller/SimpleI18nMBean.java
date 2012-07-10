/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.BaseMBean;
import com.jsf.conventions.event.LanguageChangeEvent;
import com.jsf.conventions.model.AbstractBaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano Oct 2, 2011 11:48:37 AM
 */
@Named
@ViewAccessScoped
public class SimpleI18nMBean extends BaseMBean<AbstractBaseEntity> implements Serializable{

    private String selectedLang;

    @PostConstruct
    public void initLanguage() {
        selectedLang = getResourceBundleProvider().getLanguage();
    }
    
    
    @Inject 
    private Event<LanguageChangeEvent> languageChangeEvent;
    
    public String getSimpleHello(){
        return getResourceBundle().getString("simpleHello");
    }
    
    public String getParametrizedHello(){
         List<Object> params = new ArrayList<Object>() {

                {
                    add("Conventions");
                }
            };
        return getResourceBundle().getString("parametrizedHello",params);
    }


    public String getSelectedLang() {
        return selectedLang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }

    public void changeLanguage(){
        if(selectedLang != null && !"".endsWith(selectedLang)){
            languageChangeEvent.fire(new LanguageChangeEvent(selectedLang));
//   OR     getResourceBundleProvider().setLanguage(selectedLang);
            
        }
    }
    
}
