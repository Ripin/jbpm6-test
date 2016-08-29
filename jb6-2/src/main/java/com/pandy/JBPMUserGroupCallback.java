package com.pandy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pandy.dao.OrganizationalEntityDAO;
import org.kie.api.task.UserGroupCallback;
import org.kie.api.task.model.Group;
import org.kie.api.task.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JBPMUserGroupCallback implements UserGroupCallback {

    private Map<User, List<Group>> userGroupMapping = null;
    List<com.pandy.model.User> userList = Lists.newArrayList();
    List<User> jbpmUserList = Lists.newArrayList();
    List<Group> jbpmGroupList = Lists.newArrayList();
    private OrganizationalEntityDAO organizationalEntityDAO;

    public JBPMUserGroupCallback() {
        /*userGroupMapping = Maps.newHashMap();
        List<Group> list = Lists.newArrayList();
        list.add(new GroupImpl("Test"));
        User user = new UserImpl("pandy");
        userGroupMapping.put(user,list);*/
    }

    public synchronized void init() {
        //不清楚为什么这个organizationalEntityDAO定义在类变量里面是null，难道是JPA的注解是在Spring之后？
        //所以放这里，等启动完之后再查询，才不报空异常。
        if (userGroupMapping == null) {
            userGroupMapping = Maps.newHashMap();
            //读取JBPM6里面对组和用户的定义
            jbpmUserList = organizationalEntityDAO.findAllOrgEntUser();
            jbpmGroupList = organizationalEntityDAO.findAllOrgEntGroup();
            //读取实际业务里面的用户信息
            userList = organizationalEntityDAO.findAllUser();

            //把实际用户的信息,按照JBPM定义的用户,组,建立起实际的业务关系
            if (userList != null && userList.size() > 0) {
                for (com.pandy.model.User u : userList) {
                    User mapUser = null;
                    List<Group> mapGroupList = Lists.newArrayList();
                    for (User jbpmUser : jbpmUserList) {
                        if (jbpmUser.getId().equalsIgnoreCase(u.getUserJbpmId()))
                            mapUser = jbpmUser;

                    }
                    for (Group jbpmGroup : jbpmGroupList) {
                        if (jbpmGroup.getId().equalsIgnoreCase(u.getUserJbpmGroupId()))
                            mapGroupList.add(jbpmGroup);

                    }
                    if (mapUser != null)
                        userGroupMapping.put(mapUser, mapGroupList);
                }
            }
        }
    }

    @Override
    public boolean existsUser(String userId) {
        init();
        for (User user : jbpmUserList) {
            if (user.getId().equalsIgnoreCase(userId)) {
                return true;
            }
        }
        System.out.println("=======================================NOT existsUser");
        return false;
    }

    @Override
    public boolean existsGroup(String groupId) {
        init();
        for (Group group : jbpmGroupList) {
            if (group.getId().equalsIgnoreCase(groupId)) {
                return true;
            }
        }
        System.out.println("=======================================NOT existsGroup");
        return false;
    }

    public List<String> getGroupsForUser(String userId, List<String> groupIds) {
        return getGroupsForUser(userId);
    }

    public List<String> getGroupsForUser(String userId, List<String> groupIds,
                                         List<String> allExistingGroupIds) {
        return getGroupsForUser(userId);
    }

    public List<String> getGroupsForUser(String userId) {
        init();
        Iterator<User> iter = userGroupMapping.keySet().iterator();
        while (iter.hasNext()) {
            User u = iter.next();
            if (u.getId().equalsIgnoreCase(userId)) {
                List<String> groupList = new ArrayList<String>();
                List<Group> userGroupList = userGroupMapping.get(u);
                for (Group g : userGroupList) {
                    groupList.add(g.getId());
                }
                if (groupList != null && groupList.size() > 0) {
                    return groupList;
                }

            }
        }
        System.out.println("=======================================NOT getGroupsForUser3");
        return null;
    }


    public OrganizationalEntityDAO getOrganizationalEntityDAO() {
        return organizationalEntityDAO;
    }

    public void setOrganizationalEntityDAO(OrganizationalEntityDAO organizationalEntityDAO) {
        this.organizationalEntityDAO = organizationalEntityDAO;
    }
}
