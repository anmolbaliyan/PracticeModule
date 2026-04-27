package in.com.practice.bean;

public class GeoFenceBean {

	private long geoFenceId;
	private String geoFenceCode;
	private String locationName;
	private int radius;
	private String status;

	public long getGeoFenceId() {
		return geoFenceId;
	}

	public void setGeoFenceId(long geoFenceId) {
		this.geoFenceId = geoFenceId;
	}

	public String getGeoFenceCode() {
		return geoFenceCode;
	}

	public void setGeoFenceCode(String geoFenceCode) {
		this.geoFenceCode = geoFenceCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
