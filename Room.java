import java.util.HashMap;
import java.util.LinkedList;

public class Room implements Comparable {


	public Room() {
		this("You are in a room", "Room");
	}
	public Room(String description, String shortDescription){
		
		this.roomWasVisited = false;
		this.description = description;
		this.shortDescription = shortDescription;
		this.items = new LinkedList<Item>();
		this.adjacentRooms = new HashMap<Action, Room>();
		this.transitionMessages = new HashMap<Action, String>();
		this.transitionDelay = 0;
	}
	public void setAdjacentRoom(Action a, Room r) {
		setOneWayAdjacentRoom(a, r);
		r.setOneWayAdjacentRoom(a.getOppositeDirection(), this);
	}
	public void setOneWayAdjacentRoom(Action a, Room r) {
		this.adjacentRooms.put(a, r);
	}
	public Room getRoomForDirection(Action a) {
		if(canMoveToRoomInDirection(a)) {
			return this.adjacentRooms.get(a);
		}
		return null;
	}
	public Action getDirectionForRoom(Room room) {
		for(Action a : this.adjacentRooms.keySet()) {
			if(this.adjacentRooms.get(a).compareTo(room) == 0) {
				return a;
			}
		}
		return Action.ActionUnknown;
	}
	public boolean canMoveToRoomInDirection(Action a) {
		return this.adjacentRooms.containsKey(a);
	}
	public void setAdjacentRoomTransitionMessage(String message, Action direction) {
		this.transitionMessages.put(direction, message);
	}
	public void setAdjacentRoomTransitionMessageWithDelay(String message, Action direction, int delay) {
		this.setAdjacentRoomTransitionMessage(message, direction);
		this.transitionDelay = delay;
	}
	public HashMap<Action, String> transitionMessages() {
		return this.transitionMessages;
	} 
	public int transitionDelay() {
		return this.transitionDelay;
	}
	public void putItem(Item item) {
		this.items.add(item);
	}
	public void putItems(LinkedList<Item> items) {
		for(Item i : items) {
			this.items.add(i);
		}
	}

	public Item takeItem(Item item) {
		if(item == null) {
			return null;
		}
		else if(this.items.contains(item)) {
			
			if(item.canBePickedUp()) {
				this.items.remove(item);
				return item;
			}
			else {
				System.out.println("You cannot pick up this item");
			}
		}
		else {
			System.out.println("I don't see that here.");
		}
		return Item.ItemUnknown;
	}
	public boolean hasItem(Item item) {
		return this.items.contains(item);
	}
	public void setPlayer(Player p) {
		this.player = p;
	}
	public String toString() {
		return this.description + visibleItems();
	}
	public String visibleItems() {
		String s = "";
		for(Item item : this.items) {
			if(item.isVisible()) {
				s += "There is a " + item.detailDescription() + " here.\n";
			}
		}
		return s;
	}
	public String description(){ 
		String d = this.roomWasVisited ? this.shortDescription : this.description;
		this.roomWasVisited = true;
		return d;
	}
	public String shortDescription() {
		return this.shortDescription;
	}
	public int compareTo(Object other) {
		if(shortDescription.compareTo(((Room)other).shortDescription()) == 0) {
			return 0;
		}
		return -1;
	}
	protected String description;
	protected String shortDescription;
	protected HashMap<Action, Room> adjacentRooms;
	protected HashMap<Action, String> transitionMessages;
	protected int transitionDelay;
	protected boolean roomWasVisited;
	protected LinkedList<Item> items;
	public Player player;
}
