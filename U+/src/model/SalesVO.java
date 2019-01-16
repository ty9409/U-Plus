package model;

public class SalesVO {

	// 판매정보 필드 선언
	private int sa_Num;
	private String sa_Name;
	private String sa_Phone;
	private String sc_Model;
	private String sa_Serial;
	private int sa_Plan;
	private String sa_Contract;
	private int sa_Inct;
	private String sf_Name;
	private String sa_Date;
	private String sa_Memo;

	public SalesVO() {
	}

	public SalesVO(String sa_Name, String sa_Phone, String sc_Model, String sa_Serial, int sa_Plan, String sa_Contract,
			int sa_Inct, String sf_Name, String sa_Date, String sa_Memo) {
		super();
		this.sa_Name = sa_Name;
		this.sa_Phone = sa_Phone;
		this.sc_Model = sc_Model;
		this.sa_Serial = sa_Serial;
		this.sa_Plan = sa_Plan;
		this.sa_Contract = sa_Contract;
		this.sa_Inct = sa_Inct;
		this.sf_Name = sf_Name;
		this.sa_Date = sa_Date;
		this.sa_Memo = sa_Memo;
	}

	public SalesVO(int sa_Num, String sa_Name, String sa_Phone, String sc_Model, String sa_Serial, int sa_Plan,
			String sa_Contract, int sa_Inct, String sf_Name, String sa_Date, String sa_Memo) {
		super();
		this.sa_Num = sa_Num;
		this.sa_Name = sa_Name;
		this.sa_Phone = sa_Phone;
		this.sc_Model = sc_Model;
		this.sa_Serial = sa_Serial;
		this.sa_Plan = sa_Plan;
		this.sa_Contract = sa_Contract;
		this.sa_Inct = sa_Inct;
		this.sf_Name = sf_Name;
		this.sa_Date = sa_Date;
		this.sa_Memo = sa_Memo;
	}

	public int getSa_Num() {
		return sa_Num;
	}

	public void setSa_Num(int sa_Num) {
		this.sa_Num = sa_Num;
	}

	public String getSa_Name() {
		return sa_Name;
	}

	public void setSa_Name(String sa_Name) {
		this.sa_Name = sa_Name;
	}

	public String getSa_Phone() {
		return sa_Phone;
	}

	public void setSa_Phone(String sa_Phone) {
		this.sa_Phone = sa_Phone;
	}

	public String getSc_Model() {
		return sc_Model;
	}

	public void setSc_Model(String sc_Model) {
		this.sc_Model = sc_Model;
	}

	public String getSa_Serial() {
		return sa_Serial;
	}

	public void setSa_Serial(String sa_Serial) {
		this.sa_Serial = sa_Serial;
	}

	public int getSa_Plan() {
		return sa_Plan;
	}

	public void setSa_Plan(int sa_Plan) {
		this.sa_Plan = sa_Plan;
	}

	public String getSa_Contract() {
		return sa_Contract;
	}

	public void setSa_Contract(String sa_Contract) {
		this.sa_Contract = sa_Contract;
	}

	public int getSa_Inct() {
		return sa_Inct;
	}

	public void setSa_Inct(int sa_Inct) {
		this.sa_Inct = sa_Inct;
	}

	public String getSf_Name() {
		return sf_Name;
	}

	public void setSf_Name(String sf_Name) {
		this.sf_Name = sf_Name;
	}

	public String getSa_Date() {
		return sa_Date;
	}

	public void setSa_Date(String sa_Date) {
		this.sa_Date = sa_Date;
	}

	public String getSa_Memo() {
		return sa_Memo;
	}

	public void setSa_Memo(String sa_Memo) {
		this.sa_Memo = sa_Memo;
	}

}
