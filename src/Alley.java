//Prototype implementation of Alley class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 2, 2020

public class Alley {

    Semaphore mutex, clockwise, counterClockwise;
    int nClockwise, nCounterClockwise;
    int dClockwise, dCounterClockwise;

    public Alley() {
        mutex = new Semaphore(1);
        clockwise  = new Semaphore(0);
        counterClockwise = new Semaphore(0);
    }

    /* Determine whether pos is right before alley is entered */
    public boolean atEntry(Pos pos) {
        return atEntrance(pos);
    }

    /* Determine whether pos is right after alley is left */
    public boolean atExit(Pos pos) {
        return atExits(pos);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        mutex.P();
        if(no <= 4){

            if(nClockwise > 0){
                dCounterClockwise++;
                mutex.V();
                counterClockwise.P();
            }

            nCounterClockwise++;
            signal();

        } else {

            if(nCounterClockwise > 0){
                dClockwise++;
                mutex.V();
                clockwise.P();
            }

            nClockwise++;
            signal();

        }
    }

    /* Register that car no. has left the alley */
    public void leave(int no) throws InterruptedException {
        mutex.P();
        if(no <= 4){
            nCounterClockwise--;
        } else {
            nClockwise--;
        }
        signal();
    }

    public void signal(){
        if(nClockwise == 0 && dCounterClockwise > 0){
            dCounterClockwise--;
            counterClockwise.V();
        } else if (nCounterClockwise == 0 && dClockwise > 0){
            dClockwise--;
            clockwise.V();
        } else {
            mutex.V();
        }
    }

    public boolean atEntrance(Pos pos) {
        return (pos.row == 10 && pos.col == 1) || (pos.row == 1 && pos.col == 1) || (pos.row == 2 && pos.col == 1);
    }

    public boolean atExits(Pos pos) {
        return (pos.row == 0 && pos.col == 0) || (pos.row == 9 && pos.col == 1);

    }
}