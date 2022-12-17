package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext; 
import javax.naming.NamingException; 
import javax.sql.DataSource;

import bean.StudentBean; 

 

public class StudentDAO {
	DataSource ds;
	public StudentDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//	public void readAndPrintTableToConsole() throws SQLException { 
//		try { 
//			DataSource ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS"); 
//			Connection con = ds.getConnection(); 
//			Statement stmt = con.createStatement(); 
//
// 
//
//			ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS"); 
//			while(rs.next()){ 
//				String em= rs.getString("SID"); 
//				String fname = rs.getString("SURNAME"); 
//				System.out.println("\t" + em+ ",\t" + fname+ "\t "); 
//			}//end while loop 
//
// 
//
//			con.close(); 
//		} catch (NamingException e) { 
//			e.printStackTrace(); 
//		}
//	}
	public Map<String, StudentBean> retrieve(String namePrefix, int credit_taken) throws SQLException{
		String query = "select * from students where surname like '%" + namePrefix +"%'and credit_taken >= " + credit_taken;
		Map<String, StudentBean> rv = new HashMap<String, StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String cseID = r.getString("SID");
			int credit_takens = r.getInt("CREDIT_TAKEN");
			int credit_graduate = r.getInt("CREDIT_GRADUATE");
			rv.put(cseID, new StudentBean(cseID, name, credit_takens, credit_graduate));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public int insert(String sid, String givenname, String surname, int credittake, int creditgraduate) throws SQLException, NamingException{
		String query = "insert into students values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, sid);
		stmt.setString(2, givenname);
		stmt.setString(3, surname);
		stmt.setInt(4, credittake);
		stmt.setInt(5, creditgraduate);
		
		return stmt.executeUpdate();
	}
	
	public int delete(String sid) throws SQLException, NamingException{
		String query = "delete from students where sid=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, sid);
		return stmt.executeUpdate();
	}
}