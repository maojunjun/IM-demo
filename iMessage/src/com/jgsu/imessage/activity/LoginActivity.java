package com.jgsu.imessage.activity;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.jgsu.imessage.R;
import com.jgsu.imessage.service.CoreService;
import com.jgsu.imessage.utils.ImApp;
import com.jgsu.imessage.utils.ThreadUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 
 * @author Michael Chen 2016��6��21������10:07:56
 *	��¼�����Activity
 */
public class LoginActivity extends Activity {

	@ViewInject(R.id.et_username)
	private EditText et_username ;
	
	@ViewInject(R.id.et_psd)
	private EditText et_psd ;
	
	@ViewInject(R.id.btn_login)
	private Button btn_login ;
	
	private ConnectionConfiguration config ;
	
	private XMPPConnection conn ;
	
	private String username ;
	
	private String password ;
	
	private ImApp imApp ;
	
	private String TAG = "LoginActivity" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		ViewUtils.inject(this);
		
		
		ThreadUtils.runInThread(new Thread(){
			
			@Override
			public void run() {

				try {
//					�����������ö������÷���������IP���˿ںš���������
					config = new ConnectionConfiguration("192.168.1.106", 5222, "michael-chun");
					
					conn = new XMPPConnection(config);
					
//					���ӷ�����
					conn.connect();
					
				} catch (XMPPException e) {

					e.printStackTrace();
					
				}
				
			}
			
		});
		
	}
	
	
	private static boolean flag = false ;
	
	
//	��ť�����¼
	
	@OnClick(R.id.btn_login)
	public void login(View view){
		
		username = et_username.getText().toString().trim() ;
		password = et_psd.getText().toString().trim() ;
		
		ThreadUtils.runInThread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					conn.login(username, password);
					
					flag = true ;
					
				} catch (Exception e) {
						
					e.printStackTrace();
					flag = false ;
					Log.e(TAG, "======================== : "+e.getMessage().toString());
					
				}
				
				
			ThreadUtils.runUnThread(new Runnable(){
					
					@Override
					public void run() {
						
						if(flag){
							
							imApp = (ImApp)getApplication() ;
							
							imApp.setUsername(username);
							imApp.setConn(conn);
							
							Toast.makeText(getApplicationContext(), "��½�ɹ�", 0).show();
							
							startActivity(new Intent(getBaseContext(),ContactListActivity.class));
							
//							�������ķ���
							startService(new Intent(getBaseContext(),CoreService.class));
							
							finish();
							
							Log.e(TAG, "=======================TRUE STATUS  is displayed");
							
						}else{
							
							Toast.makeText(getApplicationContext(), "��½ʧ��", 0).show();
						}
						
						System.out.println("the status is2 : "+flag);
					}
					
				});
				
			}
		});		
		
		
		
		
	}
	
	
}
