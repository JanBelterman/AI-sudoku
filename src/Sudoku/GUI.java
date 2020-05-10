package Sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

/* Class representing the Sudoku.GUI
 *
 * Author: Matt Tacchino
 *
 * Creates the interactive Sudoku.GUI to support the sudoku solver using GridBagLayout.
 * Frame is minimum 300x300px resizable
 *
 * There are 5 main parts:
 * 		Title
 * 		Grid: 9x9 grid of text fields, 81 total
 * 		Generate Example button: creates an example puzzle using the ExamplePuzzles class
 * 		Clear Table button: set sudokutable to a blank table and clear text fields
 * 		Solve button: Solves the sudoku puzzle. See SudokuTable.java for algorithm
 *
 */
public class GUI {

    private Cell[][] board;

    private JFrame frame = new JFrame("Sudoku Solver");
    private JTextField textField[][] = new JTextField[9][9];
    private GridPanel gridPanel = new GridPanel(new GridLayout(9,9,1,1));

    GUI(){
        for (int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++){
                textField[x][y] = new JTextField();
                textField[x][y].setForeground(Color.BLACK);
                textField[x][y].setHorizontalAlignment(0);
                gridPanel.add(textField[x][y]);
            }
        }
    }

    void displayNewState(Cell[][] board) {
        this.board = board;
    }

    public void sudokuTableToGUI(){
        for (int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++){
                if (board[y][x].getNumber() != 0) {
                    textField[x][y].setFont(new Font("Tahoma", Font.BOLD, 14));
                } else {
                    textField[x][y].setFont(new Font("Tahoma", Font.PLAIN, 14));
                }
                textField[x][y].setText(String.valueOf(board[y][x].getNumber()));
            }
        }
    }

    public void createGUI(){
        //create main panel and add a top title and bottom button. Also
        //add the grid panel to this panel in the centre
        JPanel mainPanel = new JPanel(new GridBagLayout()); //create main panel
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;

        //add label to top of main panel
        JLabel topLabel = new JLabel("Sudoku Solver", JLabel.CENTER);
        topLabel.setOpaque(true);
        topLabel.setBackground(Color.BLACK);
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 0.05;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(topLabel, gridBagConstraints);


        //add grid panel
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        mainPanel.add(gridPanel, gridBagConstraints);


        //create frame and add main panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.getContentPane().add(mainPanel); //add main panel to frame
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(300,300));
        frame.setVisible(true);
    }

    /* Nested class for the grid panel used in the Sudoku.GUI */
    public class GridPanel extends JPanel{
        private static final long serialVersionUID = -6157041650150998205L;

        GridPanel(GridLayout layout){
            super(layout);
        }

        //draw lines for 3x3 quardrants
        public void paintComponent(Graphics g){
            g.fillRect(getWidth()/3 - 1,0,3,getHeight());
            g.fillRect(2*getWidth()/3 - 1,0,3,getHeight());
            g.fillRect(0,getHeight()/3 - 1,getWidth(),3);
            g.fillRect(0,2*getHeight()/3 - 2,getWidth(),3);
        }
    }

}