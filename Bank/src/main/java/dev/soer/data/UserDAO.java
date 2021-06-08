package dev.soer.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dev.soer.models.Account;
import dev.soer.models.User;
import dev.soer.utils.JDBCConnection;
import dev.soer.data.AccountDAO;


public class UserDAO implements GenericRepo<User> {
	private Connection conn = JDBCConnection.getConnection();
	private AccountDAO adao = new AccountDAO();
	
	@Override
	public User add(User u) {
		String sql = "insert into users values (default, ?, ?, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getFirst());
			ps.setString(2, u.getLast());
			ps.setString(3, u.getPassword());
			ps.setString(4, u.getUsername());
			ps.setString(5, "Customer");
			boolean success = ps.execute();
			
			if (success) {
				ResultSet rs = ps.getResultSet();
				
				if (rs.next()) {
					u.setId(rs.getInt("id"));
					return u;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User get(Integer i) {
		User u = new User();
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
				u.setAccounts(adao.getAll(rs.getInt("user_id")));
				return u;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User get(String user, String pass) {
		User u = new User();
		try {
			String sql = "select u.id AS user_id, firstName, lastName, password, username, type from users u "
					+ "where u.username = ? and u.password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				u.setId(rs.getInt("user_id"));
				u.setFirst(rs.getString("firstname"));
				u.setLast(rs.getString("lastName"));
				u.setPassword(rs.getString("password"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				if(adao.getAll(rs.getInt("user_id")) != null) {
					u.setAccounts(adao.getAll(rs.getInt("user_id")));
				}
				else {
					u.setAccounts(null);
				}
				return u;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		try {
			String sql = "select u.id AS user_id, firstName, lastName, password, username, type from users u order by id";
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
				u.setAccounts(adao.getAll(rs.getInt("user_id")));
				users.add(u);
			}
			return users;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(User u) {
		String sql = "update users set firstname = ?, lastname = ?, username = ?, password = ?, type = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getFirst());
			ps.setString(2, u.getLast());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getType());
			ps.setInt(6, u.getId());
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(User u) {
		String sql = "delete from users where id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getId());
			int success = ps.executeUpdate();
			
			if (success == 0) {
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
	
	public static void main(String[] args) {
		UserDAO udao = new UserDAO();
//		System.out.println("Get user by id:");
//		System.out.println(udao.get(2));
//		System.out.println("Get user by password and username:");
//		System.out.println(udao.get("jsoer", "password"));
//		System.out.println("Get all users:");
//		System.out.println(udao.getAll());
//		System.out.println("Delete user:");
//		System.out.println(udao.remove(3));
		
	}

}
