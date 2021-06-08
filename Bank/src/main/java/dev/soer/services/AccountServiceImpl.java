package dev.soer.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.soer.data.AccountDAO;
import dev.soer.models.Account;
import dev.soer.models.Transaction;
import dev.soer.models.User;

public class AccountServiceImpl implements AccountService{
	AccountDAO adao = new AccountDAO();
	private static TransactionServiceImpl ts = new TransactionServiceImpl();

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
		Transaction t = new Transaction();
		t.setUserid(u.getId());
		t.setUserAction("Apply");
		LocalDateTime LocalDate = LocalDateTime.now();
		t.setTimestamp(LocalDate);
		ts.addTransaction(t);
		return a;
	}

	@Override
	public Account checkAccount(Account a) {
		Transaction t = new Transaction();
		t.setUserid(a.getUserID());
		t.setUserAction("Check Account Balance");
		LocalDateTime LocalDate = LocalDateTime.now();
		t.setTimestamp(LocalDate);
		ts.addTransaction(t);
		return adao.get(a.getId());
	}

	@Override
	public Account deposit(Account a, Double amount) {
		a.setBalance(a.getBalance() + amount);
		adao.update(a);
		Transaction t = new Transaction();
		t.setUserid(a.getUserID());
		t.setUserAction("Deposit");
		LocalDateTime LocalDate = LocalDateTime.now();
		t.setTimestamp(LocalDate);
		ts.addTransaction(t);
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
			Transaction t = new Transaction();
			t.setUserid(a.getUserID());
			t.setUserAction("Withdraw");
			LocalDateTime LocalDate = LocalDateTime.now();
			t.setTimestamp(LocalDate);
			ts.addTransaction(t);
		}
		return a;
	}
	
	@Override
	public boolean approveAccount(Account a) {
		a.setApproved(true);
		adao.update(a);
		return true;
	}
	
	public void unapprovedAccounts(Scanner scan) {
		List<Account> accounts = new ArrayList<Account>();
		for(Account a : adao.getUnapproved()) {
			accounts.add(a);
		}
		System.out.println(accounts.size());
		System.out.println("Do you want to approve all accounts? (Y/N)");
		String input = scan.next();
		if(input.equalsIgnoreCase("y")) {
			for(Account a : accounts) {
				approveAccount(a);
			}
		}
	}
	public void checkAll(Integer id) {
		System.out.println(adao.getAll(id));
	}

	

}
