// --== CS400 Project Three File Header ==--
// Name: <Tong Xia>
// Email: <txia38@wisc.edu>
// Team: <BLUE>
// Group: <DG>
// TA: <Bri>
// Lecturer: <Florian>
// Notes to Grader: <optional extra notes>
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

interface MapFrontEndInterface{
    public void run(BackEndInterface graph);   //main entrance
    public void addLocation(String name, int x, int y); // add a location to the backend(name, x, y)
    public String searchDirections(String start, String dest);  // search directions from a place to another place
}

public class MapFrontEnd extends Application{

    static Stage stage;
    static List<String> locationList = new ArrayList<>();
    static BackEndInterface back;


    public void run(BackEndInterface graph)
    {
        back = graph;
        locationList = back.locationNameList();  //connect to the backend
        Application.launch();

    }

    @Override
    public void start( final Stage stage)
    {
        this.stage = stage;
        stage.setTitle("UW Madison Directions Advisor");    //title
        stage.setScene(MainMenu()); // set default window to Main Menu
        stage.show();
    }

    public Scene MainMenu()
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1280, 720); //this is a 720p frame
        pane.setTop(TitleComponent("Welcome!"));    //top menu is just a welcome msg
        pane.setCenter(MainMenuButtonComponent());  //bot contains some buttons
        return scene;
    }


    public VBox MainMenuButtonComponent()
    {
        VBox MainMenuButtonBox = new VBox();

        Button SearchPathBySelectingButton = new Button("Search/Create Path By Selecting");
        Button SearchPathByTypingButton = new Button("Search Path By Typing");
        Button AddLocationButton = new Button("Add/Remove a Location");
        Button AboutButton = new Button("About");

        SearchPathBySelectingButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                stage.setScene(selectSearchWindow());

            }
        });


        SearchPathByTypingButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                stage.setScene(typeSearchWindow());
            }
        });

        AddLocationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(AddLocationWindow());
            }
        });

        AboutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(AboutWindow());
            }
        });


        SearchPathBySelectingButton.setPrefSize(400,30);
        SearchPathByTypingButton.setPrefSize(400,30);
        AddLocationButton.setPrefSize(400,30);
        AboutButton.setPrefSize(400,30);

        MainMenuButtonBox.getChildren().addAll(SearchPathBySelectingButton,SearchPathByTypingButton,AddLocationButton,AboutButton);
        MainMenuButtonBox.setAlignment(Pos.CENTER);
        MainMenuButtonBox.setSpacing(20);
        return MainMenuButtonBox;
    }

    public Scene typeSearchWindow()
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1280,720);

        pane.setTop(TitleComponent("Search By Typing"));


        pane.setCenter(TextFieldUIComponent());
        pane.requestFocus();


        return scene;
    }

    public Scene AboutWindow()
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 1280,720);
        pane.setTop(TitleComponent("About This App"));
        VBox parentBox = new VBox();
        VBox description = new VBox();
        VBox developerBox = new VBox();
        Label about = new Label ("This is just a CS400 class project at UW Madison");
        Label func = new Label ("The app is designed to give basic directions on campus");
        Label IntegrationManager = new Label("Integration Manager: Tony Chen");
        Label FrontEnd = new Label("FrontEnd Developer: Tong Xia");
        Label BackEnd = new Label("BackEnd Developer: Adwait Vaidya");
        Label DataWrangler = new Label("Data Wrangler: Zejun Zhou");
        description.getChildren().addAll(about,func);
        developerBox.getChildren().addAll(IntegrationManager,FrontEnd,BackEnd,DataWrangler);
        parentBox.getChildren().addAll(description,developerBox);
        pane.setCenter(parentBox);

        description.setAlignment(Pos.CENTER);
        developerBox.setAlignment(Pos.CENTER);
        developerBox.setPadding(new Insets(10));
        parentBox.setPadding(new Insets(100));

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(MainMenu());
            }
        });


        parentBox.getChildren().add(backButton);
        parentBox.setAlignment(Pos.CENTER);

        return scene;


    }





    public Scene AddLocationWindow()
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1280,720);
        pane.setTop(TitleComponent("Add/Remove a Location"));

        pane.setCenter(AddLocationComponents());
        pane.requestFocus();

        return scene;
    }

    public VBox AddLocationComponents()
    {
        VBox centerContainer = new VBox();
        centerContainer.setPadding(new Insets(60));
        centerContainer.setSpacing(100);
        centerContainer.setAlignment(Pos.CENTER);

        HBox box = new HBox();

        TextField name = new TextField();

        name.setPromptText("Place Name...");


        box.getChildren().addAll(name);

        box.setSpacing(60);
        box.setAlignment(Pos.CENTER);

        HBox box2 = new HBox();
        Button AddLocationButton = new Button("Add Location");
        Button RemoveLocationButton = new Button("Remove Location");
        Button BackButton = new Button("Back to Menu");

        AddLocationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String place = name.getText();
                Alert lackInfoAlert = new Alert(Alert.AlertType.WARNING, "Please Type in All the Information Required");
                Alert existedAlert = new Alert(Alert.AlertType.WARNING, "The place is EXISTED");


                if(place == null ||place.trim().isEmpty())
                {
                    lackInfoAlert.show();
                    return;
                }else if(back.contains(place) || locationList.contains(place)){
                    existedAlert.show();
                    return;
                }else{

                    addLocation(place);
                    Alert successAdded = new Alert(Alert.AlertType.INFORMATION, "Successfully Added");
                    successAdded.show();
                    return;
                }





            }
        });

        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(MainMenu());
            }
        });

        RemoveLocationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String place = name.getText();

                Alert lackInfoAlert = new Alert(Alert.AlertType.WARNING, "Please Type in the place name for removing a location");

                if(place == null ||place.trim().isEmpty())
                {
                    lackInfoAlert.show();
                    return;
                }


                if(locationList.contains(place))
                {
                    removeLocation(place);
                    Alert successRemoved = new Alert(Alert.AlertType.INFORMATION, "Successfully Removed");
                    successRemoved.show();
                }else{
                    Alert nosuchplace = new Alert(Alert.AlertType.ERROR, "No Such Place in the Database");
                    nosuchplace.show();
                }



            }
        });

        box2.getChildren().addAll(AddLocationButton,RemoveLocationButton,BackButton);
        box2.setAlignment(Pos.CENTER);
        box2.setSpacing(60);

        Label hint = new Label("*You ONLY have to put the place name if you want to add/remove a place");

        centerContainer.getChildren().addAll(box,hint,box2);

        return centerContainer;
    }





    public HBox TitleComponent(String titleWords)
    {
        HBox topBox = new HBox();
        Label title = new Label(titleWords);
        title.setFont(new Font("Times New Roman",32));
        topBox.getChildren().add(title);
        topBox.setAlignment(Pos.CENTER);
        title.setTextFill(Color.web("Tomato"));
        topBox.setStyle("-fx-background-color: SlateBlue");
        return topBox;
    }



    public Scene selectSearchWindow()
    {
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane,1280,720);

        pane.setTop(TitleComponent("Search By Selection"));


        pane.setCenter(ComboBoxUIComponent());




        return scene;

    }
    private String numberTester(String str)
    {
        try{
            Integer.parseInt(str);
            return "cool";
        }catch(NumberFormatException e){
            return "notnumber";
        }
    }

    public VBox ComboBoxUIComponent()
    {
        VBox centerContainer = new VBox();
        centerContainer.setPadding(new Insets(60));
        centerContainer.setSpacing(50);

        HBox bot = new HBox();

        ComboBox StartComboBox = new ComboBox(FXCollections.observableList(locationList));
        ComboBox DestinationComboBox = new ComboBox(FXCollections.observableList(locationList));
        Label fromLabel = new Label("From");
        Label toLabel = new Label("To");

        StartComboBox.getSelectionModel().selectFirst();    //default the first option
        DestinationComboBox.getSelectionModel().selectFirst();


        bot.getChildren().addAll(fromLabel, StartComboBox, toLabel, DestinationComboBox);
        bot.setAlignment(Pos.CENTER);
        bot.setSpacing(20);

        HBox box = new HBox();

        Button SearchButton = new Button("Search Path");
        Button CreateButton = new Button("Create Path");
        Button BackButton = new Button("Back to Menu");

        HBox pathbox = new HBox();
        pathbox.setAlignment(Pos.CENTER);

        Label weightLabel = new Label("Minutes of Walking (Only For Creating Path):");
        TextField weightText = new TextField();
        HBox midBox = new HBox();
        midBox.getChildren().addAll(weightLabel,weightText);
        midBox.setAlignment(Pos.CENTER);

        BackButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                stage.setScene(MainMenu());
            }
        });


        SearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               // try{
                    String placeA = StartComboBox.getValue().toString();
                    String placeB = DestinationComboBox.getValue().toString();
                    Alert samePlaceAlert = new Alert(Alert.AlertType.INFORMATION, "You are right at the destination!");
                    Alert NoPathAlert = new Alert(Alert.AlertType.ERROR, "No Way to Get There");

                    String status = searchDirections(placeA,placeB);

                    if(status.equals("samePlace"))
                    {
                        samePlaceAlert.show();
                        return;
                    }

                    if(status.equals("error")){
                        return;
                    }


                    if(status.equals("NoPath")){
                        NoPathAlert.show();
                        return;
                    }

                    Label pathLabel = new Label();
                    pathLabel.setText(status);
                    pathLabel.setTextFill(Color.web("Orange"));
                    pathLabel.setFont(new Font("Arial",20));

                    pathbox.getChildren().clear();
                    pathbox.getChildren().add(pathLabel);





            }
        });

        CreateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    String placeA = StartComboBox.getValue().toString();
                    String placeB = DestinationComboBox.getValue().toString();
                    Alert samePlaceAlert = new Alert(Alert.AlertType.INFORMATION, "You are right at the destination!");
                    Alert PathCreateAlert = new Alert(Alert.AlertType.INFORMATION, "Path CREATED");
                    Alert pathExistAlert = new Alert(Alert.AlertType.INFORMATION, "The Path is already exist");
                    Alert notNumAlert = new Alert(Alert.AlertType.ERROR, "Please Put a Number for minutes it took");


                    if(numberTester(weightText.getText()).equals("notnumber")){
                        notNumAlert.show();
                        return;
                    }

                    int walkTime = Integer.parseInt(weightText.getText());

                    String status = searchDirections(placeA,placeB);

                    if(status.equals("samePlace"))
                    {
                        samePlaceAlert.show();
                        return;
                    }else{
                        back.insertEdge(placeA, placeB,walkTime);   //since it should be mutual directions
                        back.insertEdge(placeB, placeA,walkTime);

                        PathCreateAlert.show();
                        return;
                    }


                }catch (NullPointerException e){
                    Alert NullAlert = new Alert(Alert.AlertType.ERROR, "the place is null");
                    NullAlert.show();


                }




            }
        });

        box.getChildren().addAll(SearchButton, CreateButton,BackButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);



        centerContainer.getChildren().addAll(bot,midBox,box,pathbox);
        return centerContainer;

    }

    public VBox TextFieldUIComponent()
    {

        VBox centerContainer = new VBox();
        HBox bot = new HBox();



        TextField StartTextField = new TextField();
        TextField DestinationTextField = new TextField();
        Label fromLabel = new Label("From");
        Label toLabel = new Label("To");

        StartTextField.setPromptText("your Starting Location...");
        DestinationTextField.setPromptText("your Destination...");


        bot.getChildren().addAll(fromLabel, StartTextField, toLabel, DestinationTextField);
        bot.setAlignment(Pos.CENTER);
        bot.setSpacing(20);

        HBox box = new HBox();

        Button SearchButton = new Button("Search Path");
        Button BackButton = new Button("Back to Menu");

        HBox pathbox = new HBox();
        pathbox.setAlignment(Pos.CENTER);

        BackButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e)
            {
                stage.setScene(MainMenu());
            }
        });

        SearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String placeA = StartTextField.getText();
                String placeB = DestinationTextField.getText();
                Alert samePlaceAlert = new Alert(Alert.AlertType.INFORMATION, "You are right at the destination!");
                Alert NoPathAlert = new Alert(Alert.AlertType.ERROR, "No Way to Get There");
                Alert containAlert = new Alert(Alert.AlertType.ERROR, "There's NO such place in the map");

                Alert lackInfoAlert = new Alert(Alert.AlertType.WARNING, "Please Type in All the Information Required");

                String status = searchDirections(placeA, placeB);
                if(status.equals("lackInfo"))
                {
                    lackInfoAlert.show();
                    return;
                }


                if(status.equals("notcontain"))
                {
                    containAlert.show();
                    return;
                }


                if(status.equals("samePlace"))
                {
                    samePlaceAlert.show();
                    return;
                }


                if(status.equals("NoPath")){
                    NoPathAlert.show();
                    return;
                }

                if(status.equals("error")) return;

                Label pathLabel = new Label();
                pathLabel.setText(status);
                pathLabel.setTextFill(Color.web("Orange"));
                pathLabel.setFont(new Font("Arial",20));
                pathbox.getChildren().clear();
                pathbox.getChildren().add(pathLabel);


            }
        });


        box.getChildren().addAll(SearchButton, BackButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);


        centerContainer.setPadding(new Insets(60));
        centerContainer.setSpacing(100);
        centerContainer.getChildren().addAll(bot, box,pathbox);
        return centerContainer;
    }


    public void addLocation(String place)
    {

        back.insertNode(place);



        locationList.add(place);
    }

    public void removeLocation(String place)
    {

       if( locationList.contains(place) || back.contains(place) )
       {

           locationList.remove(place);
           back.removeVertex(place);

       }



    }

    public String searchDirections(String placeA,String placeB)
    {
        try{
            if(placeA == null ||placeA.trim().isEmpty()   || placeB == null || placeB.trim().isEmpty() )
            {

                return"lackInfo";
            }

            if(placeA.equals(placeB))
            {

                return"samePlace";
            }


            if((!back.contains(placeA) || !back.contains(placeB)) && (!locationList.contains(placeA) || !locationList.contains(placeB)) )
            {

                return"notcontain";
            }



            String pathString = "";
            pathString = back.findShortestPath(placeA,placeB);


            return pathString;
        }catch(NoSuchElementException |NullPointerException e){
             return"NoPath";
    }

    }






}

class MapFrontEndPlaceholder implements MapFrontEndInterface{   //frontend place holder
    public void run(BackEndInterface graph)
    {
        System.out.println("The Front End has not been implemented yet");
    }

    public void addLocation(String name, int x, int y)
    {
        return;
    }

    public String searchDirections(String start, String dest)
    {
        return "place A -> place B";
    }
}
