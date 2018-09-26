public class Car {

	private int size;
	private int [] path; 
	
	public Car (int size) {
		this.size = size;
	}
	
	public Car (int size, int[] path) {
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
