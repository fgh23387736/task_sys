var  username=document.getElementById("Username").value;
var password=document.getElementById("Password").value
var repassword=document.getElementById("RePassword").value
var type;

function oFocus_1() {
  username=document.getElementById("Username").value;
}

function oBlur_1() {
	username=document.getElementById("Username").value;
	if(!username){
		  layer.msg('请输入用户名！', {
	                        icon: 5
	                    });
		  return false;
	}/*else{//用户名不为空，正则验证手机号码
		 if(!(/^1[34578]\d{9}$/.test(username))){ 
	     	 layer.msg('请输入正确的手机号码！', {
	                        icon: 5
	                    });
	     	 return false;
	     	} 
	
	
	}*/
}

function oFocus_2() {
 password=document.getElementById("Password").value;
}

function oBlur_2() {
password=document.getElementById("Password").value;
if(!password){
	  layer.msg('请输入密码！', {
                        icon: 5
                    });
	  return false;
}
}

function oFocus_3() {
 repassword=document.getElementById("RePassword").value;
 password=document.getElementById("Password").value;
}

function oBlur_3() {
repassword=document.getElementById("RePassword").value;
password=document.getElementById("Password").value;
if(repassword != password){
	  layer.msg('两次密码输入不一致！', {
                        icon: 5
                    });
	  return false;
}
}

function submitTest() {
   username=document.getElementById("Username").value;
 password=document.getElementById("Password").value
 repassword=document.getElementById("RePassword").value;
    if (!username&& !password) { //用户框value值和密码框value值都为空
     	 layer.msg('请输入用户名和密码！', {
                        icon: 5
                    });
        return false; //只有返回true表单才会提交
    } else if (!username) { //用户框value值为空
      layer.msg('请输入用户名！', {
                        icon: 5
                    });
        return false;
    } else if (!password) { //密码框value值为空
        layer.msg('请输入密码！', {
                        icon: 5
                    });
        return false;
    } else if (repassword != password) { //密码框value值为空
        layer.msg('两次密码输入不一致！', {
                        icon: 5
                    });
	  	return false;
    }
        /* if(!(/^1[34578]\d{9}$/.test(username))){ 
     	 layer.msg('请输入正确的手机号码！', {
                        icon: 5
                    });
     	 return false;
     	} */
     	
     	   layer.load(2);

       $.ajax({
        url: '/task_sys/actions/user_register.action',
        type: 'POST',
        dataType: 'json',
        data: {
            userName:username,
            password:hex_md5(password),
            rePassword:hex_md5(repassword)
        },
        success: function(data) {
            layer.msg('注册成功', {
                icon: 6
            });
            layer.closeAll('loading');
            window.location="/task_sys/mob/user/index.jsp";
        },
        error: function(data) {
            layer.closeAll('loading');
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });



}