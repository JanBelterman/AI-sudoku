package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    private int number;
    private int cageNumber;
    private int cageIndex;

    private List<Integer> possibleNrs;

    public Cell(int number, int cageNumber, int cageIndex) {
        this.number = number;
        this.cageNumber = cageNumber;
        this.cageIndex = cageIndex;

        possibleNrs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCageNumber() {
        return cageNumber;
    }

    public int getCageIndex() {
        return cageIndex;
    }

    public void setPossibleNrs(List<Integer> nrs) {
        this.possibleNrs = nrs;
    }

    public void removePossibleNr(int nr) {
        possibleNrs.remove((Integer) nr);
    }

    public boolean hasPossibleNrs() {
        return possibleNrs.size() != 0;
    }

    public Cell deepCopy() {
        Cell copy = new Cell(number, cageNumber, cageIndex);
        copy.setPossibleNrs(new ArrayList<>(possibleNrs));
        return copy;
    }

    public List<Integer> getPossibleNrs() {
        return possibleNrs;
    }

}
