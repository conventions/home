/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.provider;

import com.jsf.conventions.entitymanager.provider.CustomEntityManagerProvider;
import com.jsf.conventions.entitymanager.provider.EntityManagerProvider;
import com.jsf.conventions.entitymanager.provider.Type;
import com.jsf.conventions.qualifier.ConventionsEntityManager;
import com.jsf.conventions.showcase.controller.ComboMBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * entityManager provider for CustomHibernateService
 * @see ComboMBean#initList(com.jsf.conventions.service.BaseService) 
 * @author rmpestano
 */
@Specializes
@RequestScoped
@ConventionsEntityManager(type=Type.CUSTOM)
public class ShowcaseEntityManagerProvider extends CustomEntityManagerProvider implements EntityManagerProvider {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        return entityManager;
    }

     
}
