package festivalmanager.location;

public class StageForm extends Stage {

	/**
	 *
	 * @param name : name of this stage
	 * @param poster : poster path of this stage
	 */
	public StageForm(String name, String poster) {
		super(name, poster);
	}

	/**
	 *
	 * @return new Stage object
	 */
	public Stage toStage() {
		return new Stage(getName(), getPoster());
	}
}
