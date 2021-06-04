package dev.soer.data;

import java.util.List;

import dev.soer.models.User;

public interface UserRepository { // CRUD - Create Read Update Delete

	User addUser(User u);
	User getUser(Integer i);
	User getUser(String user, String pass);
	List<User> getUsers(); // get all users
	User updateUser(User u);
	boolean removeUser(User u);
}
