package com.cui.video.utils;

/**
 * @author great.maronghua@gmail.com
 * @since unknown
 */
public final class StringUtils {

	/**
	 * 正则剔除左右空白字符， 空白字符：[ \t\n\x0B\f\r]
	 */
	public final static String TRIM_REGULAT_EXPRESSION = "^(\\s)*|(\\s)*$";

	public final static String EMPTY_STRING = "";

	public final static String NULL_STRING = "null";

	public final static String[] EMPTY_STRING_ARRAY = new String[] {};

	public final static boolean isEquals(String s1, String s2) {
		if (s1 == null) {
			return s2 == null ? true : s2.equals(s1);
		} else {
			return s1.equals(s2);
		}
	}

	public final static boolean isEmpty(String... s) {
		boolean m = true;
		for (String i : s) {
			m = m && isEmpty(i);
			if (!m) break;
		}
		return m;
	}

	public final static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0 || "null".equalsIgnoreCase(s.trim());
	}

	public final static boolean isNotEmpty(String... s) {
		boolean m = true;
		for (String i : s) {
			m = m && isNotEmpty(i);
			if (!m) break;
		}
		return m;
	}

	public final static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public final static boolean isEmpty(Object o) {
		return o == null || isEmpty(String.valueOf(o));
	}

	public final static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}

	public final static String getString(Object o, String defaultValue) {
		return isEmpty(o) ? defaultValue : String.valueOf(o);
	}

	public final static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) { return String.valueOf((long) d); }
		return String.valueOf(d);
	}

	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}