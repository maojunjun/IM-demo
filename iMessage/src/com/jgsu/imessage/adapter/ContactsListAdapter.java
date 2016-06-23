package com.jgsu.imessage.adapter;

import java.util.List;

import org.jivesoftware.smack.RosterEntry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgsu.imessage.R;
import com.jgsu.imessage.utils.ImApp;
import com.jgsu.imessage.utils.NickUtils;

public class ContactsListAdapter extends BaseAdapter {

	private List<RosterEntry> contactsList = null ;
	
	private Context context ;
	
	private ImApp imApp ;
	
	private RosterEntry contact ;
	
	public ContactsListAdapter(List<RosterEntry> contactList , Context context ) {
	
		super();

		this.contactsList = contactList ;
		this.context = context ;
		imApp = (ImApp)((Activity)context).getApplication();
		
	}

	
	@Override
	public int getCount() {

		return contactsList.size() ;
		
	}

	@Override
	public Object getItem(int position) {

		return contactsList.get(position) ;
		
	}

	
	@Override
	public long getItemId(int position) {

		return position ;
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		
		ViewHolder holder = null ;
		
		if(convertView != null ){
			
			view = convertView ;
			holder = (ViewHolder) view.getTag() ;
			
		}else{
			
			view = View.inflate(context , R.layout.buddy_list_item, null);
			
			holder = new ViewHolder() ;
			
			view.setTag(holder);
			
		}
		
		
		holder.contact_photo = (ImageView) view.findViewById(R.id.contact_photo);
		holder.contact_nickname = (TextView) view.findViewById(R.id.contact_nickname);
		holder.contact_desc = (TextView) view.findViewById(R.id.contact_desc);
		
		contact = contactsList.get(position);
		
		String nick = NickUtils.getNick(contact.getUser(), contact.getName());
		holder.contact_nickname.setText(nick);
		holder.contact_desc.setText(contact.getUser()); 
		
		return view ;
		
	}
	
	private class ViewHolder {
		
		private ImageView contact_photo ;
		private TextView contact_nickname ;
		private TextView contact_desc ;
		
	}
	

}
