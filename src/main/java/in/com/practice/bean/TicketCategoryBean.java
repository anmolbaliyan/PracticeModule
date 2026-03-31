package in.com.practice.bean;

public class TicketCategoryBean {
	
	private Long ticketCategoryId;
	private String categoryName;
	private Double price;
	private Integer availableSeats;

	public Long getTicketCategoryId() {
		return ticketCategoryId;
	}

	public void setTicketCategoryId(Long ticketCategoryId) {
		this.ticketCategoryId = ticketCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

}
