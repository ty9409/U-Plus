package model;

public class AdministratorVO {

	private String ad_Id;
	private String ad_Pw;

	public AdministratorVO() {
	}

	public AdministratorVO(String ad_Id, String ad_Pw) {
		super();
		this.ad_Id = ad_Id;
		this.ad_Pw = ad_Pw;
	}

	public String getAd_Id() {
		return ad_Id;
	}

	public void setAd_Id(String ad_Id) {
		this.ad_Id = ad_Id;
	}

	public String getAd_Pw() {
		return ad_Pw;
	}

	public void setAd_Pw(String ad_Pw) {
		this.ad_Pw = ad_Pw;
	}

}
