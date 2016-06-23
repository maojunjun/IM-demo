
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
	 * 1.获取花名册 
	 * 2.创建ListView的适配器，为ListView设置适配器 
	 * 3.为花名册创建并设置监听器 
	 * 4.创建用于更新花名册联系人的方法
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

//				打开聊天界面，通过intent将聊天的对象传递至聊天的Activity
				RosterEntry entry = entrysList.get(position);
				Intent intent = new Intent(getBaseContext(),ChatActivity.class);
				intent.putExtra("toAccount",entry.getUser());
				intent.putExtra("toNick", NickUtils.getNick(entry.getUser(), entry.getName()));
				startActivity(intent);
				
			}
		});
		
	}
	
	
	/**
	 * 更新好友列表的方法
	 */
	private void updateContactsList() {

		ThreadUtils.runInThread(new Runnable() {

			@Override
			public void run() {
				
				
//				获取包含所有的用户的花名册		
				Roster roster = imApp.getConn().getRoster();
				
//				为花名册设置监听器，监听花名册中好友的上下线、账号信息变化以及删除、添加好友
				roster.addRosterListener(rosterListener);
				
//				每次更新添加当前花名册联系人时，清除上次list集合中的所有联系人，否则会造成数据重复显示
				entrysList.clear();
				
				entrysList.addAll(roster.getEntries());
				
				
//				在主线程中更新用户列表
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
	 * 创建一个用于监听  保存了好友列表的花名册的监听器，只要花名册中的用户上下线信息、账号信息发生变化都会触发该监听器
	 * A listener that is fired any time a roster is changed or 
	 * the presence of a user in the roster is changed.
	 */
	RosterListener rosterListener = new RosterListener() {
		
//		花名册中好友上下线信息发生变动的回调方法
		@Override
		public void presenceChanged(Presence presence) {

			updateContactsList();
		
		}

//		花名册中好友账号信息发生变动的回调方法,如修改了备注，其他设备也需要同步
		@Override
		public void entriesUpdated(Collection<String> addresses) {

			updateContactsList();
		}
		
//		花名册中好友被删除时的回调方法
		@Override
		public void entriesDeleted(Collection<String> addresses) {
			
			updateContactsList();
		}
		
//		花名册中新添加了好友的回调方法
		@Override 
		public void entriesAdded(Collection<String> addresses) {
			
			updateContactsList();
		}
	};
	
	
	
}
