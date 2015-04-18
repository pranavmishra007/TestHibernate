package com.firsthibernateproject.dto;

import javax.persistence.Entity;

@Entity
public class FourWheeler extends Vehicle
{
	private String steeringWheel;
	
	public FourWheeler()
	{
		super();
	}

	public FourWheeler(String vehicleName,String steeringWheel)
	{
		super(vehicleName);
		this.steeringWheel = steeringWheel;	
	}

	public void setSteeringWheel(String steeringWheel)
	{
		this.steeringWheel = steeringWheel;
	}

	public String getSteeringWheel()
	{
		return steeringWheel;
	}

}
