package festivalmanager.location;

import festivalmanager.contract.Contract;
import festivalmanager.festival.Festival;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Lineup {
	private @Id @GeneratedValue long id;
	private long stageId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
	protected @OneToOne Festival festival;
	private @OneToOne Contract contract;

	@SuppressWarnings("unused")
	private Lineup() {}
	
	/**
	 *
	 * @param date : date of this lineup
	 */
	public Lineup(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 *
	 * @return lineup id
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
	 * @return stage id
	 */
	public long getStageId() {
		return stageId;
	}
	
	/**
	 *
	 * @param stageId : The existing stage id will be changed to this stage id
	 * @return stage id
	 */
	public long setStageId(long stageId) {
		return this.stageId = stageId;
	}
	
	/**
	 *
	 * @return date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 *
	 * @param date : The existing date will be changed to this date
	 * @return date
	 */
	public LocalDateTime setDate(LocalDateTime date) {
		return this.date = date;
	}
	
	/**
	 *
	 * @return festival that has this lineup
	 */
	public Festival getFestival() {
		return festival;
	}
	
	/**
	 *
	 * @return contract of an artist
	 */
	public Contract getArtist() {
		return contract;
	}

	/**
	 *
	 * @param contract : The existing contract will be changed to this contract
	 * @return contract of an artist
	 */
	public Contract setArtist(Contract contract) {
		return this.contract = contract;
	}
}
