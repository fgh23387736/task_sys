package com.sys.typeConverter;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.sys.entity.User;

public class UserTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
        if (toType == User.class) {// 当字符串向Date类型转换时  
		    String[] params = (String[]) value;// Request.getParameterValues() 
		    User theUser = new User();
		    theUser.setUserId(Integer.valueOf(params[0]));
		    return theUser;
		} else if (toType == Integer.class) {// 当Date转换成字符串时 
		    User user = (User) value;  
		    return user.getUserId();  
		}else if (toType == String.class) {// 当Date转换成字符串时 
		    User user = (User) value;  
		    return user.getUserId()+"";  
		}  
        return null;  
    } 
}
