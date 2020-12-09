package de.java2enterprise.onlineshop;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.java2enterprise.onlineshop.ejb.SellBeanLocal;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@EJB
	private SellBeanLocal sellBeanLocal;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String id = request.getParameter("id");

			response.reset();
			response.getOutputStream().write(sellBeanLocal.getPhoto(Long.parseLong(id)));

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

}
