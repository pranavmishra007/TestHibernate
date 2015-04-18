package com.firsthibernateproject.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Organisation
{
	@Id
	@GeneratedValue(strategy = (GenerationType.TABLE))
	private int organisationId;

	private String organisationName;

	public Organisation()
	{
		super();
	}

	public Organisation(String organisationName)
	{
		super();
		this.organisationName = organisationName;
	}

	public int getOrganisationId()
	{
		return organisationId;
	}

	public void setOrganisationId(int organisationId)
	{
		this.organisationId = organisationId;
	}

	public String getOrganisationName()
	{
		return organisationName;
	}

	public void setOrganisationName(String organisationName)
	{
		this.organisationName = organisationName;
	}

}
