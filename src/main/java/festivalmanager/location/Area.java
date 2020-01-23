package festivalmanager.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Area {
	private @Id @GeneratedValue long id;
	private long locationId;
	private String zone;
	private boolean blocked;
	private Integer currVisitors;
	private Integer maxVisitors;
	private Integer maxStages;
	private Type type;
	
	@SuppressWarnings("unused")
	private Area() {}
	
	/**
	 *
	 * @param zone : name of the area
	 * @param maxVisitors : amount of maximum visitors
	 * @param maxStages : amount of maximum stages
	 */
	public Area(String zone, Integer maxVisitors, Integer maxStages, Type type) {
		this.zone = zone;
		this.maxVisitors = maxVisitors;
		this.maxStages = maxStages;
		this.blocked = false;
		this.currVisitors = 0;
		this.type = type;
	}
	
	/**
	 *
	 * @return area id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 *
	 * @param id : The existing id will be changed to this id
	 * @return area id
	 */
	public long setId(long id) {
		return this.id = id;
	}
	
	/**
	 *
	 * @return location id
	 */
	public long getLocationId() {
		return locationId;
	}
	
	/**
	 *
	 * @param id : The existing location id will be changed to this location id
	 * @return location id
	 */
	public long setLocationId(long locationId) {
		return this.locationId = locationId;
	}
	
	/**
	 *
	 * @return area zone
	 */
	public String getZone() {
		return zone;
	}
	
	/**
	 *
	 * @param zone : The existing zone will be changed to this zone
	 * @return area zone
	 */
	public String setZone(String zone) {
		return this.zone = zone;
	}
	
	/**
	 *
	 * @return status of this area
	 */
	public boolean getStatus() {
		return blocked;
	}
	
	/**
	 *
	 * @return status of this area
	 */
	public boolean toggleLock() {
		blocked = !blocked;
		
		return blocked;
	}
	
	/**
	 *
	 * @param visitors : plus the number of visitors
	 * @return current visitors
	 */
	public Integer countVisitors(Integer visitors) {
		if(currVisitors + visitors < 0){
			return null;
		}
		
		return currVisitors += visitors;
	}
	
	/**
	 *
	 * @return current visitors
	 */
	public Integer getCurrVisitors() {
		return currVisitors;
	}
	
	/**
	 * @param currVisitros : The existing current visitors will be changed to this current visitors
	 * @return current visitors
	 */
	public Integer setCurrVisitors(Integer currVisitors) {
		return this.currVisitors = currVisitors;
	}
	
	/**
	 *
	 * @return maximum visitors
	 */
	public Integer getMaxVisitors() {
		return maxVisitors;
	}

	/**
	 *
	 * @param maxVisitors : The existing maximum visitors will be changed to this maximum visitors
	 * @return maximum visitors
	 */
	public Integer setMaxVisitors(Integer maxVisitors) {
		return this.maxVisitors = maxVisitors;
	}
	
	/**
	 *
	 * @return maximum stages
	 */
	public Integer getMaxStages() {
		return maxStages;
	}

	/**
	 *
	 * @param maxStages : The existing maximum stages will be changed to this maximum stages
	 * @return maximum stages
	 */
	public Integer setMaxStages(Integer maxStages) {
		return this.maxStages = maxStages;
	}

	/**
	 *
	 * @return the type of this area
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 *
	 * @param type : The existing type will be changed to this type
	 * @return the type of this area
	 */
	public Type setType(Type type) {
		return this.type = type;
	}
	
	/**
	 *
	 * @return zone
	 */
	public String toString() {
		return zone;
	}
}
