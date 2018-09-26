
public class SemiTruck {
	private int size;
	private int [] path; 
	
	public SemiTruck (int size) {
		this.size = size;
	}
	
	public SemiTruck (int size, int[] path) {
		this.size = size;
		this.path = path;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[] getPath() {
		return path;
	}

	public void setPath(int[] path) {
		this.path = path;
	}
}
