package com.zktd.bigdata.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filter")
public class FilterConfig {

	private String ignores; 

	public String getIgnores() {
		return ignores;
	}

	public void setIgnores(String ignores) {
		this.ignores = ignores;

	} 


}
