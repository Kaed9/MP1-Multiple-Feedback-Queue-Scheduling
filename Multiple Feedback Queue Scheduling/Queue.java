public class Queue {
	private int index;
	private LinkedList processList;
	
	public Queue (){
		
	}
	
	public void initialProcess(Process p){
		processList = new LinkedList(p);
		index = processList.getCurrentIndex();
	}
	
	public void enqueue(Process data){
		processList.insert(data);
		index++;	
	}
	
	public Process dequeue(){
		Process n = null;
		if(index != 0){
			Node temp = processList.getHead();
			int ind = temp.id;
			n = processList.delete(ind);
			index--;
		}else{
			System.out.println("Overflow");
		}
		return n;
	}
	
	public void print(){
		processList.print();
	}
}