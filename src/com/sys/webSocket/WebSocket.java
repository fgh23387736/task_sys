package com.sys.webSocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
//import org.springframework.web.socket.server.endpoint.SpringConfigurator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sys.entity.Admin;
import com.sys.entity.ChatRecord;
import com.sys.entity.User;
import com.sys.service.AdminService;
import com.sys.service.ChatRecordService;
import com.sys.util.PublicUtils;

@ServerEndpoint(value = "/websocket/{param}", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {

	private Session session;
	private HttpSession httpSession;
	public static Vector<Session> room = new Vector<Session>();
	public static Map<Integer, Session> userSessions = new HashMap<>();
	private Integer theParam = null;
	
	private ChatRecordService chatRecordService = null;
	private User loginUser = null;
	private Admin loginAdmin = null;

	public static void sendByWebSocket(Integer[] receivers, String type, Admin sendAdmin,User sendUser,Object content,User loginUser,Admin loginAdmin) {
		Integer typeIndex = null;
		switch (type) {
		case "newNotice":
			typeIndex = 0;
			break;
		case "newPrivateMessage":
			typeIndex = 1;
			break;
		case "newTask":
			typeIndex = 2;
			break;
		case "newChat":
			typeIndex = 3;
			break;
		case "receivedChat":
			typeIndex = 4;
			break;
		default:
			break;
		}
		if (typeIndex != null) {
			Map<String, Object> webSocketMap = new HashMap();
			webSocketMap.put("type", typeIndex);
			webSocketMap.put("content", content);
			if (receivers == null) {
				for (Map.Entry<Integer, Session> entry : userSessions
						.entrySet()) {
					try {
						if(loginUser != null && loginUser.getUserId() != entry.getKey()){
							entry.getValue().getBasicRemote()
							.sendText(JSON.toJSONString(webSocketMap));
						}else if(loginAdmin != null){
							entry.getValue().getBasicRemote()
							.sendText(JSON.toJSONString(webSocketMap));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				for (Integer integer : receivers) {
					if(userSessions.containsKey(integer) ){
						try {
							if(loginUser != null && loginUser.getUserId() != integer){
								userSessions.get(integer).getBasicRemote().sendText(JSON.toJSONString(webSocketMap));
							}else if(loginAdmin != null){
								userSessions.get(integer).getBasicRemote()
								.sendText(JSON.toJSONString(webSocketMap));
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}

	@OnOpen
	public void onOpen(@PathParam(value = "param") String param,
			Session session, EndpointConfig config) throws IOException,
			InterruptedException {
		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		this.session = session;
		room.addElement(session);
		System.out.println("open:" + param);
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());
		if (httpSession != null) {
			this.loginUser = (User) httpSession.getAttribute("user"); // 如果已经登录,在别的action中已经将一个user对象存入session中,此处直接取出
			this.loginAdmin = (Admin) httpSession.getAttribute("admin");
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());
    		this.chatRecordService = (ChatRecordService)ctx.getBean("chatRecordService");
			if (loginUser != null) {
				if(userSessions.containsKey(loginUser.getUserId())){
					userSessions.get(loginUser.getUserId()).close();
				}
				userSessions.put(loginUser.getUserId(), session);
				theParam = loginUser.getUserId();
				System.out.println("isok");
			} else {
				this.session.close();
			}
		} else {
			this.session.close();
		}
	}

	@OnClose
	public void onClose() throws IOException, InterruptedException {

		room.remove(session);
		if (userSessions.containsKey(theParam)) {
			userSessions.remove(theParam);
		}
	}

	@OnMessage
	public void onMessage(String message, EndpointConfig config)
			throws IOException, InterruptedException {
		System.out.println(message);
		 JSONObject jsonObject = JSONObject.parseObject(message);
		 if(jsonObject.getString("type").equals("chatMessage")){
			 JSONObject res = jsonObject.getJSONObject("data");
			 Integer mineIdInteger = Integer.valueOf(res.getJSONObject("mine").getString("id"));
			 String content = res.getJSONObject("mine").getString("content");
			 Integer toIdInteger = Integer.valueOf(res.getJSONObject("to").getString("id"));
			 
			 ChatRecord chatRecord = new ChatRecord();
			 chatRecord.setContent(content);
			 chatRecord.setIsRead(false);
			 if(userSessions.containsKey(toIdInteger)){
				 chatRecord.setIsRead(true);
			 }
			 chatRecord.setReceiveUser(new User(toIdInteger));
			 chatRecord.setSendUser(new User(mineIdInteger));
			 chatRecord.setTime(new Date());
			 chatRecord = chatRecordService.add(chatRecord, loginUser);
			 if(chatRecord.getChatRecordId() != null){
				 if(userSessions.containsKey(toIdInteger)){
					 Map<String, Object> chatMessageMap = new HashMap<>();
					 chatMessageMap.put("username", res.getJSONObject("mine").getString("username"));
					 chatMessageMap.put("avatar", res.getJSONObject("mine").getString("avatar"));
					 chatMessageMap.put("id", res.getJSONObject("mine").getString("id"));
					 chatMessageMap.put("type", "friend");
					 chatMessageMap.put("content", content);
					 chatMessageMap.put("cid", chatRecord.getChatRecordId());
					 chatMessageMap.put("mine", false);
					 chatMessageMap.put("fromid", res.getJSONObject("mine").getString("id"));
					 chatMessageMap.put("timestamp", chatRecord.getTime().getTime());
					 
					 Map<String, Object> returnMap = new HashMap<>();
					 returnMap.put("type", "chatMessage");
					 returnMap.put("data", chatMessageMap);
					 userSessions.get(toIdInteger).getBasicRemote().sendText(JSON.toJSONString(returnMap));
				 }
			 }else{
				 
			 }
			 
		 }

	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		t.printStackTrace();
	}

}