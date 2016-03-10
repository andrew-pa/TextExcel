package pw.qxczv.TextExcel.Values;

public class TrueValue extends Value {
	
	private static final long serialVersionUID = 123456789L;

	private static TrueValue instance = new TrueValue();
	
	private TrueValue() {}
	
	public static TrueValue get() { return instance; } //this is silly but it's better than allocating a whole ton of TrueValue instances that are all exactly the same
	
	@Override
	public int compareTo(Value o) {
		return 1;
	}

	@Override
	public String toString() {
		return "T";
	}
}
