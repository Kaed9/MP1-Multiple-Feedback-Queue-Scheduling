public class LinkedList{
	private Node head;
	private Node curr;
	private int index = 0;
	
	public LinkedList(Process data){
		curr = new Node(index, data);
		head = curr;		
		index++;
	}
	
	public void insert(Process data){
		Node temp = new Node(index, data);
		curr.next = temp;
		temp.prev = curr;
		curr = temp;
		index++;
	}
	
	public Process delete(int id){ //gin rereturn an head
		Node current = head;
		Node parent = null;
		Node temp = null;
		int count = 0;
		
		while(current != null && index < id){
			parent = current;
			current = current.next;
			
			count++;
		}
		
		if(current != null && parent == null){
			if(current.next != null){
				temp = head;
				head = current.next;
			}else {
				head = null;
			}
		} else if (current != null) {
			temp = parent.next;
			parent.next = current.next;
		}
		
		return temp.getData();
	}
	
	public int getCurrentIndex(){
		return index;
	}
	
	public Node getHead(){
		return head;
	}
	
	public void print(){ //Don't mind this
		Node n = head;
		
		while (n != null){
			System.out.println(n.getData()+ " ");
			n = n.next;
		}
	}
}