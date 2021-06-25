package SGP.CA.Domain;

public class Strategy {

    private int number;
    private String strategy;
    private String goal;
    private String action;
    private String result;

    public Strategy() {
        this.number = 0;
        this.strategy = "";
        this.goal = "";
        this.action = "";
        this.result = "";
    }

    public Strategy(int number, String strategy, String goal, String action, String result) {
        this.number = number;
        this.strategy = strategy;
        this.goal = goal;
        this.action = action;
        this.result = result;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNumber() {
        return number;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getGoal() {
        return goal;
    }

    public String getAction() {
        return action;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "number: " + number + '\n' +
                "strategy: " + strategy + '\n' +
                "goal: " + goal + '\n' +
                "action: " + action + '\n' +
                "result: " + result + '\n';
    }

    @Override
    public boolean equals(Object o) {
        boolean iguales = false;
        if (this.getClass() == o.getClass()) {
            Strategy strategy = (Strategy) o;
            if (this.getStrategy().equals(strategy.getStrategy()) &&
                this.getNumber() == strategy.getNumber() &&
                this.getGoal().equals(strategy.getGoal()) &&
                this.getAction().equals(strategy.getAction()) &&
                this.getResult().equals(strategy.getResult())) {
                iguales = true;
            }
        }
        return iguales;
    }
}
