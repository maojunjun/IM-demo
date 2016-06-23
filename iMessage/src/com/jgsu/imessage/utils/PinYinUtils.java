package com.jgsu.imessage.utils;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

public class PinYinUtils {

	public static String getPinYin(String text) {

		String result = PinyinHelper.convertToPinyinString(text, "",
				PinyinFormat.WITHOUT_TONE).toUpperCase();

		return result;
	}

}
