/**
 *	A SinglyLinkedList of Coordinate objects representing
 *	a snake on a two-dimensional grid.
 *
 *	@author	Srithika Barakam
 *	@since	05/10/25
 */
public class Snake extends SinglyLinkedList<Coordinate> {
		
	/**	Constructor for making a Snake that is 5 grids high facing north.
	 *	Places the snake head at Coordinate location and the tail below.
	 *	Precondition: To place the Snake, the board must have at
	 *				least location.getRow() + 4 more rows.
	 */
	public Snake(Coordinate location) { 
			add(location);
			Coordinate cd1 = new Coordinate(location.getX()+1, location.getY());
			add(cd1);
			Coordinate cd2 = new Coordinate(location.getX()+2, location.getY());
			add(cd2);	
			Coordinate cd3 = new Coordinate(location.getX()+3, location.getY());
			add(cd3);	
			Coordinate cd4 = new Coordinate(location.getX()+4, location.getY());
			add(cd4);								
		
		}
	
	
	}
