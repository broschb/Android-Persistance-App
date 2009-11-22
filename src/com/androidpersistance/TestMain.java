package com.androidpersistance;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.androidpersistance.sqllite.APCursor;
import com.androidpersistance.test.Constants;
import com.androidpersistance.test.Contact;

public class TestMain extends ListActivity {
	String tag = "TEST MAIN";
	public static final int NEW_CONTACT=1;
	public static final int NEW_CONTACT_ACTIVITY=2;
	public static final int EDIT_CONTACT=3;
	public static final int DELETE_CONTACT=4;
	public static final int VIEW_CONTACT=5;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupData();
        registerForContextMenu(getListView());
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add(0, VIEW_CONTACT, 0, "View Details");
    	menu.add(0, EDIT_CONTACT, 0, "Edit Details");
    	menu.add(0, DELETE_CONTACT, 0, "Delete Contact");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch(item.getItemId()) {
	    case VIEW_CONTACT:
	    	viewEditContact(new Long(info.id).intValue(), VIEW_CONTACT);
	    	break;
	    case DELETE_CONTACT:
	    	deleteContact(new Long(info.id).intValue());
	    	break;
	    case EDIT_CONTACT:
	    	viewEditContact(new Long(info.id).intValue(), EDIT_CONTACT);
	    	break;
	    default:
	    	break;
    	}
    	return super.onContextItemSelected(item);
    }
    
    private void viewEditContact(int id, int action){
    	Intent i = new Intent(this, ContactActivity.class);
        i.putExtra("ID", id);
        i.putExtra("READONLY", action==VIEW_CONTACT?true:false);
        startActivityForResult(i, EDIT_CONTACT);
    }
    
    private void deleteContact(int id){
    	AndroidPersistanceManager man = AndroidPersistanceManager.getInstance(this,Constants.DB_NAME,Constants.DB_VERSION);
        try {
        	man.open();
        	Contact contact = new Contact();
        	contact.set_id(id);
        	man.delete(contact);
        }catch (Exception e) {
        	Log.e("TestMain","Error Deleting",e);
		}finally{
			man.close();
		}
		setupData();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean result =  super.onCreateOptionsMenu(menu);
    	menu.add(1,NEW_CONTACT,0,"Add Contact");
    	return result;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
            setupData();
    }
    
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case NEW_CONTACT:
			Intent i = new Intent(this, ContactActivity.class);
        	startActivityForResult(i, NEW_CONTACT_ACTIVITY);
			break;
		default:
			break;
		}
		return true;
	}
    
    private void setupData(){
    	AndroidPersistanceManager man = AndroidPersistanceManager.getInstance(this,Constants.DB_NAME,Constants.DB_VERSION);
      try {
      	man.open();
      	APCursor cursor = man.retrieve(new Contact());
        startManagingCursor(cursor);
        String[] from = new String[] { "firstName" };
        int[] to = new int[] { R.id.archive1 };
        SimpleCursorAdapter ca = new SimpleCursorAdapter(this, R.layout.archive_row, cursor, from, to);
        setListAdapter(ca);
      }catch (Exception e) {
    	  Log.e(tag, "ErrorSettingUpData",e);
	}finally{
		man.close();
	}
    }
}