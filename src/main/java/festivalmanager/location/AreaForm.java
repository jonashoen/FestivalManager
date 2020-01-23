package festivalmanager.location;

public class AreaForm extends Area {

	/**
	 *
	 * @param zone : name of the area
	 * @param maxVisitors : amount of maximum visitors
	 * @param maxStages : amount of maximum stages
	 * @param type : type of the area
	 */
	public AreaForm(String zone, Integer maxVisitors, Integer maxStages, Type type) {
		super(zone, maxVisitors, maxStages, type);
	}

	/**
	 *
	 * @return new Area object
	 */
	public Area toArea() {
		return new Area(getZone(), getMaxVisitors(), getMaxStages(), getType());
	}
}
