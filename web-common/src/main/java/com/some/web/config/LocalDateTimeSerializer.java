package com.some.web.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * 
 * @version V1.0
 * @author qzq
 * @date 2019年11月12日
 */
public class LocalDateTimeSerializer implements ObjectSerializer {
	public static final LocalDateTimeSerializer instance = new LocalDateTimeSerializer();
	private static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @param serializer
	 * @param object
	 * @param fieldName
	 * @param fieldType
	 * @param features
	 * @throws IOException
	 * @author qzq
	 * @date 2019年11月12日 下午5:16:21
	 */
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.out;
		if (object == null) {
			out.writeNull();
		} else {
			LocalDateTime result = (LocalDateTime) object;
			out.writeString(result.format(DateTimeFormatter.ofPattern(defaultPattern)));
		}
	}

}
