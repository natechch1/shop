package restService;

import java.util.Map;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bean.StudentBean;
import model.SisModel;

@Path("students") //this is the path of the resource 
public class StudentController {
	
	SisModel model;
	
	public StudentController() throws ServletException {
		
		try { 
   		 model = SisModel.getInstance(); 
   		 
   	 	} catch (ClassNotFoundException e) { 
   	 		throw new ServletException("Class Not Found" + e); 
   	 	} 

	}
	@GET 
	@Produces(MediaType.APPLICATION_JSON) 
	public String getStudent(
			@QueryParam("namePrefix") String name, 
			@QueryParam("credits") String credits) throws Exception { 
	//add the body of the method here… you can try in simple steps, //for example, just 
	//return the query parameter so you can see //it works.. 
	//you should call a method from the model in the end and format //the result as JSON. 
		String error = "";
		Map<String, StudentBean> students = null;
		try {
			students = model.retriveStudent(name, credits);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error = e.getMessage();
		}
		String text = "";
		text += "[";
		int count = 1;
		for (Map.Entry<String, StudentBean> mp: students.entrySet()) {
			text += "{\"Student Name\":";
			text += "\"" + students.get(mp.getKey()).getName() + "\"";
			text += ",\"Credit Taken\":";
			text += Integer.toString(students.get(mp.getKey()).getCredit_taken());
			text += ",\"Credits to Graduate\":";
			text += Integer.toString(students.get(mp.getKey()).getCredit_graduate());
			text += "}";
			if (count != students.size()) {
				text += ",";
			}
			count++;
		}
		text += "]";
		return text;
	} 
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createStudent(@QueryParam("sid")String sid,
	@QueryParam("givenName")String givenname, @QueryParam("surName")String
	surname, @QueryParam("creditTaken")String credittaken,
	@QueryParam("creditGraduate")String creditgraduate )
	{
	//add the body of the method here… you can try in simple steps, //for example, just
	//return the query parameters so you can see //it works..
	//later, you should call a method from the model, //SIS.getInstance().create(….)
		System.out.println("received:" + sid + " " + givenname + " " + surname + " " + credittaken + " " + creditgraduate);
		int rowInserted = 0;
		try {
			rowInserted = model.create(sid, givenname, surname, credittaken, creditgraduate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "insertRows: " + rowInserted + "\n";
		return Response.status(200).entity(result).build();
	}
	@DELETE
	@Path("/{sid}/")
	@Consumes(MediaType.TEXT_PLAIN) 
	public Response delete(@PathParam("sid")String sid) { 
	//add the body of the method here.. 
	//you should call a method from the model, //SIS.getInstance().delete(….)
		System.out.println("received:" + sid);
		int rowDeleted = 0;
		try {
			rowDeleted = model.delete(sid);
		} catch(Exception e) {
			e.printStackTrace();
		}
		String result = "deletedRows: " + rowDeleted + "\n";
		return Response.status(200).entity(result).build();
	} 
}
