package lzy;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private int DEPTH = 3;
    private static int MAX = Integer.MAX_VALUE;
    private static int MIN = Integer.MIN_VALUE;
    private List<Black> nextStates = new ArrayList<>();

    public Agent() {
    }

    public Black randomPath(Black black) {
        List<Black> steps = black.nextSteps(false);
        if (steps.size() == 0) {
            steps = black.nextSteps(true);
        }
        return steps.size() > 0 ? steps.get(0) : null;
    }

    public Black alphaBetaSearch(Black state) {
        String currSituation = state.curSituation();
        int value = 0;
        if (currSituation.equals("miniMax")) {
            value = maxValue(state, MIN, MAX);
        } else {
            value = onlyMaxValue(state, MIN, MAX);
        }

        List<Black> sameValue = new ArrayList<>();
        for (Black nextState : nextStates) {
            if (nextState.getValue() == value) {
                sameValue.add(nextState);
            }
        }
        if (sameValue.size() >= 1) {
            return sameValue.get(0);
        }
        return null;
    }

    public int onlyMaxValue(Black state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }
        state.setValue(MIN);

        state.onlyMax = true;
        List<Black> nextSteps = state.nextSteps(false);

        if (state.getDepth() == 0) {
            if (nextSteps.size() == 0) {
                nextSteps = state.nextSteps(true);
            }
            nextStates = nextSteps;
        }
        int minValue = MIN;
        for (Black ms : nextSteps) {
            if (ms.isWin()) {
                ms.setValue(Integer.MAX_VALUE - state.getDepth());
                state.setValue(Integer.MAX_VALUE - state.getDepth());
                return Integer.MAX_VALUE - state.getDepth();
            }
            ms.setDepth(state.getDepth() + 1);
            int tempValue = onlyMaxValue(ms, alpha, beta);
            if (minValue < tempValue) {
                minValue = tempValue;
            }
            state.setValue(Math.max(state.getValue(), minValue));
            if (state.getValue() >= beta) {
                return state.getValue();
            }
            alpha = Math.max(alpha, state.getValue());
        }
        return state.getValue();
    }

    public int maxValue(Black state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }
        state.setValue(MIN);
        List<Black> nextSteps = state.nextSteps(false);
        if (state.getDepth() == 0) {
            if (nextSteps.size() == 0) {
                nextSteps = state.nextSteps(true);
            }
            nextStates = nextSteps;
        }
        int minValue = MIN;
        for (Black ms : nextSteps) {
            if (ms.isWin()) {
                ms.setValue(Integer.MAX_VALUE - state.getDepth());
                state.setValue(Integer.MAX_VALUE - state.getDepth());
                return Integer.MAX_VALUE - state.getDepth();
            }
            ms.setDepth(state.getDepth() + 1);
            int tempValue = minValue(ms, alpha, beta);
            if (minValue < tempValue) {
                minValue = tempValue;
            }
            state.setValue(Math.max(state.getValue(), minValue));
            if (state.getValue() >= beta) {
                return state.getValue();
            }
            alpha = Math.max(alpha, state.getValue());
        }
        return state.getValue();
    }

    public int minValue(Black state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }

        state.setValue(MAX);

        List<Black> nextSteps = state.nextSteps(false);
        int maxValue = MAX;
        for (Black ms : nextSteps) {
            if (ms.isLose()) {
                ms.setValue(Integer.MIN_VALUE + state.getDepth());
                state.setValue(Integer.MIN_VALUE + state.getDepth());
                return Integer.MIN_VALUE + state.getDepth();
            }

            ms.setDepth(state.getDepth() + 1);
            int tempValue = maxValue(ms, alpha, beta);

            if (maxValue > tempValue) {
                maxValue = tempValue;
            }

            state.setValue(Math.min(state.getValue(), maxValue));
            if (state.getValue() <= alpha) {
                return state.getValue();
            }

            beta = Math.min(beta, state.getValue());
        }
        return state.getValue();
    }

    public void setDEPTH(int DEPTH) {
        this.DEPTH = DEPTH;
    }
}
