/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.StateMBean;
import com.jsf.conventions.bean.modal.ModalObserver;
import com.jsf.conventions.event.ModalCallback;
import com.jsf.conventions.exception.BusinessException;
import com.jsf.conventions.bean.state.CrudState;
import com.jsf.conventions.qualifier.BeanState;
import com.jsf.conventions.qualifier.BeanStates;
import com.jsf.conventions.qualifier.PersistentClass;
import java.io.Serializable;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.model.ShowcaseState;
import com.jsf.conventions.showcase.service.PersonService;
import com.jsf.conventions.showcase.util.ConstantUtils;
import com.jsf.conventions.showcase.util.Pages;
import com.jsf.conventions.util.MessagesController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
@Named("trackablePersonMBean")
@BeanStates({
    //as some people asked me, translating:
    //if this managed bean is in "find state", a breadCrumb link with title "Search ManagedBean1" will be generated in the page(if historyStackLinks component is present),
    //also when you click the link it will execute an action with return = "Pages.History.LIST_PAGE+ConstantUtils.FACES_REDIRECT"
    @BeanState(beanState=ConstantUtils.State.FIND_STATE,page=Pages.History.LIST_PAGE+ConstantUtils.FACES_REDIRECT,title="Search ManagedBean1"),
    @BeanState(beanState=ConstantUtils.State.INSERT_STATE,page=Pages.History.EDIT_PAGE+ConstantUtils.FACES_REDIRECT,title="Create ManagedBean1"),
    @BeanState(beanState=ConstantUtils.State.UPDATE_STATE,page=Pages.History.EDIT_PAGE+ConstantUtils.FACES_REDIRECT,title="Edit ManagedBean1"),
    @BeanState(beanState=ConstantUtils.State.FRIEND_STATE,page=Pages.History.FRIEND_PAGE+ConstantUtils.FACES_REDIRECT,title="Friends of ManagedBean1")
})
@PersistentClass(Person.class)
public class TrackablePersonMBean extends StateMBean<Person> implements Serializable, ModalObserver {

   

    public TrackablePersonMBean() {
    }

    /**
     * this method is REQUIRED (or use the @Service annotation) to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @Inject
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService() {
        return (PersonService) super.getBaseService();
    }

    @Override
    public void store() {
        super.store();
    }

  
    public boolean isFriendState() {
        return ShowcaseState.isFriendState(getBeanState());
    }


    /**
     * this method is called after 'newButton' is clicked  
     * you dont need to overhide this method
     */
    @Override
    public String afterPrepareInsert() {
        return Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @Override
    public String afterPrepareUpdate() {
        return Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT;
    }


    public String associateFriends() {
        setBeanState(ShowcaseState.FRIEND);
        return Pages.History.FRIEND_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @Override
     public void modalResponse(@Observes(receive= Reception.IF_EXISTS) ModalCallback callback) {
        
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

   public void initPersonSelectionModal() {
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());
        super.initModal(PersonSelectionModalMBean.MODAL_NAME, parameters);
    }



    public String go() {
        setBeanState(CrudState.FIND);
        return Pages.History.LIST_PAGE + ConstantUtils.FACES_REDIRECT;
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
