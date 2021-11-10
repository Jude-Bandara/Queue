package classes.extended;

public class Room {
    private String roomName;
    private int roomNum;
    private int noOfGuest;
    private Person customer;

    public Room(String roomName, int noOfGuest,Person customer){
        this.roomName = roomName;
        this.noOfGuest = noOfGuest;
        this.customer = customer;
    }
    public Room(){
    }
    @Override
    public String toString() {
        return "{" +
                 this.roomName + "," + this.noOfGuest + "," + this.customer.toString() +
                '}';
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(int noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

}
