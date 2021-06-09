package dev.soer.services;

import java.util.List;

import dev.soer.data.TransactionDAO;
import dev.soer.models.Transaction;
import dev.soer.utils.AppLogger;

public class TransactionServiceImpl implements TransactionService{
	private static TransactionDAO tdao = new TransactionDAO();

	@Override //called from each other service layer
	public void addTransaction(Transaction t) {
		tdao.add(t);
		AppLogger.logger.info("Transaction added to database");
	}

	@Override //called from driver to view log of transactions
	public List<Transaction> getAllTransactions() {
		return tdao.getAll();
	}

	

}
