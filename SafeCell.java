public class SafeCell extends Cell {
    private int neighborMines;

    public SafeCell() {
        this.neighborMines = 0;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int count) {
        this.neighborMines = count;
    }

    @Override
    public boolean isMine() {
        return false;
    }
}
