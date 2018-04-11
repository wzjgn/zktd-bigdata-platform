package com.zktd.bigdata.common.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

import com.zktd.bigdata.common.mongodb.utils.ReflectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ConvertUtils {

	static {
		registerDateConverter();
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 *
	 * @param collection   来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 *
	 * @param collection   来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator    分隔符.
	 */
	@SuppressWarnings("rawtypes")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
														final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 *
	 * @param value  待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 定义日期Converter的格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 */
	private static void registerDateConverter() {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
		org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
	}

	public static String getDateYYYYMMDD(Date date) {
		if (null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		return (df.format(date));
	}


	/**
	 * @param [obj]
	 * @return java.lang.String
	 * @desc null对象返回空字符串
	 * @author wangzhenjiang
	 * @date 2017-12-15 02:13
	 */
	public static String convertNullStr(Object obj) {
		if (null == obj) {
			return "";
		}


		return obj.toString().trim();
	}


	/**
	* @desc  string(2017-12-15 14:35)  返回  date(2017-12-15 14:35:00)
	* @author wangzhenjiang
	* @date 2017-12-15 02:35
	* @param [str]
	* @return java.util.Date
	*/
	public static Date ToDateTime(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		java.util.Date sdate = null; //初始化
		try {

			 sdate = sdf.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdate;
	}

	/**
	 * @desc  date(2017-12-15 14:35:33)  返回  String(2017-12-15 14:35:33)
	 * @author wangzhenjiang
	 * @date 2017-12-15 02:35
	 * @param [str]
	 * @return java.util.Date
	 */
	public static String dateToString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 String str=null;
		try {

			str=  sdf.format(date);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
