package com.some.web.url;

import com.some.web.utils.DateUtils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @version V1.0
 * @author qzq
 */
@Slf4j
public class UrlCache {
	//url版本号缓存--只在当前项目服务器生效 不走全局缓存
	private static Map<String, String> urlCache = new HashMap<>();
	//是否启用url自动md5版本
	private static boolean enable_url_md5 = true;
	//自动加md5支持的后缀
	private static String enable_suffix = ".+(?i).(js|css|json)$";
	/**
	 * 获取最终url; enable_url_md5=true 则自动加md5版本号
	 * @param outUrl cdn外网地址
	 * @param subPath 相对地址
	 * @return
	 */
	public static String getUrl(String outUrl,String subPath) {
		// 加版本号
		if (enable_url_md5) {
			if(!outUrl.matches(enable_suffix)&&outUrl.indexOf(".js")==-1){
				return outUrl;
			}
			// 获取不包含？url
			int idx = outUrl.indexOf("?");
			String realUrl = outUrl;
			if (idx > 0) {
				realUrl = outUrl.substring(0, idx);
			}

			String md5Url = urlCache.get(outUrl);
			if (md5Url == null) {
				try {
					//md5要用内网地址去获取最新文件
					String internalUrl = RD.getInternalStaticPath(subPath);
					URL uri;
					if (internalUrl.startsWith("//")) {
						uri = new URL("http:" + internalUrl);
					} else {
						uri = new URL(internalUrl);
					}
					HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
					InputStream in = conn.getInputStream();
					String md5Hex = DigestUtils.md2Hex(in);
					md5Url = realUrl + "?v1=" + md5Hex;
					if (idx > 0) {
						md5Url = md5Url + "&" + outUrl.substring(idx + 1);
					}
					urlCache.put(realUrl, md5Url);
				} catch (Exception e) {
					log.error("获取md5版本号出错，" + e.getMessage(), e);
					// 出错则用当前时间做版本号
					md5Url = realUrl + "?v1=" + DateUtils.getCurrtDoneTimeString14();
					if (idx > 0) {
						md5Url = md5Url + "&" + outUrl.substring(idx + 1);
					}
				}
			}
			return md5Url;
		}
		return outUrl;
	}
}
