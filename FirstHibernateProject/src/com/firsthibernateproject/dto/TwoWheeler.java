package com.firsthibernateproject.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// Now Following @DiscriminatorValue will get saved in the Vehicle Table in VehicleType column.
@DiscriminatorValue(value = "Bike")
public class TwoWheeler extends Vehicle
{
	private String steeringHandle;

	public TwoWheeler()
	{
		super();
	}

	public TwoWheeler(String vehicleName, String steeringHandle)
	{
		super(vehicleName);
		this.steeringHandle = steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle)
	{
		this.steeringHandle = steeringHandle;
	}

	public String getSteeringHandle()
	{
		return steeringHandle;
	}

}
