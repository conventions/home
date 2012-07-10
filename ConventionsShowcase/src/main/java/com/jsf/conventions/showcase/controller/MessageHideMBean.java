/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.util.MessagesController;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Rafael M. Pestano Apr 3, 2011 8:27:59 PM
 */
@ManagedBean(name="messageHideMBean")
public class MessageHideMBean {
    
    
    public void addInfo(){
        MessagesController.addInfo("info message");
    }
    public void addWarn(){
        MessagesController.addWarn("warn message");
    }
    public void addError(){
        MessagesController.addError("error message");
              
    }
}
