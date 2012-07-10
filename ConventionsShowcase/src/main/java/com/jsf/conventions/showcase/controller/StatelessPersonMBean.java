
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.BaseMBean;
import com.jsf.conventions.bean.modal.ModalObserver;
import com.jsf.conventions.event.ModalCallback;
import com.jsf.conventions.bean.state.CrudState;
import java.io.Serializable;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.model.ShowcaseState;
import com.jsf.conventions.showcase.service.CustomPersonService;
import com.jsf.conventions.showcase.service.StatelessPersonService;
import com.jsf.conventions.showcase.util.ConstantUtils;
import com.jsf.conventions.util.MessagesController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@Named("statelessPersonMBean")
public class StatelessPersonMBean extends BaseMBean<Person> implements Serializable, ModalObserver {

    private List<Person> friends;

  /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @Inject
    public void setStatelessPersonService(StatelessPersonService statelessPersonService) {
        super.setBaseService(statelessPersonService);
    }

    public StatelessPersonService getStatelessPersonService(){
        return (StatelessPersonService)super.getBaseService();
    }

    public List<Person> getFriends() {
        if(friends == null){
            friends = new ArrayList<Person>();
        }
        return friends;
    }
   
    public void addFriend(Person p){
        if(!getFriends().contains(p)){
            getFriends().add(p);
        }
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    
    @Override
    public Person create() {
        Person p = new Person();
        /* create person age starts with 25 by default */
        p.setAge(25);
        return p;
    }
    
    

    @Override
    public void store() {
        getEntity().setFriends(this.attachPersons(getFriends()));
        
        super.store();
    }

    /**
     * this method is called when 'removeButton' is clicked
     */
    @Override
    public void delete() {
        setEntityAux(getStatelessPersonService().get(getEntityAux().getId()));//attach person to session - again -
        super.delete();
    }

    public boolean isFriendState() {

        return ShowcaseState.FRIEND.equals(getBeanState());
    }


    /**
     * this method is called after 'newButton' is clicked  
     * you dont need to overhide this method
     */
    @Override
    public String afterPrepareInsert() {
        return null;
    }

    @Override
    public String afterPrepareUpdate() {
        return null;
    }

    public int getNumFriends(){
        if(friends != null){
            return friends.size();
        }
        return 0;
    }
    
    /**
     * called when 'filterButton' is clicked
     * you dont need to override this method
     */
    @Override
    public void find() {
    }

    public void associateFriends() {
        this.setFriends(getStatelessPersonService().findFriends(getEntity().getId()));
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
         * and has the advantage that the bean constructor is not called 
         */
        if(callback.getInvokerName() != null && callback.getInvokerName().equals(ConstantUtils.Invoker.PERSON_STATELESS_BEAN)){
            if (getEntity().getFriends() == null) {
                getEntity().setFriends(new ArrayList<Person>());
            }
            Person[] selectedPerson = (Person[]) callback.getResult();
            for (Person person : selectedPerson) {
            addFriend(person);
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
        if (this.getFriends() == null) {
            return;
        }
        if (this.getFriends().contains(getEntityAux())) {
            this.getFriends().remove(getEntityAux());
            MessagesController.addInfo("Friend removed from list");
        }
    }

    /**
     * attach detached Person to hibernate Session
     * @param friends
     * @return 
     */
    private List<Person> attachPersons(List<Person> friends) {
        List<Person> attachedPersons = new ArrayList<Person>();
        for (Person person : friends) {
            attachedPersons.add(getStatelessPersonService().load(person.getId()));
        }
        return attachedPersons;        
    }
}

 