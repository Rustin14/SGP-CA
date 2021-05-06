package SGP.CA.Domain;

public class Objective {

    private String objectiveTitle;
    private String description;
    private String strategy;

    public Objective(){
        this.objectiveTitle = "";
        this.description = "";
        this.strategy = "";
    }

    public Objective(String objectiveTitle, String description, String strategy){
        this.objectiveTitle = objectiveTitle;
        this.description = description;
        this.strategy = strategy;
    }

    public void setObjectiveTitle(String objectiveTitle) {
        this.objectiveTitle = objectiveTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getObjectiveTitle() {
        return objectiveTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return "objectiveTitle: " + objectiveTitle + '\n' +
                "description:" + description + '\n' +
                "strategy: " + strategy + '\n';
    }

    @Override
    public boolean equals(Object o){
        boolean iguales = false;
        if (this.getClass() == o.getClass()){
            Objective objective = (Objective) o;
            if (this.getObjectiveTitle().equals(objective.getObjectiveTitle()) &&
                this.getDescription().equals(objective.getDescription()) &&
                this.getStrategy().equals(objective.getStrategy())){
                iguales = true;
            }
        }
        return iguales;
    }
}
