import org.apache.jena.rdf.model.*;
import java.util.ArrayList;
import java.util.List;

public class Jena1 {

    private List<Person> tab;

    public Jena1(Model model){

        // write it to standard out
        //model.write(System.out, "N-TRIPLES");

        StmtIterator iter = model.listStatements();
        tab = new ArrayList<Person>();
        List<Movie> movies = new ArrayList<Movie>();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            if(predicate.toString().contains("nationality")) tab.add(new Person((subject.toString())));
            for (Person b : tab ) {
                if(b.getId().equals(subject.toString())){
                    if(predicate.toString().contains("name")) b.setName(object.toString());
                    if(predicate.toString().contains("age")) b.setAge(object.toString().substring(0,2));
                    if(predicate.toString().contains("nationality")) b.setNationality(object.toString());
                    if(predicate.toString().contains("gender")) b.setGender(object.toString());
                }
            }

            if(predicate.toString().contains("language"))  movies.add(new Movie(subject.toString()));
            for (Movie b : movies ) {
                if(b.getId().equals(subject.toString())){
                    if(predicate.toString().contains("year")) b.setYear(object.toString().substring(0,4));
                    else if(predicate.toString().contains("title")) b.setTitle(object.toString());
                    else if(predicate.toString().contains("country")) b.setCountry(object.toString());
                    else if(predicate.toString().contains("language")) b.setLanguage(object.toString());
                    else if(predicate.toString().contains("type")&& !object.toString().contains("NamedIndividuals")) b.setType(object.toString().split("#")[1]);
                }
            }
        }

        /*for (Movie b: movies) {
            System.out.println(b);
        }

        for (Person b: tab) {
            System.out.println(b);
        }*/
    }

    public List<Person> getPersons() {
        return tab;
    }
}