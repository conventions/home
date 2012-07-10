/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.BaseMBean;
import com.jsf.conventions.bean.ModalMBean;
import com.jsf.conventions.bean.modal.ModalInitializable;
import com.jsf.conventions.event.ModalInitialization;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.service.PersonService;
import java.io.Serializable;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;
import org.primefaces.event.CloseEvent;

 /**
 * 
 * @author rmpestano
 */
@Named(value = "personSelectionModalMBean")
@WindowScoped
//@Service(value=PersonService.class)
public class PersonSelectionModalMBean extends ModalMBean<Person> implements Serializable, ModalInitializable {

    private Person[] selectedPeople;
    public static final String MODAL_NAME = "personSelectionModal"; 
    
    public PersonSelectionModalMBean() {
    }

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @Inject
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }

    public Person[] getSelectedPeople() {
        return selectedPeople;
    }

    public void setSelectedPeople(Person selectedPeople[]) {
        this.selectedPeople = selectedPeople;
    }


    @Override
    public Object modalCallback() {
       return selectedPeople;
    }

    public String getModalName() {
        return MODAL_NAME;
    }
     

    /**
     * this event is fired by initModal method
     * @see PersonSelectionMBean#initPersonSelectionModal()
     * @param modalInit 
     */
    @Override
    public void beforeOpen(@Observes ModalInitialization modalInit) {
        if(getModalName().equals(modalInit.getModal())){//make sure the parameter is for you 
           getPaginator().getFilter().put("age", modalInit.getParameters().get("age").toString());
           getPaginator().getFilter().put("ignoreId", modalInit.getParameters().get("ignoreId"));
         }
    }
  
    public void clearSelection(CloseEvent event){
        this.selectedPeople = null;
    }
    
    
}