package dev.soer.DAOTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dev.soer.data.UserDAO;
import dev.soer.models.Account;
import dev.soer.models.User;


public class UserDAOTests {
	
	private UserDAO udao = new UserDAO();
	
	@Test
	public void getUserByID() {
		List<User> users = new ArrayList<User>();
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account(2, 201110.55, "Checking", true, 2));
		accounts.add(new Account(1, 12234.54, "Savings", true, 2));
		accounts.add(new Account(7, 10000.0, "Checking", true, 2));
		accounts.add(new Account(11, 100.0, "Savings", true, 2));
		
		User uTest = new User(2, "jsoer", "password", "Joseph", "Soer", "Customer", accounts);
		users.add(uTest);
		
		Assert.assertEquals(uTest, udao.get(2));
	}
	
}
