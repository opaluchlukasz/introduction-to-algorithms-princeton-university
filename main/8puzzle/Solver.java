import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparingInt;

public class Solver {

    private static final Comparator<SolutionStep> SOLUTION_STEP_COMPARATOR =
            comparingInt(solutionStep -> solutionStep.board.manhattan() + solutionStep.movesToCurrentStep);
    private List<Board> solution;
    private boolean solvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SolutionStep> minPriorityQueue = new MinPQ<>(SOLUTION_STEP_COMPARATOR);
        minPriorityQueue.insert(new SolutionStep(initial, null));

        Board twin = initial.twin();
        MinPQ<SolutionStep> twinMinPriorityQueue = new MinPQ<>(SOLUTION_STEP_COMPARATOR);
        twinMinPriorityQueue.insert(new SolutionStep(twin, null));

        while(!minPriorityQueue.isEmpty() && !twinMinPriorityQueue.isEmpty()) {
            SolutionStep next = next(minPriorityQueue);
            if (next.board.isGoal()) {
                solvable = true;
                solution = next.pathToCurrentSolutionStep();
                break;
            }
            SolutionStep nextTwin = next(twinMinPriorityQueue);
            if (nextTwin.board.isGoal()) {
                solvable = false;
                break;
            }
        }
    }

    private static SolutionStep next(MinPQ<SolutionStep> solutionStepsQueue) {
        SolutionStep next = solutionStepsQueue.delMin();
        StreamSupport.stream(next.board.neighbors().spliterator(), false)
                .filter(neighbour -> !next.pathToCurrentSolutionStep().contains(neighbour))
                .map(neighbourBoard -> new SolutionStep(neighbourBoard, next))
                .forEach(solutionStepsQueue::insert);
        return next;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? Math.max(solution.size() - 1, 0) : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class SolutionStep {
        private final Board board;
        private final int movesToCurrentStep;
        private final SolutionStep previousStep;

        private SolutionStep(Board board, SolutionStep previousStep) {
            this.board = board;
            this.movesToCurrentStep = previousStep == null ? 0 : previousStep.movesToCurrentStep + 1;
            this.previousStep = previousStep;
        }

        private List<Board> pathToCurrentSolutionStep() {
            SolutionStep solutionStep = this;
            List<Board> boardLinkedList = new LinkedList<>();

            while (solutionStep.previousStep != null) {
                boardLinkedList.add(0, solutionStep.board);
                solutionStep = solutionStep.previousStep;
            }
            boardLinkedList.add(0, solutionStep.board);

            return boardLinkedList;
        }
    }
}
