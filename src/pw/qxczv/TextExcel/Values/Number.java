package pw.qxczv.TextExcel.Values;

public class Number extends Value {
	
	private static final long serialVersionUID = 123456786L;
	
	public double v;
	
	public Number(double d) { v = d; }

	@Override
	public int compareTo(Value o) {
		Number on = (Number)o;
		if(on == null) return -1; //values of different types are always less
		return Double.compare(v, on.v);
	}
	
	@Override
	public String toString() {
		return Double.toString(v);
	}
}
