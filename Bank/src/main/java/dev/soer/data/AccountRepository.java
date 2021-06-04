package dev.soer.data;

import java.util.List;

import dev.soer.models.Account;

public interface AccountRepository {

	Account addAccount(Account a);
	Account getAccount(Integer i);
	Account getAccount(String username, String password);
	List<Account> getAccounts();
	Account updateAccount(Account a);
	boolean removeAccount(Account a);
}
