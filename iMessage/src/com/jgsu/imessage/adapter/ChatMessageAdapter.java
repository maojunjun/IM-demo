package com.jgsu.imessage.adapter;

import java.util.List;

import org.jivesoftware.smack.packet.Message;

import com.jgsu.imessage.R;
import com.jgsu.imessage.utils.ImApp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ChatMessageAdapter extends BaseAdapter {

//	保存历史消息的集合List
	private List<Message> msgList = null ;
	
	private Context context ;
	
	private Message chatMessage ;
	
	private ImApp imApp ;
	
	
	public ChatMessageAdapter(List<Message> msgList , Context context ) {
	
		super();

		this.msgList = msgList ;
		this.context = context ;
		
		imApp = (ImApp)((Activity)context).getApplication();
		
	}

//	设置ListView显示的Item的种类
	@Override
	public int getViewTypeCount() {

		return 2 ;
	}
	
	
//	获取消息的类型，0 从当前用户发送出去的消息，1  代表本机当前用户接收到的用户消息
	@Override
	public int getItemViewType(int position) {

		chatMessage = (Message) getItem(position);
		
		System.out.println("=====================================");
		System.out.println("ChatMesageTo : "+chatMessage.getTo() + " ===ChatMessageFrom : "+chatMessage.getFrom());
		System.out.println("CurrntUserName : "+imApp.getUsername());
		
		if(chatMessage.getFrom().equals(imApp.getUsername()+"@michael-chun")){
			
			return 0 ;
			
		}else {
			
			return 1 ;
		}
		
	}
	
	
	@Override
	public int getCount() {

		return msgList.size() ;
		
	}

	@Override
	public Object getItem(int position) {

		return msgList.get(position) ;
		
	}

	
	@Override
	public long getItemId(int position) {

		return position ;
		
	}

	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Message chatMessage = (Message) getItem(position);
		
		int type = getItemViewType(position) ;

		//		处理消息
		if(0 == type){
			
		convertView = setSendView(chatMessage, convertView);
			
		}else{
			
//			接收的消息
			convertView = setReceiveView(chatMessage, convertView);
			
		}
		
		
		return convertView ;
		
	}
	
	private class ViewHolder {
		
		private ImageView head ;
		private TextView time ;
		private TextView content ;
		
	}
	
	
	/**
	 * 创建发送的消息对应界面上的view
	 * @param chatMessage	消息对象
	 * @param convertView
	 * @return
	 */
	private View setSendView(Message chatMessage,View convertView){
		
			
			ViewHolder holder = null ;
//			发送的消息
			if(convertView == null){
				
				convertView = View.inflate(context, R.layout.item_chat_send, null);
				holder = new ViewHolder() ;
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
				
			}
			
			holder.content.setText(chatMessage.getBody());
			holder.time.setText(chatMessage.getSubject());
			
			return convertView ;
	}

	
	
	/**
	 * 创建接收的消息对应界面上的view
	 * @param chatMessage	消息对象
	 * @param convertView
	 * @return
	 */
	private View setReceiveView(Message chatMessage,View convertView){
			
			ViewHolder holder = null ;
//			发送的消息
			if(convertView == null){
				
				convertView = View.inflate(context, R.layout.item_chat_receive, null);
				holder = new ViewHolder() ;
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.head = (ImageView) convertView.findViewById(R.id.head);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				
				convertView.setTag(holder);
				
			}else{
				
				holder = (ViewHolder) convertView.getTag();
				
			}
			
			holder.content.setText(chatMessage.getBody());
			holder.time.setText(chatMessage.getSubject());
			
			return convertView ;
	}
}
