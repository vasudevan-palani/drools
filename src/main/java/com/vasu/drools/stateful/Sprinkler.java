package com.vasu.drools.stateful;

public class Sprinkler {

    private Room room;

    private boolean on;

	public Sprinkler(Room room2) {
		this.room =room2;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

    

}