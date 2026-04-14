package in.com.practice.bean;

import java.util.Date;

public class LabTestBean {

	private Long labTestId;
	private String testName;
	private Double cost;
	private Date testDate;

	public Long getLabTestId() {
		return labTestId;
	}

	public void setLabTestId(Long labTestId) {
		this.labTestId = labTestId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

}
