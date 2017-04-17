package Project;

public class Memory {
	public static final int DATA_SIZE= 2048;
	private int[] data=new int[DATA_SIZE];
	int[] getData() {
		return data;
	}
	void setData(int[] data) {
		this.data = data;
	}
	
	int getData(int index) {
		return data[index];
	}
	void setData(int index,int value) {
		this.data[index] = value;
	}
	
}
