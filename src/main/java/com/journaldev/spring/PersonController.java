package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() {
		connectServer();
		connectServer2();
		return "Welcome to Spring Boot REST...";
	}
	
	public void connectServer() {
	    String host="ec2-52-206-61-127.compute-1.amazonaws.com";
	    String user="ubuntu";
	    String password="ubuntu";
	    String command1="ls -ltr";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";
	    try{
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
	    	Session session=jsch.getSession(user, host, 22);	
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");			
	    	session.setConfig(config);
			session.setPassword(password);
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command1);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}
	
	public void connectServer2() {
	    String host="ec2-52-206-61-127.compute-1.amazonaws.com";
	    String user="ubuntu";
	    String password="ubuntu";
	    String command1="sudo apt-get update";
		String command2="sudo apt-get -y install apache2";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";
	    try{
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
	    	Session session=jsch.getSession(user, host, 22);	
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");			
	    	session.setConfig(config);
			session.setPassword(password);
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command1);
			((ChannelExec)channel).setCommand(command2);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}
	
}
