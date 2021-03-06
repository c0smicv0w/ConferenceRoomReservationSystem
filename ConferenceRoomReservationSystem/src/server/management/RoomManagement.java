package server.management;

import java.util.Date;

import server.list.RoomList;
import server.room.Room;

public class RoomManagement {

	private RoomList RL = new RoomList();

	public RoomManagement() {
	}

	public RoomList getRoomList() {
		return RL;
	}

	public RoomList getRegisteredRoomList(int key) {
		// get registered room list by EPuser
		return RL.findByEPKey(key);
	}

	public RoomList getBookableRoomList(Room room, Date date) {
		return RL.findBookableRoomList(room, date);
	}

	public Room getRoom(int key) {
		// get room data by name
		return RL.findByKey(key);
	}

	public boolean isItduplicated(Room room) {
		// true if overlap, false if non-overlap
		return RL.isItDuplicated(room.getName());
	}

	public void deleteRoom(int key) {
		RL.deleteByKey(key);
	}

	public void addRoom(Room room) {
		room.setKey(RL.getKey());
		RL.add(room);
		RL.increaseKey();
	}

	public void setRoomList(RoomList RL) {
		this.RL = RL;
	}

}