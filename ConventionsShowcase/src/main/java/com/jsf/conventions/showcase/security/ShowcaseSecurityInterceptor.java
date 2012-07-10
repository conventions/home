/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.security;

import com.jsf.conventions.security.SecurityMethodInterceptor;
import com.jsf.conventions.qualifier.SecurityMethod;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptor;

/**
 *
 * @author rmpestano Nov 20, 2011 9:46:59 PM
 */

@SecurityMethod
@Interceptor
public class ShowcaseSecurityInterceptor extends SecurityMethodInterceptor{

    /**
     * this method is responsible for deciding if current user has permission 
     * to execute a method
     * 
     * @param rolesAllowed roles passed in the method
     * @return true if user has permission, false otherwise
     */
    @Override
    public boolean checkUserPermissions(String[] rolesAllowed) {
        //user role(s) should be extracted from current logged user
        //we just put the role in the session for testing purposes
        List<String> userRoles = (List<String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userRoles");
        if(userRoles == null || userRoles.isEmpty()){
            return false;
        }
        for (String role : rolesAllowed) {
            if(userRoles.contains(role)){
                return true;
            }
        }
        return false;
    }
    
}
