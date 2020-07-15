package carbonIT.treasureHunting.exceptions;

public class OutOfMapException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3960057133375348608L;

	public OutOfMapException() {
        super();
    }

    public OutOfMapException(String message) {
        super(message);
    }
}
