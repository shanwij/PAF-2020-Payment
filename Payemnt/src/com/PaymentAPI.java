package com;
import com.Payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PaymentAPI
 */
@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Payment PayObj = new Payment();
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = PayObj.addPayment(request.getParameter("cardHolderName"),
				request.getParameter("amount"),
				request.getParameter("creditCardNumber"),
				request.getParameter("year"),
				request.getParameter("month"),
				request.getParameter("cvc"));
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		String output = PayObj.UpdatePayment(paras.get("hidPaymentIDSave").toString(),
				paras.get("cardHolderName").toString(),
				paras.get("amount").toString(),
				paras.get("creditCardNumber").toString(),
				paras.get("year").toString(),
				paras.get("month").toString(),
				paras.get("cvc").toString());
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map paras = getParasMap(request);
		
		 String output = PayObj.DeletePayment(paras.get("paymentID").toString());
		
		response.getWriter().write(output); 
	}
	
	private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
						scanner.useDelimiter("\\A").next() : "";
						scanner.close();
						String[] params = queryString.split("&");
						for (String param : params)
						{ 
							String[] p = param.split("=");
							map.put(p[0], p[1]);
						}
			}
			catch (Exception e)
			{
			}
			return map;
		}


}
