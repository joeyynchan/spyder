package com.journaldev.mongodb.listener;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;

@WebListener
public class EventTimesContextListener implements ServletContextListener {

	private ScheduledExecutorService scheduler;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();
		System.out.println("Scheduler shut down");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		StartAndStopEvents runnable = new StartAndStopEvents();
		try {
			runnable.setClient(new MongoClient(
						sce.getServletContext().getInitParameter("MONGODB_HOST"), 
						Integer.parseInt(sce.getServletContext().getInitParameter("MONGODB_PORT"))));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("STASRTeDS");
		scheduler.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.SECONDS);
	}

}
