/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.exception.BusinessException;
import java.io.Serializable;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Rafael M. Pestano Apr 4, 2011 7:58:11 PM
 */
@Named("exceptionMBean")
@ViewAccessScoped
public class ExceptionMBean implements Serializable{
    
   
   public void doBusinessLogicException(){
       if(1 < 2 && (1+1 == 2)){
           throw new BusinessException("Business Logic exception...");
       }
   }
   
   public void doUncheckedException(){
       System.out.println(1/0);
   }
    
}
