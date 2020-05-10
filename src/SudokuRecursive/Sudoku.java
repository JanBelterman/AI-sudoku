package SudokuRecursive;

import java.util.ArrayList;
import java.util.List;


public class Sudoku {

    private static final int UNASSIGNED = 0;
    private final Cell[][] grid;

    public Sudoku(Cell[][] grid) {
        this.grid = grid;
    }

    public void changeCell(int row, int col, int number) {
        grid[row][col].setNumber(number);
    }

    public boolean containsCellWithoutPossibleNrs() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getNumber() == UNASSIGNED && !grid[i][j].hasPossibleNrs()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cell[] getCellsInSameRow(int row) {
        return grid[row];
    }

    public Cell[] getCellsInSameCol(int col) {
        List<Cell> cells = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            cells.add(grid[i][col]);
        }
        return cells.toArray(new Cell[0]);
    }

    public Cell[] getCellsInSameBox(int col, int row) {
        List<Cell> cells = new ArrayList<>();

        int r = row - row % 3;
        int c = col - col % 3;

        for(int i = r; i < r + 3; i++) {
            for(int j = c; j < c + 3; j++) {
                cells.add(grid[i][j]);
            }
        }

        return cells.toArray(new Cell[0]);
    }

    public Sudoku deepCopy() {
        Cell[][] copy = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = grid[i][j].deepCopy();
            }
        }
        return new Sudoku(copy);
    }

    private boolean containsInRow(int row, int number) {
        for(int i = 0; i < 9; i++)
        {
            if(grid[row][i].getNumber() == number) {
                return true;
            }
        }

        return false;
    }

    private boolean containsInCol(int col, int number) {
        for(int i = 0; i < 9; i++) {
            if(grid[i][col].getNumber() == number) {
                return true;
            }
        }

        return false;
    }

    private boolean containsInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for(int i = r; i < r + 3; i++) {
            for(int j = c; j < c + 3; j++) {
                if (grid[i][j].getNumber() == number) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean containsInCage(List<Cell> cells, int number) {
        for(Cell cell : cells) {
            if(cell.getNumber() == number) {
                return true;
            }
        }

        return false;
    }

    public List<Cell> getCellsInSameCage(int cageIndex) {
        List<Cell> cells = new ArrayList<>();

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                if(grid[row][col].getCageIndex() == cageIndex) {
                    cells.add(grid[row][col]);
                }
            }
        }

        return cells;
    }

    private boolean cageNrExceeded(List<Cell> cellsInCage, int number) {
        int wantedCageTotal = 0;
        int i = 0;
        while(wantedCageTotal == 0) {
            wantedCageTotal = cellsInCage.get(i).getCageNumber();
            i++;
        }
        int realCageTotal = number;
        for (Cell c : cellsInCage) {
            realCageTotal += c.getNumber();
        }
        return realCageTotal > wantedCageTotal;
    }

    // return false if whole cage is filled and nrs do not match
    private boolean cageNrDoesNotMatchWhenWholeCageIsFilled(List<Cell> cellsInCage, int number) {
        int wantedCageTotal = 0;
        int i = 0;
        while(wantedCageTotal == 0) {
            wantedCageTotal = cellsInCage.get(i).getCageNumber();
            i++;
        }
        int emptyCells = -1;
        int realCageTotal = number;
        for (Cell c : cellsInCage) {
            if (c.getNumber() == 0) { emptyCells++; }
            realCageTotal += c.getNumber();
        }
        boolean wholeCageFilled = emptyCells == 0;
        return wholeCageFilled && realCageTotal != wantedCageTotal;
    }

    public boolean isAllowed(int row, int col, int number, List<Cell> cellsInCage) {
        return !(containsInRow(row, number) || containsInCol(col, number) || containsInBox(row, col, number) || containsInCage(cellsInCage, number) || cageNrExceeded(cellsInCage, number) || cageNrDoesNotMatchWhenWholeCageIsFilled(cellsInCage, number) || !grid[row][col].getPossibleNrs().contains(number));
    }

    public Cell[][] getGrid() {
        return this.grid;
    }

    public void displaySudoku(Cell[][] grid) {
        for(int i = 0; i < 9; i++) {
            if(i % 3 == 0 && i != 0) {
                System.out.println("----------------------------------\n");
            }
            for(int j = 0; j < 9; j++) {
                if(j % 3 == 0 && j != 0) {
                    System.out.print(" | ");
                }
                System.out.print(" " + grid[i][j].getNumber() + " ");
            }
            System.out.println();
        }
        System.out.println("\n\n__________________________________________\n\n");
    }
}
