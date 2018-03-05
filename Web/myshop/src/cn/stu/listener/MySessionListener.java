package cn.stu.listener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.stu.model.MySessionContext;

@WebListener
public class MySessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent arg0)  { 
    	MySessionContext.AddSession(arg0.getSession());
    }
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	MySessionContext.DelSession(arg0.getSession());
    }
	
}
