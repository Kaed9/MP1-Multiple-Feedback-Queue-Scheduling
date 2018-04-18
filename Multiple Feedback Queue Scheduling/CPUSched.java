public class CPUSched {
	private Queue queue = new Queue();
	private final Process[] process;
	private Process[] copy;

	private QueuesPanel queuesPanel;
	
	public CPUSched(Process[] process) {
		this.process = process;
		copy = process;
	}
	
	//check process
	public void check(){
		System.out.println("\nChecking");
		for(int i = 0; i < process.length; i++){
			System.out.print(process[i].getProcessID()+" ");
		}
		System.out.println("\nDone\\");
	}
	
	//CPU Scheduling Algorithm
	public Process[] FCFS(){
		check();
		Process[] temp = process;
		//sort arrival Time
		quicksort(temp, 0, temp.length-1, 0);
		/*
		new Thread() {
			public void run() {
				try {
					for(int i = 0; i < temp.length; i++){
						for(int j = 0; j < temp[i].getBurstTime(); j++){
							//initializeProcess
							if(j == 0){
								queue.initialProcess(temp[i]);
								System.out.print(temp[i].getBurstTime()+" ");
							}else{
								queue.enqueue(temp[i]);
								System.out.print(temp[i].getBurstTime()+" ");
								// queuesPanel.addToQueue(" ", i);
							}
							Thread.sleep(100);
						}
						// queuesPanel.addToQueue("" + temp[i].getProcessID(), i);
					}
					check();
				} catch(InterruptedException iEx) { }
			}
		}.start();
		*/
		
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[i].getBurstTime(); j++){
				//initializeProcess
				if(j == 0){
					queue.initialProcess(temp[i]);
					System.out.print(temp[i].getBurstTime()+" ");
				}else{
					queue.enqueue(temp[i]);
					System.out.print(temp[i].getBurstTime()+" ");
				}				
			}
		}
		check();

		return temp;
	}
	
	public void SJF(){
		Process[] temp = process;
		int burst[] = new int[process.length], last = 0, arrival[] = new int[process.length];
		boolean isAvailable[] = new boolean[process.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		for(int i =0; i< process.length; i++){
			burst[i] = 0;
			arrival[i] = temp[i].getArrivalTime();
		}
		
		for(int i = 0; i < temp.length; i++){			
			if(i == 0 && getSmallestNum(arrival, 1) != -1){
				for(int j = 0; j < temp[i].getBurstTime(); j++){
					if(j == 0){
						queue.initialProcess(temp[i]); 
						System.out.print(temp[i].getProcessID()+" ");
					}else{
						queue.enqueue(temp[i]); 
						System.out.print(temp[i].getProcessID()+" ");
					}
				}
				last+= temp[i].getBurstTime();				
				burst[i] = -1;		
				
			}else{
				for(int j = 1; j< temp.length; j++){
					if(last >= temp[j].getArrivalTime() && burst[j] != -1){
						isAvailable[j] = true;
				
						burst[j] = temp[j].getBurstTime();						
					}	
				}						
				boolean flag = false;
				
				for(int j = 0; j < temp.length; j++){					
					if(isAvailable[j] == true && burst[j] == getSmallestNum(burst, 0)&& flag == false){						
						for(int k = 0; k < temp[j].getBurstTime(); k++){
							queue.enqueue(temp[j]);
							System.out.print(temp[j].getProcessID ()+" ");
						}
						last+=temp[j].getBurstTime();
						burst[j] = -1;
						flag = true;
					}else{continue;}
				}
			}	
		} 
	}
	
	public void STRF(){
		Process[] temp = process;
		boolean[] isAvailable = new boolean[temp.length];
		int[] burst = new int[temp.length], tempB = new int[temp.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		
		//initializeProcess
		for(int i = 0; i < temp.length; i++){
			isAvailable[i] = false;
			burst[i] = temp[i].getBurstTime();
			tempB[i] = 0;
		}
		
		for(int i = 0; i < totalTime(1); i++){
			boolean flag = false;
			int in = 0;
			for(int j =0; j < temp.length; j++){
				if(i == temp[j].getArrivalTime()){
					isAvailable[j] = true;
					tempB[j] = burst[j];					
				}else{ continue; }
			}
					
			for(int j = 0; j < temp.length; j++){
				if(isAvailable[j] = true && getSmallestNum(tempB,0) != -1 && burst[j] == getSmallestNum(tempB,0) && flag == false){
					if(i == 0){
						queue.initialProcess(temp[j]);
						burst[j]--;
						tempB[j]--;
						flag = true;
						System.out.print(temp[j].getArrivalTime()+" ");
					}else {
						queue.enqueue(temp[j]);
						burst[j]--;
						tempB[j]--;
						flag = true;
						System.out.print(temp[j].getArrivalTime()+" ");
					}
				}else {continue;}
			}
		}
	}
	
	public void NPrio(){
		Process[] temp = process;	
		int last = 0, prio[] = new int[process.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		
		for(int i =0; i< process.length; i++){
			prio[i] = temp[i].getPriority();
		}
		
		for(int i = 0; i < temp.length; i++){
			if(i == 0){
				for(int j = 0; j < temp[i].getBurstTime(); j++){
					if(j ==0){
						queue.initialProcess(temp[i]); 
						System.out.print(temp[i].getArrivalTime()+" ");
					}else{
						queue.enqueue(temp[i]); 
						System.out.print(temp[i].getArrivalTime()+" ");
					}					
				}
				last+=temp[i].getBurstTime();
				//System.out.println("test: "+last);
				prio[i] = -1;
			}else{
				boolean flag = false;
				for(int j = 0; j < temp.length; j++){
					//System.out.println("done prio:"+getSmallestNum(prio, 1));					
					if(temp[j].getPriority() == getSmallestNum(prio, 1) && prio[j] != -1 && flag == false){						
						for(int k = 0; k < temp[j].getBurstTime(); k++){
							queue.enqueue(temp[j]); 
							System.out.print(temp[j].getArrivalTime()+" ");							
						}
						prio[j] = -1;							
						//System.out.println("test: "+last);
						last+=temp[j].getBurstTime();
						flag = true;
					}
				}
			}
		}
	}
	
	/* public void Prio(){				//gin comment ko la anay
		Process temp[] = process;
		int[] burst = new int[process.length], prio = new int[process.length];
		boolean isAvailable[] = new boolean[process.length];
		//initializeProcess
		quicksort(temp, 0, temp.length-1, 0);
		
		for(int i = 0; i < temp.length; i++){
			burst[i] = temp[i].getBurstTime();
			prio[i] = temp[i].getPriority();
			isAvailable[i] = false;	
		}
		
		for(int i = 0; i < totalTime(1); i++){
			for(int j = 0; j < temp.length; j++){
				if(i >= temp[j].getArrivalTime() && prio[j] != -1){
					isAvailable[j] = true;
					//prio[j] = temp[j].getPriority();
					//System.out.println(i+": add");
				}
				
				if(i < temp[j].getArrivalTime()){
					isAvailable[j] = false;
					//prio[j] = -1;
					//System.out.println(i+": remove");
				}
			}
			
			if(i == 0){
				queue.initialProcess(temp[0]);
				burst-=1;
			}else if()
		}
	} */
	
	public void RoundRobin(int timeQ){
		Process temp[] = process;
		
		quicksort(temp, 0, temp.length-1, 0);
		
		//for(int i = 0; i < timeQ; i++){
				
		//}
	}
	
	//other methods
	public int getSmallestNum(int num[], int c){
		int temp = -1; boolean flag = false;
		
		if(c == 0){
			for(int i = 0; i < num.length; i++){
				if(num[i] != 0 && flag == false && num[i] != -1){
					temp = num[i];
					flag = true;
				}else
					continue;
			}
		
			for(int i = 0; i < num.length; i++){
				if(temp > num[i] && num[i] != 0 && num[i] != -1)
					temp = num[i];
			}
		}else if(c == 1){
			for(int i = 0; i < num.length; i++){
				if(flag == false && num[i] != -1){
					temp = num[i];
					flag = true;
				}else
					continue;
			}
			
			for(int i = 0; i < num.length; i++){
				if(temp > num[i] && num[i] != -1)
					temp = num[i];
			}
		}
		
		return temp;
	}
	
	public int totalTime(int c){
		int count = 0;
		
		//count arrival time
		if(c == 0){
			for(int i = 0; i < process.length; i++){
				count += process[i].getArrivalTime();
			}
		}
		//count burst time
		if(c == 1){
			for(int i = 0; i < process.length; i++){
				count += process[i].getBurstTime();
			}
		}
		return count;
	}
	
	public void quicksort(Process p[], int low, int high, int c) {
		int i = low, j = high;
		
		Process pivot = p[low + (high-low)/2];
		
		if(c == 0) {							//sort arrival time
			while(i <=j){
				while (p[i].getArrivalTime() < pivot.getArrivalTime()){
					i++;
				}
			
				while (p[j].getArrivalTime() > pivot.getArrivalTime()){
					j--;
				}
			
				if(i <=j){
					swap(p,i, j);
					i++;
					j--;
				}
			}
		}else if(c == 1) {						//sort burstTime
			while(i <=j){
				while (p[i].getBurstTime() < pivot.getBurstTime()){
					i++;
				}
			
				while (p[j].getBurstTime() > pivot.getBurstTime()){
					j--;
				}
			
				if(i <=j){
					swap(p,i, j);
					i++;
					j--;
				}
			}
		}else if(c == 2) {						//sort priority
			while(i <=j){
				while (p[i].getPriority() < pivot.getPriority()){
					i++;
				}
			
				while (p[j].getPriority() > pivot.getPriority()){
					j--;
				}
			
				if(i <=j){
					swap(p,i, j);
					i++;
					j--;
				}
			}
		}
		
		if(low < j)
			quicksort(p, low, j, c);
		if(i < high)
			quicksort(p,i, high, c);
		
	}
	
	private void swap(Process arr[], int i, int j){
		Process temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		arr[j] = temp;
	}
	
	
	public static void main(String[] args){
		Process p[] = {new Process(1, 5, 7, 9), new Process(2, 1, 5, 2), new Process(3, 2, 3, 5), new Process(4, 0, 1, 1), new Process(5, 4, 2, 6), new Process(6, 3, 1, 10)}; 
		CPUSched sched = new CPUSched(p);
		//process	arrival		burst		priority
		//  p1 		   5		  7				9
		//  p2		   1	      5				2
		//	p3         2		  3				5
		//  p4		   0  		  1				1
		// 	p5		   4		  2				6
		//  p6		   3  		  1				10
		
		
		System.out.println("FCFS");
		sched.FCFS();
		System.out.println("\nSJF");
		sched.SJF();
		System.out.println("\nSTRF, arrival time printed");
		sched.STRF();
		System.out.println("\nNPrio");
		sched.NPrio();
		// System.out.println("\nPrio");
		// sched.Prio();
		
		int num[] = {1, 0, 0, 0, 0, 0,0};
		
		int tem = sched.getSmallestNum(num, 0);
		//System.out.println("\ntem = "+tem);
		
	}
	
}