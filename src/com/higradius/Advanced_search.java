package com.higradius;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.cj.protocol.Resultset;

@SuppressWarnings("unused")
@WebServlet("/Advanced_search")
public class Advanced_search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String DB_URI = "jdbc:mysql://localhost:3306/grey_goose";
	String USERNAME = "root";
	String PASSWORD = "root";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Advanced_search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HashMap<Object, Object> Response = new HashMap<Object, Object>();
		String doc_id = request.getParameter("doc_id");
		int cust_number = Integer.parseInt(request.getParameter("cust_number"));
		int invoice_id =  Integer.parseInt(request.getParameter("invoice_id"));
		String buisness_year = (request.getParameter("buisness_year"));
		
		ArrayList<Response> Invoices = new ArrayList<Response>();
		Response inv = new Response();
		
		inv.setDoc_id(doc_id);
		inv.setCust_number(cust_number);
		inv.setInvoice_id(invoice_id);
		inv.setBuisness_year(buisness_year);
		
		
		String query = "SELECT * FROM winter_internship WHERE doc_id=? and cust_number=? and invoice_id=? and buisness_year=?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, USERNAME, PASSWORD);
			//Creating an PreparedStatement to edit data into an database
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, inv.getDoc_id());
			ps.setInt(2, inv.getCust_number());
			ps.setInt(3, inv.getInvoice_id());
			ps.setString(4, inv.getBuisness_year());
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Response invoice = new Response();
				
				invoice.setSl_no(rs.getInt("sl_no"));
				invoice.setBusiness_code(rs.getString("business_code"));
				invoice.setCust_number(rs.getInt("cust_number"));
				invoice.setClear_date(rs.getString("clear_date"));
				invoice.setBuisness_year(rs.getString("buisness_year"));
				invoice.setDoc_id(rs.getString("doc_id"));
				invoice.setPosting_date(rs.getString("posting_date"));
				invoice.setDocument_create_date(rs.getString("document_create_date"));
				invoice.setDue_in_date(rs.getString("due_in_date"));
				invoice.setInvoice_currency(rs.getString("invoice_currency"));
				invoice.setDocument_type(rs.getString("document_type"));
				invoice.setBaseline_create_date(rs.getString("baseline_create_date"));
				invoice.setCust_payment_terms(rs.getString("cust_payment_terms"));
				invoice.setInvoice_id(rs.getInt("invoice_id"));

				Invoices.add(invoice);
			}
			Response.put("advance", Invoices);
			Gson gson = new Gson();
			response.setHeader("Access-Control-Allow-Origin", "*");
			String res = gson.toJson(Response);
		    response.getWriter().append(res); 
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		response.getWriter().write("Search Done");
	}

}