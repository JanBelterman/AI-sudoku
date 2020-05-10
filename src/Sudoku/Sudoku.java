package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Sudoku {
    private Cell[][] originGrid;
    private static final int UNASSIGNED = 0;

    public Sudoku(Cell[][] grid) {
        originGrid = grid;
    }

    public Cell[][] getOriginGrid() {
        return originGrid;
    }

    public int i = 0;

    public boolean solveSudoku(Cell[][] grid, GUI gui) throws InterruptedException {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                if(grid[row][col].getNumber() == UNASSIGNED) {
                    for(int number = 1; number <= 9; number++) {

                        if(isAllowed(grid, row, col, number, getCellsInSameCage(grid, grid[row][col].getCageIndex()))) {
                            Cell[][] newGrid = deepCopy(grid);
                            newGrid[row][col].setNumber(number);

                            Cell[] cellsInCage = getCellsInSameCage(newGrid, grid[row][col].getCageIndex()).toArray(new Cell[0]);
                            Cell[] cellsInRow = getCellsInSameRow(newGrid, row);
                            Cell[] cellsInCol = getCellsInSameCol(newGrid, col);
                            Cell[] cellsInBox = getCellsInSameBox(newGrid, col, row);

                            List<Cell> cellsToRemovePossibleNr = new ArrayList<>(cellsInCage.length + cellsInRow.length + cellsInCol.length + cellsInBox.length);
                            Collections.addAll(cellsToRemovePossibleNr, cellsInCage);
                            Collections.addAll(cellsToRemovePossibleNr, cellsInBox);
                            Collections.addAll(cellsToRemovePossibleNr, cellsInRow);
                            Collections.addAll(cellsToRemovePossibleNr, cellsInCol);
                            for (Cell cell : cellsToRemovePossibleNr) {
                                cell.removePossibleNr(number);
                            }

                            if (!containsCellWithoutPossibleNrs(newGrid)) {
                                i++;
                                this.originGrid = newGrid;
//                                displaySudoku(newGrid);
                                if (i % 5 == 0) {
                                    gui.displayNewState(newGrid);
                                    gui.sudokuTableToGUI();
                                }
//                                Thread.sleep(500);
                                if (solveSudoku(newGrid, gui)) {
                                    return true;
                                }
                            }
                        }

                    }
                    return false; // Backtrack

                }

            }
        }
        return true;
    }

    private boolean containsCellWithoutPossibleNrs(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getNumber() == UNASSIGNED && !grid[i][j].hasPossibleNrs()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Cell[] getCellsInSameRow(Cell[][] grid, int row) {
        return grid[row];
    }

    private Cell[] getCellsInSameCol(Cell[][] grid, int col) {
        List<Cell> cells = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            cells.add(grid[i][col]);
        }
        return cells.toArray(new Cell[0]);
    }

    private Cell[] getCellsInSameBox(Cell[][] grid, int col, int row) {
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

    private Cell[][] deepCopy(Cell[][] grid) {
        Cell[][] copy = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = grid[i][j].deepCopy();
            }
        }
        return copy;
    }

    private boolean containsInRow(Cell[][] grid, int row, int number) {
        for(int i = 0; i < 9; i++)
        {
            if(grid[row][i].getNumber() == number) {
                return true;
            }
        }

        return false;
    }

    private boolean containsInCol(Cell[][] grid, int col, int number) {
        for(int i = 0; i < 9; i++) {
            if(grid[i][col].getNumber() == number) {
                return true;
            }
        }

        return false;
    }

    private boolean containsInBox(Cell[][] grid, int row, int col, int number) {
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

    private List<Cell> getCellsInSameCage(Cell[][] grid, int cageIndex) {
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

    private boolean isAllowed(Cell[][] grid, int row, int col, int number, List<Cell> cellsInCage) {
        return !(containsInRow(grid, row, number) || containsInCol(grid, col, number) || containsInBox(grid, row, col, number) || containsInCage(cellsInCage, number) || cageNrExceeded(cellsInCage, number) || cageNrDoesNotMatchWhenWholeCageIsFilled(cellsInCage, number) || !grid[row][col].getPossibleNrs().contains(number));
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
