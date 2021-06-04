package dev.soer.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dev.soer.models.Account;
import dev.soer.models.User;
import dev.soer.utils.JDBCConnection;


public class UserDAO implements UserRepository {
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public User addUser(User u) {
	
//		try {
//			
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
		
		return null;
	}

	@Override
	public User getUser(Integer i) {
		User u = new User();
		List<Account> accounts = new ArrayList<Account>();
		Account a = new Account();
		try {
			String sql = "select u.id AS user_id, firstName, lastName, password, username, type, a.id AS account_id, accountType, balance, approved from users u "
					+ "join accounts a on u.id = a.userID where u.id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setFirst(rs.getString("firstname"));
				u.setLast(rs.getString("lastName"));
				u.setPassword(rs.getString("password"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				a.setId(rs.getInt("account_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setType(rs.getString("accountType"));
				accounts.add(a);
				u.setAccounts(accounts);
				return u;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(String user, String pass) {
		User u = new User();
		List<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "select u.id AS user_id, firstName, lastName, password, username, type, a.id AS account_id, accountType, balance, approved from users u "
					+ "join accounts a on u.id = a.userID where u.username = ? and u.password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setFirst(rs.getString("firstname"));
				u.setLast(rs.getString("lastName"));
				u.setPassword(rs.getString("password"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				Account a = new Account();
				a.setId(rs.getInt("account_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				a.setType(rs.getString("accountType"));
				accounts.add(a);
				u.setAccounts(accounts);
				return u;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		try {
			String sql = "select u.id AS user_id, firstName, lastName, password, username, type from users u";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("user_id"));
				u.setFirst(rs.getString("firstname"));
				u.setLast(rs.getString("lastName"));
				u.setPassword(rs.getString("password"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				users.add(u);
			}
			return users;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) {
		UserDAO udao = new UserDAO();
		System.out.println("Get user by id:");
		System.out.println(udao.getUser(2));
//		System.out.println("Get user by password and username:");
//		System.out.println(udao.getUser("jsoer", "password"));
//		System.out.println("Get all users:");
//		System.out.println(udao.getUsers());
	}

}
