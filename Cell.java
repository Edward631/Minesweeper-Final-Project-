public abstract class Cell {
    protected boolean isRevealed;
    protected boolean isFlagged;

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void reveal() {
        this.isRevealed = true;
    }

    public void toggleFlag() {
        this.isFlagged = !this.isFlagged;
    }

    // Abstract because each type determines its own behavior
    public abstract boolean isMine();
}
