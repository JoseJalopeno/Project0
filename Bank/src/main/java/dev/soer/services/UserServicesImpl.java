package dev.soer.services;

import java.util.Scanner;

import dev.soer.data.UserDAO;
import dev.soer.models.User;

public class UserServicesImpl implements UserServices{
	
	private static UserDAO udao = new UserDAO();

	@Override
	public User login(Scanner scan) {
		System.out.println("Enter username:");
		String user = scan.next();
		System.out.println("Enter password:");
		String pass = scan.next();
		return udao.get(user, pass);
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
		
	}

}
