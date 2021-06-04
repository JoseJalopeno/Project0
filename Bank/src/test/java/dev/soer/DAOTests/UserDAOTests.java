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
		accounts.add(new Account(1, 2000.54, "Savings", true));
		
		User uTest = new User(2, "jsoer", "password", "Joseph", "Soer", "Customer", accounts);
		users.add(uTest);
		
		Assert.assertEquals(uTest, udao.getUser(2));
	}
	
}
