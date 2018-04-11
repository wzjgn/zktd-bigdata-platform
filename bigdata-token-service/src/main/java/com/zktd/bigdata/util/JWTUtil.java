package com.zktd.bigdata.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import groovy.util.logging.Log;
import groovy.util.logging.Slf4j;

@Slf4j
public class JWTUtil {

	/**
	 * 校验token是否正确
	 * 
	 * @param token
	 *            密钥
	 * @param secret
	 *            用户的密码
	 * @return 是否正确
	 */
	public static String verify(String token, String userId, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("userId", userId).build();
			DecodedJWT jwt = verifier.verify(token);

			return "success";
		} catch (TokenExpiredException exception) {

			return "TokenExpired";

		} catch (Exception exception) {

			return "error";
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * 
	 * @return token中包含的用户名
	 */
	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userId").asString();
		} catch (JWTDecodeException e) {

			return null;
		}
	}

	/**
	 * 生成签名,EXPIRE_TIME 后过期
	 * 
	 * @param username
	 *            用户名
	 * @param secret
	 *            用户的密码
	 * @return 加密的token
	 */
	public static String sign(String userId, String secret, long EXPIRE_TIME) {
		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			String token = JWT.create().withClaim("userId", userId).withExpiresAt(date).sign(algorithm);

			return token;

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
