package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.ItemBean;

public class ShowDAO {
	DataSource ds;
	public ShowDAO() throws ClassNotFoundException{
		// TODO Auto-generated constructor stub
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	public Map<String, ItemBean> showAll() throws SQLException{
		String query = "select * from item";
		Map<String, ItemBean> rv = new HashMap<String, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			String bid = r.getString("BID");
			String name = r.getString("NAME");
			String type = r.getString("TYPE");
			String description = r.getString("DESCRIPTION");
			String brand = r.getString("BRAND");
			int quantity = r.getInt("QUANTITY");
			int price = r.getInt("PRICE");
			rv.put(bid, new ItemBean(bid, name, description, type, brand, quantity, price));
//			System.out.println("1  The type: " + type + ", BID: " + bid + ", Name: " + name + ", Brand: " + brand);
			
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public Map<String, ItemBean> retrieveBrand(String brand) throws SQLException{
		String query = "select * from item where brand like '%" + brand + "%'";
		Map<String, ItemBean> rv = new HashMap<String, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()){
			String bid = r.getString("BID");
			String name = r.getString("NAME");
			String description = r.getString("DESCRIPTION");
			String type = r.getString("TYPE");
			int quantity = r.getInt("QUANTITY");
			int price = r.getInt("PRICE");
			rv.put(bid, new ItemBean(bid, name, description, type, brand, quantity, price));
//			System.out.println("1  The Brand: " + brand + ", BID: " + bid + ", Name: " + name + ", Brand: " + brand);
			
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

}
