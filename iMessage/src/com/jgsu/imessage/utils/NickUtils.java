package com.jgsu.imessage.utils;

/**
 * 获取昵称的工具
 * @author Michael Chen 2016年6月21日下午4:04:35
 *
 */
public class NickUtils {

	/**
	 * 获取好友昵称的方法，当没有为好友设置昵称时，截取好友的账号的@字符前面的字段
	 * @param account  用户账号
	 * @param nick		昵称
	 * @return			非空的昵称
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
	 * 返回截掉客户端标识的用户账号     李四@michael-chun/Smack ---》  李四@michael-chun
	 * @param userNameWithIdentifier   李四@michael-chun/Smack
	 * @return			李四@michael-chun
	 */
	public static String getCorrectedUserName(String userNameWithIdentifier){
		
		return userNameWithIdentifier.substring(0, userNameWithIdentifier.indexOf("@")) + "@michael-chun";
		
	}

	
	
}
