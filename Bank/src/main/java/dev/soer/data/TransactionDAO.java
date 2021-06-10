package dev.soer.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.soer.models.Transaction;
import dev.soer.utils.JDBCConnection;

public class TransactionDAO implements GenericRepo<Transaction>{
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public Transaction add(Transaction t) {
		String sql = "call addTransaction(?, ?, ?)"; //stored procedure from the database
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getUserid());
			ps.setString(2, t.getUserAction());
			ps.setTimestamp(3, t.getTimestamp());
			boolean success = ps.execute();
			
			if(success) {
				ResultSet rs = ps.getResultSet();
				if(rs.next()) {
					t.setId(rs.getInt("id"));
					return t;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Transaction get(Integer i) {
		return null;
	}

	@Override
	public Transaction get(String user, String pass) {
		return null;
	}

	@Override
	public List<Transaction> getAll() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String sql = "select * from (select * from transactions order by id desc limit 10) g order by g.id";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setUserid(rs.getInt("userid"));
				t.setUserAction(rs.getString("userAction"));
				t.setTimestamp(rs.getTimestamp("logdate"));
				transactions.add(t);
			}
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Transaction u) {
		return false;
	}

	@Override
	public boolean remove(Transaction t) {
		return false;
	}
}
