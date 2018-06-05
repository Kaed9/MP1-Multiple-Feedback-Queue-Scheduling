public class Process {
	private int processID, arrivalTime, burstTime, priority/*, historyInfo*/;
	
	public Process(int pID, int a, int b, int p){
		processID = pID;
		arrivalTime = a;
		burstTime = b;
		priority = p;
		// historyInfo = 0;
	}
	
	//public void setPriority(int p){
	//	priority = p;
	//}

	// public void setHistoryInfo(int h) {
	// 	historyInfo = h;
	// }
	
	public int getProcessID() {
		return processID;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime(){
		return burstTime;	
	}
	
	public int getPriority() { 
		return priority;
	}

	// public int getHistoryInfo() {
	// 	return historyInfo;
	// }


	public void setArrivalTime(int arrivalTime) {

		this.arrivalTime = arrivalTime;
	}

	public void setBurstTime(int burstTime) {

		this.burstTime = burstTime;
	}
}