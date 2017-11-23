package library;

import java.util.Arrays;
import java.util.List;

class Command{
    private String action;
    private List<Model> models;


    public Command(String action, Model... models){
        this.action = action;
        this.models = Arrays.asList(models);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}