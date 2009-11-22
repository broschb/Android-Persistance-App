package com.androidpersistance.test;

import java.util.Date;

import com.androidpersistance.annotation.AndroidPersistable;
import com.androidpersistance.annotation.Key;

@AndroidPersistable
public class TestPOJO {

@Key	
public Integer testkey;
public String testString;
@Key
public Integer testInteger;
public Float testFloat;
public Date testDate;


public TestPOJO() {
	// TODO Auto-generated constructor stub
}

public TestPOJO(Integer key1, Integer key2) {
	testkey = key1;
	testInteger = key2;
}

public String getTestString() {
	return testString;
}
public void setTestString(String testString) {
	this.testString = testString;
}
public Integer getTestInteger() {
	return testInteger;
}
public void setTestInteger(Integer testInteger) {
	this.testInteger = testInteger;
}
public Float getTestFloat() {
	return testFloat;
}
public void setTestFloat(Float testFloat) {
	this.testFloat = testFloat;
}
public Date getTestDate() {
	return testDate;
}
public void setTestDate(Date testDate) {
	this.testDate = testDate;
}

public Integer getTestkey() {
	return testkey;
}

public void setTestkey(Integer testkey) {
	this.testkey = testkey;
}

}
