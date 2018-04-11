package com.zktd.bigdata.token.entity;

import lombok.Data;

import javax.persistence.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.zktd.bigdata.common.util.ConvertUtils;

import java.util.Date;

/**
 * 系统功能目录
 * @author ouburikou
 *
 */
@Data
@Document(collection = "function")
public class Function implements java.io.Serializable {

	@Id
	private String id;
	@Field
    private String functionName;
	@Field
    private String password;
    
	@Field
	private String createTime = ConvertUtils.dateToString(new Date());
 
	@Field
	private String childId;
	@Field
	private Integer pid;
	 
	
}
