
package com.jgsu.imessage.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jgsu.imessage.R;
import com.jgsu.imessage.adapter.ContactsListAdapter;
import com.jgsu.imessage.utils.ImApp;
import com.jgsu.imessage.utils.NickUtils;
import com.jgsu.imessage.utils.ThreadUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class ContactListActivity extends Activity {

	@ViewInject(R.id.contacts_list)
	private ListView contacts_list ;
	
	private ContactsListAdapter contactsListAdapter ;
	private ImApp imApp;
	private List<RosterEntry> entrysList = new ArrayList<RosterEntry>();
	
	
	/**
	 * 1.��ȡ������ 
	 * 2.����ListView����������ΪListView���������� 
	 * 3.Ϊ�����ᴴ�������ü����� 
	 * 4.�������ڸ��»�������ϵ�˵ķ���
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		ViewUtils.inject(this);
		
		
		imApp = (ImApp) getApplication();
		
		updateContactsList();
		
		contacts_list.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

//				��������棬ͨ��intent������Ķ��󴫵��������Activity
				RosterEntry entry = entrysList.get(position);
				Intent intent = new Intent(getBaseContext(),ChatActivity.class);
				intent.putExtra("toAccount",entry.getUser());
				intent.putExtra("toNick", NickUtils.getNick(entry.getUser(), entry.getName()));
				startActivity(intent);
				
			}
		});
		
	}
	
	
	/**
	 * ���º����б�ķ���
	 */
	private void updateContactsList() {

		ThreadUtils.runInThread(new Runnable() {

			@Override
			public void run() {
				
				
//				��ȡ�������е��û��Ļ�����		
				Roster roster = imApp.getConn().getRoster();
				
//				Ϊ���������ü������������������к��ѵ������ߡ��˺���Ϣ�仯�Լ�ɾ������Ӻ���
				roster.addRosterListener(rosterListener);
				
//				ÿ�θ�����ӵ�ǰ��������ϵ��ʱ������ϴ�list�����е�������ϵ�ˣ��������������ظ���ʾ
				entrysList.clear();
				
				entrysList.addAll(roster.getEntries());
				
				
//				�����߳��и����û��б�
				ThreadUtils.runUnThread(new Runnable() {
					
					@Override
					public void run() {

						if(contactsListAdapter == null){
							
							contactsListAdapter = new ContactsListAdapter(entrysList, ContactListActivity.this);
							contacts_list.setAdapter(contactsListAdapter);
							
						}else{
							
							contactsListAdapter.notifyDataSetChanged();
						}
						
					}
				});
				
			}
		});
	}

	/**
	 * ����һ�����ڼ���  �����˺����б�Ļ�����ļ�������ֻҪ�������е��û���������Ϣ���˺���Ϣ�����仯���ᴥ���ü�����
	 * A listener that is fired any time a roster is changed or 
	 * the presence of a user in the roster is changed.
	 */
	RosterListener rosterListener = new RosterListener() {
		
//		�������к�����������Ϣ�����䶯�Ļص�����
		@Override
		public void presenceChanged(Presence presence) {

			updateContactsList();
		
		}

//		�������к����˺���Ϣ�����䶯�Ļص�����,���޸��˱�ע�������豸Ҳ��Ҫͬ��
		@Override
		public void entriesUpdated(Collection<String> addresses) {

			updateContactsList();
		}
		
//		�������к��ѱ�ɾ��ʱ�Ļص�����
		@Override
		public void entriesDeleted(Collection<String> addresses) {
			
			updateContactsList();
		}
		
//		��������������˺��ѵĻص�����
		@Override 
		public void entriesAdded(Collection<String> addresses) {
			
			updateContactsList();
		}
	};
	
	
	
}
