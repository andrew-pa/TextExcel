package pw.qxczv.TextExcel.Values;

public class StringValue extends Value {
	public String v;
	
	public StringValue(String s) { v = s; }
	
	@Override
	public int compareTo(Value o) {
		StringValue os = (StringValue)o;
		if(os == null) return -1;
		return v.compareTo(os.v);
	}
	
	@Override
	public String toString() {
		return "\"" + v + "\"";
	}
}
