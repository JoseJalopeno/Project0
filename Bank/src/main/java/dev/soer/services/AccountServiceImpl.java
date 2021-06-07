package dev.soer.services;

import java.util.Scanner;

import dev.soer.data.AccountDAO;
import dev.soer.models.Account;
import dev.soer.models.User;

public class AccountServiceImpl implements AccountService{
	AccountDAO adao = new AccountDAO();

	@Override
	public Account apply(Scanner scan, User u) {
		Account a = new Account();
		System.out.println("Set starting balance: ");
		a.setBalance(Double.valueOf(scan.next()));
		System.out.println("Checking/Savings?");
		a.setType(scan.next());
		a.setApproved(false);
		a.setUserID(u.getId());
		adao.add(a);
		return a;
	}

	@Override
	public Account checkAccount(Account a) {
		return adao.get(a.getId());
	}

	@Override
	public Account deposit(Account a, Double amount) {
		a.setBalance(a.getBalance() + amount);
		adao.update(a);
		return a;
	}

	@Override
	public Account withdraw(Account a, Double amount) {
		if((a.getBalance() - amount) < 0) {
			System.out.println("Enter a valid amount.");
			return a;
		}
		else {
			a.setBalance(a.getBalance() - amount);
			adao.update(a);
		}
		return a;
	}
	
	@Override
	public boolean approveAccount(Account a) {
		return false;
	}

	

}
