package com.pandy.dao.impl;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.google.common.collect.Lists;
import com.pandy.dao.OrganizationalEntityDAO;
import com.pandy.model.User;
import org.jbpm.services.task.impl.model.GroupImpl;
import org.jbpm.services.task.impl.model.OrganizationalEntityImpl;
import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.model.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "organizationalEntityDAO")
public class OrganizationalEntityDAOImpl implements OrganizationalEntityDAO {
	@PersistenceContext
	private EntityManager entityManager;

	public void saveUser(User user) {
    	/*Query query = em.createQuery("from User");
    	List list = query.getResultList();
    	if(list.size() > 0) {
    		User u = (User) list.get(0);
    		System.out.println("=======" + u.getName());
    	}*/
    	entityManager.persist(user);
    }


    @Override
    public List<org.kie.api.task.model.User> findAllOrgEntUser() {
        List<org.kie.api.task.model.User> rtList = Lists.newArrayList();
        Query query = entityManager.createQuery("select o from OrganizationalEntityImpl o", OrganizationalEntityImpl.class);
        List<OrganizationalEntityImpl> list = query.getResultList();
        if(list!=null&&list.size()>0){
            for(Object obj :list){
                if(obj instanceof UserImpl){
                    rtList.add((UserImpl)obj);
                }
            }
        }
        return rtList;
    }

    @Override
    public List<Group> findAllOrgEntGroup() {
        List<org.kie.api.task.model.Group> rtList = Lists.newArrayList();
        Query query = entityManager.createQuery("select o from OrganizationalEntityImpl o", OrganizationalEntityImpl.class);
        List<OrganizationalEntityImpl> list = query.getResultList();
        if(list!=null&&list.size()>0){
            for(Object obj :list){
                if(obj instanceof GroupImpl){
                    rtList.add((GroupImpl)obj);
                }
            }
        }
        return rtList;
    }

    @Override
    public List<User> findAllUser() {
        List<User> rtList = Lists.newArrayList();
        Query query = entityManager.createQuery("select o from User o", User.class);
        rtList = query.getResultList();
        return rtList;
    }
}
