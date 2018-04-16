package com.sys.typeConverter;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.sys.entity.Admin;

public class AdminTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
        if (toType == Admin.class) {// 当字符串向Date类型转换时  
		    String[] params = (String[]) value;// Request.getParameterValues() 
		    Admin theAdmin = new Admin();
		    theAdmin.setAdminId(Integer.valueOf(params[0]));
		    return theAdmin;
		} else if (toType == Integer.class) {// 当Date转换成字符串时 
		    Admin admin = (Admin) value;  
		    return admin.getAdminId();  
		}else if (toType == String.class) {// 当Date转换成字符串时 
		    Admin admin = (Admin) value;  
		    return admin.getAdminId()+"";  
		}  
        return null;  
    } 
}
