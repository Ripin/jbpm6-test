package net.hjfax.service.impl;

import javax.persistence.EntityManagerFactory;

import net.hjfax.dao.UserDAO;
import net.hjfax.model.User;
import net.hjfax.service.UserService;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

/**
 * Created with IntelliJ IDEA.
 * User: yanggengxiang
 * Date: 3/24/14
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
//@Transactional
//@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
	
	private StatefulKnowledgeSession ksession;
	
	private KieBase kbase;
	
	private EntityManagerFactory entityManagerFactory;

    public void setKbase(KieBase kbase) {
		this.kbase = kbase;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
        if(ksession == null) {
        	System.out.println("=========");
        } else {
        	System.out.println("+++++++++++++");
        	ksession.startProcess("com.sample.bpmn.hello");
        }
//        throw new RuntimeException();
    }
    
    /*private void sprocess() {
    	System.out.println("++++++++++++");
    	RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder()
    			.entityManagerFactory(entityManagerFactory).knowledgeBase(kbase);
    	RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(builder.get(), "com.sample:example:1.0");
    	RuntimeEngine engine = manager.getRuntimeEngine(null);
    	KieSession ks = engine.getKieSession();
    	ks.startProcess("com.sample.bpmn.hello");
    }*/
}
