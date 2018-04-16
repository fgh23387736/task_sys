package com.sys.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File; 
import java.io.FileOutputStream;  
import java.io.OutputStream;  

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;   

public class PublicUtils {
	private static String hexStr =  "0123456789abcdef"; 
	private static String[] binaryArray =   
	        {"0000","0001","0010","0011",  
	        "0100","0101","0110","0111",  
	        "1000","1001","1010","1011",  
	        "1100","1101","1110","1111"}; 
	/** 
     *  
     * @param str 
     * @return 二进制数组转换为二进制字符串   2-2
     */  
    public static String bytes2BinStr(byte[] bArray){  

        String outStr = "";  
        int pos = 0;  
        for(byte b:bArray){  
            //高四位  
            pos = (b&0xF0)>>4;  
            outStr+=binaryArray[pos];  
            //低四位  
            pos=b&0x0F;  
            outStr+=binaryArray[pos];  
        }  
        return outStr;  
    }  

      /** 
     *  
     * @param bytes 
     * @return 将二进制数组转换为十六进制字符串  2-16
     */  
    public static String bin2HexStr(byte[] bytes){  

        String result = "";  
        String hex = "";  
        for(int i=0;i<bytes.length;i++){  
            //字节高4位  
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));  
            //字节低4位  
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));  
            result +=hex;  //+" "
        }  
        return result;  
    } 

    /** 
     *  
     * @param hexString 
     * @return 将十六进制转换为二进制字节数组   16-2
     */  
    public static byte[] hexStr2BinArr(String hexString){  
        //hexString的长度对2取整，作为bytes的长度  
        int len = hexString.length()/2;  
        byte[] bytes = new byte[len];  
        byte high = 0;//字节高四位  
        byte low = 0;//字节低四位  
        for(int i=0;i<len;i++){  
             //右移四位得到高位  
             high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);  
             low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));  
             bytes[i] = (byte) (high|low);//高地位做或运算  
        }  
        return bytes;  
    }

    /** 
     *  
     * @param hexString 
     * @return 将十六进制转换为二进制字符串   16-2 
     */  
    public static String hexStr2BinStr(String hexString){
        return bytes2BinStr(hexStr2BinArr(hexString));
    }
	public static String getMD5(String str) {
		try {
		// 生成一个MD5加密计算摘要
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 计算md5函数
		md.update(str.getBytes());
		// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
		// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
		return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
		 
		}
		return null;
	}
	
	
	public static Integer[] getIdsByString(String ids,String regex){
		if(ids == null || ids.equals("")){
			return new Integer[0];
		}
		String[] idsArr = ids.split(regex);
		Integer[] theIds = new Integer[idsArr.length];
		for(int i = 0; i < idsArr.length; i++) {
			theIds[i] = Integer.parseInt(idsArr[i]);
		}
		return theIds;
	}
	
	public static String[] getStringIdsByString(String ids,String regex){
		if(ids == null || ids.equals("")){
			return new String[0];
		}
		String[] idsArr = ids.split(regex);
		String[] theIds = new String[idsArr.length];
		for(int i = 0; i < idsArr.length; i++) {
			theIds[i] = idsArr[i];
		}
		return theIds;
	}
	public static String getFormatStringByZero(int number,String theString){
		if(number < 0 || theString == null){
			return null;
		}
		int theNumber = number - theString.length();
		if(theNumber < 0){
			return theString.substring(Math.abs(theNumber));
		}else{
			String finalString = "";
			for (int i = 0; i < theNumber; i++) {
				finalString += "0";
			}
			finalString += theString;
			return finalString;
		}
		
	}
	public static char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
                   bb.flip ();
         CharBuffer cb = cs.decode (bb);
    
     return cb.array();
  }
	public static byte[] getBytes (char[] chars) {
	   Charset cs = Charset.forName ("UTF-8");
	   CharBuffer cb = CharBuffer.allocate (chars.length);
	   cb.put (chars);
	   cb.flip ();
	   ByteBuffer bb = cs.encode (cb);
	  
	   return bb.array();
	
	 }
	public static Integer getProjectFromCommand(byte[] command){
		if(isCrc(command)){
			byte[] projectByte = {command[2],command[3],command[4],command[5]};
			String projectStringHex = bytes2BinStr(projectByte);
			Integer project = Integer.parseInt(projectStringHex,2);
			return project;
		}
		return null;
	}
	public static boolean isCrc(byte[] command){
		if(command.length <= 4){
			return false;
		}else{
			byte[] theCrcByte = new byte[command.length - 4];
			byte[] theCrc = new byte[2];
			for (int i = 1; i < command.length - 3; i++) {
				theCrcByte[i-1] = command[i];
			}
			for (int i = command.length - 3; i < command.length - 1; i++) {
				theCrc[i - (command.length - 3)] = command[i];
			}
			String realCrcBytes = String.format("%04x", CRC_XModem(theCrcByte));
			if(realCrcBytes.equals(bin2HexStr(theCrc))){
				return true;
			}
		}
		
		return false;
	}
	public static byte[] encodeCommendToByte(String type,String device,String table,Integer[] content){
		
		byte[] myCommend = null;
		String commendHead = "55";
		String commendContentLength = "00";
		String commendDevice = "";
		String commendType = "";
		String commendTable = "";
		String commendContent = "";
		String commendCodeString = "";
		String commendTail = "ed";
		String commendString = "";
		if(content == null){
			content = new Integer[0];
		}
		switch (table) {
		case "project":
			commendTable = "01";
			break;
		case "device":
			commendTable = "02";
			break;
		case "alarm":
			commendTable = "03";
			break;
		case "report":
			commendTable = "04";
			break;

		default:
			break;
		}
		if(device == null){
			device = "00000000";
		}
		commendDevice = getFormatStringByZero(8, device);
		if(type != null && commendDevice != null){
			if(type.equals("test")){
				commendType = "11";
				commendContentLength = "05";
			}else if(type.equals("print")){
				commendType = "12";
				commendContentLength = Long.toHexString(content.length*4+5) + "";
			}else if(type.equals("add")){
				commendType = "13";
				commendContentLength = Long.toHexString(content.length*4+6) + "";
			}else if(type.equals("delete")){
				commendType = "14";
				commendContentLength = Long.toHexString(content.length*4+6) + "";
			}else if(type.equals("update")){
				commendType = "15";
				commendContentLength = Long.toHexString(content.length*4+6) + "";
			}
			commendContentLength = getFormatStringByZero(2, commendContentLength);
			for (int i = 0; i < content.length; i++) {
				commendContent += getFormatStringByZero(8,Long.toHexString(content[i]));
			}
			String crcString = commendContentLength+commendDevice+commendType+commendTable+commendContent;
			commendCodeString = String.format("%04x", CRC_XModem(hexStr2BinArr(crcString)));
			commendString = commendHead 
					+ commendContentLength 
					+ commendDevice 
					+ commendType 
					+ commendTable 
					+ commendContent 
					+ commendCodeString 
					+ commendTail;
//			System.out.println("------------------------------");
//			System.out.println("commendHead:"+commendHead);
//			System.out.println("commendContentLength:"+commendContentLength);
//			System.out.println("commendDevice:"+commendDevice);
//			System.out.println("commendType:"+commendType);
//			System.out.println("commendTable:"+commendTable);
//			System.out.println("commendContent:"+commendContent);
//			System.out.println("commendCodeString:"+commendCodeString);
//			System.out.println("commendTail:"+commendTail);
//			System.out.println("command:"+commendString);
//			System.out.println("------------------------------");
			myCommend = hexStr2BinArr(commendString);
		}
		return myCommend;
	}
	public static int CRC_XModem(byte[] bytes){  
	       int crc = 0x00;          // initial value  
	       int polynomial = 0x1021;    
	       for (int index = 0 ; index< bytes.length; index++) {  
	           byte b = bytes[index];  
	           for (int i = 0; i < 8; i++) {  
	               boolean bit = ((b   >> (7-i) & 1) == 1);  
	               boolean c15 = ((crc >> 15    & 1) == 1);  
	               crc <<= 1;  
	               if (c15 ^ bit) crc ^= polynomial;  
	            }  
	       }  
	       crc &= 0xffff;  
	       return crc;     
	}  

	public static Map<String, Object> generateImage(String imgStr, String path) {

		//data:image/jpeg;base64,
		if(imgStr == null)
			return null;
		String[] imgStrings = imgStr.split(",");
		String type = imgStrings[0].split("\\/")[1];
		type = type.split(";")[0];
		String name =""+ new Date().getTime()+PublicUtils.getRandom(0, 9)+"."+type;
		System.out.println(name);
		String realPath = ServletActionContext.getServletContext().getRealPath(path);
		PublicUtils.checkDir(realPath);
		realPath += "/" + name;
		path = "/task_sys"+path+"/" + name;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(imgStrings[1]);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(realPath);
			out.write(b);
			out.flush();
			out.close();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("path", path);
			map.put("size", b.length);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getRandom(int min,int max){
		Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	public static String uploadFile(File file,String fileName,String path,boolean isResetName){
		if(file == null || fileName == null || fileName.split("\\.").length < 2)
			return null;
		
		if(isResetName){
			String type = fileName.split("\\.")[1];
			fileName =""+ new Date().getTime()+PublicUtils.getRandom(0, 9)+"."+type;
		}
		String realPath = ServletActionContext.getServletContext().getRealPath(path); 
		PublicUtils.checkDir(realPath);
		realPath += "/" + fileName;
		path = "/task_sys"+path+"/" + fileName;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File serverFile = new File(realPath);
			FileUtils.copyFile(file, serverFile);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getServerRootPath(){
		return ServletActionContext.getServletContext().getRealPath("/"); 
	}
	
	public static void checkDir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public static boolean deleteFileFromServer(String path){
		if(!path.equals("/task_sys/assets/images/100.jpg")){
			File file = new File(PublicUtils.getServerRootPath()+path);
			if(file.exists()){
				return file.delete();
			}
		}
		return true;
	}
}
