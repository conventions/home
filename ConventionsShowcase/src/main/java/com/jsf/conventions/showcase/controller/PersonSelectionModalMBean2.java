/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.ModalMBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.service.PersonService;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;

 /**
 * 
 * @author rmpestano
 */
@WindowScoped
@Named(value = "personSelectionModalMBean2")
public class PersonSelectionModalMBean2 extends ModalMBean<Person> implements Serializable {


    private Person[] selectedPeople;
    private List<Person> list;
    public static final String MODAL_NAME = "personSelectionModal2";
    
    public PersonSelectionModalMBean2() {
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
    
    @PostConstruct
    @Override
    public void init(){
        Person p = new Person();
        p.setAge(20);
        list = getPersonService().findByExample(p);
    }


    public Person[] getSelectedPeople() {
        return selectedPeople;
    }

    public void setSelectedPeople(Person selectedPeople[]) {
        this.selectedPeople = selectedPeople;
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }

    @Override
    public Object modalCallback(){
        return selectedPeople;
    }

}