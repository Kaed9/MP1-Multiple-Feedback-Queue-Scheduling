public class RoundRobin{
	private final Process[] process;
	private Queue queue = new Queue();
	private int timeQ;
	
	public RoundRobin(Process[] process, int timeQ){
		this.process = process;
		this.timeQ = timeQ;
	}
	
	public void RR(){
		Process temp[] = process;
		Queue q = new Queue();
		int burst[] = new int[temp.length], arrival[] = new int[temp.length];
		int index = 0,pdex = 0, n=0;
		boolean flag = false, isAvailable[] = new boolean[temp.length], in;
		
		quicksort(temp, 0, temp.length-1, 0);
		
		//initialize values
		for(int i = 0; i < temp.length; i++){
			burst[i] = temp[i].getBurstTime();
			isAvailable[i] = false; // checks the top of q
			arrival[i] = -1;
		}
		
		while(!flag){
			in = false;
			boolean flag2 = true;
			Process p;
			//initial					
			if(index == 0){
				q.initialProcess(temp[0]);
				
				arrival[0] = temp[0].getArrivalTime();
				isAvailable[0] = true;
				p = q.dequeue();
				System.out.println("p: "+p.getProcessID());
				pdex = 0;					
			}else{
				p = q.dequeue();		
				System.out.println("p: "+p.getProcessID());
				for(int i = 0; i < temp.length; i++){
					if(p.getArrivalTime() == temp[i].getArrivalTime()){
						pdex = i;
					}
				}	
			}
			
			//adding to sched queue
			for(int i = 0; i< temp.length; i++){
				if(isAvailable[i] && arrival[i] != -1 && burst[i] != 0 && p.getArrivalTime() == temp[i].getArrivalTime() && flag2 == true){					
					if(burst[i] > timeQ){
						for(int j= 0; j < timeQ; j++){
							if(index == 0){
								queue.initialProcess(temp[i]);
							}else{
								queue.enqueue(temp[i]);
							}
							System.out.print(temp[i].getProcessID()+" ");
							burst[i]--;
							index++;							
						}
						isAvailable[i] = false;
						flag2 = false;	
						System.out.print(flag2);
					}else if(burst[i] < timeQ){
						for(int j = 0; j < burst[i]; j++){
							if(index == 0){
								queue.initialProcess(temp[i]);
							}else{
								queue.enqueue(temp[i]);
							}
							System.out.print(temp[i].getProcessID()+" ");
							burst[i]--;
							index++;		
						}
						isAvailable[i] = false;
						flag2 = false;
						System.out.print(flag2);						
					}					
				}								
			}
								
			//int dd = pdex; 			
			
			/* if(in){
				//flag2 = true;	
				num = 0;
			}else{ num=1;}
			 */
			//System.out.println(" ind: "+flag2);
			
			//something's wrong
			for(int i = 0; i < temp.length; i++){										
				if(temp[i].getArrivalTime() >= index && p.getProcessID() != temp[i].getProcessID() && !isAvailable[i] && burst[i] != 0){														
					q.enqueue(temp[i]);															
					System.out.println("Hell 2: "+temp[i].getProcessID());					
					
					//flag2 = false;
					arrival[i] = temp[i].getArrivalTime();	
					isAvailable[i] = true;
					System.out.println("index: "+index+"ID: "+temp[i].getProcessID()+", arrival[i]: "+temp[i].getArrivalTime()+", p.arrival: "+p.getArrivalTime()+", isAvailable[i]: "+isAvailable[i]);						
				}								
			}
			
			if(burst[pdex] != 0){
				q.enqueue(temp[pdex]);
				arrival[pdex] = temp[pdex].getArrivalTime();	
				isAvailable[pdex] = true;
			}
			
			//index++;
						
			if(index == totalTime(1)){
				flag = true;
				System.out.print("index: "+index);
			}
		}
		
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
		//System.out.println("temp: "+temp);
		return temp;
	}
	
	public static void main(String[] args){
		System.out.println("Round Robin Test");
		Process p[] = {new Process(1, 5, 7, 9), new Process(2, 1, 5, 2), new Process(3, 2, 3, 5), new Process(4, 0, 1, 1), new Process(5, 4, 2, 6), new Process(6, 3, 1, 10)};
		RoundRobin r = new RoundRobin(p, 2);
		r.RR();
		//r.test();	
//		pID	AT	BT	P

//		1	5	7	9
//		2	1	5	2
//		3	2	3	5
//		4	0	1	1
//		5	4	2	6
//		6	3	1	10
		
		/* Queue q = new Queue();
		
		q.initialProcess(p[0]);
		System.out.println("index1: "+q.getIndex());
		q.dequeue();
		q.enqueue(p[1]);
		q.dequeue();
		System.out.println("index2: "+q.getIndex()); */

	}
}