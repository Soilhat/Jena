import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

import java.net.URL;
import java.util.ResourceBundle;


public class JenaController implements Initializable {
    @FXML
    ListView<Person> listActors;

    @Override
    public void initialize(URL location, ResourceBundle resources){
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
        Jena1 jena1 = new Jena1(model);
        //listActors.setItems((ObservableList<Person>) jena1.getPersons());
        //ObservableList<Person> p = (ObservableList<Person>) jena1.getPersons();
        //ObservableList<Person> p = FXCollections.observableArrayList(jena1.getPersons());
        Person p = new Person("aa");
        //listActors.getItems().setAll(p);
        //listActors.setItems().add(p);

    }
}
