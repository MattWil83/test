package Project;

public class Job {
	private int id;
	private int startcodeIndex;
	private int codeSize;
	private int startmemoryIndex;
	private int currentPC;
	private int currentAcc;
	private States currentState;
	public int getId() {
		return id;
	}
	public int getStartcodeIndex() {
		return startcodeIndex;
	}
	public int getCodeSize() {
		return codeSize;
	}
	public int getStartmemoryIndex() {
		return startmemoryIndex;
	}
	public int getCurrentPC() {
		return currentPC;
	}
	public int getCurrentAcc() {
		return currentAcc;
	}
	public States getCurrentState() {
		return currentState;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setStartcodeIndex(int startcodeIndex) {
		this.startcodeIndex = startcodeIndex;
	}
	public void setCodeSize(int codeSize) {
		this.codeSize = codeSize;
	}
	public void setStartmemoryIndex(int startmemoryIndex) {
		this.startmemoryIndex = startmemoryIndex;
	}
	public void setCurrentPC(int currentPC) {
		this.currentPC = currentPC;
	}
	public void setCurrentAcc(int currentAcc) {
		this.currentAcc = currentAcc;
	}
	public void setCurrentState(States currentState) {
		this.currentState = currentState;
	}
	public void reset(){
		codeSize=0;
		currentState=States.NOTHING_LOADED;
		currentAcc=0;
		currentPC=startcodeIndex;
	}

}


