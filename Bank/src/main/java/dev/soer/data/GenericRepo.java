package dev.soer.data;

import java.util.List;

public interface GenericRepo<T> {

	T add(T t);
	T get(Integer i);
	T get(String user, String pass);
	List<T> getAll(); // get all users
	boolean update(T u);
	boolean remove(T t);
}
