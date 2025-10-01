import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - These are methods that are used for SinglyLinkedList,
 * these include add, clear, remove, etc. These methods are similar to those
 * used in arraylists, and have similar mannerisms.
 *
 *	@author		Srithika Barakam
 *	@since		05/05/2025
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	
	/* No-args Constructors */
	public SinglyLinkedList() {}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) {
		head = oldList.head;
		tail = oldList.tail;
	}
	
	/**	Clears the list of elements */
	public void clear() {
		head = null;
		tail = null;
	}
	
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		ListNode<E> node = new ListNode<>(obj);
		if (tail == null) {
			tail = node;
			head = node;
		}
		else {
			tail.setNext(node);
			tail = node;
		}
		
		return true;
	}
	
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		
		if (index == size()) {
			add(obj);
			return true;
		}
		if (index < 0 || index > size())				
			throw new NoSuchElementException();
		ListNode<E> curr = head;				
		ListNode<E> prev = null;
		for (int i = 0; i < index; i++) {
			prev = curr;
			curr = curr.getNext();
		}
		ListNode newNode = new ListNode(obj, curr);
		if (prev != null) 
			prev.setNext(newNode);
		else {
			head = newNode;
		}
		return true;
		
	}
	
	/**	@return the number of elements in this list */
	public int size() {
		if (head == null)
			return 0;
		
		int count = 1;					
		ListNode<E> curr = head;
		while(curr.getNext() != null) {
			curr = curr.getNext();
			count++;
		}
		return count;
	}
	
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		if (index < 0 || index >= size())
			throw new NoSuchElementException();
		ListNode curr = head;
		for (int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		return curr;
	}
	
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj) {
		if (index < 0 || index >= size())
			throw new NoSuchElementException();
		ListNode<E> curr = head;
		for (int i = 0; i < index; i++)
			curr = curr.getNext();
		
		E temp = curr.getValue();
		curr.setValue(obj);
		return temp;
	}
	
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {
		if (index < 0 || index >= size()) 
			throw new NoSuchElementException();
		
		if (index == 0 ) {
			E temp = head.getValue();
			head = head.getNext();
			return temp;
		}
		ListNode<E> curr = head;
		for (int i = 0; i < index-1; i++) {
			curr = curr.getNext();
		}
		E temp = curr.getNext().getValue();
		curr.setNext(curr.getNext().getNext());
		if (curr.getNext() == null)
			tail = curr;
		return temp;
	}
	
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}
	
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) {
		if (head == null)
			return false;
		ListNode curr = head;
		if (curr.getNext() == null) {
			if (curr.getValue().equals(object))
				return true;
		}
		while (curr.getNext() != null) {			
			if (curr.getValue().equals(object))
				return true;
			curr = curr.getNext();
		}
		return false;
	}
	
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) {
		if (head == null)
			return -1;					
		ListNode curr = head;
		if (curr.getNext() == null) {
			if (curr.getValue().equals(element))
				return 0;
		}
		for (int i = 0; i < size(); i++) {
			if (curr.getValue().equals(element))
				return i;
			curr = curr.getNext();
		}
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
	

}
