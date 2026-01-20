import java.util.Random;

public class Board {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private int mineCount;

    public Board(int rows, int cols, int mineCount) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        grid = new Cell[rows][cols];

        initializeBoard();
        placeMines();
        calculateNeighborCounts();
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    private void initializeBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new SafeCell();
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;

        while (placed < mineCount) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            if (!grid[r][c].isMine()) {
                grid[r][c] = new MineCell();
                placed++;
            }
        }
    }

    private void calculateNeighborCounts() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMine()) {
                    int count = countAdjacentMines(r, c);
                    ((SafeCell) grid[r][c]).setNeighborMines(count);
                }
            }
        }
    }

    public int countAdjacentMines(int r, int c) {
        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {

                int nr = r + dr;
                int nc = c + dc;

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    if (grid[nr][nc].isMine()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
