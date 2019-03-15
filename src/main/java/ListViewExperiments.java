
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;


public class ListViewExperiments extends Application  {




    @Override
    public void start(Stage primaryStage) throws Exception {


        /*try{
            Parent root = FXMLLoader.load(getClass().getResource("/jena.fxml"));
            Scene scene = new Scene(root, 870,610);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }*/


        Model model = ModelFactory.createDefaultModel();
        String inputFileName = "data/Movie.rdf";
        // use the FileManager to find the input file
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);


        primaryStage.setTitle("ListView Experiment 1");

        ListView listActors = new ListView();
        Label lblGenres = new Label("Select genre :");
        Label lblActors = new Label("Select actor :");
        Label lblWriters = new Label("Select writer :");
        Label lblDirectors = new Label("Select director :");

        listActors.setLayoutX(38.0);
        listActors.setLayoutY(21.0);
        listActors.setPrefHeight(155.0);
        listActors.setPrefWidth(112.0);

        listActors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Jena2 jena1 = new Jena2 (model);
        for(Person p : jena1.getPeople()){
            if (p.getType() != null && p.getType().contains("Actor")) listActors.getItems().add(p.getName());
        }

        ListView listWriters = new ListView();
        listWriters.setLayoutX(177.0);
        listWriters.setLayoutY(21.0);
        listWriters.setPrefHeight(155.0);
        listWriters.setPrefWidth(112.0);



        listWriters.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(Person p : jena1.getPeople()){
            if (p.getType() != null && p.getType().contains("Writer")) listWriters.getItems().add(p.getName());
        }

        ListView listDirectors = new ListView();
        listDirectors.setLayoutX(316.0);
        listDirectors.setLayoutY(21.0);
        listDirectors.setPrefHeight(155.0);
        listDirectors.setPrefWidth(112.0);
        listDirectors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for(Person p : jena1.getPeople()){
            if (p.getType() != null && p.getType().contains("Director")) listDirectors.getItems().add(p.getName());
        }

        //listActors.getItems().setAll(jena1.getPersons());


        ListView listGenres = new ListView();
        listGenres.setLayoutX(455);
        listGenres.setLayoutY(21.0);
        listGenres.setPrefHeight(155.0);
        listGenres.setPrefWidth(112.0);

        listGenres.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGenres.getItems().add("Thriller");
        listGenres.getItems().add("Action");
        listGenres.getItems().add("Crime");
        listGenres.getItems().add("Comedy");



        ListView listMovies = new ListView();
        listGenres.setLayoutX(39);
        listGenres.setLayoutY(231.0);
        listGenres.setPrefHeight(155.0);
        listGenres.setPrefWidth(528.0);

        TextField t = new TextField();
        t.setPrefHeight(90);
        t.setPrefWidth(456);


        Button button = new Button("Read Selected Value");

        button.setOnAction(event -> {
            listMovies.setItems(FXCollections.observableArrayList());
            ObservableList indicesActors = listActors.getSelectionModel().getSelectedIndices();
            ObservableList indicesGenres = listGenres.getSelectionModel().getSelectedIndices();
            ObservableList indicesWriters = listWriters.getSelectionModel().getSelectedIndices();
            ObservableList indicesDirectors = listDirectors.getSelectionModel().getSelectedIndices();
            if(listMovies.getItems().size() > 0) System.out.println(listMovies.getItems().size());
                listMovies.setItems(FXCollections.observableArrayList());
                listMovies.getItems().removeAll();


            for(Object o : indicesActors){
                //System.out.println((jena1.getPeople().get((int)o)) );
                if (jena1.getPeople().get((int)o).getActIn() != null){
                    for(Movie m : (jena1.getPeople().get((int)o)).getActIn()){
                    listMovies.getItems().add(m.toString());
                    t.setText(m.toString());
                    }
                }
                else listMovies.getItems().add("No match");
            }

            for(Object o : indicesGenres){

                for(Movie m : jena1.getMovies()){
                    if(m.getType().contains((String)listGenres.getItems().get((int)o))){
                        listMovies.getItems().add(m.toString());
                        t.setText(m.toString());
                    }

                }
            }
            for(Object o : indicesWriters){
                //System.out.println((jena1.getPeople().get((int)o)) );
                if (jena1.getPeople().get((int)o).getWrite() != null){
                    for(Movie m : (jena1.getPeople().get((int)o)).getWrite()){
                        listMovies.getItems().add(m.toString());
                    }
                }

                else listMovies.getItems().add("No match");
            }
            for(Object o : indicesDirectors){
                //System.out.println((jena1.getPeople().get((int)o)) );
                if (jena1.getPeople().get((int)o).getDirect() != null){
                    for(Movie m : (jena1.getPeople().get((int)o)).getDirect()){
                        listMovies.getItems().add(m.toString());
                    }
                }
                else listMovies.getItems().add("No match");
            }



        });

        listMovies.getItems().clear();
        VBox vBox = new VBox(lblActors,listActors,lblDirectors,listDirectors,lblWriters, listWriters,lblGenres, listGenres, button,listMovies);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}

