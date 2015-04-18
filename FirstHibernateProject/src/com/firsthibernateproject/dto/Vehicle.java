package com.firsthibernateproject.dto;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
// To Define Inheritance strategy - single or multiple table
@Inheritance(strategy = (InheritanceType.SINGLE_TABLE))
// for Single table Inheritance the Discriminator Column name and type
// if we were using TABLE_PER_CLASS inheritance strategy we don't need Discriminator then.
@DiscriminatorColumn(name = "VEHICLE_TYPE", discriminatorType = DiscriminatorType.STRING)
public class Vehicle
{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int vehicleId;

	private String vehicleName;

	public Vehicle()
	{
		super();
	}

	public int getVehicleId()
	{
		return vehicleId;
	}

	public Vehicle(String vehicleName)
	{
		super();
		this.vehicleName = vehicleName;
	}

	public void setVehicleId(int vehicleId)
	{
		this.vehicleId = vehicleId;
	}

	public String getVehicleName()
	{
		return vehicleName;
	}

	public void setVehicleName(String vehicleName)
	{
		this.vehicleName = vehicleName;
	}

}
