package dev.soer.services;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.soer.data.UserDAO;
import dev.soer.models.Transaction;
import dev.soer.models.User;

public class UserServicesImpl implements UserServices{
	
	private static UserDAO udao = new UserDAO();
	private static TransactionServiceImpl ts = new TransactionServiceImpl();

	@Override
	public User login(Scanner scan) {
		System.out.println("Enter username:");
		String user = scan.next();
		System.out.println("Enter password:");
		String pass = scan.next();
		User u = udao.get(user, pass);
		Transaction t = new Transaction();
		t.setUserid(u.getId());
		t.setUserAction("Login");
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
		return u;
	}

	@Override
	public void register(Scanner scan) {
		User u = new User();
		System.out.println("Enter username:");
		u.setUsername(scan.next());
		System.out.println("Enter password:");
		u.setPassword(scan.next());
		System.out.println("Enter firstname:");
		u.setFirst(scan.next());
		System.out.println("Enter lastname:");
		u.setLast(scan.next());
		u.setType("Customer"); // set new accounts defaulted to Customer type
		udao.add(u);
		Transaction t = new Transaction();
		t.setUserid(u.getId());
		t.setUserAction("Register");
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
		
	}

	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for(User u : udao.getAll()) {
			users.add(u);
		}
		return users;
	}

}
