
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.BaseMBean;
import com.jsf.conventions.bean.modal.ModalObserver;
import com.jsf.conventions.bean.state.CrudState;
import com.jsf.conventions.event.ModalCallback;
import com.jsf.conventions.qualifier.PropertyFile;
import com.jsf.conventions.qualifier.Service;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.model.ShowcaseState;
import com.jsf.conventions.showcase.service.PersonService;
import com.jsf.conventions.showcase.util.ConstantUtils;
import com.jsf.conventions.util.MessagesController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@ViewAccessScoped
@Named("personMBean")
@Service(name="personService")
public class PersonMBean extends BaseMBean<Person> implements Serializable,ModalObserver {
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    
  

    

  /**
     * this method is REQUIRED (or use the @Service annotation) to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
//    @Inject
//    public void setPersonService(PersonService personService) {
//        super.setBaseService(personService);
//    }
    

    
    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }

    @Override
    public Person create() {
        Person p = new Person();
        /* create person age starts with 25 by default */
        p.setAge(25);
        return p;
    }

    /**
     * called by saveButton
     */
    @Override
    public void store() {
        super.store();
    }
 
    public boolean isFriendState() {
        return ShowcaseState.FRIEND.equals(getBeanState());
    }


    /**
     * called after 'addButton' is clicked  
     * to decide the navigation
     */
    @Override
    public String afterPrepareInsert() {
        return null;
    }

    /**
     * called after 'editButton' is clicked  
     * to decide the navigation
     */
    @Override
    public String afterPrepareUpdate() {
        return null;
    }

    

    /**
     * called when 'filterButton' is clicked
     */
    @Override
    public void find() {
    }

    public void associateFriends() {
        setBeanState(ShowcaseState.FRIEND);
    }

    /**
     * ModalCallback event is fired by modal popup
     * and is observed by ModalObserver ManagedBeans
     * to retrieve data from popup(acts like lov pattern)
     * @param callback 
     */
    @Override
    public void modalResponse(@Observes(receive= Reception.IF_EXISTS) ModalCallback callback) {
        /**
         * invokerName is used for identifying purposes as ModalCallback event
         * can be observed by various managed beans.
         * also receive= Reception.IF_EXISTS can do this job
         */
        if(callback.getInvokerName() != null && callback.getInvokerName().equals(ConstantUtils.Invoker.PERSON_BEAN)){
            if (getEntity().getFriends() == null) {
                getEntity().setFriends(new ArrayList<Person>());
            }
            Person[] selectedPerson = (Person[]) callback.getResult();
            for (Person person : selectedPerson) {
                if (!getEntity().hasFriend(person.getId())) {
                    getEntity().getFriends().add(getPersonService().load(person.getId()));
                }
            }
        }
    }

    public void initPersonSelectionModal() {
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());
        super.initModal(PersonSelectionModalMBean.MODAL_NAME, parameters);
    }

    public void backEdit() {
        setBeanState(CrudState.UPDATE);
    }
   
    public void back(){
         setBeanState(CrudState.FIND);
    }
   
    @Override
    public void removeFromList() {
        if (getEntity().getFriends() == null) {
            return;
        }
        if (getEntity().hasFriend(getEntityAux().getId())) {
            getEntity().removeFriend(getEntityAux().getId());
            MessagesController.addInfo("Friend removed from list");
        }
    }
}

 