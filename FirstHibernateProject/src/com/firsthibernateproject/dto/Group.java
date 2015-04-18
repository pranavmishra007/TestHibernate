package com.firsthibernateproject.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPDETAILS")
public class Group
{
	@Id
	@GeneratedValue(strategy=(GenerationType.TABLE))
	private int groupId;
	
	private String groupName;
	
	@ManyToMany(mappedBy="groupList")
	private Collection<UserDetails> usersList = new ArrayList<UserDetails>();

	public Group()
	{
		super();
	}

	public Group(String groupName)
	{
		super();
		this.groupName = groupName;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public void setUsersList(Collection<UserDetails> usersList)
	{
		this.usersList = usersList;
	}

	public Collection<UserDetails> getUsersList()
	{
		return usersList;
	}

}
