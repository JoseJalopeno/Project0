package dev.soer.services;

import java.util.List;
import java.util.Scanner;

import dev.soer.models.User;

public interface UserServices {

	User login(Scanner scan);
	void register(Scanner scan);
	List<User> getUsers();
}
