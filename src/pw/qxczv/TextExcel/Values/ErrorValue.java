package pw.qxczv.TextExcel.Values;

public class ErrorValue extends Value {
	public Exception err;
	
	public ErrorValue(Exception e) {
		err = e;
	}
	
	public ErrorValue(String msg) {
		err = new Exception(msg);
	}
	
	@Override
	public int compareTo(Value o) {
		return -1; //No ordering for Errors, they're all bad
	}

}
