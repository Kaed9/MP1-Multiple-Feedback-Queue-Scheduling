public class CPUSched {
	private Queue queue = new Queue();
	private final Process[] process;
	private Process[] copy, other;
	
	private QueuesPanel queuesPanel;
	private boolean isMLFQ = false;
	
	public CPUSched(Process[] process) {
		this.process = process;
		copy = process;
	}
	
	//check process
	/*public void check(){
		System.out.println("\nChecking");
		for(int i = 0; i < process.length; i++){
			System.out.print(process[i].getProcessID()+" ");
		}
		System.out.println("\nDone\\");
	}*/
	
	//CPU Scheduling Algorithm
	public Queue FCFS(){
		// check();
		Process[] temp;
		if(isMLFQ == false){
			temp = process;
		}else{
			temp = other;
		}
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
					// check();
				} catch(InterruptedException iEx) { }
			}
		}.start();
		*/
		
		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		for(int i = 0; i < temp.length; i++){
			// if(i == 0) {
				// printQueue.initialProcess(temp[i]);
				// timesQueue.initialProcess(temp[i]);
			//  } else {
				// printQueue.enqueue(temp[i]);
				// timesQueue.enqueue(temp[i]);
			// }
			for(int j = 0; j < temp[i].getBurstTime(); j++){
				//initializeProcess
				if(i == 0 && j == 0){
					queue.initialProcess(temp[i]);
					// printQueue.initialProcess(temp[i]);
					// timesQueue.initialProcess(temp[i]);
					// System.out.print("P" + temp[i].getProcessID()+" ");
				}else{
					queue.enqueue(temp[i]);
					// printQueue.enqueue(temp[i]);
					// timesQueue.enqueue(temp[i]);
					// System.out.print("P" + temp[i].getProcessID()+" ");
				}				
			}
		}

		Process sample = null;

		while(queue.getIndex() > 0) {
			sample = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
				printable.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
				printable.enqueue(sample);
			}
		}

		System.out.println();
		// System.out.println(printQueue.getIndex());
		// Queue printable = printQueue;
		// System.out.println(printable.getIndex());
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();
		// check();

		return printable;
	}
	
	public Queue SJF(){
		Process[] temp;
		if(isMLFQ == false){
			temp = process;
		}else{
			temp = other;
		}
		Process[] sample = new Process[process.length];
		int burst[] = new int[process.length], last = 0, arrival[] = new int[process.length];
		boolean isAvailable[] = new boolean[process.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		for(int i =0; i< process.length; i++){
			burst[i] = 0;
			arrival[i] = temp[i].getArrivalTime();
		}
		
		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		for(int i = 0; i < temp.length; i++){
			if(i == 0 && getSmallestNum(arrival, 1) != -1){
				for(int j = 0; j < temp[i].getBurstTime(); j++){
					if(j == 0){
						queue.initialProcess(temp[i]); 
						// if(i == 0) {
						// 	printQueue.initialProcess(temp[i]);
						// 	timesQueue.initialProcess(temp[i]);
						// } else {
						// 	printQueue.enqueue(temp[i]);
						// 	timesQueue.enqueue(temp[i]);
						// }
						// System.out.print(temp[i].getProcessID()+" ");
					}else{
						queue.enqueue(temp[i]); 
						// System.out.print(temp[i].getProcessID()+" ");
					}
				}
				sample[i] = temp[i];
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
						// if(j == 0) {
						// 	printQueue.initialProcess(temp[j]);
						// 	timesQueue.initialProcess(temp[j]);
						// } else {
						// 	printQueue.enqueue(temp[j]);
						// 	timesQueue.enqueue(temp[j]);
						// }
						for(int k = 0; k < temp[j].getBurstTime(); k++){
							queue.enqueue(temp[j]);
							// System.out.print(temp[j].getProcessID()+" ");
						}
						last+=temp[j].getBurstTime();
						burst[j] = -1;
						flag = true;
					}else{continue;}
					sample[i] = temp[j];
				}
			}	
		} 

		// System.out.println(last + " ");
		// for(int i = 0; i < process.length; i++) {
			// sample[i] = queue.dequeue();
		// 	System.out.print("" + sample[i].getProcessID());
		// }

		Process sampler = null;

		while(queue.getIndex() > 0) {
			sampler = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sampler);
				timesQueue.initialProcess(sampler);
				printable.initialProcess(sampler);
			} else {
				printQueue.enqueue(sampler);
				timesQueue.enqueue(sampler);
				printable.enqueue(sampler);
			}
		}

		System.out.println();
		// System.out.println(printQueue.getIndex());
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return printable;
	}
	
	public Queue SRTF(){
		Process[] temp;
		if(isMLFQ == false){
			temp = process;
		}else{
			temp = other;
		}
		boolean[] isAvailable = new boolean[temp.length];
		int[] burst = new int[temp.length], tempB = new int[temp.length], arrival = new int[temp.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		
		// for(int j = 0;j < temp.length; j++){
		// 	System.out.print(temp[j].getArrivalTime()+" ");
		// } 		
		// System.out.println();
		//initializeProcess
		for(int i = 0; i < temp.length; i++){
			isAvailable[i] = false;
			burst[i] = temp[i].getBurstTime();
			tempB[i] = 0;
			arrival[i] = temp[i].getArrivalTime();
		}
		
		for(int i = 0; i < (totalTime(1)+getSmallestNum(arrival, 1)); i++){
			boolean flag = false;
			int in = 0;
			
			for(int j =0; j < temp.length; j++){
				if(i == temp[j].getArrivalTime()){
					isAvailable[j] = true;
					tempB[j] = burst[j];					
				}else{ continue; }
			}
					
			/* for(int j = 0; j< totalTime(1); j++){
				System.out.println();
			} */		
			
			for(int j = 0; j < temp.length; j++){
				//System.out.println("av:"+isAvailable[j]+", small: "+getSmallestNum(tempB,0)+", burst:"+burst[j]);
				if(isAvailable[j] = true && getSmallestNum(tempB,0) != -1 && burst[j] == getSmallestNum(tempB,0) && flag == false){
					//System.out.println(i+": "+"av:"+isAvailable[j]+", small: "+getSmallestNum(tempB,0)+", burst:"+burst[j]);
					if(i == getSmallestNum(arrival, 1)){
						// System.out.println("Yes");
						queue.initialProcess(temp[j]);
						burst[j]--;
						tempB[j]--;
						flag = true;
						// System.out.print("P" + temp[j].getProcessID()+" ");
					}else {
						queue.enqueue(temp[j]);
						burst[j]--;
						tempB[j]--;
						flag = true;
						// System.out.print("P" + temp[j].getProcessID()+" ");
					}
				}else {continue;}
			}
		}

		// System.out.println("\n");

		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		Process sample = null;

		while(queue.getIndex() > 0) {
			sample = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
				printable.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
				printable.enqueue(sample);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return printable;
	}
	
	public Queue NPrio(){
		Process[] temp;
		if(isMLFQ == false){
			temp = process;
		}else{
			temp = other;
		}
		Process[] sample = new Process[process.length];
		int last = 0, prio[] = new int[process.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		
		for(int i =0; i< process.length; i++){
			prio[i] = temp[i].getPriority();
		}
		
		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		for(int i = 0; i < temp.length; i++){
			if(i == 0){
				for(int j = 0; j < temp[i].getBurstTime(); j++){
					if(j ==0){
						queue.initialProcess(temp[i]);
						// if(i == 0) {
						// 	printQueue.initialProcess(temp[i]);
						// 	timesQueue.initialProcess(temp[i]);
						// } else {
						// 	printQueue.enqueue(temp[i]);
						// 	timesQueue.enqueue(temp[i]);
						// }
						// System.out.print(temp[i].getProcessID()+" ");
					}else{
						queue.enqueue(temp[i]); 
						// System.out.print(temp[i].getProcessID()+" ");
					}					
				}
				last+=temp[i].getBurstTime();
				//System.out.println("test: "+last);
				prio[i] = -1;
				sample[i] = temp[i];
			}else{
				boolean flag = false;
				for(int j = 0; j < temp.length; j++){
					//System.out.println("done prio:"+getSmallestNum(prio, 1));					
					if(temp[j].getPriority() == getSmallestNum(prio, 1) && prio[j] != -1 && flag == false){						
						// if(j == 0) {
						// 	printQueue.initialProcess(temp[j]);
						// 	timesQueue.initialProcess(temp[j]);
						// } else {
						// 	printQueue.enqueue(temp[j]);
						// 	timesQueue.enqueue(temp[j]);
						// }
						for(int k = 0; k < temp[j].getBurstTime(); k++){
							queue.enqueue(temp[j]); 
							// System.out.print(temp[j].getProcessID()+" ");
						}
						prio[j] = -1;
						//System.out.println("test: "+last);
						last+=temp[j].getBurstTime();
						flag = true;
						sample[i] = temp[j];
					}
				}
			}
		}

		// for(int i = 0; i < process.length; i++ ){
		// 	System.out.print(sample[i].getProcessID());
		// }

		Process sampler = null;

		while(queue.getIndex() > 0) {
			sampler = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sampler);
				timesQueue.initialProcess(sampler);
				printable.initialProcess(sampler);
			} else {
				printQueue.enqueue(sampler);
				timesQueue.enqueue(sampler);
				printable.enqueue(sampler);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return printable;
	}
	
	public Queue Prio(){			
		Process[] temp;
		if(isMLFQ == false){
			temp = process;
		}else{
			temp = other;
		}
		boolean isAvailable[] = new boolean[temp.length], flag2=false;
		int[] burst = new int[temp.length], tempB = new int[temp.length], prio = new int[temp.length], arrival = new int[temp.length];
			
		quicksort(temp, 0, temp.length-1, 0);
		
		//initializeProcess
		for(int i = 0; i < temp.length; i++){
			isAvailable[i] = false;
			burst[i] = temp[i].getBurstTime();
			tempB[i] = 0;
			prio[i] = 0;
			arrival[i] = temp[i].getArrivalTime();
			//done=false;
		}
		
		//process
		for(int i = 0; i < (totalTime(1)+getSmallestNum(arrival, 1)); i++){
			boolean flag = false, falg2 =false;
			int in = 0;
			boolean done = true, first = false;
			
			for(int j =0; j < temp.length; j++){			
				if(i == temp[j].getArrivalTime()){
					isAvailable[j] = true;
					tempB[j] = burst[j];
					prio[j] = temp[j].getPriority();
					
				}else{ continue; }
			}
			
			///check if all isAvailable is true			
			first = isAvailable[0];
			for(int j = 0; j < temp.length; j++){				
				if(first != isAvailable[j]){
	
					done = false;
				
				}		
			}
	
			if(done == false){				
				for(int j = 0; j < temp.length; j++){
					if(isAvailable[j] = true && getSmallestNum(prio, 0) == prio[j] && flag == false){
						if(i == getSmallestNum(arrival, 1)){
							queue.initialProcess(temp[j]);
							burst[j]--;
							flag = true;
							// System.out.print(temp[j].getProcessID()+" ");
							if(burst[j] == 0) {prio[j] = -1;}
						}else{
							queue.enqueue(temp[j]);
							burst[j]--;
							flag=true;
							// System.out.print(temp[j].getProcessID()+" ");
							if(burst[j] == 0) {prio[j] = -1;}
						}
					}else{continue;}
				}
			}else if(done){
				for(int j = 0; j < temp.length; j++){
					if(getSmallestNum(prio,0) == prio[j] && flag == false){
						queue.enqueue(temp[j]);
						burst[j]--;
						flag=true;
						// System.out.print(temp[j].getProcessID()+" ");
						if(burst[j] == 0) {prio[j] = -1;}
					}
				}
			} 
		}

		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		Process sample = null;

		while(queue.getIndex() > 0) {
			sample = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
				printable.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
				printable.enqueue(sample);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return printable;
	}

	public Queue RR(int quantumTime) {
	
		Process[] temp, temp1;
		// Process[] temp;
		if(isMLFQ == false){
			temp = process;
			temp1 = process;
		}else{
			temp = other;
			temp1 = other;
		}
		
		Process usable = null;
		Queue queue1 = new Queue();
		Queue queue2 = new Queue();
		int tempLength = temp.length;

		// for(int i = 0; i < tempLength; i++) {
		// 	System.out.print("P" + temp1[i].getProcessID() + " ");
		// }
		// System.out.println();

		int[] bursts = new int[tempLength];
		int[] arrivals = new int[tempLength];

		for(int i = 0; i < tempLength; i++) {
			bursts[i] = temp[i].getBurstTime();
			arrivals[i] = temp[i].getArrivalTime();
		}

		quicksort(temp, 0, temp.length - 1, 0);

		// for(int i = 0; i < tempLength; i++) {
		// 	System.out.print("P" + temp1[i].getProcessID() + " ");
		// }
		// System.out.println();
		
		queue2.initialProcess(temp[0]);
		for(int i = 1; i < temp.length; i++) {
			queue2.enqueue(temp[i]);
		}

		int countTempLength = tempLength;
		int quantumTimeLoop = temp[0].getArrivalTime();

		while(true) {
			usable = queue2.dequeue();
			// System.out.print("P" + usable.getProcessID() + " ");

			int currentBurst = usable.getBurstTime();
			int currentArrival = usable.getArrivalTime();

			if(usable.getArrivalTime() <= quantumTimeLoop) {
				if(currentBurst > quantumTime) {
					usable.setBurstTime(currentBurst - quantumTime);
					usable.setArrivalTime(currentArrival + quantumTime);
					// System.out.print("P" + usable.getProcessID() + " ");
					quantumTimeLoop += quantumTime;

					for(int j = 0; j < quantumTime; j++) {
						for(int i = 0; i < tempLength; i++) {
							if(queue1.getIndex() == 0) {
								if(temp1[i].getProcessID() == usable.getProcessID())
									queue1.initialProcess(temp1[i]);
							} else {
								if(temp1[i].getProcessID() == usable.getProcessID())
									queue1.enqueue(temp1[i]);
							}
						}
					}
				} else if(currentBurst <= quantumTime && currentBurst != 0) {
					usable.setBurstTime(currentBurst - currentBurst);
					// System.out.print("P" + usable.getProcessID() + " ");
					countTempLength--;
					quantumTimeLoop += currentBurst;

					for(int j = 0; j < currentBurst; j++) {
						for(int i = 0; i < tempLength; i++) {
							if(queue1.getIndex() == 0) {
								if(temp1[i].getProcessID() == usable.getProcessID())
									queue1.initialProcess(temp1[i]);
							} else {
								if(temp1[i].getProcessID() == usable.getProcessID())
									queue1.enqueue(temp1[i]);
							}
						}
					}
				}
			}
			
			queue2.enqueue(usable);

			if(countTempLength == 0) {
				break;
			}
		}
		System.out.println();

		// while(queue1.getIndex() > 0) {
		// 	Process pr = queue1.dequeue();

		// 	pr.setBurstTime(bursts[pr.getProcessID() - 1]);
		// 	pr.setArrivalTime(arrivals[pr.getProcessID() - 1]);

		// 	System.out.print("P" + pr.getProcessID() + "|" + pr.getBurstTime() + "|" + pr.getArrivalTime() + " ");
		// }

		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		Queue printable = new Queue();
		int starter = temp[0].getArrivalTime();
		Process sample = null;

		while(queue1.getIndex() > 0) {
			sample = queue1.dequeue();

			sample.setBurstTime(bursts[sample.getProcessID() - 1]);
			sample.setArrivalTime(arrivals[sample.getProcessID() - 1]);

			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
				printable.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
				printable.enqueue(sample);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return printable;
	}
	
	public void MLFQ(int[] sched, int[] timeq){ // MLFQ({num sched, timeq}) note: timeq = -1; if not RR
		int index  = 0;
		System.out.println("MLFQ");
		Process temp[] = process;
		int burst[] = new int[temp.length];
		int qnum[] = new int[temp.length]; //ikangain na queue hiya		
		Queue q[] = new Queue[sched.length];
		Queue qother = new Queue();
		quicksort(temp, 0, temp.length-1, 0); //sort arrival
		
		//initialize
		for(int i = 0; i < temp.length; i++){
			burst[i] = temp[i].getBurstTime();
			qnum[i] = 0;			
		}
		
		for(int i = 0; i < sched.length; i++){
			q[i] = new Queue();
		}
		
		int n = 0;
		for(int i = 0; i < sched.length; i++){
			if(sched[0] != 5){		//if not RR
				q[i] = Qpriority(sched[i]);		
				break;	
			}else{
				for(int j = 0; j < temp.length; j++){
					if(timeq[i] != -1){					
						if(burst[j] > timeq[i] && qnum[j] == i){						
							if(q[i].getIndex() == 0){
								q[i].initialProcess(temp[j]); //naexceptio naliwat
								System.out.print(temp[j].getProcessID()+" ");
							}else{
								q[i].enqueue(temp[j]);
								System.out.print(temp[j].getProcessID()+" ");
							}
							burst[j]-=timeq[i];
							qnum[j]++; //demote							
						}else{
							continue;
						}
					}else{
						//dapat same i
						if(qnum[j] == i){
							if(n == 0){
								qother.initialProcess(temp[j]);	
							}else{
								qother.enqueue(temp[j]);
							}
							n++;
							
							if(j == temp.length-1){
								other = qother.getProcess();
								isMLFQ = true;
								q[i] = Qpriority(sched[i]); 
								//break;
								//paano pagbreak na inner loop la??
							}
						}											
					}
					index++;
				}
			}
		}
	}
	
	
	//other methods: pag balhin ha other sched
	public Queue Qpriority(int i){
		Queue q = new Queue();
		if(i == 0){
			q = FCFS();
			System.out.print(" FCFS ");
		}else if(i == 1){
			q = SJF();
			System.out.print(" SJF ");
		}else if(i == 2){
			q = SRTF();
			System.out.print(" SRTF ");
		}else if(i == 3){
			q = NPrio();
			System.out.print(" NPrio ");
		}else if(i == 4){
			q = Prio();
			System.out.print(" Prio ");
		}
		return q;
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
	
	public Process[] quicksort(Process p[], int low, int high, int c) {
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
		
		return p;
	}
	
	private void swap(Process arr[], int i, int j){
		Process temporary = arr[i];
		arr[i] = arr[j];
		arr[j] = temporary;
		arr[j] = temporary;
	}

	private void ganttChart(Queue printQueue, Queue timesQueue, int starter) {
		Process able = null;
		int arrivals = 0;
		int buff = timesQueue.getIndex() - 1;

		if(starter != 0)
			System.out.print("|    |");
		else
			System.out.print("|");

		while(printQueue.getIndex() > 0) {
			System.out.print(" P" + printQueue.dequeue().getProcessID() + " |");
		}

		if(starter != 0)
			System.out.print("\n0    ");
		else
			System.out.println();
		
		while(timesQueue.getIndex() > 0) {
			able = timesQueue.dequeue();
			arrivals += 1;
			if(timesQueue.getIndex() == buff) {
				if(able.getProcessID() >= 1 && able.getProcessID() <= 9) {
					if(able.getArrivalTime() >= 0 && able.getArrivalTime() <= 9)
						System.out.print(able.getArrivalTime() + "    ");
					else 
						System.out.print(able.getArrivalTime() + "   ");
					arrivals += able.getArrivalTime();
				} else {
					if(able.getArrivalTime() >= 0 && able.getArrivalTime() <= 9)
						System.out.print(able.getArrivalTime() + "     ");
					else
						System.out.print(able.getArrivalTime() + "    ");
					arrivals += able.getArrivalTime();
				}
			}

			if(able.getProcessID() >= 1 && able.getProcessID() <= 9) {
				if(arrivals >= 0 && arrivals <= 9)
					System.out.print(arrivals + "    ");
				else if(arrivals >= 10 && arrivals <= 99)
					System.out.print(arrivals + "   ");
				else
					System.out.print(arrivals + "  ");
			} else {
				if(arrivals >= 0 && arrivals <= 9)
					System.out.print(arrivals + "     ");
				else if(arrivals >= 10 && arrivals <= 99)
					System.out.print(arrivals + "    ");
				else
					System.out.print(arrivals + "   ");
			}
		}
	}
	
	
	public static void main(String[] args){
		Process p[] = {new Process(1, 5, 7, 9), 
					   new Process(2, 1, 5, 2), 
					   new Process(3, 2, 3, 5), 
					   new Process(4, 6, 1, 1), 
					   new Process(5, 4, 2, 6), 
					   new Process(6, 3, 1, 10)}; 
		CPUSched sched = new CPUSched(p);
		//process	arrival		burst		priority
		//  p1 		   5		  7				9
		//  p2		   1	      5				2
		//	p3         2		  3				5
		//  p4		   6  		  1				1
		// 	p5		   4		  2				6
		//  p6		   3  		  1				10
		
		
		// System.out.println("\nFCFS"); // ok
		// sched.FCFS();
		// System.out.println("\nSJF");  // ok
		// sched.SJF();
		// System.out.println("\nSRTF");  // adjust burst time
		// sched.SRTF();
		// System.out.println("\nNPrio"); // ok
		// sched.NPrio();
		// System.out.println("\nPrio"); // adjust burst time
		// sched.Prio();
		System.out.println("\nRound Robin");
		int s[] = {5, 5, 1}, t[] = {2, 3, -1};
		sched.MLFQ(s, t);
		
		int num[] = {1, 0, 0, 0, 0, 0,0};
		
		int tem = sched.getSmallestNum(num, 0);
		//System.out.println("\ntem = "+tem);
		
	}
	
}