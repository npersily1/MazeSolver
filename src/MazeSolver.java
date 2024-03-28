/**
 * Solves the given maze using DFS or BFS
 *
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {

    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     *
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells

        // Create a Stack with the last cell pushed
        Stack<MazeCell> solution = new Stack<MazeCell>();
        solution.push(maze.getEndCell());

        // While we are not at the start
        while (!(solution.peek().equals(maze.getStartCell()))) {
            // Add the most recent cells parent
            MazeCell next = solution.peek().getParent();
            solution.push(next);
        }
        // Put into ArrayList and while doing so it will reverse
        ArrayList<MazeCell> sol = new ArrayList<MazeCell>();
        int n = solution.size();
        for (int i = 0; i < n; i++) {
            sol.add(solution.pop());
        }
        return sol;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     *
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // New Stack with the start cell
        Stack<MazeCell> solution = new Stack<MazeCell>();
        solution.push(maze.getStartCell());

        // While we are not at the end
        while (!(solution.peek() == maze.getEndCell())) {
            // Represents the current cell
            MazeCell cell = solution.pop();

            // North
            checkValid(solution, cell, cell.getRow() - 1, cell.getCol());

            // East
            checkValid(solution, cell, cell.getRow(), cell.getCol() + 1);

            // South
            checkValid(solution, cell, cell.getRow() + 1, cell.getCol());

            // West
            checkValid(solution, cell, cell.getRow(), cell.getCol() - 1);

        }

        return getSolution();
    }

    //
    public void checkValid(Stack<MazeCell> sol, MazeCell parent, int row, int col) {
        // If the cell is in the maze
        if (!maze.isValidCell(row, col)) {
            return;
        }
        // Gets the neighbor cell
        MazeCell cell = maze.getCell(row, col);
        // If it is new and is not a wall
        if (!cell.isExplored() && !cell.isWall()) {
            // Add the cell set the parent and set explored
            cell.setExplored(true);
            cell.setParent(parent);
            sol.push(cell);
        }
    }
    // Same method as above but with queues
    public void checkValid(Queue<MazeCell> sol, MazeCell parent, int row, int col) {
        if (!maze.isValidCell(row, col)) {
            return;
        }

        MazeCell cell = maze.getCell(row, col);
        if (!cell.isExplored() && !cell.isWall()) {
            cell.setExplored(true);
            cell.setParent(parent);
            sol.add(cell);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     *
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST

        // Create Line and add start cell to front
        Queue<MazeCell> solution = new LinkedList<MazeCell>();
        solution.add(maze.getStartCell());

        // While not at the end
        while (!(solution.peek() == maze.getEndCell())) {
            // Cell currently being looked at
            MazeCell cell = solution.remove();

            // North
            checkValid(solution, cell, cell.getRow() - 1, cell.getCol());

            // East
            checkValid(solution, cell, cell.getRow(), cell.getCol() + 1);

            // South
            checkValid(solution, cell, cell.getRow() + 1, cell.getCol());

            // West
            checkValid(solution, cell, cell.getRow(), cell.getCol() - 1);

        }

        return getSolution();

    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
