package com.jgsu.imessage.activity;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgsu.imessage.R;
import com.jgsu.imessage.adapter.ChatMessageAdapter;
import com.jgsu.imessage.utils.ImApp;
import com.jgsu.imessage.utils.NickUtils;
import com.jgsu.imessage.utils.ThreadUtils;
import com.jgsu.imessage.utils.TimeUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ChatActivity extends Activity {

	
	@ViewInject(R.id.friend_title)
	private TextView friend_title ;
	
	@ViewInject(R.id.lv_chat_content)
	private static ListView lv_chat_content ;
	
	@ViewInject(R.id.send_msg)
	private Button send_msg ;
	
	@ViewInject(R.id.et_msg_content)
	private EditText et_msg_content ;
	
	private List<Message> msgList = new ArrayList<Message>();
	
	private ChatMessageAdapter msgListAdapter ;
	
	private ImApp imApp ;

	private String toNickName;

	private String toAccount;

	private String toNick;

	private ChatManager chatManager;

	private Chat chat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);
		
		ViewUtils.inject(this);
		
		imApp = (ImApp) getApplication();
		
		Intent intent = getIntent();
		
//		��ȡIntent���ݹ������û������ǳ�
		toAccount = intent.getStringExtra("toAccount");
		toNick = intent.getStringExtra("toNick");
		
		friend_title.setText("��"+toNick+"������");//�����������ı���
		
		
		ThreadUtils.runInThread(new Runnable() {
			
			@Override
			public void run() {
				
//				1.��ȡ���칤�߹�����
				chatManager = imApp.getConn().getChatManager();
				
//				2.ͨ�����칤�߹�������ȡ���칤�ߣ���Ϊ�����ý�����Ϣ�ļ�������ʵ�ִ�����Ϣ�ķ���
				chat = chatManager.createChat(toAccount, null);
				
				chat.addMessageListener(msgListener);
				
			}
		});
		
	}
	
	private void updateMsgListView(){
		
		if(msgListAdapter == null){
			
			msgListAdapter = new ChatMessageAdapter(msgList, ChatActivity.this);
			lv_chat_content.setAdapter(msgListAdapter);
			
		}else{
			
//			notifyDataSetChanged
			msgListAdapter.notifyDataSetChanged();
			
		}
		
	}
	
	
	/**
	 * ������Ͱ�ť��������Ϣ
	 * @param view
	 */
	@OnClick(R.id.send_msg)
	private void sendMsg(View view){
		
		final String content = et_msg_content.getText().toString().trim();
		
		if("".equals(content)){
			
			Toast.makeText(this, "��Ϣ����Ϊ��", 0).show();
			return ;
		}else{
			
			et_msg_content.setText("");
			
			ThreadUtils.runInThread(new Runnable() {
				
				@Override
				public void run() {
					
//					������Ϣ
					try {
						
						Message msg = new Message(toAccount,Type.chat);
						msg.setBody(content);
						msg.setFrom(imApp.getUsername()+"@michael-chun");
						msg.setSubject(TimeUtils.getCurrentTime());
						chat.sendMessage(msg);
						
						msgList.add(msg) ;
						
						ThreadUtils.runUnThread(new Runnable() {
							
							@Override
							public void run() {
								
								updateMsgListView();
							}
						});
						
					} catch (XMPPException e) {
						
						e.printStackTrace();
						
					}
				}
				
			});
		}
	}
	
	
	/**
	 *�������յ����ѷ�������Ϣ�ļ�����
	 */
	MessageListener msgListener = new MessageListener() {
		
		@Override
		public void processMessage(Chat chat,final Message message) {

			ThreadUtils.runUnThread(new Runnable() {
				
				@Override
				public void run() {
					
					
					System.out.println(message.toXML());
					
					if(message.getBody() != null){
						
						Toast.makeText(ChatActivity.this, message.getBody(), 0).show();
						
//						���յ���Ϣʱ������Ϣ���󱣴浽List�����У�ͨ��������֪ͨ���������Ϣ�б�
						message.setSubject(TimeUtils.getCurrentTime());
						message.setTo(NickUtils.getCorrectedUserName(message.getTo()));
						message.setFrom(NickUtils.getCorrectedUserName(message.getFrom()));
						msgList.add(message) ;
						
						updateMsgListView();
					}
				}
			});
		}
	};
}
