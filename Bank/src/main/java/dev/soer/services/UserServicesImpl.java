package dev.soer.services;

import java.util.Scanner;

import dev.soer.models.User;

public class UserServicesImpl implements UserServices{

	@Override
	public void login(Scanner scan) {
		
	}

	@Override
	public void register(Scanner scan) {
		User u = new User();
		System.out.println("Enter username: ");
		u.setUsername(scan.nextLine());
		System.out.println("Enter password: ");
		u.setPassword(scan.nextLine());
		System.out.println("Enter firstname: ");
		u.setFirst(scan.nextLine());
		System.out.println("Enter lastname: ");
		u.setLast(scan.nextLine());
	}

	

}
