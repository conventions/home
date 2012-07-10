/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.bean.StateMBean;
import com.jsf.conventions.bean.modal.ModalObserver;
import com.jsf.conventions.bean.state.State;
import com.jsf.conventions.event.ModalCallback;
import com.jsf.conventions.qualifier.BeanState;
import com.jsf.conventions.qualifier.BeanStates;
import com.jsf.conventions.qualifier.PersistentClass;
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
 * @author rmpestano
 */

@ViewAccessScoped
@Named
@PersistentClass(Person.class)
@BeanStates({
    //if this managed bean is in "find state", a breadCrumb link with title "Search Person" will be generated in the page(if stateItens component is present),
    //also when you click the link it will execute the callback "#{ajaxStateMBean.setFindState}" and will update ":historyForm:pageControl"
    @BeanState(beanState=ConstantUtils.State.FIND_STATE,title="Search Person", callback="#{ajaxStateMBean.setFindState}",update=":historyForm:pageControl"),
    @BeanState(beanState=ConstantUtils.State.INSERT_STATE,title="Create Person", callback="#{ajaxStateMBean.setInsertState}",update=":historyForm:pageControl"),
    @BeanState(beanState=ConstantUtils.State.UPDATE_STATE,title="Edit Person",callback="#{ajaxStateMBean.setUpdateState}",update=":historyForm:pageControl"),
    @BeanState(beanState=ConstantUtils.State.FRIEND_STATE,title="Manage Friends",callback="#{ajaxStateMBean.setFriendState}",update=":historyForm:pageControl"),
    @BeanState(beanState="init",title="Ajax StateMBean",callback="#{ajaxStateMBean.setInitState}",update=":historyForm:pageControl")
})
public class AjaxStateMBean extends StateMBean<Person> implements ModalObserver{
    
    
    @Inject
    public void setService(PersonService service){
        super.setBaseService(service);
    }
    
    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }
    
    public boolean isFriendState() {
        return ShowcaseState.isFriendState(getBeanState());
    }
    public boolean isInitState() {
        return HistoryState.INIT.equals(getBeanState());
    }
    
    public void setFriendState(){
        setBeanState(ShowcaseState.FRIEND);
    }
    
    public void setInitState(){
        setBeanState(HistoryState.INIT);
    }

    public String go() {
        setBeanState(HistoryState.INIT);
        return Pages.AjaxHistory.HOME + ConstantUtils.FACES_REDIRECT;
    }
    
    public void goList(){
        setFindState();
    }
    
    public void goFriend(){
        setBeanState(ShowcaseState.FRIEND);
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

    /**
 * Custom state for ajax history stack
 * @author rmpestano
 */
   enum HistoryState implements State {

    INIT("init");
    
    private final String stateName;

    HistoryState(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getStateName() {
        return this.stateName;
    }

}

    
}
