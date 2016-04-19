package com.tyl.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NextServlet
 */
public class NextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NextServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String basePath = request.getSession().getServletContext().getRealPath("") ;
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String query =request.getParameter("query");
		String txtName =request.getParameter("key");
		String[] txtNameSp = txtName.split("_");
		String bookName = txtNameSp[0];
		String totalBumber = txtNameSp[1];
		int n = Integer.parseInt(totalBumber);
		n++;
		
		String index = basePath + File.separator + ".."+File.separator+"lucenceWeb_data\\lucenceWeb_text\\";
		File bookNameFile = new File(index + bookName );// book name
		File[] bookNameFiles = bookNameFile.listFiles();
		for(int i = 0; i < bookNameFiles.length; i++){
			String bn = bookNameFiles[i].getName();
			String[] bnsStrings = bn.split("_");
			if(bnsStrings[1].equals(""+n)|| (n+"")==bnsStrings[1] ){
				txtName = bn;
			}
		}
		File file = new File(index + bookName + File.separator + txtName);
		if(file.exists()){
			String encoding="UTF-8";
			InputStreamReader read = new InputStreamReader(
            new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String all = "";
            while((lineTxt = bufferedReader.readLine()) != null){
                all = all + lineTxt;
            }
            request.setAttribute("text", all);
            request.setAttribute("query", query);
            request.setAttribute("title", txtName);
            RequestDispatcher dispatcher = request.getRequestDispatcher("detail.jsp");   
            dispatcher.forward(request, response); 
            read.close();
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
