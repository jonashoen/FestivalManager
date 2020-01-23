package festivalmanager.location;

import java.time.LocalDateTime;

public class LineupForm extends Lineup{

	/**
	 *
	 * @param date : date of this lineup
	 */
	public LineupForm(LocalDateTime date) {
		super(date);
	}
	
	/**
	 *
	 * @return new Lineup object
	 */
	public Lineup toLineup() {
		return new Lineup(getDate());
	}
}
