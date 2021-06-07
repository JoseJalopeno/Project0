package dev.soer.driver;

import java.util.Scanner;

import dev.soer.models.Account;
import dev.soer.models.User;
import dev.soer.services.AccountServiceImpl;
import dev.soer.services.UserServicesImpl;

public class BankApp {

	private static Scanner sc = new Scanner(System.in);
	
	private static UserServicesImpl us = new UserServicesImpl();
	private static AccountServiceImpl as = new AccountServiceImpl();
	public static void main(String[] args) {
		sc.useDelimiter(System.lineSeparator());
		int choice = -1;
		while (choice != 3) {
			firstMenu();
			choice = sc.nextInt();
			switch(choice) {
				case 1: {
					User u = us.login(sc);
					int choice2 = -1;
					if(u.getType().equalsIgnoreCase("customer")) { //if user logging in is a customer it will go through this switch 
						while (choice2 != 4) {
							System.out.println("Hello: " + u.getFirst() + " " + u.getLast());
							customerMenu();
							choice2 = sc.nextInt();
							switch(choice2) {
								case 1: { //apply for account
									as.apply(sc, u);
									break;
								}
								case 2: { //check account balance
									int count = 1;
									for(Account a : u.getAccounts()) {
										System.out.println(count + ". " + a.getType());
										count++;
									}
									int account = sc.nextInt();
									System.out.println(as.checkAccount(u.getAccounts().get(account - 1)));
									break;
								}
								case 3: { //deposit/withdrawal
									int choice3 = -1;
									while(choice3 != 3) {
										System.out.println("1. Deposit\n2. Withdraw\n3. Go back");
										choice3 = sc.nextInt();
										switch(choice3) {
											case 1: { //deposit
												System.out.println("Which account to deposit into:");
												int count = 1;
												for(Account a : u.getAccounts()) {
													System.out.println(count + ". " + a.getType());
													count++;
												}
												int account = sc.nextInt();
												System.out.println("How much do you want to deposit");
												double amount = sc.nextDouble();
												as.deposit(u.getAccounts().get(account - 1), amount);
												System.out.println("New balance is: " + u.getAccounts().get(account - 1));
												break;
											}
											case 2: { //withdrawal
												System.out.println("Which account to withdraw from:");
												int count = 1;
												for(Account a : u.getAccounts()) {
													System.out.println(count + ". " + a.getType());
													count++;
												}
												int account = sc.nextInt();
												System.out.println("How much do you want to withdraw");
												double amount = sc.nextDouble();
												as.withdraw(u.getAccounts().get(account - 1), amount);
												System.out.println("New balance is: " + u.getAccounts().get(account - 1));
												break;
											}
											case 3: {
												break;
											}
										}
									}
									
									break;
								}
								case 4: { //logout
									System.out.println("You have logged out.");
									break;
								}
							}
						}
					} else {
						while(choice2 != 4) { // if user is not a customer they are an employee
							employeeMenu();
							choice2 = sc.nextInt();
							switch(choice2) {
								case 1: { 
									
									break;
								}
								case 2: {
									
									break;
								}
								case 3: {
									
									break;
								}
								case 4: {
									System.out.println("You have logged out.");
									break;
								}
							}
						}
					}
					break;
				}
				case 2: {
					us.register(sc);
					System.out.println("You have registered. Now please sign in.");
					break;
				}
				case 3: {
					System.out.println("Goodbye...");
					break;
				}
			}
		}
	}
	
	public static void firstMenu() {
		System.out.println("1. Login\n2. Register\n3. Quit");
	}
	public static void customerMenu() {
		System.out.println("1. Apply for New Account\n2. Check Balance\n3. Deposit/Withdrawal\n4. Logout");
	}
	public static void employeeMenu() {
		System.out.println("1.Approve Accounts\n2. View Customer Accounts\n3. View Transaction Log\n4. Logout");
	}
}