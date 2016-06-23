package com.jgsu.imessage.utils;

import org.jivesoftware.smack.XMPPConnection;

import com.jgsu.imessage.dao.ContactDao;
import com.jgsu.imessage.dao.DaoMaster;
import com.jgsu.imessage.dao.DaoSession;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class ImApp extends Application {

	private XMPPConnection conn ;
	
	private String username ;
	
	private String password ;

	private ContactDao contactDao;

	private SQLiteDatabase sqliteDatabase;
	
	
	public SQLiteDatabase getSqliteDatabase() {
		return sqliteDatabase;
	}


	public ContactDao getContactDao() {
		return contactDao;
	}


	@Override
	public void onCreate() {

		super.onCreate();
		
		System.out.println("ImApp¥¥Ω®¡À");
		
		
		sqliteDatabase = new DaoMaster.DevOpenHelper(this, "im.db", null).getWritableDatabase();
		
		DaoMaster master = new DaoMaster(sqliteDatabase);
		
		DaoSession session = master.newSession();
		
		contactDao = session.getContactDao();
		
		
	}


	public XMPPConnection getConn() {
		return conn;
	}


	public void setConn(XMPPConnection conn) {
		this.conn = conn;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
