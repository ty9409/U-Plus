package model;

public class StaffVO {

	// 직원 필드 선언
	private int sf_Num;
	private String sf_Rank;
	private String sf_Name;
	private String sf_Birth;
	private String sf_Phone;
	private String sf_Addre;
	private int sf_Basic;
	private int sf_Inct;
	private int sf_Total;
	private int sf_Sales;
	private String sf_Date;
	private String sf_OutDate;
	private String sf_Image;

	public StaffVO() {
	}

	public StaffVO(String sf_Rank, String sf_Name, String sf_Birth, String sf_Phone, String sf_Addre, int sf_Basic,
			int sf_Inct, int sf_Total, int sf_Sales, String sf_Date, String sf_OutDate, String sf_Image) {
		super();
		this.sf_Rank = sf_Rank;
		this.sf_Name = sf_Name;
		this.sf_Birth = sf_Birth;
		this.sf_Phone = sf_Phone;
		this.sf_Addre = sf_Addre;
		this.sf_Basic = sf_Basic;
		this.sf_Inct = sf_Inct;
		this.sf_Total = sf_Total;
		this.sf_Sales = sf_Sales;
		this.sf_Date = sf_Date;
		this.sf_OutDate = sf_OutDate;
		this.sf_Image = sf_Image;
	}

	public StaffVO(int sf_Num, String sf_Rank, String sf_Name, String sf_Birth, String sf_Phone, String sf_Addre,
			int sf_Basic, int sf_Inct, int sf_Total, int sf_Sales, String sf_Date, String sf_OutDate, String sf_Image) {
		super();
		this.sf_Num = sf_Num;
		this.sf_Rank = sf_Rank;
		this.sf_Name = sf_Name;
		this.sf_Birth = sf_Birth;
		this.sf_Phone = sf_Phone;
		this.sf_Addre = sf_Addre;
		this.sf_Basic = sf_Basic;
		this.sf_Inct = sf_Inct;
		this.sf_Total = sf_Total;
		this.sf_Sales = sf_Sales;
		this.sf_Date = sf_Date;
		this.sf_OutDate = sf_OutDate;
		this.sf_Image = sf_Image;
	}

	public int getSf_Num() {
		return sf_Num;
	}

	public void setSf_Num(int sf_Num) {
		this.sf_Num = sf_Num;
	}

	public String getSf_Rank() {
		return sf_Rank;
	}

	public void setSf_Rank(String sf_Rank) {
		this.sf_Rank = sf_Rank;
	}

	public String getSf_Name() {
		return sf_Name;
	}

	public void setSf_Name(String sf_Name) {
		this.sf_Name = sf_Name;
	}

	public String getSf_Birth() {
		return sf_Birth;
	}

	public void setSf_Birth(String sf_Birth) {
		this.sf_Birth = sf_Birth;
	}

	public String getSf_Phone() {
		return sf_Phone;
	}

	public void setSf_Phone(String sf_Phone) {
		this.sf_Phone = sf_Phone;
	}

	public String getSf_Addre() {
		return sf_Addre;
	}

	public void setSf_Addre(String sf_Addre) {
		this.sf_Addre = sf_Addre;
	}

	public int getSf_Basic() {
		return sf_Basic;
	}

	public void setSf_Basic(int sf_Basic) {
		this.sf_Basic = sf_Basic;
	}

	public int getSf_Inct() {
		return sf_Inct;
	}

	public void setSf_Inct(int sf_Inct) {
		this.sf_Inct = sf_Inct;
	}

	public int getSf_Total() {
		return sf_Total;
	}

	public void setSf_Total(int sf_Total) {
		this.sf_Total = sf_Total;
	}

	public int getSf_Sales() {
		return sf_Sales;
	}

	public void setSf_Sales(int sf_Sales) {
		this.sf_Sales = sf_Sales;
	}

	public String getSf_Date() {
		return sf_Date;
	}

	public void setSf_Date(String sf_Date) {
		this.sf_Date = sf_Date;
	}

	public String getSf_OutDate() {
		return sf_OutDate;
	}

	public void setSf_OutDate(String sf_OutDate) {
		this.sf_OutDate = sf_OutDate;
	}

	public String getSf_Image() {
		return sf_Image;
	}

	public void setSf_Image(String sf_Image) {
		this.sf_Image = sf_Image;
	}

}
