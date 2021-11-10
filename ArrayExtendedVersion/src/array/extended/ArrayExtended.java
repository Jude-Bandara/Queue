package array.extended;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayExtended {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException{
        int size = 8;
        String[] hotel = new String[size];
        int[] noOfGuestPerRoom = new int[size];
        String[][] payingGuest = new String[size][3];
        initialise(hotel,payingGuest);
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
        //Displaying the menu
        System.out.println(menu);
        String option = input.next().toUpperCase();
        bigLoop:
        while (true) {
            switch (option) {
                case "A":
                    addCustomer(hotel,noOfGuestPerRoom,payingGuest);
                    break;
                case "V":
                    viewAllRooms(hotel,noOfGuestPerRoom);
                    break;
                case "E":
                    displayEmptyRooms(hotel);
                    break;
                case "D":
                    deleteCustomer(hotel,noOfGuestPerRoom,payingGuest);
                    break;
                case "F":
                    findCustomer(hotel);
                    break;
                case "S":
                    writeToFile(noOfGuestPerRoom,payingGuest);
                    break;
                case "L":
                    loadDate(hotel,noOfGuestPerRoom,payingGuest);
                    break;
                case "O":
                    System.out.println(Arrays.toString(ascOrder(hotel)));
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
    This Method:
    initialises payingGuest and hotel array and sets all their values to 'e'(empty)
     */
    private static void initialise( String[] hotelRef,String[][] payingGuestRef) {
        for (int x = 0; x < hotelRef.length; x++ ) hotelRef[x] = "e";
        for (int x = 0; x < payingGuestRef.length; x++) {
            for(int i = 0; i < 3; i++){
                payingGuestRef[x][i] = "e";
            }
        }
        System.out.println( "initialise ");
    }
    /*
    This Method: takes room number, no of guests, first name, surname and creditcard number from the user and saves
    it inside the respective arrays.
     */
    private static void addCustomer(String[] hotel,int[] noOfGuestPerRoom,String[][] payingGuests){
        System.out.println("Enter room number (0-7)");
        try {
            int roomNum = input.nextInt();
            if(roomNum >= 0 && roomNum <= 7){
                if(hotel[roomNum].equals("e")) {
                    System.out.println("Enter Number of guests that will be in the room");
                    int noOfGuests = input.nextInt();
                    if(noOfGuests <=0){
                        System.out.println("invalid input");
                        return;
                    }
                    System.out.println("Enter first name: ");
                    String firstName = validateString(input.next());
                    if(firstName == ""){
                        System.out.println("invalid input");
                        return;
                    }
                    System.out.println("Enter surname name: ");
                    String surname = validateString(input.next());
                    if(surname == ""){
                        System.out.println("invalid input");
                        return;
                    }
                    System.out.println("Enter credit card details");
                    String cardNum = validateCreditCard(input.next());
                    if(cardNum == ""){
                        System.out.println("invalid input");
                        return;
                    }
                    hotel[roomNum] = firstName+surname;
                    noOfGuestPerRoom[roomNum] = noOfGuests;
                    payingGuests[roomNum][0] = firstName;
                    payingGuests[roomNum][1] = surname;
                    payingGuests[roomNum][2] = cardNum;
                    System.out.println("Successfully checked in");
                }else{
                    System.out.println("room is already occupied");
                }
            }else{
                System.out.println("Invalid input Error");
            }
        }catch(InputMismatchException e){
            input.nextLine();
            System.out.println("You have entered an incorrect character");
        }
    }
    /*
    This Method: takes customers name who has to be removed and the room number and then loops through the
    hotel array finds the room name equal to the customer and replaces the values in all arrays with the same index
    to "e"
     */
    private static void deleteCustomer(String[] hotel, int[] noOfGuestPerRoom, String[][] payingGuest){
        try{
            System.out.println("Enter the customer you want to remove");
            String delCustomer = validateString(input.next());
            if(delCustomer == ""){
                System.out.println("invalid input");
                return;
            }
            boolean success = false;
            System.out.println("Enter the room that the customer was in");
            int roomNum = input.nextInt();
            for(int i = 0 ; i < hotel.length; i++){
                if ((delCustomer.equals(hotel[i])) && roomNum == i){
                    hotel[i] = "e";
                    noOfGuestPerRoom[i] = 0;
                    payingGuest[i][0] = "e";
                    payingGuest[i][1] = "e";
                    payingGuest[i][2] = "e";
                    System.out.println("Successfully removed");
                    success = true;
                }
            }if(!success){
                System.out.println("Invalid name or room number");
            }
        }catch (InputMismatchException e){
            System.out.println("invalid Input");
        }
    }
    /*
    This Method: compares the roomNames and sets it in ascending order
     */
    private static String[] ascOrder(String[] array){
        String[] ascArray = array;
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
    This Method: prints all the rooms in the given format
     */
    private static void viewAllRooms(String[] hotel,int[] noOfGuestsPerRoom){
        System.out.println("All rooms");
        for (int x = 0; x < 8; x++) {
            System.out.println("room " + x + " occupied by " + hotel[x] +", total guests : " + noOfGuestsPerRoom[x]);
        }
    }
    /*
    This Method: loops through the hotel array checks if the roomName is "e" the prints the prints the empty rooms
     */
    private static void displayEmptyRooms(String[] hotel){
        System.out.println("Empty rooms");
        for (int x = 0; x < 8; x++) {
            if (hotel[x].equals("e")) System.out.println("room " + x + " is empty");
        }
    }
    /*
    This Method: loops the the hotel and finds which index the customer is in and then displays it in the below
    format
     */
    private static void findCustomer(String[] hotel){
        System.out.println("Enter customers name");
        String findCustomer = validateString(input.next());
        if(findCustomer == ""){
            System.out.println("invalid input");
            return;
        }
        boolean found = false;
        for(int i = 0; i < hotel.length; i++){
            if (findCustomer.equals(hotel[i])){
                System.out.println("The customer " + findCustomer + " can be found in room number " + i);
                found = true;
            }
        }if(!found){
            System.out.println("Customer not found or does not exist");
        }
    }
    /*
    this method: writes the data from the indexs from the arrays into the storage.txt file
     */
    private static void writeToFile(int[] noOfGuestPerRoom,String[][] payingGuests)  {
        try(FileWriter file = new FileWriter("src/resources/storage.txt")) {
            for(int i = 0; i < noOfGuestPerRoom.length; i++){
                file.write( payingGuests[i][0] + "," + payingGuests[i][1]  + "," + payingGuests[i][2] + ","
                        + noOfGuestPerRoom[i] + "\n");
            }
            System.out.println("Successfully wrote to the file.");
        }catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    /*
    this method:validates and loads the data from the file assigning them to the respective arrays
     */
    private static void loadDate(String[] hotel,int[] noOfGuestPerRoom,String[][] payingGuests){
        try {
            File file = new File("src/resources/storage.txt");
            Scanner fileReader = new Scanner(file);
            fileReader.useDelimiter("\n");

            while (fileReader.hasNext()) {
                for(int i = 0 ; i < 8; i ++){
                    String[] line = fileReader.next().split(",");
                    hotel[i] = validateString(line[0]+line[1]) ;
                    payingGuests[i][0] = validateString(line[0]);
                    payingGuests[i][1] = validateString(line[1]);
                    payingGuests[i][2] = validateCreditCard(line[2]);
                    noOfGuestPerRoom[i] = Integer.parseInt(line[3]);
                    if(hotel[i] == "" || payingGuests[i][0] == "" || payingGuests[i][1] == "" || payingGuests[i][2] == ""){
                        System.out.println("invalid inputs");
                        return;
                    }else{
                        System.out.println(payingGuests[i][0]+ " " + payingGuests[i][1] + "'s record has been added" );
                    }
                }
            }
            fileReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }catch (InputMismatchException e){
            System.out.println("invalid Inputs");
        }
    }
    /*
    this method : validates strings using regular expressions
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
    this method : validates the creditcard number using regular expressions
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
