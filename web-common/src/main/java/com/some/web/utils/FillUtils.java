package com.some.web.utils;

import java.util.Arrays;

/**
 * 字段填充
 * @version V1.0
 * @author qzq
 */
public class FillUtils {
	public static final int DT_L = 0;
	public static final int DT_R = 1;

   public static String CHARSET = "UTF-8";
	public static byte[] fill(byte[] value, int size, String fillStr, int direct) {
		if (value == null)
			return value;
		int lenOfValue = value.length;
		int lenOfFill = size - lenOfValue;
		if (lenOfFill <= 0)
			return value;
		byte[] fills = new byte[size];
		Arrays.fill(fills, fillStr.getBytes()[0]);
		if (direct == 0)
			System.arraycopy(value, 0, fills, lenOfFill, lenOfValue);
		else {
			System.arraycopy(value, 0, fills, 0, lenOfValue);
		}
		return fills;
	}

	public static String fill(String value, int size, String fillStr, int direct) {
		if (value == null)
			return value;
		int lenOfValue = value.length();
		int lenOfFill = size - lenOfValue;
		if (lenOfFill <= 0)
			return value;
		byte[] fills = new byte[lenOfFill];
		Arrays.fill(fills, fillStr.getBytes()[0]);

		if (direct == 0) {
			return new String(fills) + value;
		}
		return value + new String(fills);
	}

	public static String spaceFill(String value, int size) {
		return fill(value, size, " ", 1);
	}


	public static String zeroFill(String value, int size) {
		return fill(value, size, "0", 0);
	}
	
	public static  String fillBlankStr(String value,int leng) throws Exception{
		  value = value==null?"":value;
		  int size = value.getBytes(CHARSET).length;
		  if(leng==size){
			  return value;
		  }else if(size>leng){
			  throw new Exception("字符格式化异常,"+value+",超过最大长度："+leng);
		  }
		  return fill(value, size, " ", FillUtils.DT_R);
	  }

	public static  String fillOriBlankStr(String value,int leng) throws Exception{
		  value = value==null?"":value;
		  int size = value.length();
		  if(leng==size){
			  return value;
		  }else if(size>leng){
			  throw new Exception("字符格式化异常,"+value+",超过最大长度："+leng);
		  }
		  for(int i=0;i<leng-size;i++){
			  value=value+" ";
		  }
		  return value;
	  }
	public static  byte[] fillBlankByte(String value,int leng) throws Exception{
		byte[] vb = value.getBytes(CHARSET);
		int size = vb.length;
		  if(leng==size){
			  return vb;
		  }else if(size>leng){
			  throw new Exception("字符格式化异常,"+value+",超过最大长度："+leng);
		  }
		  byte[] b =  fill(vb, leng, " ", FillUtils.DT_R);
		  return b;
	  }
}
