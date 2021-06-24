package com.some.common.utils;

import java.io.IOException;
import java.util.Map;

import com.some.common.exception.RespException;
import com.some.common.result.RespResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @date 2017年11月13日
 * @since JDK1.6
 */
public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	//public static final MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");
	private static OkHttpClient httpClient = new OkHttpClient();

	public static String httpGet(String url)  {

		String str;
		Response response = null;
		try {
			Request request = new Request.Builder().url(url).build();
			response = httpClient.newCall(request).execute();
			str = response.body().string();
			if(response.code()!=200){
				logger.error("http请求异常,异常编码:{},异常信息{}",response.code(),response.message());
				throw new RespException("http请求异常,异常编码:"+response.code()+",异常信息："+response.message());
			}
		} catch (Exception e) {
			logger.error("http get:{}，出错{}",url,e.getMessage(),e);
			throw new RespException("请求出错,"+e.getMessage());
		}finally{
			try {
				if(response!=null){
					response.body().close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return str;
	}

	/**
	 * 返回JSONObject
	 * @param url
	 * @return
	 * @author qzq
	 */
	public static JSONObject httpGet2Json(String url){
		return JSONObject.parseObject(httpGet(url));
	}

	public static RespResult<?> httpGet2Result(String url) {
		return JSONObject.toJavaObject(JSONObject.parseObject(httpGet(url)), RespResult.class);
	}

	public static RespResult<?> httpPost2Result(String url, Map<String,String> paramsMap)  {
		return JSONObject.toJavaObject(JSONObject.parseObject(httpPost(url,paramsMap)), RespResult.class);
	}

	public static RespResult<?> httpPost2Result(String url){
		return JSONObject.toJavaObject(JSONObject.parseObject(httpPost(url,null)), RespResult.class);
	}

	/**
	 * 返回JSONObject
	 * @param url
	 * @param paramsMap
	 * @return
	 * @author qzq
	 */
	public static JSONObject httpPost2Json(String url, Map<String,String> paramsMap){
		return  JSONObject.parseObject(httpPost(url,paramsMap));
	}
	/**
	 * 异步请求
	 * @param url
	 * @param callback
	 * @author qzq
	 */
	public void asynCall(String url,Callback callback){
		Request req = new Request.Builder().url(url).build();
		httpClient.newCall(req).enqueue(callback);
	}

	public static String httpPost(String url, Map<String,String> paramsMap) {
		String str;
		Response response = null;
		try {
			FormBody.Builder builder = new FormBody.Builder();
			if(paramsMap!=null&&!paramsMap.isEmpty()){
				for (String key : paramsMap.keySet()) {
					builder.add(key, paramsMap.get(key));
				}
			}

			RequestBody formBody=builder.build();
			Request request = new Request.Builder()
					.url(url)
					.post(formBody)
					.build();
			response = httpClient.newCall(request).execute();
			str = response.body().string();
			if(response.code()!=200){
				logger.error("http请求异常,异常编码:{},异常信息{}",response.code(),response.message());
				throw new RespException("http请求异常,异常编码:"+response.code()+",异常信息："+response.message());
			}
		}catch (Exception e) {
			logger.error("http post:{}，出错{}",url,e.getMessage(),e);
			throw new RespException("请求出错,"+e.getMessage());
		}finally{
			try {
				if(response!=null){
					response.body().close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return str;
	}
	/**
	 * application/json格式发送json数据
	 *
	 * @param url
	 * @param json
	 * @return
	 */
	public static String httpPostByjson(String url, String json) {
		return httpPostByjson(url,json,null);
	}
	/**
	 * application/json格式发送json数据
	 *
	 * @param url
	 * @param json
	 * @return
	 */
	public static String httpPostByjson(String url, String json,Map<String,String> header) {
		String str;
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		Response response = null;
		try {
			// 创建一个RequestBody(参数1：数据类型 参数2传递的json串)
			// json为String类型的json数据
			RequestBody requestBody = RequestBody.create(JSON, json);
			Request.Builder builder = new Request.Builder().url(url);
			if(header!=null&&header.size()>0){
				header.forEach((k,v)->{
					builder.header(k,v);
				});
			}
			Request request = builder.post(requestBody).build();

			response = httpClient.newCall(request).execute();
			str = response.body().string();
			if (response.code() != 200) {
				logger.error("http请求异常,异常编码:{},异常信息:{}", response.code(), response.message());
				throw new RespException("http请求异常,异常编码:" + response.code() + ",异常信息：" + response.message());
			}
		} catch (Exception e) {
			logger.error("http post:{}，出错{}", url, e.getMessage(), e);
			throw new RespException("请求出错," + e.getMessage());
		} finally {
			try {
				if (response != null) {
					response.body().close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return str;
	}

	public static void main(String[] args) {
		Request request = new Request.Builder().url("http://localhost:8081/api/TestHandler/handle?client_id=1000000045&appsecret=59cdf4b8e4b0cf0b5473011a00000000&user_id=qzq&user_code=&nick_name=&need_refresh_token=0&ts=20190828141431&sign=&get_sign=").build();
		try {
			httpClient.newCall(request).enqueue(new Callback(){

				@Override
				public void onFailure(Call call, IOException e) {
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					System.out.println("dd");
				}

			} );

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("999");
	}
}
