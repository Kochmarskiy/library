package library;

class Model{

    private String authorName;
    private String modelName;
    public Model(String authorName, String modelName){
        this.authorName = authorName;
        this.modelName = modelName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}