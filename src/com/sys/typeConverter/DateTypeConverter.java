package com.sys.typeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {  
            if (toType == Date.class) {// 当字符串向Date类型转换时  
                String[] params = (String[]) value;// Request.getParameterValues() 
                return dateFormat.parse(params[0]);  
            } else if (toType == String.class) {// 当Date转换成字符串时 
                Date date = (Date) value;  
                return dateFormat.format(date);  
            }  
        } catch (java.text.ParseException e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
}
