package model;

public class StockVO {

	// 재고 필드 선언
	private int sc_Num;
	private String sc_Model;
	private int sc_Amount;
	private int sc_Price;
	private String sc_Company;
	private String sc_Date;
	private String sc_Image;

	public StockVO() {
	}

	public StockVO(String sc_Model, int sc_Amount, int sc_Price, String sc_Company, String sc_Date, String sc_Image) {
		super();
		this.sc_Model = sc_Model;
		this.sc_Amount = sc_Amount;
		this.sc_Price = sc_Price;
		this.sc_Company = sc_Company;
		this.sc_Date = sc_Date;
		this.sc_Image = sc_Image;
	}

	public StockVO(int sc_Num, String sc_Model, int sc_Amount, int sc_Price, String sc_Company, String sc_Date,
			String sc_Image) {
		super();
		this.sc_Num = sc_Num;
		this.sc_Model = sc_Model;
		this.sc_Amount = sc_Amount;
		this.sc_Price = sc_Price;
		this.sc_Company = sc_Company;
		this.sc_Date = sc_Date;
		this.sc_Image = sc_Image;
	}

	public int getSc_Num() {
		return sc_Num;
	}

	public void setSc_Num(int sc_Num) {
		this.sc_Num = sc_Num;
	}

	public String getSc_Model() {
		return sc_Model;
	}

	public void setSc_Model(String sc_Model) {
		this.sc_Model = sc_Model;
	}

	public int getSc_Amount() {
		return sc_Amount;
	}

	public void setSc_Amount(int sc_Amount) {
		this.sc_Amount = sc_Amount;
	}

	public int getSc_Price() {
		return sc_Price;
	}

	public void setSc_Price(int sc_Price) {
		this.sc_Price = sc_Price;
	}

	public String getSc_Company() {
		return sc_Company;
	}

	public void setSc_Company(String sc_Company) {
		this.sc_Company = sc_Company;
	}

	public String getSc_Date() {
		return sc_Date;
	}

	public void setSc_Date(String sc_Date) {
		this.sc_Date = sc_Date;
	}

	public String getSc_Image() {
		return sc_Image;
	}

	public void setSc_Image(String sc_Image) {
		this.sc_Image = sc_Image;
	}

}
