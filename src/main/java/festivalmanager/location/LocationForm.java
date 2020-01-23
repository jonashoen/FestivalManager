package festivalmanager.location;

public class LocationForm extends Location {
	
	/**
	 *
	 * @param zone : name of this location
	 * @param address : address of this location
	 * @param price : price of this location
	 * @param maxVisitors : amount of maximum visitors
	 * @param thumbnail : thumbnail path of this locaiton
	 * @param groundPlan : ground plan path of this locaiton
	 */
	public LocationForm(String name, String address, Integer price, Integer maxVisitors, String thumbnail, String groundPlan) {
		super(name, address, price, maxVisitors, thumbnail, groundPlan);
	}

	/**
	 *
	 * @return new Location object
	 */
	public Location toLocation() {
		return new Location(getName(), getAddress(), getPrice(), getMaxVisitors(), getThumbnail(), getGroundPlan());
	}
}