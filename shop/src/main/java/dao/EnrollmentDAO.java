package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.EnrollmentBean;

public class EnrollmentDAO {
	DataSource ds;
	public EnrollmentDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public Map<String, EnrollmentBean> retrieve() throws SQLException{
		String query = "select * from enrollment";
		Map<String, EnrollmentBean> rv = new HashMap<String, EnrollmentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		List<String> students = new ArrayList<>();
		String cid = null;
		while (r.next()){
			String classID = r.getString("CID");
			String cseID = r.getString("SID");
			int credit = r.getInt("CREDIT");
			if (cid == null || cid != classID) {
				cid = classID;
			} 
			if(cid == classID) {
				students.add(cseID);
			}
			System.out.println("ClassId=" + classID + " SID=" + cseID + " credit=" + credit);
			rv.put(cid, new EnrollmentBean(cid, students, credit));
			students = new ArrayList<>();
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}
