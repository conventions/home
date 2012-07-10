/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.converter;

import com.jsf.conventions.converter.AbstractBaseConverter;
import com.jsf.conventions.showcase.service.PersonService;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.myfaces.extensions.cdi.core.api.Advanced;

/**
 *
 * @author rmpestano Dec 25, 2011 12:32:50 PM
 */
@Advanced
@FacesConverter(value="personConverter")
public class PersonConverter extends AbstractBaseConverter {
    
    @Inject 
    public void setPersonService(PersonService personService){
        super.setBaseService(personService);
    }
	
}
