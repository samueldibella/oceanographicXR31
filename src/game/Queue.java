package game;
import java.util.ArrayList;

/**Queue ADT used to implement the queue side of SetOfSpaces
 * @author Samuel
 * @see SetofSpaces
 */
public class Queue {
	//index is queue's last element
	private ArrayList<Space> queue;
	private boolean isEmpty;
	private int index;
	
	/**Class Constructor
	 */
	public Queue() {
		queue = new ArrayList<Space>();
		setEmpty(true);
		index = -1;
	}
	
	void enqueue(Space input) {
		setEmpty(false);
		queue.ensureCapacity(4);
		index++;
		queue.add(input);
	}
	
	Space dequeue() {
		Space output = queue.get(0);
		queue.remove(0);
		
		index--;
		if (index == -1) {
			setEmpty(true);
		} 

		return output;
	}
	
	Space peak() {
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
		String output = "{";
		for(int i = 0; i < index; i++) {
			output += "X" + queue.get(i).getX() + "Y" + queue.get(i).getY();
			output += "'" + queue.get(i) + "'\\";
		}
		
		return output;
	}
}
