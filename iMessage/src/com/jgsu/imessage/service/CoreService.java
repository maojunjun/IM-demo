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
 * ����������Ϣ�ͺ��ѻ�����Roster�仯�ĺ��ķ���
 * @author Michael Chen 2016��6��23������10:08:09
 *
 */
public class CoreService extends Service {

	/**
	 * 1.��ȡ���ѻ�����
	 * 2.�������ݿ�
	 * 3.��������Ϣ���浽���ݿ���
	 * 4.�����������������û�Roster��������Ϣʱ���������ݿ���Ϣ
	 * 5.����ContentObserver�������ݿ���Ϣ�ı仯��������������ݿ���Ϣ�仯����ˢ�½���
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
		
		Toast.makeText(this, "���ķ��񴴽����ˣ�", 0).show();
		
		imApp = (ImApp) getApplication();
		
		contactDao = imApp.getContactDao();
		
		ThreadUtils.runInThread(new Runnable() {
			
			@Override
			public void run() {

				rosterEntryList = new ArrayList<RosterEntry>(imApp.getConn().getRoster().getEntries());
				
//				�������б��е����к�����Ϣ���浽���ݿ���
				for(RosterEntry entry : rosterEntryList){
					
					System.out.println("EntryName : "+entry.getName() + "EntryUser : "+entry.getUser());
					
					String nick  = NickUtils.getNick(entry.getUser(), entry.getName());
					Contact entity = new Contact();
					entity.setAccount(entry.getUser());
					entity.setNick(nick);
					entity.setAvator(R.drawable.ic_launcher);
					entity.setSpell(PinYinUtils.getPinYin(nick));
					
//					��ѯ���ݿ����Ƿ񱣴��˸ú��ѵ���Ϣ
					List<Contact> resultList = contactDao.queryRaw("where ACCOUNT = ?", entry.getUser());
					
					if(resultList.size() == 0){
						
//						û�б��棬��ֱ�ӱ�����ѵ���Ϣ
						contactDao.insert(entity);
						
						System.out.println("ֱ�ӱ��棺======= "+entity.toString());
						
					}else{
						
//						ͨ��ID���ҳ��Ѿ��������ϵ����Ϣ��¼
						Contact existedContact = contactDao.load(resultList.get(0).getId());
						
						existedContact.setNick(entity.getNick());
						existedContact.setSpell(entity.getSpell());
						existedContact.setAvator(entity.getAvator());
						
//						�Ѿ������˺��ѵ���Ϣ������º��ѵ���Ϣ
						contactDao.insertOrReplace(existedContact);
						
						System.out.println("�޸ı��棺======= existedContact �� "+ "Nick : " +existedContact.getNick() +"Account : "+existedContact.getAccount() );
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
