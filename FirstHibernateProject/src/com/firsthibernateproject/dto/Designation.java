package com.firsthibernateproject.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Designation
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int designationCode;

	private String designationName;

	public Designation()
	{
		super();
	}

	public Designation(String designationName)
	{
		super();
		this.designationName = designationName;
	}

	public int getDesignationCode()
	{
		return designationCode;
	}

	public void setDesignationCode(int designationCode)
	{
		this.designationCode = designationCode;
	}

	public String getDesignationName()
	{
		return designationName;
	}

	public void setDesignationName(String designationName)
	{
		this.designationName = designationName;
	}

}
