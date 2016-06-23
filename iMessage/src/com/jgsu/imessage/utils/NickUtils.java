package com.jgsu.imessage.utils;

/**
 * ��ȡ�ǳƵĹ���
 * @author Michael Chen 2016��6��21������4:04:35
 *
 */
public class NickUtils {

	/**
	 * ��ȡ�����ǳƵķ�������û��Ϊ���������ǳ�ʱ����ȡ���ѵ��˺ŵ�@�ַ�ǰ����ֶ�
	 * @param account  �û��˺�
	 * @param nick		�ǳ�
	 * @return			�ǿյ��ǳ�
	 */
	public static String getNick(String account, String nick){
		
		String temp ;
		
		if("".equals(nick) || nick == null){
			
			temp = account.substring(0, "@".indexOf(account));
			
		}else{
			temp = nick ;
		}
		
		return temp ;
		
	}
	
	
	/**
	 * ���ؽص��ͻ��˱�ʶ���û��˺�     ����@michael-chun/Smack ---��  ����@michael-chun
	 * @param userNameWithIdentifier   ����@michael-chun/Smack
	 * @return			����@michael-chun
	 */
	public static String getCorrectedUserName(String userNameWithIdentifier){
		
		return userNameWithIdentifier.substring(0, userNameWithIdentifier.indexOf("@")) + "@michael-chun";
		
	}

	
	
}
