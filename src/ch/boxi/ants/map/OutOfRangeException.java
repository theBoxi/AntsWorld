package ch.boxi.ants.map;

public class OutOfRangeException extends RuntimeException {
	private static final long	serialVersionUID	= -4358538081938424682L;
	
	public OutOfRangeException() {
	}
	
	public OutOfRangeException(String arg0) {
		super(arg0);
	}
	
	public OutOfRangeException(Throwable arg0) {
		super(arg0);
	}
	
	public OutOfRangeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
}
