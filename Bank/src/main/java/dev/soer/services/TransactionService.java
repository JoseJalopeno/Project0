package dev.soer.services;

import java.util.List;

import dev.soer.models.Transaction;

public interface TransactionService {

	public void addTransaction(Transaction t);
	public List<Transaction> getAllTransactions();
}
