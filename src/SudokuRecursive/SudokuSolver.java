package SudokuRecursive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuSolver {

    private static final int UNASSIGNED = 0;

    private Sudoku solvedSudoku;

    private CellChanged cellChanged;

    public boolean solve(Sudoku sudoku, boolean forwardChecking) throws InterruptedException {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                if(sudoku.getGrid()[row][col].getNumber() == UNASSIGNED) {
                    for(int number = 1; number <= 9; number++) {

                        if(sudoku.isAllowed(row, col, number, sudoku.getCellsInSameCage(sudoku.getGrid()[row][col].getCageIndex()))) {
                            Sudoku newSudoku = sudoku.deepCopy();
                            newSudoku.changeCell(row, col, number);

                            if (forwardChecking) {
                                Cell[] cellsInCage = newSudoku.getCellsInSameCage(newSudoku.getGrid()[row][col].getCageIndex()).toArray(new Cell[0]);
                                Cell[] cellsInRow = newSudoku.getCellsInSameRow(row);
                                Cell[] cellsInCol = newSudoku.getCellsInSameCol(col);
                                Cell[] cellsInBox = newSudoku.getCellsInSameBox(col, row);

                                List<Cell> cellsToRemovePossibleNr = new ArrayList<>(cellsInCage.length + cellsInRow.length + cellsInCol.length + cellsInBox.length);
                                Collections.addAll(cellsToRemovePossibleNr, cellsInCage);
                                Collections.addAll(cellsToRemovePossibleNr, cellsInBox);
                                Collections.addAll(cellsToRemovePossibleNr, cellsInRow);
                                Collections.addAll(cellsToRemovePossibleNr, cellsInCol);
                                for (Cell cell : cellsToRemovePossibleNr) {
                                    cell.removePossibleNr(number);
                                }
                            }

                            if (!forwardChecking || !newSudoku.containsCellWithoutPossibleNrs()) {
                                this.solvedSudoku = newSudoku;
                                cellChanged.cellChanged(newSudoku);
                                if (solve(newSudoku, forwardChecking)) {
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

    public Sudoku getSolvedSudoku() {
        return this.solvedSudoku;
    }

    public void setCellChanged(CellChanged cellChanged) {
        this.cellChanged = cellChanged;
    }

}
