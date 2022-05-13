package com.higradius;

import java.io.BufferedReader;
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
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class Add_invoice
 */
@SuppressWarnings("unused")
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String DB_URI = "jdbc:mysql://localhost:3306/grey_goose";
		String USERNAME = "root";
		String PASSWORD = "root";
		try {
			HashMap<Object, Object> Response = new HashMap<Object, Object>();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URI, USERNAME, PASSWORD);
			
			String businessCode = request.getParameter("business_code");
			String customerNumber = request.getParameter("cust_number");
			String clearDate = request.getParameter("clear_date");
			String buisnessYear = request.getParameter("buisness_year");
			String docId = request.getParameter("doc_id");
			String postingDate = request.getParameter("posting_date");
			String documentCreateDate = request.getParameter("document_create_date");
			String dueDate = request.getParameter("due_in_date");
			String invoiceCurrency = request.getParameter("invoice_currency");
			String documentType = request.getParameter("document_type");
			String postingId = request.getParameter("posting_id");
			String openAmount = request.getParameter("total_open_amount");
			String baselineCreateDate = request.getParameter("baseline_create_date");
			String customerPaymentTerms = request.getParameter("cust_payment_terms");
			String invoiceId = request.getParameter("invoice_id");
			
			String sql_statement = "INSERT INTO winter_internship (business_code, cust_number, clear_date, buisness_year, doc_id, posting_date, document_create_date, due_in_date, invoice_currency, document_type, posting_id, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement st = con.prepareStatement(sql_statement);
			st.setString(1, businessCode);
			st.setString(2, customerNumber);
			st.setString(3, clearDate);
			st.setString(4, buisnessYear);
			st.setString(5,  docId);
			st.setString(6, postingDate);
			st.setString(7, documentCreateDate);
			st.setString(8, dueDate);
			st.setString(9, invoiceCurrency);
			st.setString(10, documentType);
			st.setString(11, postingId);
			st.setString(12, openAmount);
			st.setString(13, baselineCreateDate);
			st.setString(14, customerPaymentTerms);
			st.setString(15, invoiceId);
			//System.out.println(st);
			
			if(st.executeUpdate()>0) {
				Response.put("insert",true);
			}else {
				Response.put("insert", false);
			}
			
			Gson gson = new Gson();
			String JSONresponse = gson.toJson(Response);
			response.setHeader("Access-Control-Allow-Origin", "*");
			
			response.getWriter().append(JSONresponse);
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