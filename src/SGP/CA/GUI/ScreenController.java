package SGP.CA.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

public class ScreenController {

    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;
    public static ScreenController instance;


    public ScreenController(Scene main) {
        this.main = main;
        instance = this;
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot(screenMap.get(name));
    }

    protected boolean isScreenOnMap(String name) {
        return screenMap.containsKey(name);
    }
}


