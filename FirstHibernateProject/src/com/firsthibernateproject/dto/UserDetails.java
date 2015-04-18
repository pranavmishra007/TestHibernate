package com.firsthibernateproject.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * 
 * @author Pranav
 * 
 */
/*
 * The @entity and @table annotations differ in following way.
 * 
 * @Table is optional. @Entity is needed for annotating a POJO class as an entity, but the name
 * attribute is not mandatory.
 * 
 * If you have a class
 * 
 * @Entity class UserDetails {} A table with name "UserDetails" will be created and the Entity name
 * will be UserDetails.
 * 
 * Your JPQL query would be: select * from UserDetails In JPQL you always use the Entity name and by
 * default it is the class name.
 * 
 * if you have a class
 * 
 * @Entity(name="USERDETAILS")
 * 
 * @Table(name="USER_DETAILS") class UserDetails {} then a table with name USER_DETAILS is created
 * and the entity name is USERDETAILS.
 * 
 * Your JPQL query would be : select * from USERDETAILS.
 */
@Entity(name = "USER")
@Table(name = "USERS")
// To Configure second level cache. We are telling hibernate that following entity is cacheable.
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// Named and NamedNative Queries for UserDetails Entity
@NamedQuery(name = "UserDetails.byId", query = "from USER where id = ?")
@NamedNativeQuery(name = "UserDeatils.byJoiningDate", query = "select * from USERS where JOINNING_DATE < ?", resultClass = UserDetails.class)
public class UserDetails
{
	/**
	 * NOTE : If we would have a case where our primary key is combination of columns and we are
	 * representing the attribute in java as an embedded object then as we cannot use @ID and @Embedded
	 * annotations simultaneously on a single attribute (), we have to use @EmbeddedID annotation
	 * which will works same.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	@Embedded
	// @Embedded is optional. As we have already declared Name Class as Embeddable.
	private Name userName;

	@Column(name = "JOINNING_DATE")
	@Temporal(TemporalType.DATE)
	// only date will be saved not time
	private Date joiningDate;

	@ElementCollection(fetch = FetchType.EAGER)
	// default is LAZY initialization
	@JoinTable(name = "USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID"))
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "ADDRESS_ID") }, generator = "hilo-gen", type = @Type(type = "long"))
	private Collection<Address> listOfAddress = new ArrayList<Address>();

	@Embedded
	@AttributeOverrides( { @AttributeOverride(name = "firstName", column = @Column(name = "FATHERS_FIRST_NAME")),
			@AttributeOverride(name = "lastName", column = @Column(name = "FATHERS_LAST_NAME")) })
	private Name fathersName;

	@OneToOne
	private Designation designation;

	@OneToMany(cascade = (CascadeType.PERSIST))
	@JoinTable(name = "USER_VEHICLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID"))
	private Collection<Vehicle> vehicleList = new ArrayList<Vehicle>();

	@ManyToOne
	private Organisation organisation;

	@ManyToMany
	private Collection<Group> groupList = new ArrayList<Group>();

	@Transient
	// this property won't be be saved.
	private String description;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setUserName(Name userName)
	{
		this.userName = userName;
	}

	public Name getUserName()
	{
		return userName;
	}

	public void setFathersName(Name fathersName)
	{
		this.fathersName = fathersName;
	}

	public Name getFathersName()
	{
		return fathersName;
	}

	public Date getJoiningDate()
	{
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate)
	{
		this.joiningDate = joiningDate;
	}

	public void setListOfAddress(Collection<Address> listOfAddress)
	{
		this.listOfAddress = listOfAddress;
	}

	public Collection<Address> getListOfAddress()
	{
		return listOfAddress;
	}

	public void setDesignation(Designation designation)
	{
		this.designation = designation;
	}

	public Designation getDesignation()
	{
		return designation;
	}

	public void setVehicleList(Collection<Vehicle> vehicleList)
	{
		this.vehicleList = vehicleList;
	}

	public Collection<Vehicle> getVehicleList()
	{
		return vehicleList;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setOrganisation(Organisation organisation)
	{
		this.organisation = organisation;
	}

	public Organisation getOrganisation()
	{
		return organisation;
	}

	public void setGroupList(Collection<Group> groupList)
	{
		this.groupList = groupList;
	}

	public Collection<Group> getGroupList()
	{
		return groupList;
	}

	@Override
	public String toString()
	{
		return this.getUserName().toString();
	}

}
