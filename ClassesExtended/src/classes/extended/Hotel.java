package classes.extended;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Hotel {
    private Room[] rooms = new Room[8];
    static Scanner input = new Scanner(System.in);
    private static WaitingQueue queue = new WaitingQueue(5);
    public static void main(String[] args) throws InterruptedException {
        Hotel hotel = new Hotel();
        Room[] rooms = hotel.initialise();
        String menu = """
                A: add customer to room
                V: view rooms
                E: display empty rooms
                D: delete customer from room
                F: find room from customer name
                S: store program data file
                L: load program data from file
                O: View guests Ordered alphabetically by name
                x: Exit Menu""";
        System.out.println("\nwhat option would you like to use?");
        System.out.println(menu);
        String option = input.next().toUpperCase();

        bigLoop:
        while (true) {
            switch (option) {
                case "A":
                    addCustomer(rooms);
                    break;
                case "V":
                    viewAllRooms(rooms);
                    break;
                case "E":
                    displayEmptyRooms(rooms);
                    break;
                case "D":
                    deleteCustomer(rooms);
                    break;
                case "F":
                    findCustomer(rooms);
                    break;
                case "S":
                    writeToFile(rooms);
                    break;
                case "L":
                    loadDate(rooms);
                    break;
                case "O":
                    System.out.println(Arrays.deepToString((ascOrder(rooms))));
                    break;
                case "X":
                    break bigLoop;
                default:
                    System.out.println("Enter a valid option");

            }
            Thread.sleep(1000);
            System.out.println("\nwhat option would you like to use?");
            System.out.println(menu);
            option = input.next().toUpperCase();
        }
    }
    /*
    This method : initialises a array of Rooms objects and sets values to "e" and 0
     */
    private Room[] initialise() {
        for(int i = 0; i < 8; i++){
            Room room = new Room("e", 0, new Person("e","e","e"));
            rooms[i] = room;
        }
        System.out.println( "initialise ");
        return rooms;
    }
    /*
    This method: adds customers to rooms array and if the rooms array is filled with customers it adds the customers to
    a waiting queue
     */
    private static void addCustomer(Room[] rooms){
        int empty = 0;
        //checks the amount of empty rooms
        for (int x = 0; x < rooms.length; x++) {
            if (rooms[x].getRoomName().equals("e")) {
                empty++;
            }
        }
        try{
            int front = queue.getFront();
            int end = queue.getEnd();
            int size = queue.getQueSize();
            //checks if queue is full
            if((front == 0) && (end == (size)) || (front == end + 1)) {
                System.out.println("Queue is full");
                return;
            }
            int roomNum = -1;
            //checks if rooms are empty in the rooms array
            if(empty > 0){
                System.out.println("Enter room number (0-7)");
                roomNum = input.nextInt();
                //validates the number range and if room not empty returns back to menu
                if(!((roomNum >= 0 && roomNum <= 7) && (rooms[roomNum].getRoomName().equals("e")))){
                    System.out.println("Error: room invalid");
                    return;
                }
            }
            System.out.println("Enter Number of guests that will be in the room");
            int noOfGuest = input.nextInt();
            if(noOfGuest <=0){
                System.out.println("invalid input");
                return;
            }
            System.out.println("Enter your first name");
            String firstName = validateString(input.next());
            if(firstName == ""){
                System.out.println("invalid input");
                return;
            }
            System.out.println("Enter you surname");
            String surname = validateString(input.next());
            if(surname == ""){
                System.out.println("invalid input");
                return;
            }
            String roomName = firstName+surname;
            System.out.println("Enter your credit card number");
            String cardNum = validateCreditCard(input.next());
            if(cardNum == ""){
                System.out.println("invalid input");
                return;
            }
            Person customer = new Person(firstName, surname, cardNum);
            if(roomNum >=0) {
                rooms[roomNum] = new Room(roomName, noOfGuest, customer);
                System.out.println("Successfully checked in customer");
            }else{
                queue.enqueue(new Room(roomName, noOfGuest, customer));
                System.out.println("Customer in waiting queue");
            }
        }catch(InputMismatchException e){
            input.nextLine();
            System.out.println("invalid input");
        }
    }
    /*
    This method: removes a customer from the rooms array
     */
    private static void deleteCustomer(Room[] rooms){
        try{
            System.out.println("Enter the customer you want to remove");
            String delCustomer = validateString(input.next());
            if (delCustomer == "") {
                System.out.println("invalid input");
                return;
            }
            System.out.println("Enter the room that the customer was in");
            int roomNum = input.nextInt();
            boolean success = false;
            int front = queue.getFront();
            int end = queue.getEnd();
            if (front == end) {
                if (rooms[roomNum].getRoomName().equals(delCustomer)) {
                    rooms[roomNum].setRoomName("e");
                    rooms[roomNum].setNoOfGuest(0);
                    rooms[roomNum].setCustomer(new Person("e", "e", "e"));
                    success = true;
                }
            }else{
                if (rooms[roomNum].getRoomName().equals(delCustomer)) {
                    Room room = queue.dequeue();
                    rooms[roomNum] = room;
                    success = true;
                }
            }
            if (!success) {
                System.out.println("Customer was not deleted");
            }
        }catch (InputMismatchException e){
            System.out.println("invalid input");
            input.nextLine();
        }
    }
    /*
    This method: prints the roomNames in ascending order
     */
    private static String[] ascOrder(Room[] array){
        String[] ascArray = new String[8];
        for(int i = 0; i <array.length; i++){
            ascArray[i] = array[i].getRoomName();
        }
        for(int i = 0; i < array.length - 1; i++){
            for(int j = i+1; j < array.length; j++){
                if(ascArray[i].compareToIgnoreCase(ascArray[j])>0){
                    String temp = ascArray[i];
                    ascArray[i] = ascArray[j];
                    ascArray[j] = temp;
                }
            }
        }
        return ascArray;
    }
    /*
    This method: displays all rooms
     */
    private static void viewAllRooms(Room[] rooms){
        System.out.println("All rooms");
        for (int x = 0; x < rooms.length; x++) {
            System.out.println("room " + x + " occupied by " + rooms[x].getRoomName()  +" , total guests : " + rooms[x].getNoOfGuest() );
        }
    }
    /*
    This method: displays all empty rooms in the rooms array
     */
    private static void displayEmptyRooms(Room[] rooms){
        System.out.println("Empty rooms");
        for (int x = 0; x < rooms.length; x++) {
            if (rooms[x].getRoomName().equals("e")) System.out.println("room " + x + " is empty");
        }
    }
    /*
    This method: displays the required search customer using RoomName (RoomName = firstName + surname)
     */
    private static void findCustomer(Room[] rooms){
        System.out.println("Enter customers name");
        String findCustomer = validateString(input.next());
        if(findCustomer == ""){
            System.out.println("invalid input");
            return;
        }
        boolean found = false;
        for(int i = 0; i < rooms.length; i++){
            if (findCustomer.equals(rooms[i].getRoomName())){
                System.out.println("The customer " + findCustomer + " can be found in room number " + i);
                found = true;
            }
        }if(!found){
            System.out.println("Customer does not exist");
        }
    }
    /*
    This method writes all the data  into the storage.txt file
     */
    private static void writeToFile(Room[] rooms){
        try(FileWriter file = new FileWriter("src/resources/storage.txt")) {
            for (Room room : rooms) {
                Person customer = room.getCustomer();
                file.write(customer.getFirstName() + "," + customer.getSurname() + "," + customer.getCreditCard() + ","
                        + room.getNoOfGuest() + "\n");
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    /*
    This method: loads all the data from storage.txt and sets them into the required object classes using setters and
    getters
     */
    private static void loadDate(Room[] rooms){
        try {
            File file = new File("src/resources/storage.txt");
            Scanner fileReader = new Scanner(file);
            fileReader.useDelimiter("\n");

            while (fileReader.hasNext())
            {
                for(int i = 0 ; i < 8; i ++){
                    String[] line = fileReader.next().split(",");
                    rooms[i].setRoomName(validateString(line[0]+line[1]));
                    rooms[i].getCustomer().setFirstName(validateString(line[0]));
                    rooms[i].getCustomer().setSurname(validateString(line[1]));
                    rooms[i].getCustomer().setCreditCard(validateCreditCard(line[2]));
                    rooms[i].setNoOfGuest(Integer.parseInt(line[3]));

                }
                System.out.println("loaded successfully");
            }fileReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }catch (NumberFormatException e){
            System.out.println("Error");
        }
    }
    /*
    this method: validates strings using regular expressions
     */
    private static String validateString(String word){
        String stringRegX = "^[a-zA-Z]+$";
        word = word.trim();
        if(word.matches(stringRegX)){
            return word;
        }else{
            return "";
        }
    }
    /*
    this method: validates credit card number using regular expressions
     */
    private static String validateCreditCard(String cardNumber){
        String cardRegX = "^[0-9]{16}$";
        cardNumber = cardNumber.trim();
        if(cardNumber.matches(cardRegX)){
            return cardNumber;
        }else{
            return "";
        }
    }
}
