package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ItemBean;
import bean.StudentBean;
import dao.FilterDAO;
import dao.ShowDAO;
import model.SisModel;

/**
 * Servlet implementation class Sis
 */
@WebServlet({"/Sis", "/Sis/*"})
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Map<String, ItemBean> cart = new HashMap<String, ItemBean>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sis() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
    	
    }
    
    public void init(ServletConfig config) throws ServletException { 
    	 super.init(config); 
//    	 ServletContext context = getServletContext(); 
//    	 
//    	 SisModel model = SisModel.getInstance();
//    	 context.setAttribute("sisModel", model); 
//    	 try { 
//    		 SisModel model = SisModel.getInstance(); 
//    		 this.getServletContext().setAttribute("sisModel", model);
//    		 
//    	 } catch (ClassNotFoundException e) { 
//    		 throw new ServletException("Class Not Found" + e); 
//    	 } 
    } 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Writer resOut = response.getWriter();
		ServletContext context = request.getServletContext();
		String surname = request.getParameter("surname");
		
		
		request.getSession().setAttribute("surname", surname);
		
		// Item view part
		System.out.println(request.getPathInfo());
		if (request.getPathInfo() != null && request.getPathInfo().indexOf("Ajax") >= 0) {
			response.setContentType("application/json");
			System.out.println("Ajax call"); //this is for testing at server side...
			 //here we return json data note the escape characters \”
			
			try {
				// for table jason not using the itemModel.java
				FilterDAO t = new FilterDAO();
				Map<String, ItemBean> itemResult = new HashMap<String, ItemBean>();
				itemResult = t.retrieveType(surname);
				System.out.println(itemResult);
				if(itemResult.isEmpty()) {
					System.out.println("1");
					itemResult = t.retrieveBrand(surname);
					System.out.println(itemResult);
				}
				resOut.append("[");
				int indexCount = 1;
				for(ItemBean i : itemResult.values()) {
					resOut.append("{\"Bid\":");
					resOut.append("\"" + i.getBid() + "\"");
					resOut.append(",\"Item Name\":");
					resOut.append("\"" + i.getName() + "\"");
					resOut.append(",\"Type\":");
					resOut.append("\"" + i.getType() + "\"");
					resOut.append(",\"Brand\":");
					resOut.append("\"" + i.getBrand() + "\"");
					resOut.append(",\"Quantity\":");
					resOut.append(Integer.toString(i.getQuantity()));
					resOut.append(",\"Price\":");
					resOut.append(Integer.toString(i.getPrice()));
					resOut.append("}");
					System.out.println("1  The type: " + i.getType() + ", BID: " + i.getBid() + ", Name: " + i.getName() + ", Brand: " + i.getBrand());
					if(indexCount < itemResult.size()) resOut.append(",");
					indexCount++;
				}
				
				resOut.append("]");
				resOut.flush();
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Shopping cart showAll
		System.out.println(request.getPathInfo());
		if (request.getPathInfo() != null && request.getPathInfo().indexOf("ShowAll") >= 0) {
			response.setContentType("application/json");
			System.out.println("showAll call"); //this is for testing at server side...
			 //here we return json data note the escape characters \”
			
			try {
				// for table jason not using the itemModel.java
				ShowDAO s = new ShowDAO();
				Map<String, ItemBean> itemResult = new HashMap<String, ItemBean>();
				itemResult = s.showAll();
				System.out.println(itemResult);
				resOut.append("[");
				int indexCount = 1;
				for(ItemBean i : itemResult.values()) {
					resOut.append("{\"Bid\":");
					resOut.append("\"" + i.getBid() + "\"");
					resOut.append(",\"Item Name\":");
					resOut.append("\"" + i.getName() + "\"");
					resOut.append(",\"Type\":");
					resOut.append("\"" + i.getType() + "\"");
					resOut.append(",\"Brand\":");
					resOut.append("\"" + i.getBrand() + "\"");
					resOut.append(",\"Quantity\":");
					resOut.append(Integer.toString(i.getQuantity()));
					resOut.append(",\"Price\":");
					resOut.append(Integer.toString(i.getPrice()));
					resOut.append("}");
					System.out.println("1  The type: " + i.getType() + ", BID: " + i.getBid() + ", Name: " + i.getName() + ", Brand: " + i.getBrand());
					if(indexCount < itemResult.size()) resOut.append(",");
					indexCount++;
				}
				
				resOut.append("]");
				resOut.flush();
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Shopping cart add
				System.out.println(request.getPathInfo());
				if (request.getPathInfo() != null && request.getPathInfo().indexOf("add") >= 0) {
					response.setContentType("application/json");
					System.out.println("add call"); //this is for testing at server side...
					 //here we return json data note the escape characters \”
					
					try {
						// for table jason not using the itemModel.java
						FilterDAO t = new FilterDAO();
						Map<String, ItemBean> tmp = new HashMap<String, ItemBean>();
						tmp = t.search(surname);
						System.out.println(tmp);
						if(tmp.isEmpty()) {
							tmp = t.retrieveBrand(surname);
						}
						//add tmp into cart hash map
						for(ItemBean i : tmp.values()) {
							cart.put(Integer.toString(cart.size()+1), new ItemBean(i.getBid(), i.getName(), i.getDescription(), i.getType(), i.getBrand(), i.getQuantity(), i.getPrice()));
						}
						
						resOut.append("[");
						int indexCount = 1;
						for(ItemBean i : cart.values()) {
							resOut.append("{\"Bid\":");
							resOut.append("\"" + i.getBid() + "\"");
							resOut.append(",\"Item Name\":");
							resOut.append("\"" + i.getName() + "\"");
							resOut.append(",\"Type\":");
							resOut.append("\"" + i.getType() + "\"");
							resOut.append(",\"Brand\":");
							resOut.append("\"" + i.getBrand() + "\"");
							resOut.append(",\"Quantity\":");
							resOut.append(Integer.toString(1));
							resOut.append(",\"Price\":");
							resOut.append(Integer.toString(i.getPrice()));
							resOut.append("}");
							System.out.println("1  The type: " + i.getType() + ", BID: " + i.getBid() + ", Name: " + i.getName() + ", Brand: " + i.getBrand());
							if(indexCount < cart.size()) resOut.append(",");
							indexCount++;
						}
						
						resOut.append("]");
						resOut.flush();
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// Shopping cart delete
				System.out.println(request.getPathInfo());
				if (request.getPathInfo() != null && request.getPathInfo().indexOf("clear") >= 0) {
					response.setContentType("application/json");
					System.out.println("clear call"); //this is for testing at server side...
					 //here we return json data note the escape characters \”
					cart.clear();
					
					
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
