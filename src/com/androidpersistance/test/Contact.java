package com.androidpersistance.test;

import com.androidpersistance.annotation.AndroidPersistable;
import com.androidpersistance.annotation.AutoIncrement;
import com.androidpersistance.annotation.Key;

@AndroidPersistable
public class Contact {
	@Key @AutoIncrement
	public Integer _id;
	public String firstName;
	public String lastName;
	
	public Contact() {
	}
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer id) {
		_id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
