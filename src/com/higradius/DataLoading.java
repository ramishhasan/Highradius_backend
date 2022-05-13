package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class Data_fetch
 * @param <Invoice>
 */
@SuppressWarnings("unused")
@WebServlet("/DataLoading")
public class DataLoading extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataLoading() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:*");
		// TODO Auto-generated method stub
		
//		String DB_URI = "jdbc:mysql://localhost:3306/grey_goose";
//		String USERNAME = "root";
//		String PASSWORD = "abcd1234";
		
		HashMap<Object, Object> Response = new HashMap<Object, Object>();
		try {
			//registering the jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grey_goose", "root", "root");
			String query = "SELECT * from winter_internship";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery(query);
			
			ArrayList<Response> Invoices = new ArrayList<Response>();
			
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
				invoice.setPosting_id(rs.getString("posting_id"));
				invoice.setTotal_open_amount(rs.getInt("total_open_amount"));
				invoice.setBaseline_create_date(rs.getString("baseline_create_date"));
				invoice.setCust_payment_terms(rs.getString("cust_payment_terms"));
				invoice.setInvoice_id(rs.getInt("invoice_id"));

				Invoices.add(invoice);
			}
			Response.put("invoices", Invoices);
		    response.setContentType("application/json;charset=UTF-8");
			Gson gson = new Gson();
			response.setHeader("Access-Control-Allow-Origin", "*");
			String res = gson.toJson(Response);
		    response.getWriter().append(res);      
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}