package com.androidpersistance;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.androidpersistance.sqllite.APCursor;
import com.androidpersistance.test.Constants;
import com.androidpersistance.test.Contact;

public class ContactActivity extends Activity {
	public static final int CANCEL_RESULT=1;
	public static final int SAVE_RESULT=2;
	public static final String LOG = "ContactActivity";
	private Integer rowId = null;
	private Boolean readOnly = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		
		//see if new or edit
		rowId = savedInstanceState != null ? savedInstanceState
				.getInt("ID") : null;
		if (rowId == null) {
			Bundle extras = getIntent().getExtras();
			rowId = extras != null ? extras.getInt("ID")
					: null;
		}
		
		readOnly = savedInstanceState != null ? savedInstanceState
				.getBoolean("READONLY") : null;
		if (readOnly == null) {
			Bundle extras = getIntent().getExtras();
			readOnly = extras != null ? extras.getBoolean("READONLY")
					: null;
		}
		
		Button save = (Button) findViewById(R.id.saveButton);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveData();
				setResult(SAVE_RESULT);
				finish();
			}
		});
		if(readOnly!= null && readOnly)
			save.setVisibility(View.INVISIBLE);
		Button cancel = (Button) findViewById(R.id.cancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(CANCEL_RESULT);
				finish();
			}
		});
		fillData();
	}
	
	private void fillData(){
		if(rowId!=null){
			Contact contact = new Contact();
			contact.set_id(rowId);
			AndroidPersistanceManager man = AndroidPersistanceManager.getInstance(this,Constants.DB_NAME,Constants.DB_VERSION);
			man.open();
			try {
				APCursor cursor = man.retrieve(contact);
				if(cursor!=null){
					Object o = cursor.getPersistObject();
					if(o!=null && o instanceof Contact){
						contact = (Contact) o;
						EditText fname = (EditText) findViewById(R.id.fnameText);
						EditText lname= (EditText) findViewById(R.id.lnameText);
						fname.setText(contact.getFirstName());
						lname.setText(contact.getLastName());
					}
				}
			} catch (AndroidPersistableException e) {
				Log.e(LOG,"ERROR SAVING CONTACT",e);
			}finally{
				man.close();
			}
		}
	}
	
	private void saveData(){
		EditText fname = (EditText) findViewById(R.id.fnameText);
		EditText lname= (EditText) findViewById(R.id.lnameText);
		Contact contact = new Contact();
		contact.setFirstName(fname.getText().toString());
		contact.setLastName(lname.getText().toString());
		//now save
		AndroidPersistanceManager man = AndroidPersistanceManager.getInstance(this,Constants.DB_NAME,Constants.DB_VERSION);
		man.open();
		try {
			if(rowId!=null){
				contact.set_id(rowId);
				man.update(contact);
			}else
				man.create(contact);
		} catch (AndroidPersistableException e) {
			Log.e(LOG,"ERROR SAVING CONTACT",e);
		}finally{
			man.close();
		}
	}

}
