//Prototype implementation of Alley class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 2, 2020

public class Alley {

    Semaphore mutex;
    int car;

    public Alley() {
        mutex = new Semaphore(1);
    }

    /* Determine whether pos is right before alley is entered */
    public boolean atEntry(Pos pos) {
        return atExitOrEntrance(pos);
    }

    /* Determine whether pos is right after alley is left */
    public boolean atExit(Pos pos) {
        return atExitOrEntrance(pos);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        mutex.P();
        car = no;
    }

    /* Register that car no. has left the alley */
    public void leave(int no) {
        if(no == car) {
            mutex.V();
        }
    }

    public boolean atExitOrEntrance(Pos pos){
        return (pos.row == 9 && pos.col == 1) || (pos.row == 10 && pos.col == 1) || (pos.row == 0 && pos.col == 0)
                    || (pos.row == 1 && pos.col == 1) || (pos.row == 2 && pos.col == 1);
    }
}
