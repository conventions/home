package com.jsf.conventions.showcase.service.impl;

import com.jsf.conventions.exception.BusinessException;
import com.jsf.conventions.model.WrappedData;
import com.jsf.conventions.service.impl.StatelessHibernateService;
import com.jsf.conventions.showcase.model.Person;
import com.jsf.conventions.showcase.service.StatelessPersonService;
import java.lang.Long;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.custom.ScalarReturn;
import org.hibernate.type.LongType;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
@Named(value="hibernateStatelessPersonService")
public class StatelessPersonServiceImpl extends StatelessHibernateService<Person, Long> implements
        StatelessPersonService {


    @Override
    public WrappedData<Person> configFindPaginated(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters, Map externalFilter) {
         DetachedCriteria dc = getDao().getDetachedCriteria();
        if(sortField == null || "".equals(sortField)){
            sortField = "name";
        }
        if (externalFilter != null && !externalFilter.isEmpty()) {
            String name = (String) externalFilter.get("name");
            if (name != null) {
                dc.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
            }
            String lastname = (String) externalFilter.get("lastname");
            if (lastname != null) {
                dc.add(Restrictions.ilike("lastname", lastname, MatchMode.ANYWHERE));
            }
            String age = (String) externalFilter.get("age");
            if(age != null && !StringUtils.isBlank(age)){
                dc.add(Restrictions.eq("age", new Integer(age)));
            }
            Long ignoreId = (Long) externalFilter.get("ignoreId");
            if(ignoreId != null){
                dc.add(Restrictions.ne("id", ignoreId));
            }
        }
        /* config prime datatable filter columns */
        if (filters != null && !filters.isEmpty()) {
            if (filters.get("name") != null) {
                dc.add(Restrictions.ilike("name", (String) filters.get("name"), MatchMode.ANYWHERE));
            }
            if (filters.get("lastname") != null) {
                dc.add(Restrictions.ilike("lastname", (String) filters.get("lastname"), MatchMode.ANYWHERE));
            }

            if (filters.get("age") != null) {
                dc.add(Restrictions.eq("age", new Integer((String) filters.get("age"))));
            }
        }
        return getDao().findPaginated(first, pageSize, sortField, sortOrder, dc);
    }

    @Override
    public boolean alowDeletePerson(Person p) {
        if (p.getAge() > 60) {
            return false;
        }
        return true;
    }
    
    @Override
    public void remove(Person entity) {
        if(this.alowDeletePerson(entity)){
            super.remove(entity);
        }
        else{
            throw new BusinessException("Not allowed to remove person above 60 year old.");
        }
    }
    

    @Override
    public List<Person> findFriends(final Long personID) {
       String query = "select p.friends_id from person_person p where p.person_id = :id";
       Map params = new HashMap(){{put("id",personID);}};
       List<Long> friendsId = super.dao.findByNativeQuery(query, params,null,null,new ScalarReturn(LongType.INSTANCE, "friends_id")); 
       if(friendsId != null && !friendsId.isEmpty()){
           DetachedCriteria dc = getDetachedCriteria();
           dc.add(Restrictions.in("id", friendsId));
           return super.dao.findByCriteria(dc);
       }

       return null;
    }
     
}
