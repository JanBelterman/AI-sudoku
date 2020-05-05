import java.util.ArrayList;

public class AI {

    public GUI gui;

    private SudokuNode root;
    private SudokuNode currentNode;


    public AI(Sudoku root) {
        this.currentNode = new SudokuNode(root, 0);
        this.root = this.currentNode;
    }

    public void solve() throws InterruptedException {
        while(true) {
//            Thread.sleep(50);

            Sudoku nextStep = new Sudoku(currentNode.sudoku.board, currentNode.sudoku.size);

            boolean nextStepFound = false;

            // Tries next step
            for (int y = 0; y < nextStep.size; y++) {
                if (nextStepFound) {
                    break;
                }
                for (int x = 0; x < nextStep.size; x++) {
                    if (nextStep.board[y][x] == 0) {
                        // Empty cell found
                        nextStep.board[y][x] = currentNode.lastMoveAsNextStep + 1;
                        nextStepFound = true;
                        break;
                    }
                }
            }


            // Displays it
//            nextStep.display();
            this.gui.displayNewState(nextStep.board);
            gui.sudokuTableToGUI();


            // Determines next step
            if (nextStep.isValid()) { // Valid next state
                SudokuNode nextNode = new SudokuNode(nextStep, 0);
                currentNode.addChild(nextNode);
                currentNode = nextNode;
            } else { // Not valid next state!!
                if (currentNode.lastMoveAsNextStep + 1 >= 9) {
                    currentNode = currentNode.parent; // Backtrack
//                    currentNode.parent.lastMoveAsNextStep += 1;
                    currentNode.lastMoveAsNextStep += 1;
                } else {
                    currentNode.lastMoveAsNextStep = currentNode.lastMoveAsNextStep + 1;
                }
            }

            // Sudoku solved?
            if (!nextStepFound) {
                break;
            }
        }
    }

}

class SudokuNode {

    public Sudoku sudoku;

    public SudokuNode parent;
    public ArrayList<SudokuNode> children;

    public int lastMoveAsNextStep;

    public SudokuNode(Sudoku sudoku, int lastMoveAsNextStep) {
        this.sudoku = sudoku;
        this.children = new ArrayList<>();
        this.lastMoveAsNextStep = lastMoveAsNextStep;
    }

    public void addChild(SudokuNode child) {
        child.parent = this;
        this.children.add(child);
    }

}