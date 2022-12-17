package bean;

public class AddressBean {
	private int aid;
	private String street;
	private String province;
	private String country;
	private String zip;
	private String phone;
	public AddressBean(int aid, String street, String province, String country, String zip, String phone) {
		// TODO Auto-generated constructor stub
		super();
		this.setAid(aid);
		this.setStreet(street);
		this.setProvince(province);
		this.setCountry(country);
		this.setZip(zip);
		this.setPhone(phone);
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
