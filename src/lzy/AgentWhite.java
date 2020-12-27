package lzy;

import java.util.ArrayList;
import java.util.List;

public class AgentWhite {
    private int DEPTH = 3;
    private static int MAX = Integer.MAX_VALUE;
    private static int MIN = Integer.MIN_VALUE;
    private List<White> nextStates = new ArrayList<>();

    public AgentWhite() {
    }

    public White randomPath(White white) {
        List<White> steps = white.nextSteps(false);
        if (steps.size() == 0) {
            steps = white.nextSteps(true);
        }
        return steps.size() > 0 ? steps.get(0) : null;
    }

    public White alphaBetaSearch(White state) {
        String currSituation = state.curSituation();
        int value = 0;
        if (currSituation.equals("miniMax")) {
            value = maxValue(state, MIN, MAX);
        } else {
            value = onlyMaxValue(state, MIN, MAX);
        }

        List<White> sameValue = new ArrayList<>();
        for (White nextState : nextStates) {
            if (nextState.getValue() == value) {
                sameValue.add(nextState);
            }
        }
        if (sameValue.size() >= 1) {
            return sameValue.get(0);
        }
        return null;
    }

    public int onlyMaxValue(White state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }
        state.setValue(MIN);

        state.onlyMax = true;
        List<White> nextSteps = state.nextSteps(false);

        if (state.getDepth() == 0) {
            if (nextSteps.size() == 0) {
                nextSteps = state.nextSteps(true);
            }
            nextStates = nextSteps;
        }
        int minValue = MIN;
        for (White ms : nextSteps) {
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

    public int maxValue(White state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }
        state.setValue(MIN);
        List<White> nextSteps = state.nextSteps(false);
        if (state.getDepth() == 0) {
            if (nextSteps.size() == 0) {
                nextSteps = state.nextSteps(true);
            }
            nextStates = nextSteps;
        }
        int minValue = MIN;
        for (White ms : nextSteps) {
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

    public int minValue(White state, int alpha, int beta) {
        if (state.getDepth() == DEPTH) {
            state.eval();
            return state.getValue();
        }
        state.setValue(MAX);
        List<White> nextSteps = state.nextSteps(false);
        int maxValue = MAX;
        for (White ms : nextSteps) {
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
