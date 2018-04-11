function login(){
	
	 
	 var formvalue= $("#loginForm").serialize();
	 
	 $.ajax({   
		 url:zuul_url+"/api-token/login",   
	     type:'POST',  
	     dataType: "json",
	     data:formvalue,
	     success:function(d){   
	    	 
	    	 	if (d.msg=='success'){
	    	 		
	    	 		alert(d.data.split("_")[0]);
	    	 		
	    	 		localStorage.setItem('accessToken', d.data.split("__")[0]);
	    	 		localStorage.setItem('refreshToken', d.data.split("__")[1]);
	    	 		window.location.href="/index.html" 
	    	 		
	        	}else if(d.msg=='false'){
	        		 alert("登录失败");
	        		  
	        	}
	     }
	 	});
}

//带token ，模拟请求
function test(){
	
	$.ajax({   
		 url:zuul_url+"/api-token/test",   
	     type:'GET',  
	     dataType: "json",
	     beforeSend: function (xhr) {
	    	 xhr.setRequestHeader("token", localStorage.getItem('accessToken'));
	      },
	     success:function(d, textStatus, request){   
	    	 
	    	 alert(d.msg);
	    	 
	     },
	     error:function(d){
	    	 if("TokenExpired"==d.responseText){
		    	   alert("accessToken过期,系统自动通过refreshToken 获取最新accessToken");
		    	   refreshToken();
		    	   
		    	   setTimeout(test,1000) //重置token后，系统自动重新调用原操作。
		    	   
		     } else if("RedisTokenExpired"==d.responseText){
		    	 
		    	 alert("accessToken ----RedisTokenExpired");
		    	 refreshToken();
		    	 
		     }else{
		    	 alert("accessToken ----"+d.responseText);
		     }
	    	 
	    	 
	     }
	 });
}

//accessToken过期，通过refreshToken 重置accessToken和refreshToken
function refreshToken(){
	
	$.ajax({   
		 url:zuul_url+"/api-token/refreshToken", 
	     type:'GET',  
	     dataType: "json",
	     beforeSend: function (xhr) {
	    	 xhr.setRequestHeader("token", localStorage.getItem('refreshToken'));
	    	 xhr.setRequestHeader("accessToken", localStorage.getItem('accessToken'));
	      },
	     success:function(d, textStatus, request){   
	    	 
	    	 if (d.msg=='success'){
	    	 		
	    	 		alert("refreshToken  success");
	    	 		
	    	 		localStorage.setItem('accessToken', d.data.split("__")[0]);
	    	 		localStorage.setItem('refreshToken', d.data.split("__")[1]);
	    	 		//window.location.href="/index.html" 
	    	 		
	        	}else {
	        		 alert("refreshToken  失败");
	        		  
	        	} 
	    	 
	     },
	     error:function(d){//zuul filter  过滤失败（token 未通过验证）
	    	 
	    	 if("TokenExpired"==d.responseText){
	    	    alert("refreshToken过期,重新登录");
	    	   
	    	 }else if("RedisTokenExpired"==d.responseText){
		    	 
		    	 alert("refreshToken ---RedisTokenExpired");
		      }
	    	 else{
	    		  alert("refreshToken 校验失败,重新登录");
	    	 }
	    	 
	    	    localStorage.removeItem('accessToken');
	    	    localStorage.removeItem('refreshToken');
	    	    window.location.href="/login.html" 
	       }
	 });
	
}

/**
 * 正取的登出
 * @returns
 */
function logout(){
	$.ajax({   
		 url:zuul_url+"/api-token/logout?accessToken="+localStorage.getItem('accessToken')+"&refreshToken="+localStorage.getItem('refreshToken'),   
	     type:'GET',  
	     dataType: "json",
	     success:function(d, textStatus, request){   
	    	 
	    	 if (d.msg=='success'){
	    		 
	    		 alert("登出成功");
	    		 localStorage.removeItem('accessToken');
		    	 localStorage.removeItem('refreshToken');
		    	    
	    		 window.location.href="/login.html" 
	    	 }else{
	    		 
	    	 }
	    	 
	     }
	 });
}

/**
 * 模拟token被劫持，同时在多个设备使用。当用户登出后，另一个设备在1秒后也会登出。
 * 
 * 也可以模拟模拟并发访问
 * @returns
 */
function logout_mn(){
	$.ajax({   
		 url:zuul_url+"/api-token/logout?accessToken="+localStorage.getItem('accessToken')+"&refreshToken="+localStorage.getItem('refreshToken'),   
	     type:'GET',  
	     dataType: "json",
	     success:function(d, textStatus, request){   
	    	 
	    	 if (d.msg=='success'){
	    		 
	    		 alert("登出成功");
		    	    
	    		 window.location.href="/login.html" 
	    	 }else{
	    		 
	    	 }
	    	 
	     }
	 });
}
 