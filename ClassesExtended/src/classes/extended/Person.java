package classes.extended;

public class Person {
    private String firstName;
    private String surname;
    private String creditCard;

    public Person(String firstName, String surname, String creditCard){
        this.firstName = firstName;
        this.surname = surname;
        this.creditCard = creditCard;
    }
    @Override
    public String toString() {
        return  firstName + "," + surname + "," + creditCard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
