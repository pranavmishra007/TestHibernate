package com.firsthibernateproject.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name
{
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	public Name()
	{
		super();
	}

	public Name(String firstName, String lastName)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	@Override
	public String toString()
	{ 
		return this.getFirstName() + " " + this.getLastName();
	}
}
