import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class main {
    public static void main(String[] args) {
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

        /*Jena1 jena1 = new Jena1(model);
        List<Person> personList = jena1.getPersons();
        for(Person p : personList) System.out.println(p);*/
        Jena2 jena2 = new Jena2(model);
        /*List<Movie> people = jena2.getMovies();
        for(Movie p : people) System.out.println(p);*/
        //Jena3 jena3 = new Jena3(model);
    }
}