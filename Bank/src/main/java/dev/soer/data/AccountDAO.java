package dev.soer.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.soer.models.Account;
import dev.soer.models.User;
import dev.soer.utils.JDBCConnection;

public class AccountDAO implements GenericRepo<Account>{
	private Connection conn = JDBCConnection.getConnection();

	@Override
	public Account add(Account a) {
		String sql = "insert into accounts values (default, ?, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getType());
			ps.setDouble(2, a.getBalance());
			ps.setBoolean(3, false);
			ps.setInt(4, a.getUserID());
			boolean success = ps.execute();
			
			if (success) {
				ResultSet rs = ps.getResultSet();
				
				if (rs.next()) {
					a.setId(rs.getInt("id"));
					return a;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override // should return just a balance of an account for a user
	public Account get(Integer id) {
		Account a = new Account();
		String sql = "select id, accounttype, balance, approved, userid from accounts where id = ? and approved = true";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				a.setId(rs.getInt("id"));
				a.setType(rs.getString("accounttype"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setUserID(rs.getInt("userid"));
			}
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override // not needed i think
	public Account get(String user, String pass) {
		return null;
	}

	@Override
	public List<Account> getAll() {
		String sql = "select * from accounts where approved = true";
		List<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setType(rs.getString("accounttype"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setUserID(rs.getInt("userid"));
				accounts.add(a);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Account a) {
		String sql = "update accounts set accounttype = ?, balance = ?, approved = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getType());
			ps.setDouble(2, a.getBalance());
			ps.setBoolean(3, a.isApproved());
			ps.setInt(4, a.getId());
			
			int success = ps.executeUpdate();
			if(success != 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Account a) {
		String sql = "delete from accounts where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			int success = ps.executeUpdate();
			if(success == 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Account> getAll(Integer id) {
		String sql = "select * from accounts a join users u on a.userid = u.id where a.userid = ?";
		List<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setType(rs.getString("accounttype"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setUserID(rs.getInt("userid"));
				accounts.add(a);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Account> getUnapproved() {
		List<Account> accounts = new ArrayList<Account>();
		String sql = "select * from accounts where approvede = false";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setType(rs.getString("accounttype"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setUserID(rs.getInt("userid"));
				accounts.add(a);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
