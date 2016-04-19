package com.tyl.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tyl.text.SearchText;

/**
 * Servlet implementation class TextSearchServlet
 */
public class TextSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TextSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String basePath = request.getSession().getServletContext().getRealPath("") ;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String query =request.getParameter("query");
		System.out.println(query);
		String index = basePath + File.separator + ".."+File.separator+"lucenceWeb_data\\lucenceWeb_text_index\\";
		list = new SearchText().search(query,index);
		request.setAttribute("queryResult", list);
		request.setAttribute("query", query);
		RequestDispatcher dispatcher = request.getRequestDispatcher("showText.jsp");   
        dispatcher.forward(request, response);    
	}

}
