function login(){
	
	 
	 var formvalue= $("#loginForm").serialize();
	 
	 $.ajax({   
		// url:zuul_url+"/api-token/login",   
		 url:"http://localhost:8000/login",
	     type:'POST',  
	     dataType: "json",
	     data:formvalue,
	     success:function(d){   
	    	 
	    	 	if (d.msg=='success'){
	    	 		
	    	 		localStorage.setItem('token', d.data);
	    	 		window.location.href="/index.html" 
	    	 		
	        	}else if(d.msg=='false'){
	        		 alert("登录失败");
	        		 localStorage.removeItem('token');
	        	}
	     }
	 	});
}

function test(){
	
	$.ajax({   
		 url:"http://localhost:8000/test",
	     type:'GET',  
	     dataType: "json",
	     beforeSend: function (xhr) {
	    	 xhr.setRequestHeader("token", localStorage.getItem('token'));
	      },
	     success:function(d, textStatus, request){   
	    	 
	    	 	if (d.msg=='success'){
	    	 		
	    	 		alert(request.getResponseHeader('newToken'));
	    	 		localStorage.setItem('token',request.getResponseHeader('newToken'));

	    	 	}else if(d.msg=='false'){
	        		 alert("操作失败");
	        		 
	        	}
	     }
	 	});
}