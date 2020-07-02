package com.some.common.utils;

import com.some.common.cache.DefaultTokenDisableCache;
import com.some.common.cache.ITokenDisableCache;
import com.some.common.constants.SystemEnum;
import com.some.common.exception.RespException;
import com.some.common.exception.TokenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 功能说明:
 *
 * @version V1.0
 * @author qzq
 * @since JDK1.6
 */
@Slf4j
@Component
public class TokenUtils {

	@Value("${oauth2.default.clientId:system}")
	private  String clientId;
	@Value("${oauth2.default.clientSecret:59cdf4b8e4b0cf0b5473011a00000000}")
	private  String secret;
	// 默认key 用于项目间的授信
	private  Key defaultKey;
	//token有效期时间 单位毫秒
	private long expiresTime = 1000*60*60*2;
	@Autowired(required=false)
	private ITokenDisableCache tokenDisableCache;

	@ConditionalOnMissingBean(ITokenDisableCache.class)
	@Bean
	public ITokenDisableCache tokenDisableCache(){
		return new DefaultTokenDisableCache();
	}

	public static TokenUtils getInstance(){
		return ApplicationContextUtils.getBean(TokenUtils.class);
	}

	@PostConstruct
	public void init(){
		DESedeKeySpec dks;
		try {
			dks = new DESedeKeySpec(secret.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			defaultKey = keyFactory.generateSecret(dks);
		} catch (Exception e) {
			log.error("token key初始化失败",e);
		}
	}



	/**
	 * 项目间默认授信token
	 * 
	 * @param userId
	 * @return
	 * @author qzq
	 */
	public  String getDefaultToken(String userId) {
		long endTime = System.currentTimeMillis() + expiresTime;
		Date expires = new Date(endTime);
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return getToken(map, expires, defaultKey);
	}

	/**
	 * 获取token签名
	 * 
	 * @param map
	 * @param expires
	 * @param key
	 * @return
	 * @author qzq
	 */
	public  String getToken(Map<String, Object> map, Date expires, Key key) {

		if (expires == null) {
			throw new NullPointerException("null expires is illegal");
		}
		if (key == null) {
			throw new NullPointerException("null key is illegal");
		}

		// 用签名算法HS256和私钥key生成token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String jwtString = Jwts.builder().setClaims(map).setIssuer("qzq")// 设置发行人
				.setExpiration(expires)// 过期时间
				.setIssuedAt(new Date())// 设置现在时间
				.signWith(signatureAlgorithm, key).compact();
		return jwtString;
	}

	public  Key getSecretkey(String secret) {
		try {

			DESedeKeySpec dks;

			dks = new DESedeKeySpec(secret.getBytes());

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			return keyFactory.generateSecret(dks);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}

	}

	/**
	 * 刷新token
	 * 
	 * @param tokenStr
	 * @param key
	 * @param expires
	 * @return
	 * @author qzq
	 */
	public  String refreshToken(String tokenStr, Key key, Date expires) {
		Jws<Claims> claims;
		try {
			// 验证旧Token
			claims = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenStr);
		} catch (ExpiredJwtException e) {
			log.debug("令牌超时");
			throw new TokenException(SystemEnum.codesEnum.ERROR_TOKEN_TIMEOUT);
		} catch (SignatureException e) {
			log.warn(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			throw new TokenException(SystemEnum.codesEnum.ERROR_SIGN_DECODE);
		} catch (Exception e) {
			log.warn(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			throw new TokenException(SystemEnum.codesEnum.ERROR_SIGN_DECODE);
		}

		// 已经过期的不放入黑名单
		// 将旧的Token加入黑名单


		// 生成一个新的Token
		return getToken(claims.getBody(), expires, key);
	}

	/**
	 * 注销token
	 * 
	 * @param tokenStr
	 * @param key
	 * @return
	 * @author qzq
	 */
	public  SystemEnum.codesEnum logoutToken(String tokenStr, Key key) {

		//Jwts.parser().setSigningKey(key).parseClaimsJws(tokenStr);


		// 将注销的Token加入黑名单

		return SystemEnum.codesEnum.SUCCESS;
	}

	/**
	 * 默认key校验token
	 * @param tokenStr
	 * @return
	 */
	public  SystemEnum.codesEnum isValidDefault(String tokenStr) {

		return isValid(tokenStr,defaultKey);
	}
	/**
	 * 超时校验
	 * 
	 * @param tokenStr
	 * @param key
	 * @return
	 * @author qzq
	 */
	public  SystemEnum.codesEnum isValid(String tokenStr, Key key) {
		try {
			// 是否在黑名单
			if (tokenDisableCache.containsKey(tokenStr)) {
				return SystemEnum.codesEnum.ERROR_TOKEN_INVALID;
			}
			Jwts.parser().setSigningKey(key).parseClaimsJws(tokenStr);
			return SystemEnum.codesEnum.SUCCESS;
		} catch (ExpiredJwtException e) {
			log.warn("令牌超时");
			return SystemEnum.codesEnum.ERROR_TOKEN_TIMEOUT;
		} catch (SignatureException e) {
			log.debug(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			return SystemEnum.codesEnum.ERROR_SIGN_DECODE;
		} catch (Exception e) {
			log.debug(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			return SystemEnum.codesEnum.ERROR_SIGN_DECODE;
		}
	}
	public  String getUserId(String jwsToken) {
		return getUserId(jwsToken,defaultKey);
	}
	public  String getUserId(String jwsToken, Key key) {
		Claims body = getBody(jwsToken, key);
		return body == null ? null : (String) body.get("userId");
	}

	public  String getUserCode(String jwsToken, Key key) {
		Claims body = getBody(jwsToken, key);
		return body == null ? null : (String) body.get("userCode");
	}

	public  String getUserHeadUrl(String jwsToken, Key key) {
		Claims body = getBody(jwsToken, key);
		return body == null ? null : (String) body.get("headUrl");
	}


	/**
	 * 校验令牌并获取令牌内容
	 * 
	 * @param jwsToken
	 * @param key
	 * @return
	 * @author qzq
	 * @date 2019年4月22日 下午2:46:34
	 */
	public  Claims getBody(String jwsToken, Key key) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody();
		} catch (ExpiredJwtException e) {
			log.warn("令牌超时");
			throw new RespException(SystemEnum.codesEnum.ERROR_TOKEN_TIMEOUT);
		} catch (SignatureException e) {
			log.info(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			throw new RespException(SystemEnum.codesEnum.ERROR_SIGN_DECODE);
		} catch (Exception e) {
			log.info(SystemEnum.codesEnum.ERROR_SIGN_DECODE.getMsg() + e.getMessage(), e);
			throw new RespException(SystemEnum.codesEnum.ERROR_SIGN_DECODE);
		}

	}

	public static void main(String[] args) throws Exception {

	}
}
