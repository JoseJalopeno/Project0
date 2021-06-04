package dev.soer.services;

import java.util.Scanner;

import dev.soer.models.User;

public interface UserServices {

	void login(Scanner scan);
	void register(Scanner scan);
}
