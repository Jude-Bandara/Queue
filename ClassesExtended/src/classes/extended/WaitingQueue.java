package classes.extended;
import java.util.Arrays;
public class WaitingQueue {
    private Room[] array;
    private int front,end = 0;

    public WaitingQueue(int size){
        this.array = new Room[size];
    }

    public void enqueue(Room item){
       if(end == this.array.length){
           end = 0;
       }
       array[end++] = item;
    }

    public Room dequeue(){
        if(front == this.array.length){
            front = 0;
        }
       Room temp = array[front];
       array[front++] = new Room();

        return temp;
    }
    public int getFront() {
        return front;
    }
    public int getEnd() {
        return end;
    }
    public int getQueSize(){
        return this.array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
