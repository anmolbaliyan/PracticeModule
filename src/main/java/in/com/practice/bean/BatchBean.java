package in.com.practice.bean;

public class BatchBean {

	private Long batchId;
	private String batchCode;
	private Integer totalMessages;
	private Integer processedCount;
	private String status;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Integer getTotalMessages() {
		return totalMessages;
	}

	public void setTotalMessages(Integer totalMessages) {
		this.totalMessages = totalMessages;
	}

	public Integer getProcessedCount() {
		return processedCount;
	}

	public void setProcessedCount(Integer processedCount) {
		this.processedCount = processedCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}