//Prototype implementation of Field class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU, Fall 2020

//Hans Henrik Lovengreen     Oct 2, 2020

public class Field {

    private Semaphore[][] semaphore;

    public Field() {
        semaphore = new Semaphore[Layout.ROWS][Layout.COLS];

        for (int i = 0; i < Layout.ROWS; i++){
            for (int j = 0; j < Layout.COLS; j++) {
                semaphore[i][j] = new Semaphore(1);
            }
        }
    }

    /* Block until car no. may safely enter tile at pos */
    public void enter(int no, Pos pos) throws InterruptedException {
        semaphore[pos.row][pos.col].P();
    }

    /* Release tile at position pos */
    public void leave(Pos pos) {
        semaphore[pos.row][pos.col].V();
    }
}
