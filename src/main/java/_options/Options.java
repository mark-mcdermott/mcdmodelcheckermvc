package _options;

public class Options {

    // paths
    private String pathToXmlDir;
    private String pathToKrpDir;
    private String pathToTests;
    private String pathToModels;

    public Options() {

        // set paths
        pathToXmlDir = "src/main/resources/xml/";
        pathToKrpDir = "src/main/resources/krp/";
        pathToTests = "src/main/resources/tests/";
        pathToModels = "src/main/java/controller/content/";

    }

    public String getPathToTests() {
        return pathToTests;
    }

    public String getPathToKrpDir() {
        return pathToKrpDir;
    }

    public String getPathToXmlDir() {
        return pathToXmlDir;
    }

    public String getPathToModels() {
        return pathToModels;
    }
}
