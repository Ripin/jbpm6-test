package com.pandy.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id;
    @Basic
    private String userJbpmId;
    @Basic
    private String userJbpmGroupId;
	@Basic
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserJbpmId() {
        return userJbpmId;
    }

    public void setUserJbpmId(String userJbpmId) {
        this.userJbpmId = userJbpmId;
    }

    public String getUserJbpmGroupId() {
        return userJbpmGroupId;
    }

    public void setUserJbpmGroupId(String userJbpmGroupId) {
        this.userJbpmGroupId = userJbpmGroupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
