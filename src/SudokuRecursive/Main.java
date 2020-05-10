package SudokuRecursive;

public class Main {

    public GUI gui = new GUI();

    public static Cell[][] GRID_TO_SOLVE = {
            {new Cell(0, 3, 1),  new Cell(0, 0, 1),  new Cell(0, 15, 2), new Cell(0, 0, 2),  new Cell(0, 0, 2),  new Cell(0, 22, 3), new Cell(0, 4, 4),  new Cell(0, 16, 5), new Cell(0, 15, 6)},
            {new Cell(0, 25, 7), new Cell(0, 0, 7),  new Cell(0, 17, 8), new Cell(0, 0, 8),  new Cell(0, 0, 3),  new Cell(0, 0, 3),  new Cell(0, 0, 4),  new Cell(0, 0, 5),  new Cell(0, 0, 6)},
            {new Cell(0, 0, 7),  new Cell(0, 0, 7),  new Cell(0, 9, 9),  new Cell(0, 0, 9),  new Cell(0, 0, 3),  new Cell(0, 8, 10), new Cell(0, 20, 11),new Cell(0, 0, 11), new Cell(0, 0, 6)},
            {new Cell(0, 6, 13), new Cell(0, 14, 14),new Cell(0, 0, 14), new Cell(0, 0, 9),  new Cell(0, 17, 17),new Cell(0, 0, 10), new Cell(0, 0, 11), new Cell(0, 17, 12),new Cell(0, 0, 6)},
            {new Cell(0, 0, 13), new Cell(0, 13, 15),new Cell(0, 0, 15), new Cell(0, 20, 16),new Cell(0, 0, 17), new Cell(0, 0, 10), new Cell(0, 0, 12), new Cell(0, 0, 12), new Cell(0, 12, 19)},
            {new Cell(0, 27, 20),new Cell(0, 0, 15), new Cell(0, 6, 21), new Cell(0, 0, 16), new Cell(0, 0, 17), new Cell(0, 20, 23),new Cell(0, 6, 18), new Cell(0, 0, 18), new Cell(0, 0, 19)},
            {new Cell(0, 0, 20), new Cell(0, 0, 21), new Cell(0, 0, 21), new Cell(0, 0, 16), new Cell(0, 10, 22),new Cell(0, 0, 23), new Cell(0, 0, 23), new Cell(0, 14, 24),new Cell(0, 0, 24)},
            {new Cell(0, 0, 20), new Cell(0, 8, 25), new Cell(0, 16, 26),new Cell(0, 0, 22), new Cell(0, 0, 22), new Cell(0, 15, 27),new Cell(0, 0, 27), new Cell(0, 0, 24), new Cell(0, 0, 24)},
            {new Cell(0, 0, 20), new Cell(0, 0, 25), new Cell(0, 0, 26), new Cell(0, 0, 22), new Cell(0, 13, 28),new Cell(0, 0, 28), new Cell(0, 0, 28), new Cell(0, 17, 29),new Cell(0, 0, 29)},
    };

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n\n------------------------------\n");

        GUI gui = new GUI();
        gui.createGUI();
        Sudoku sudoku = new Sudoku(GRID_TO_SOLVE);
        SudokuSolver solver = new SudokuSolver();

        SudokuSolverRunner runner = new SudokuSolverRunner(gui, solver, sudoku);
        runner.run();
        System.out.println("\n------------------------------");
    }

}

class SudokuSolverRunner implements CellChanged {

    public GUI gui;
    public int steps;

    public SudokuSolver sudokuSolver;
    public Sudoku sudoku;

    public SudokuSolverRunner(GUI gui, SudokuSolver sudokuSolver, Sudoku sudoku) {
        this.gui = gui;
        this.sudokuSolver = sudokuSolver;
        this.sudokuSolver.setCellChanged(this);
        this.sudoku = sudoku;
    }

    public void run() throws InterruptedException {
        long startTime = System.nanoTime();
        sudokuSolver.solve(sudoku, true);
        System.out.println((System.nanoTime() - startTime) / 1000000 + " milliseconds to solve");
        System.out.println(steps + " steps to solve");

        gui.displayNewState(sudokuSolver.getSolvedSudoku().getGrid());
        gui.sudokuTableToGUI();
    }

    @Override
    public void cellChanged(Sudoku sudoku) throws InterruptedException {
        steps++;
        if (steps % 100 == 0) {
            gui.displayNewState(sudoku.getGrid());
            gui.sudokuTableToGUI();
//            Thread.sleep(100);
        }
    }

}
