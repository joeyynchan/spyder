package com.journaldev.mongodb.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/deletePerson")
public class DeletePersonServlet extends HttpServlet {

	private static final long serialVersionUID = 6798036766148281767L;

/*	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for delete operation");
		}
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBMobileUsersDAO personDAO = new MongoDBMobileUsersDAO(mongo);
		MobileUser p = new MobileUser();
		p.setId(id);
		personDAO.deletePerson(p);
		System.out.println("Person deleted successfully with id=" + id);
		request.setAttribute("success", "Person deleted successfully");
		List<MobileUser> persons = personDAO.readAllPerson();
		request.setAttribute("persons", persons);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/persons.jsp");
		rd.forward(request, response);
	} */

}
