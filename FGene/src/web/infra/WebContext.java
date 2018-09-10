package web.infra;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import core.FileStreamController;

@WebListener
public class WebContext implements ServletContextListener{

	private FileStreamController ctrl = new FileStreamController();
	
	public void contextInitialized(ServletContextEvent event) {
		if(!ctrl.load()){
			ctrl.start();
		}
		Initializer.updatePilots();
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		//ctrl.save();
	}
}
