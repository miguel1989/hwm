package hwm.exceptions;

public class WrongCoordinatesProvided extends RuntimeException {
	public WrongCoordinatesProvided() {
		super("Wrong x or y provided");
	}
}
