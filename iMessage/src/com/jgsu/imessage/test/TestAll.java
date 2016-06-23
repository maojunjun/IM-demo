package com.jgsu.imessage.test;

import org.junit.Test;

import com.jgsu.imessage.dao.Contact;
import com.jgsu.imessage.dao.ContactDao;
import com.jgsu.imessage.dao.DaoMaster;
import com.jgsu.imessage.dao.DaoSession;
import com.jgsu.imessage.utils.TimeUtils;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TestAll extends AndroidTestCase {

	@Test
	public void testGetDate(){
		
		System.out.println("当前小时数： " + TimeUtils.getCurrentHour());
		
	}
	
	
	@Test
	public void test2(){
		
//		创建target工程的Dao
		
		SQLiteDatabase db = new DaoMaster.DevOpenHelper(mContext, "im.db", null).getWritableDatabase();
		
		DaoMaster master = new DaoMaster(db);
		
		DaoSession newSession = master.newSession();
		
		ContactDao contactDao = newSession.getContactDao();
		
		Contact contact = contactDao.load(1l);
		
		System.out.println("============="+contact.toString());
		
		
	}
	
	
}
