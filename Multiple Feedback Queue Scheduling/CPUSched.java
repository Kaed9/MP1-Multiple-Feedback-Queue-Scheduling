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
	/*public void check(){
		System.out.println("\nChecking");
		for(int i = 0; i < process.length; i++){
			System.out.print(process[i].getProcessID()+" ");
		}
		System.out.println("\nDone\\");
	}*/
	
	//CPU Scheduling Algorithm
	public Process[] FCFS(){
		// check();
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
					// check();
				} catch(InterruptedException iEx) { }
			}
		}.start();
		*/
		
		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
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
				if(j == 0){
					queue.initialProcess(temp[i]);
					printQueue.initialProcess(temp[i]);
					timesQueue.initialProcess(temp[i]);
					// System.out.print("P" + temp[i].getProcessID()+" ");
				}else{
					queue.enqueue(temp[i]);
					printQueue.enqueue(temp[i]);
					timesQueue.enqueue(temp[i]);
					// System.out.print("P" + temp[i].getProcessID()+" ");
				}				
			}
		}

		// Process sample = null;

		// while(queue.getIndex() > 0) {
		// 	sample = queue.dequeue();
		// 	if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
		// 		printQueue.initialProcess(sample);
		// 		timesQueue.initialProcess(sample);
		// 	} else {
		// 		printQueue.enqueue(sample);
		// 		timesQueue.enqueue(sample);
		// 	}
		// }

		System.out.println();
		// System.out.println(printQueue.getIndex());
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();
		// check();

		return temp;
	}
	
	public Process[] SJF(){
		Process[] temp = process;
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
			} else {
				printQueue.enqueue(sampler);
				timesQueue.enqueue(sampler);
			}
		}

		System.out.println();
		// System.out.println(printQueue.getIndex());
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return sample;
	}
	
	public Process[] SRTF(){
		Process[] temp = process;
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
		int starter = temp[0].getArrivalTime();
		Process sample = null;

		while(queue.getIndex() > 0) {
			sample = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return temp;
	}
	
	public Process[] NPrio(){
		Process[] temp = process;	
		Process[] sample = new Process[process.length];
		int last = 0, prio[] = new int[process.length];
		
		quicksort(temp, 0, temp.length-1, 0);
		
		for(int i =0; i< process.length; i++){
			prio[i] = temp[i].getPriority();
		}
		
		Queue printQueue = new Queue();
		Queue timesQueue = new Queue();
		int starter = temp[0].getArrivalTime();
		for(int i = 0; i < temp.length; i++){
			if(i == 0){
				for(int j = 0; j < temp[i].getBurstTime(); j++){
					if(j ==0){
						queue.initialProcess(temp[i]);
						if(i == 0) {
							printQueue.initialProcess(temp[i]);
							timesQueue.initialProcess(temp[i]);
						} else {
							printQueue.enqueue(temp[i]);
							timesQueue.enqueue(temp[i]);
						}
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
						if(j == 0) {
							printQueue.initialProcess(temp[j]);
							timesQueue.initialProcess(temp[j]);
						} else {
							printQueue.enqueue(temp[j]);
							timesQueue.enqueue(temp[j]);
						}
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

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return sample;
	}
	
	public Process[] Prio(){			
		Process[] temp = process;
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
		int starter = temp[0].getArrivalTime();
		Process sample = null;

		while(queue.getIndex() > 0) {
			sample = queue.dequeue();
			if(printQueue.getIndex() == 0 || timesQueue.getIndex() == 0) {
				printQueue.initialProcess(sample);
				timesQueue.initialProcess(sample);
			} else {
				printQueue.enqueue(sample);
				timesQueue.enqueue(sample);
			}
		}

		System.out.println();
		ganttChart(printQueue, timesQueue, starter);
		System.out.println();

		return temp;
	}
	
	public void RR(int timeQ){			//???
		//important variables
		Process temp[] = process;
		Queue q = new Queue();
		
		
		int burst[] = new int[process.length], arrival[] = new int[process.length];		
		boolean flag = true, isAvailable[] = new boolean[process.length];
		int count = 0, index = 0;
		
		//sort by arrival time
		quicksort(temp, 0, temp.length-1, 0);
		
		//initialize
		for(int i = 0; i < temp.length; i++){
			burst[i] = temp[i].getBurstTime();
			arrival[i] = -1;		
			isAvailable[i] = false;
		}
		
		while(flag == true){		
			//System.out.println("start");
			//initial 	process
			int top=0; //index han top queue
			boolean done = false;
			boolean flag2 = false;
			//initialize top
			if(index == 0){
				for(int i = 0; i < temp.length; i++){
					if(temp[i].getArrivalTime() == getSmallestNum(arrival, 0)){
						top = i;
						//System.out.println("top: "+top);
					}
				}
				arrival[top] = temp[top].getArrivalTime();	
				isAvailable[top] = true;
				q.initialProcess(temp[top]);
				//System.out.println("top:"+top);
			}
			
			//process			
			boolean cont = (burst[top] >= timeQ);
			int bb = burst[top];
			//System.out.println(!cont);
			if((bb >= timeQ && bb!=0)  && flag2 == false && isAvailable[top] == true){
				//System.out.println("i:"+index);
				for(int i = 0; i < timeQ; i++){
					if(index == 0){
						queue.initialProcess(temp[top]);
					}else{
						queue.enqueue(temp[top]);
					}
					index++;
					burst[top]--;
					System.out.print(temp[top].getArrivalTime()+" ");
				}
				flag2 = true;				
				bb=burst[top];
				/* 
				for(int i = 0; i<timeQ; i++){
					if(index	 == 0){
						queue.initialProcess(temp[top]);
					}else{
						queue.enqueue(temp[top]);
					}
					System.out.print(temp[top].getArrivalTime()+" ");					
					//System.out.println(burst[top]);
					index++;
					burst[top]--;					
				}
				flag2 = true;
				burst[top]-=timeQ;
				
				System.out.println("burst:"+bb); */
			}else if(bb < timeQ && bb !=0  && flag2 == false && isAvailable[top] == true){				
				//System.out.println("i:"+index);
				for(int i = 0; i < bb; i++){
					if(index == 0){
						queue.initialProcess(temp[top]);
					}else{
						queue.enqueue(temp[top]);
					}
					index++;
					burst[top]--;
					System.out.print(temp[top].getArrivalTime()+" ");					
				}
				//burst[top]-=bb;
				bb = burst[top];
				flag2 = true;
				/* for(int i = 0; i < bb; i++){									
					if(index == 0){
						queue.initialProcess(temp[top]);
					}else{
						queue.enqueue(temp[top]);
						System.out.println("no");
					}
					System.out.print(temp[top].getArrivalTime()+" ");					
					//System.out.println(burst[top]);
					index++;	
					burst[top]--;
					
				}
				flag2 = true;
				bb = burst[top]; */
				//burst[top]-=bb;
			}						
			//System.out.print(index+" ");
			//Take note of 'q'			 			
			
			for(int i = 0; i < temp.length; i++){
				if(index >= temp[i].getArrivalTime()&& i!=top){
					q.enqueue(temp[i]);					
					isAvailable[i]=true;	
					arrival[i] = temp[i].getArrivalTime();	
					//System.out.println("MO");
				}
			}
			
			/* for(int i = 0; i< temp.length;i++){
				System.out.println(i+".arr: "+isAvailable[i]);
			} */
			//add top process if burst!=0
			if(burst[top] != 0){
				q.enqueue(temp[top]);
			}
			
			for(int i = 0; i < temp.length; i++){
				if(burst[i] == 0){
					burst[i] = -1;
				}
				//System.out.println(i+".burst: "+burst[i]);
			} 				
			
			Process pro;
			//set top
			//System.out.println("Non:"+q.getIndex());
			for(int i = 0; i < temp.length; i++){
				if(isAvailable[i] == true && q.getIndex() != 0){
					pro = q.dequeue();
					System.out.println(i+":"+pro.getArrivalTime());
					if(pro.getArrivalTime() == temp[i].getArrivalTime()){
						top = i;						
					}						
				}
			}
			
			//index++;
			if(index == totalTime(1)-1){
				flag=false;
				System.out.print("index: "+index);
			}			
		}
	}

	public void myRR(int quantumTime) {

		Process[] temp = process, temp1 = null;
		Queue queue1 = new Queue();
		Queue queue2 = new Queue();
		int tempLength = temp.length;

		quicksort(temp, 0, temp.length - 1, 0);
		for(int i = 0; i < temp.length; i++) {
			System.out.print(temp[i].getProcessID() + " ");
		}
		System.out.println();
		for(int i = 0; i < temp.length; i++) {
			System.out.print(temp[i].getArrivalTime() + " ");
		}
		System.out.println();

		int smallestArrival = temp[0].getArrivalTime();
		int totalArrival = totalTime(0);
		int totalBurst = totalTime(1);
		System.out.println(totalArrival + "|" + totalBurst + "||" + tempLength);

		queue1.initialProcess(temp[0]);
		// queue1.initialProcess(new Process(30, 0, 0, 0));
		// queue1.enqueue(temp[0]);
		queue2.initialProcess(temp[0]);
		for(int i = 1; i < temp.length; i++) {
			queue2.enqueue(temp[i]);
		}
		// temp1 = new Process[temp.length];
		// temp1 = queue1.getProcess();
		// System.out.println();
		// for(int i = 0; i < temp.length + 1; i++) {
		// 	if(i == 0) {
		// 		queue1.enqueue(queue1.dequeue());
		// 		System.out.print(queue1.dequeue().getProcessID() + "|");
		// 	} else {
		// 		System.out.print(queue1.dequeue().getProcessID() + " ");
		// 	}
		// }
		// System.out.println("\n");

		// int count = 0;
		// int bound = 0;
		// temp1 = temp;
		// System.out.println(tempLength + "|" + (tempLength - 1));
		System.out.println();
		// for(int i = 0; i < temp1[0].getArrivalTime(); i++) {
		// 	System.out.println(i + "|");
		// 	bound++;
		// }
		// Process unused1 = queue1.dequeue();
		// Process unused2 = queue2.dequeue();
		Process usable = null, checkable = null;
		int countable = 0, maxCount = queue1.getIndex();
		// int boundary = temp[0].getArrivalTime();

		// for(int i = temp1[0].getArrivalTime(); i < (totalBurst + bound); i++) {
		// 	System.out.print(i + "|");
		// 	countable = 0;
		// 	while(count < tempLength) {
		// 		int tempArrival = temp1[count].getArrivalTime();
		// 		if(i == tempArrival) {
		// 			System.out.print(" " + queue1.getIndex() + " ");
		// 			// if(count == 0) {
		// 			// 	queue1.initialProcess(temp1[count]);
		// 			// 	// System.out.print("||");
		// 			// 	queue1.enqueue(temp1[count]);
		// 			// } else {
		// 			// 	queue1.enqueue(temp1[count]);
		// 			// }
		// 			countable++;
		// 			// System.out.print(queue1.getIndex());
		// 			int qc = quantumTime * countable;
		// 			// System.out.print("=" + qc + "=");

		// 			// if(queue1.getIndex() > 0) {
		// 				// usable = queue1.dequeue();
		// 				System.out.print(" P" + temp1[count].getProcessID() + " ");

		// 				if(temp1[count].getBurstTime() > quantumTime) {
		// 					temp1[count].setBurstTime(temp1[count].getBurstTime() - quantumTime);
		// 					temp1[count].setArrivalTime(temp1[count].getArrivalTime() + qc);
		// 					// queue1.enqueue(temp1[count]);
		// 					// countable++;
		// 				} else if(temp1[count].getBurstTime() < quantumTime && temp1[count].getBurstTime() != 0) {
		// 					temp1[count].setBurstTime(0);
		// 				}
		// 			// }
		// 			// System.out.print("=" + qc + "=");
		// 		}
		// 		count++;
		// 	}

		/*while(countable < maxCount) {
			// System.out.print(countable + "|");

			usable = queue1.dequeue();
			int currentBurst = usable.getBurstTime();
			int currentArrival = usable.getArrivalTime();
			if(currentBurst >= quantumTime) {
				usable.setBurstTime(currentBurst - quantumTime);
				usable.setArrivalTime(currentArrival + quantumTime);
				System.out.print("P" + usable.getProcessID() + " ");
				queue1.enqueue(usable);
			} else if(currentBurst < quantumTime && currentBurst != 0) {
				usable.setBurstTime(currentBurst - currentBurst);
				System.out.print("P" + usable.getProcessID() + " ");
				countable++;
			}

			if(queue1.getIndex() == 1) {
				queue1.enqueue(new Process(30, 100, 0, 0));
				break;
			}
			// if(countable == maxCount - 1) {
			// 	break;
			// }
			// System.out.println("|" + countable);
		}
		usable = queue1.dequeue();
		System.out.print("|P" + usable.getProcessID() + " ");*/

		int countTempLength = 0;
		int quantumTimeLoop = temp[0].getArrivalTime();
		Process curSmal = null, minSmal = null;

		// while(queue1.getIndex() > 0) {
			
		// }
		// while(true) {
			/*
			try {
				usable = queue1.dequeue();
			} catch(NullPointerException npEx) {
				// for(int i = 0; i < tempLength; i++) {
				// 	curSmal = queue2.dequeue();
				// 	if(i == 0) {
				// 		minSmal = curSmal;
				// 	} else {
				// 		if(curSmal.getArrivalTime() < minSmal.getArrivalTime()) {
				// 			minSmal = curSmal;
				// 		}
				// 	}
				// 	queue2.enqueue(curSmal);
				// }
				queue1.enqueue(temp[countTempLength]);
				// queue1.enqueue(usable);
				// continue;
			}
			// System.out.print("|" + usable.getProcessID() + "|");
			if(usable.getProcessID() == 30) {
				queue1.enqueue(usable);
			} else {
				int currentBurst = usable.getBurstTime();
				int currentArrival = usable.getArrivalTime();
				if(currentBurst > quantumTime) {
					usable.setBurstTime(currentBurst - quantumTime);
					usable.setArrivalTime(currentArrival + quantumTime);
					System.out.print("P" + usable.getProcessID() + " ");
					// queue1.enqueue(usable);
					quantumTimeLoop += quantumTime;
				} else if(currentBurst <= quantumTime && currentBurst != 0) {
					usable.setBurstTime(currentBurst - currentBurst);
					System.out.print("P" + usable.getProcessID() + " ");
					countTempLength++;
					quantumTimeLoop += currentBurst;
					// countable++;
				}
				// System.out.print("=" + countTempLength + "-" + tempLength + "= ");

				for(int i = 0; i < tempLength; i++) {
					checkable = queue2.dequeue();
					if(checkable.getArrivalTime() <= quantumTimeLoop) {
						// System.out.print("|" + checkable.getProcessID() + "|");
						if(checkable.getProcessID() != usable.getProcessID()) {
							queue1.enqueue(checkable);
						}
					}
					queue2.enqueue(checkable);
				}
				// queue1.enqueue(usable);
				*/
			// }

			// if(countTempLength == tempLength) {
			// 	break;
			// }
		// }

			// System.out.println();
			// count = 0;
		// }
		// System.out.println();
		// for(int i = 0; i < countable; i++) {
		// 	System.out.println(queue1.dequeue().getProcessID() + " ");
		// }
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
			arrivals += able.getBurstTime();
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
		System.out.println("\nSJF");  // ok
		sched.SJF();
		// System.out.println("\nSRTF");  // adjust burst time
		// sched.SRTF();
		// System.out.println("\nNPrio"); // ok
		// sched.NPrio();
		// System.out.println("\nPrio"); // adjust burst time
		// sched.Prio();
		// System.out.println("\nRound Robin");
		// sched.myRR(2);
		
		int num[] = {1, 0, 0, 0, 0, 0,0};
		
		int tem = sched.getSmallestNum(num, 0);
		//System.out.println("\ntem = "+tem);
		
	}
	
}