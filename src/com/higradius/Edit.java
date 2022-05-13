package com.higradius;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HashMap<Object,Object> Response=new HashMap<Object,Object>();
			String invoice_currency = request.getParameter("invoice_currency");
			String cust_payment_terms = request.getParameter("cust_payment_terms");
			String sl_no = request.getParameter("sl_no");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grey_goose","root","root");
			String sql_statement="UPDATE winter_internship SET invoice_currency=?,cust_payment_terms=? WHERE sl_no=?";
			PreparedStatement st=con.prepareStatement(sql_statement);
			st.setString(1, invoice_currency);
			st.setString(2, cust_payment_terms);
			st.setString(3, sl_no);

			if(st.executeUpdate()>0)
			{
				Response.put("update",true);
			}else {	Response.put("update",false);
			}
			Gson gson=new Gson();
			response.setHeader("Access-Control-Allow-Origin","*");
			String Responsejson=gson.toJson(Response);
			response.getWriter().append(Responsejson);

					}catch (Exception e) {
						e.printStackTrace();
					}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
