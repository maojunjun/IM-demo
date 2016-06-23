package com.jgsu.imessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.jgsu.imessage.activity.LoginActivity;
import com.jgsu.imessage.utils.ThreadUtils;
import com.jgsu.imessage.utils.TimeUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class SplashActivity extends Activity {

	
	@ViewInject(R.id.iv_welcome)
	private ImageView iv_welcome ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		ViewUtils.inject(this);
		
		loadImage();
		

		ThreadUtils.runInThread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					startActivity(new Intent(getBaseContext(),LoginActivity.class));
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}
	

	


	private void loadImage() {

		int currentHour = TimeUtils.getCurrentHour() ;
		
		if(currentHour >= 0 && currentHour <= 10){
			
//			加载早上的图片
			iv_welcome.setImageResource(R.drawable.morning);
			
		}else if(currentHour > 10 && currentHour <= 18 ){
			
//		加载中午的图片
			iv_welcome.setImageResource(R.drawable.afternoon);
			
		}else{
			
//			加载晚上的图片
			iv_welcome.setImageResource(R.drawable.night);
		}
		
		
	}
}
