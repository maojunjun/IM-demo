package com.jgsu.imessage.service;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.RosterEntry;

import com.jgsu.imessage.R;
import com.jgsu.imessage.dao.Contact;
import com.jgsu.imessage.dao.ContactDao;
import com.jgsu.imessage.utils.ImApp;
import com.jgsu.imessage.utils.NickUtils;
import com.jgsu.imessage.utils.PinYinUtils;
import com.jgsu.imessage.utils.ThreadUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 监听好友消息和好友花名册Roster变化的核心服务
 * @author Michael Chen 2016年6月23日上午10:08:09
 *
 */
public class CoreService extends Service {

	/**
	 * 1.获取好友花名册
	 * 2.创建数据库
	 * 3.将好友信息保存到数据库中
	 * 4.创建监听器监听到用户Roster或发来的消息时，更新数据库信息
	 * 5.创建ContentObserver监听数据库信息的变化，如果监听到数据库信息变化，则刷新界面
	 * 
	 */
	private ImApp imApp ;
	private List<RosterEntry> rosterEntryList ;
	private ContactDao contactDao;
	
	@Override
	public IBinder onBind(Intent intent) {

		return null;
		
	}

	
	@Override
	public void onCreate() {

		super.onCreate();
		
		Toast.makeText(this, "核心服务创建好了！", 0).show();
		
		imApp = (ImApp) getApplication();
		
		contactDao = imApp.getContactDao();
		
		ThreadUtils.runInThread(new Runnable() {
			
			@Override
			public void run() {

				rosterEntryList = new ArrayList<RosterEntry>(imApp.getConn().getRoster().getEntries());
				
//				将好友列表中的所有好友信息保存到数据库中
				for(RosterEntry entry : rosterEntryList){
					
					System.out.println("EntryName : "+entry.getName() + "EntryUser : "+entry.getUser());
					
					String nick  = NickUtils.getNick(entry.getUser(), entry.getName());
					Contact entity = new Contact();
					entity.setAccount(entry.getUser());
					entity.setNick(nick);
					entity.setAvator(R.drawable.ic_launcher);
					entity.setSpell(PinYinUtils.getPinYin(nick));
					
//					查询数据库中是否保存了该好友的信息
					List<Contact> resultList = contactDao.queryRaw("where ACCOUNT = ?", entry.getUser());
					
					if(resultList.size() == 0){
						
//						没有保存，则直接保存好友的信息
						contactDao.insert(entity);
						
						System.out.println("直接保存：======= "+entity.toString());
						
					}else{
						
//						通过ID查找出已经保存的联系人信息记录
						Contact existedContact = contactDao.load(resultList.get(0).getId());
						
						existedContact.setNick(entity.getNick());
						existedContact.setSpell(entity.getSpell());
						existedContact.setAvator(entity.getAvator());
						
//						已经保存了好友的信息，则更新好友的信息
						contactDao.insertOrReplace(existedContact);
						
						System.out.println("修改保存：======= existedContact ： "+ "Nick : " +existedContact.getNick() +"Account : "+existedContact.getAccount() );
					}
				}
			}
		});
		
	}
	
	
	
	@Override
	public void onDestroy() {

		super.onDestroy();
		
	}
	
	
}
