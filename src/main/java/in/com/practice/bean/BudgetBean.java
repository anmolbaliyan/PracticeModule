package in.com.practice.bean;

public class BudgetBean {

	private Long budgetId;
	private Double allocatedAmount;
	private Double spentAmount;
	private String department;

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public Double getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(Double allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Double getSpentAmount() {
		return spentAmount;
	}

	public void setSpentAmount(Double spentAmount) {
		this.spentAmount = spentAmount;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
