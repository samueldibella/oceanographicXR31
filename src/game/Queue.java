package game;
import java.util.ArrayList;

/**Queue ADT used to implement the queue side of SetOfSpaces
 * @author Samuel
 * @see SetofSpaces
 */
public class Queue {
	//index is queue's last element
	private ArrayList<String> queue;
	private boolean isEmpty;
	private int index;
	
	/**Class Constructor
	 */
	public Queue() {
		queue = new ArrayList<String>();
		setEmpty(true);
		index = -1;
	}
	
	void enqueue(String input) {
		setEmpty(false);
		queue.ensureCapacity(1);
		index++;
		queue.add(input);
	}
	
	String dequeue() {
		String output = queue.get(0);
		queue.remove(0);
		
		index--;
		if (index == -1) {
			setEmpty(true);
		} 

		return output;
	}
	
	String peak() {
		return queue.get(0); 
	}

	/**
	 * @return the isEmpty
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	@Override
	public String toString() {
		String output = "";
		for(int i = index; i > -1; i--) {
			output += queue.get(i) + "\n";
		}
		
		return output;
	}
	
	public int getIndex() {
		return index;
	}
}
