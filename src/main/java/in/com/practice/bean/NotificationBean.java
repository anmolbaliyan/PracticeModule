package in.com.practice.bean;

public class NotificationBean {

	private Long prefId;
	private String prefCode;
	private String userName;
	public String preference;
	private String status;

	public Long getPrefId() {
		return prefId;
	}

	public void setPrefId(Long prefId) {
		this.prefId = prefId;
	}

	public String getPrefCode() {
		return prefCode;
	}

	public void setPrefCode(String prefCode) {
		this.prefCode = prefCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
