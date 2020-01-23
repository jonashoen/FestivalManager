package festivalmanager.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {
	private @Id @GeneratedValue long id;
	private String name;
	private String address;
	private boolean isBooked;
	private Integer price;
	private Integer currVisitors;
	private Integer maxVisitors;
	private String thumbnail;
	private String groundPlan;
	
	@SuppressWarnings("unused")
	private Location() {}
	
	/**
	 *
	 * @param zone : name of this location
	 * @param address : address of this location
	 * @param price : price of this location
	 * @param maxVisitors : amount of maximum visitors
	 * @param thumbnail : thumbnail path of this locaiton
	 * @param groundPlan : ground plan path of this locaiton
	 */
	public Location(String name, String address, Integer price, Integer maxVisitors, String thumbnail, String groundPlan) {
		this.name = name;
		this.address = address;
		this.price = price;
		this.maxVisitors = maxVisitors;
		this.thumbnail = thumbnail;
		this.groundPlan = groundPlan;
		this.isBooked = false;
		this.currVisitors = 0;
	}
	
	/**
	 *
	 * @return location id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 *
	 * @param id : The existing id will be changed to this id
	 * @return location id
	 */
	public long setId(long id) {
		return this.id = id;
	}
	
	/**
	 *
	 * @return name of this location
	 */
	public String getName() {		
		return name;
	}
	
	/**
	 *
	 * @param name : The existing name will be changed to this name
	 * @return name of this location
	 */
	public String setName(String name) {		
		return this.name = name;
	}
	
	/**
	 *
	 * @return address of this location
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 *
	 * @param address : The existing address will be changed to this address
	 * @return address of this location
	 */
	public String setAddress(String address) {		
		return this.address = address;
	}
	
	/**
	 *
	 * @return status of this location
	 */
	public boolean getStatus() {
		return isBooked;
	}
	
	/**
	 *
	 * @param isBooked : The existing status will be changed to this status
	 * @return status of this location
	 */
	public boolean setStatus(boolean isBooked) {
		return this.isBooked = isBooked;
	}
	
	/**
	 *
	 * @return status of this location
	 */
	public boolean toggleBook() {
		return isBooked = !isBooked;
	}
	
	/**
	 *
	 * @return price of this location
	 */
	public Integer getPrice() {
		return price;
	}
	
	/**
	 *
	 * @param price : The existing price will be changed to this price
	 * @return price of this location
	 */
	public Integer setPrice(Integer price) {
		return this.price = price;
	}
	
	/**
	 *
	 * @param visitors : plus the number of visitors
	 * @return current visitors
	 */
	public Integer countVisitors(int visitors) {		
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
	 * @return thumbnail path of this location
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	
	/**
	 *
	 * @param thumbnail : The existing thumbnail will be changed to this thumbnail
	 * @return thumbnail path of this location
	 */
	public String setThumbnail(String thumbnail) {
		return this.thumbnail = thumbnail;
	}
	
	/**
	 *
	 * @return ground plan path of this location
	 */
	public String getGroundPlan() {
		return groundPlan;
	}
	
	/**
	 *
	 * @param groundPlan : The existing groundPlan will be changed to this groundPlan
	 * @return ground plan path of this location
	 */
	public String setGroundPlan(String groundPlan) {
		return this.groundPlan = groundPlan;
	}
}