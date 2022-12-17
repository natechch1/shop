package model;

import java.util.Map;

import com.google.gson.Gson;

import dao.EnrollmentDAO;
import dao.StudentDAO;
import bean.StudentBean;
import bean.EnrollmentBean;

public class SisModel {
	private static SisModel instance;
	private StudentDAO studentData;
	private EnrollmentDAO enrollmentData;
	
	public static SisModel getInstance() throws ClassNotFoundException{ 
		 if (instance==null) { 
			 instance =new SisModel();
			 instance.studentData = new StudentDAO();
			 instance.enrollmentData = new EnrollmentDAO();
		 } 
		 return instance; 
	 }
	
	private SisModel() {
		
	}

	public Map<String, StudentBean> retriveStudent(String namePrefix, String credit_taken) throws Exception{
		if (namePrefix == null) {
			throw new Exception("Empty! Please fill in the surname");
		}
		if (credit_taken == null) {
			throw new Exception("Empty! Please fill in the credit taken");
		}
		int credit = Integer.parseInt(credit_taken);
		StudentDAO sd = new StudentDAO();
		Map<String, StudentBean> temp = sd.retrieve(namePrefix, credit);
		return temp;
	}
	public Map<String, EnrollmentBean> retriveEnrollment() throws Exception{
		EnrollmentDAO ed = new EnrollmentDAO();
		Map<String, EnrollmentBean> temp = ed.retrieve();
		if (temp == null) {
			throw new Exception("Enrollment is empty");
		}
		return temp;
	}
	
	private String sanitizeNamePrefix(String namePrefix) throws Exception{
		if (namePrefix == null) {
			return "";
		}
		return namePrefix;
	}
	
	private int sanitizeCreditTaken(String credit_taken) throws Exception{
		int result = -1;
		if ((credit_taken.equals("")) || (credit_taken == null)) {
			return 0;
		}
		if (credit_taken.matches("[0-9]+")) {
			result = Integer.parseInt(credit_taken);
		}
		else {
			throw new Exception("CREDIT_TAKEN format is invalid");
		}
		if ((result < 0) || (result > 150)) {
			throw new Exception("CREDIT_TAKEN must be between 0-150");
		}
		return result;
	}
	
	public int create(String sid, String givenname, String surname, String credittaken, String creditgraduate) throws Exception{
		return this.studentData.insert(sanitizeNamePrefix(sid), sanitizeNamePrefix(givenname), sanitizeNamePrefix(surname), sanitizeCreditTaken(credittaken), sanitizeCreditTaken(creditgraduate));
	}
	
	public int delete(String sid) throws Exception{
		return this.studentData.delete(sanitizeNamePrefix(sid));
	}
	
//	public String getStudentAsJson(String text) {
//		Gson gson=new Gson();
//		String result = gson.toJson(text);
//        return result;
//	}
}
