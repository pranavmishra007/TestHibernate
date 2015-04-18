package com.firsthibernateproject.test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.firsthibernateproject.dto.Address;
import com.firsthibernateproject.dto.Designation;
import com.firsthibernateproject.dto.FourWheeler;
import com.firsthibernateproject.dto.Group;
import com.firsthibernateproject.dto.Name;
import com.firsthibernateproject.dto.Organisation;
import com.firsthibernateproject.dto.TwoWheeler;
import com.firsthibernateproject.dto.UserDetails;
import com.firsthibernateproject.dto.Vehicle;

public class HibernateTest
{
	SessionFactory sessionFactory;

	public HibernateTest()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		HibernateTest hbTest = new HibernateTest();
		// hbTest.saveUser();
		// hbTest.getUser();
		hbTest.checkCaching();
	}

	@SuppressWarnings("unused")
	private void saveUser()
	{
		Session session = null;
		Transaction transaction = null;

		Group commonGroup = new Group("Common group");

		for (int i = 1; i <= 10; i++)
		{
			try
			{
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();

				UserDetails user = new UserDetails();
				user.setUserName(new Name("First Name_" + i, "Last Name_" + i));
				user.setJoiningDate(new Date());
				user.setDescription("Desc of the user" + i);
				user.setFathersName(new Name("Father First Name_" + i, "Father Last Name_" + i));
				user.getListOfAddress().add(
						new Address("Home_street_" + i, "Home_city_" + i, "Home_state_" + i, "Home_pin" + i));
				user.getListOfAddress().add(
						new Address("Office_street_" + i, "Office_city_" + i, "Office_state_" + i, "Office__pin" + i));
				Designation designation = new Designation("Hibernate Developer_" + i);
				user.setDesignation(designation);
				Vehicle vehicle;
				Vehicle vehicle2;
				Organisation organisation = new Organisation("HibernateOrg");
				user.setOrganisation(organisation);
				Group group;
				if (i % 2 == 0)
				{
					vehicle = new Vehicle("Scooter_" + i);
					vehicle2 = new TwoWheeler("Bike_" + i, "Bike-SteeringHandle");
					group = new Group("TwoWheeler group");

				} else
				{
					vehicle = new Vehicle("Car_" + i);
					vehicle2 = new FourWheeler("SUV_" + i, "SUV-SteeringWheel");
					group = new Group("FourWheeler group");
				}
				user.getVehicleList().add(vehicle);
				user.getVehicleList().add(vehicle2);
				user.getGroupList().add(commonGroup);
				user.getGroupList().add(group);
				// Using persist instead of save to save cascaded entry with UserDetail like
				// Vehicle.
				session.persist(user);
				// Since Vehicle attribute of UserDetail is Cascaded-Need not to be saved.
				// It will be saved with user.
				// session.save(vehicle);
				// session.save(vehicle2);
				session.save(designation);
				session.save(organisation);
				session.save(group);
				if (i == 1)
					session.save(commonGroup);
				transaction.commit();
			} catch (Exception ex)
			{
				ex.printStackTrace();
				transaction.rollback();
			} finally
			{
				session.close();
			}
		}
	}

	@SuppressWarnings("unused")
	private void getUser()
	{
		getUsersUsingHibernateMethods();
		getUsersUsingHibernateQuery();
		getUsersUsingNamedQuery();
		getUsersUsingNamedNativeQuery();
		getUsersUsingCriteria();
		getUsersUsingQueryByExample();
	}

	private void getUsersUsingHibernateMethods()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingHibernateMethods");

		Session session = sessionFactory.openSession();

		UserDetails user = (UserDetails) session.get(UserDetails.class, 1);
		System.out.println(user.getUserName().getFirstName() + " " + user.getUserName().getLastName());
		session.close();
		// In case of fetchType="LAZY" in UserDetails below statement..fetching after session.close
		// will give an Exception
		System.out.println(user.getListOfAddress().size());
	}

	@SuppressWarnings("unchecked")
	private void getUsersUsingHibernateQuery()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingHibernateQuery");
		Session session = sessionFactory.openSession();

		// Use Entity Name to access the table via HQL
		Query query = session.createQuery("from USER");
		List<UserDetails> userList = (List<UserDetails>) query.list();

		// Getting specific attribute of class
		query = session.createQuery("Select fathersName from USER");
		List<Name> fatherNameList = (List<Name>) query.list();

		session.close();

		System.out.println("Number of users : " + userList.size());

		for (UserDetails user : userList)
			System.out.println(user);

		for (Name name : fatherNameList)
			System.out.println(name);
	}

	@SuppressWarnings("unchecked")
	private void getUsersUsingNamedQuery()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingNamedQuery");
		Session session = sessionFactory.openSession();

		// Use of Named query
		Query query = session.getNamedQuery("UserDetails.byId");
		query.setInteger(0, 1);
		List<UserDetails> usersList = (List<UserDetails>) query.list();

		session.close();

		for (UserDetails user : usersList)
			System.out.println(user);

	}

	@SuppressWarnings("unchecked")
	private void getUsersUsingNamedNativeQuery()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingNamedNativeQuery");
		Session session = sessionFactory.openSession();

		// Use of Named Native query
		Query query = session.getNamedQuery("UserDeatils.byJoiningDate");
		query.setDate(0, new Date());
		List<UserDetails> usersList = (List<UserDetails>) query.list();

		session.close();

		for (UserDetails user : usersList)
			System.out.println(user);

	}

	@SuppressWarnings("unchecked")
	private void getUsersUsingCriteria()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingCriteria");
		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.like("userName", new Name("First Name_1%", "Last Name_1%")));
		criteria.add(Restrictions.gt("id", 1));

		List<UserDetails> usersList = (List<UserDetails>) criteria.list();
		// NOTE : Converting the List into Set to remove duplicate records because Hibernate
		// criteria is returning two records for single user when put a join with Address table.
		Set<UserDetails> userSet = new HashSet<UserDetails>(usersList);
		session.close();

		for (UserDetails user : userSet)
			System.out.println(user);
	}

	@SuppressWarnings("unchecked")
	private void getUsersUsingQueryByExample()
	{
		System.out.println("***************************************************");
		System.out.println("getUsersUsingQueryByExample");
		Session session = sessionFactory.openSession();

		UserDetails exampleUser = new UserDetails();
		exampleUser.setId(2);
		// NOTE : Hibernate example will ignore the null values and primary key
		// Therefore putting id in exampleUser won't work.
		exampleUser.setUserName(new Name("First Name_1%", "Last Name_1%"));

		Example example = Example.create(exampleUser);
		example.enableLike();

		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(example);

		List<UserDetails> usersList = (List<UserDetails>) criteria.list();
		// NOTE : Converting the List into Set to remove duplicate records because Hibernate
		// criteria is returning two records for single user when put a join with Address table.
		Set<UserDetails> userSet = new HashSet<UserDetails>(usersList);
		session.close();

		for (UserDetails user : userSet)
			System.out.println(user);

	}

	private void checkCaching()
	{
		checkFirstLevelCaching();
		checkSecondLevelCaching();
		checkQueryCaching();
	}

	@SuppressWarnings("unused")
	private void checkFirstLevelCaching()
	{
		System.out.println("***************************************************");
		System.out.println("checkFirstLevelCaching");

		Session session = sessionFactory.openSession();
		UserDetails user = (UserDetails) session.get(UserDetails.class, 1); // Query will get fired.
		// user.setUserName(new Name("Updated First Name_1", "Updated Last Name_1"));
		user = (UserDetails) session.get(UserDetails.class, 1); // Query will not get fired.
		session.close();

	}

	@SuppressWarnings("unused")
	private void checkSecondLevelCaching()
	{
		System.out.println("***************************************************");
		System.out.println("checkSecondLevelCaching");

		Session session = sessionFactory.openSession();
		// Here Query will not get Fired because it has been Cached while executing
		// checkFirstLevelCaching() method.
		UserDetails user = (UserDetails) session.get(UserDetails.class, 1);
		session.close();

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void checkQueryCaching()
	{
		System.out.println("***************************************************");
		System.out.println("checkQueryCaching");

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from USER where id = 1");
		query.setCacheable(true);
		List<UserDetails> userList = (List<UserDetails>) query.list();
		session.close();

		session = sessionFactory.openSession();
		query = session.createQuery("from USER where id = 1");
		query.setCacheable(true);
		userList = (List<UserDetails>) query.list();
		session.close();
	}

}
