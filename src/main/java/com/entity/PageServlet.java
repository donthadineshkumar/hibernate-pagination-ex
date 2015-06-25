package com.entity;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


@WebServlet(name="pageme",urlPatterns="/pageme")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SessionFactory factory;

	@Override
public void init() throws ServletException {
	Configuration cfg = new Configuration();
	cfg.configure("hibernate.cfg.xml");
	
	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	factory = cfg.buildSessionFactory(reg);
}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

			int pageNo =Integer.parseInt(request.getParameter("pno"));
		
			Session s= factory.openSession();
			/*Query q = s.createQuery("select a.name from Actor a");*/
			
			// using the Criteria Query
			Criteria cr = s.createCriteria(Actor.class);
		
			int maxRes = 3;
			int firstRes = maxRes*pageNo - 3;
			
/*			q.setFirstResult(firstRes);
			q.setMaxResults(maxRes);*/
			
			cr.setFirstResult(firstRes);
			cr.setMaxResults(maxRes);
			
			System.out.println("maxRes "+maxRes+" firstRes "+firstRes);
			
			List<Actor> actors =cr.list();
			
			for(Actor a: actors){
				response.getWriter().println(a.getName());
			}
			firstRes=0;
	}
	@Override
	public void destroy() {
		factory.close();
	}

}
