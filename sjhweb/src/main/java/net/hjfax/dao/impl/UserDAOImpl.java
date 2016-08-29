package net.hjfax.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.hjfax.dao.UserDAO;
import net.hjfax.model.User;

import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository(value = "userDAO")
public class UserDAOImpl implements UserDAO {
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
}
