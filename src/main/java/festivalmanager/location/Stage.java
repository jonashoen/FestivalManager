package festivalmanager.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Stage {
	private @Id @GeneratedValue long id;
	private long areaId;
	private String name;
	private String poster;
	
	@SuppressWarnings("unused")
	private Stage() {}
	
	/**
	 *
	 * @param name : name of this stage
	 * @param poster : poster path of this stage
	 */
	public Stage(String name, String poster) {
		this.name = name;
		this.poster = poster;
	}
	
	/**
	 *
	 * @return stage id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 *
	 * @param id : The existing id will be changed to this id
	 * @return stage id
	 */
	public long setId(long id) {
		return this.id = id;
	}
	
	/**
	 *
	 * @return area id
	 */
	public long getAreaId() {
		return areaId;
	}

	/**
	 *
	 * @param areaId : The existing area id will be changed to this area id
	 * @return area id
	 */
	public long setAreaId(long areaId) {
		return this.areaId = areaId;
	}
	
	/**
	 *
	 * @return name of this stage
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *
	 * @param name : The existing name will be changed to this name
	 * @return name of this stage
	 */
	public String setName(String name) {
		return this.name = name;
	}
	
	/**
	 *
	 * @return poster path of this stage
	 */
	public String getPoster() {
		return poster;
	}
	
	/**
	 *
	 * @param poster : The existing poster path will be changed to this poster path
	 * @return poster path of this stage
	 */
	public String setPoster(String poster) {
		return this.poster = poster;
	}
}
