package com.zktd.bigdata.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

	private String accessToken;
	private String refreshToken;
	private String exp;
	private  String secret;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;

	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;

	}
	
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;

	}
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;

	}


}
