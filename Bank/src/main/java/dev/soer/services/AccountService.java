package dev.soer.services;

import java.util.Scanner;

import dev.soer.models.Account;
import dev.soer.models.User;

public interface AccountService {

	Account apply(Scanner scan, User u);
	Account checkAccount(Account a);
	Account deposit(Account a, Double d);
	Account withdraw(Account a, Double d);
	boolean approveAccount(Account a);
}
