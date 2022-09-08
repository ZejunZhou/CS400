// --== CS400 File Header Information ==--
// Name: Tony Chen
// Email: zchen873@wisc.edu
// Team: DG
// TA: Brianna
// Lecturer: Florian

import java.util.List;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MapApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Map of UW Madison");
        List<LocationDataInterface> locations = new LocationLoader().loadFile("./location.csv/");
        BackEnd engine = new BackEnd();
        //Inserting vertexs into the graph
        for(LocationDataInterface location : locations) {
            if(!engine.containsVertex(location.getLocation())&& !location.getLocation().equals("Location")){
            	engine.insertNode(location.getLocation());
	    }
	    if(!engine.containsVertex(location.getDestination())&& !location.getDestination().equals("Destination")){
	   	engine.insertNode(location.getDestination());
	    }

        }
        //Insert edges into the graph
        for(LocationDataInterface location : locations) {
            if(location.getLocation().equals("Location")){
            }
            else {
                engine.insertEdge(location.getLocation(),location.getDestination(),
                        Integer.parseInt(location.getWeight()));
		engine.insertEdge(location.getDestination(),location.getLocation(),
                        Integer.parseInt(location.getWeight()));
            }
        }
        MapFrontEnd ui = new MapFrontEnd();
        ui.run(engine);
    }

}
