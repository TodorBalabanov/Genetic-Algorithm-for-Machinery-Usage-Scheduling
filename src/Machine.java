/**
 * Machine description.
 * 
 * @author Todor Balabanov
 */
class Machine {
	/**
	 * Machine title.
	 */
	private String name = "";

	/**
	 * Is machine occupied by particular action flag.
	 */
	private boolean occupied = false;

	/**
	 * Reference to external action object.
	 */
	private Action action = null;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Machine title.
	 * @param occupied
	 *            Machine status.
	 * @param action
	 *            Reference to the action in progress.
	 */
	public Machine(String name, boolean occupied, Action action) {
		this.name = name;
		this.occupied = occupied;
		this.action = action;
	}

	/**
	 * Name field getter.
	 * 
	 * @return Name of the machine.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name field setter.
	 * 
	 * @param name
	 *            Name of the machine.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Occupied field getter.
	 * 
	 * @return Is occupied flag
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Occupied field setter.
	 * 
	 * @param occupied
	 *            Is machine occupied by action flag.
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * Action field getter.
	 * 
	 * @return Reference to action object.
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Action field setter.
	 * 
	 * @param action
	 *            Reference to external action object.
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine [name=" + name + ", occupied=" + occupied + "]";
	}
}