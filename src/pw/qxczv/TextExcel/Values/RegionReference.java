package pw.qxczv.TextExcel.Values;

public class RegionReference extends Value{

	public char colStrtIdx; public int rowStrtIdx; public int colRegRng; public int rowRegRng;
	
	public RegionReference (char c, int r, int colEnd, int rowEnd){
		//colEnd and rowEnd are inclusive
		colStrtIdx = c; rowStrtIdx = r; colRegRng = colEnd; rowRegRng = rowEnd;
	}
	
	@Override
	public int compareTo(Value o) {
		// Andrew says that they will never be compared. RIP comparable Regions
		return -1;
	}
	
	@Override
	public String toString(){
		return colStrtIdx + "" + rowStrtIdx + " - " + ((char)(colRegRng + ((int) colStrtIdx))) + (rowStrtIdx + rowRegRng);
	}
	
}
