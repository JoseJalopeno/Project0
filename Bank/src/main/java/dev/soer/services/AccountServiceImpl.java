package dev.soer.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.soer.data.AccountDAO;
import dev.soer.models.Account;
import dev.soer.models.Transaction;
import dev.soer.models.User;
import dev.soer.utils.AppLogger;

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
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
		return a;
	}

	@Override
	public Account checkAccount(Account a) {
		Transaction t = new Transaction();
		t.setUserid(a.getUserID());
		t.setUserAction("Check Account Balance");
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
		return adao.get(a.getId());
	}

	@Override
	public Account deposit(Account a, Double amount) {
		if(amount < 0) {
			System.out.println("Enter a valid amount");
			AppLogger.logger.info("Customer tried to deposit less than 0 dollars");
			return a;
		}
		a.setBalance(a.getBalance() + amount);
		adao.update(a);
		Transaction t = new Transaction();
		t.setUserid(a.getUserID());
		t.setUserAction("Deposit");
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
		return a;
	}

	@Override
	public Account withdraw(Account a, Double amount) {
		if((a.getBalance() - amount) < 0 || amount < 0) {
			System.out.println("Enter a valid amount.");
			AppLogger.logger.info("Customer tried to withdraw more than account has");
			return a;
		}
		else {
			a.setBalance(a.getBalance() - amount);
			adao.update(a);
			Transaction t = new Transaction();
			t.setUserid(a.getUserID());
			t.setUserAction("Withdraw");
			Timestamp now = Timestamp.from(Instant.now());
			t.setTimestamp(now);
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
		List<Account> accounts = adao.getAll(id);
		for(Account a : accounts) {
			System.out.println(a);
		}
	}

	public void transfer(Account from, Account to, double amount) {
		withdraw(from, amount);
		if(amount > from.getBalance()) {
			return;
		}
		deposit(to, amount);
		Transaction t = new Transaction();
		t.setUserid(from.getUserID());
		t.setUserAction("Transfer");
		Timestamp now = Timestamp.from(Instant.now());
		t.setTimestamp(now);
		ts.addTransaction(t);
	}

}
