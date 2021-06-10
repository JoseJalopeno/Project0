package dev.soer.driver;

import java.util.List;
import java.util.Scanner;

import dev.soer.models.Account;
import dev.soer.models.Transaction;
import dev.soer.models.User;
import dev.soer.services.AccountServiceImpl;
import dev.soer.services.TransactionServiceImpl;
import dev.soer.services.UserServicesImpl;
import dev.soer.utils.AppLogger;

public class BankApp {

	private static Scanner sc = new Scanner(System.in);
	
	private static UserServicesImpl us = new UserServicesImpl();
	private static AccountServiceImpl as = new AccountServiceImpl();
	private static TransactionServiceImpl ts = new TransactionServiceImpl();
	
	public static void main(String[] args) {
		AppLogger.logger.info("Program Started");
		sc.useDelimiter(System.lineSeparator());
		int choice = -1;
		while (choice != 3) {
			firstMenu();
			choice = sc.nextInt();
			switch(choice) {
				case 1: {
					User u = us.login(sc);
					if(u.getId().equals(null)) {
						System.out.println("Error please try again");
						break;
					}
					else {
						AppLogger.logger.info("User logged in");
						int choice2 = -1;
						if(u.getType().equalsIgnoreCase("customer")) { //if user logging in is a customer it will go through this switch 
							while (choice2 != 4) {
								System.out.println("Hello: " + u.getFirst() + " " + u.getLast());
								customerMenu();
								choice2 = sc.nextInt();
								switch(choice2) {
									case 1: { //apply for account
										as.apply(sc, u);
										System.out.println("You have successfully applied for an account, the admin will approve it shortly.");
										break;
									}
									case 2: { //check account balance
										if(u.getAccounts().size() == 0) {
											System.out.println("You have no accounts approved");
											break;
										}
										else {
											System.out.println("Your accounts: ");
											for(Account a : u.getAccounts()) {
												System.out.println(a);
											}
											String account = sc.next();
											//System.out.println(as.checkAccount(u.getAccounts().get(account - 1)));
										}
										break;
									}
									case 3: { //deposit/withdrawal
										if(u.getAccounts().size() == 0) {
											System.out.println("You have no accounts approved.");
										}
										else {
											int choice3 = -1;
											while(choice3 != 4) {
												System.out.println("1. Deposit\n2. Withdraw\n3. Transfer Between Accounts\n4. Go Back");
												choice3 = sc.nextInt();
												switch(choice3) {
													case 1: { //deposit
														System.out.println("Which account to deposit into:");
														int count = 1;
														for(Account a : u.getAccounts()) {
															System.out.println(count + ". " + a);
															count++;
														}
														int accountnum = sc.nextInt();
														if((accountnum) > u.getAccounts().size()) {
															System.out.println("Enter valid account number");
															break;
														}
														System.out.println("How much do you want to deposit?");
														double amount = sc.nextDouble();
														as.deposit(u.getAccounts().get(accountnum - 1), amount);
														System.out.println("New balance is: " + u.getAccounts().get(accountnum - 1));
														break;
													}
													case 2: { //withdrawal
														System.out.println("Which account to withdraw from:");
														int count = 1;
														for(Account a : u.getAccounts()) {
															System.out.println(count + ". " + a);
															count++;
														}
														int accountnum = sc.nextInt();
														if(accountnum > u.getAccounts().size()) {
															System.out.println("Enter valid account number");
															break;
														}
														System.out.println("How much do you want to withdraw?");
														double amount = sc.nextDouble();
														as.withdraw(u.getAccounts().get(accountnum - 1), amount);
														System.out.println("New balance is: " + u.getAccounts().get(accountnum - 1));
														break;
													}
													case 3: {
														if(u.getAccounts().size() >= 2) {
															System.out.println("What account to transfer from:");
															int count = 1;
															for(Account a : u.getAccounts()) {
																System.out.println(count + ". " + a);
																count++;
															}
															int from = sc.nextInt();
															System.out.println("Which account to transfer to:");
															count = 1;
															for(Account a : u.getAccounts()) {
																System.out.println(count + ". " + a);
																count++;
															}
															int to = sc.nextInt();
															if(from > u.getAccounts().size() || to > u.getAccounts().size()) {
																System.out.println("Enter valid account number");
																break;
															}
															System.out.println("Enter how much you want to transfer?");
															double amount = sc.nextDouble();
															System.out.println("Are you sure you want to transfer " + amount + ". (Y/N)");
															String response = sc.next();
															if(response.equalsIgnoreCase("y")) {
																as.transfer(u.getAccounts().get(from - 1),  u.getAccounts().get(to - 1),  amount);
																System.out.println("New balance is: " + u.getAccounts().get(from - 1));
																System.out.println("New balance is: " + u.getAccounts().get(to - 1));
															}
															else {
																System.out.println("You have canceled the transfer.");
															}
														}
														else {
															System.out.println("You only have 1 account.");
														}
													}
													case 4: {
														break;
													}
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
									case 1: { //approve accounts (currently can only do all accounts approved or no accounts approved)
										System.out.print("Unapproved accounts: ");
										as.unapprovedAccounts(sc);
										break;
									}
									case 2: { //View a customer accounts (gets all accounts for a single customer)
										List<User> users = us.getUsers();
										int count = 1;
										for(User user : users) {
											System.out.println(count + ". " + user.getFirst() + " " + user.getLast());
											count++;
										}
										int inputUser = sc.nextInt();
										as.checkAll(inputUser);
										break;
									}
									case 3: { //view transaction log across whole database
										List<Transaction> transactions = ts.getAllTransactions();
										for(Transaction t : transactions) {
											System.out.println(t);
										}
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
				}
				case 2: { //registers a user accounts and allows them to login to then apply for new accounts
					us.register(sc);
					break;
				}
				case 3: { //quits the banking app
					System.out.println("Goodbye...");
					sc.close();
					break;
				}
				default: {
					System.out.println("Enter a valid option");
					break;
				}
			}
		}
	}
	
	public static void firstMenu() {
		System.out.println("1. Login\n2. Register\n3. Quit");
	}
	public static void customerMenu() {
		System.out.println("1. Apply for New Account\n2. Check Balance\n3. Deposit/Withdrawal/Transfer\n4. Logout");
	}
	public static void employeeMenu() {
		System.out.println("1. Approve Accounts\n2. View Customer Accounts\n3. View Transaction Log\n4. Logout");
	}
}