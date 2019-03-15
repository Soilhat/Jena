import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class Jena2 {
    private Model model;
    private List<Person> people;
    private List<Movie> movies;

    Jena2(Model model){

        this.model = model;

        String sparql = "PREFIX pref: <http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?name\n" +
                "WHERE {\n" +
                " ?x pref:name ?name .\n" +
                "}";

        Query query = QueryFactory.create(sparql);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            //System.out.println(s);
            Literal c = s.getLiteral("name");
            //System.out.println(c);
        }
        qexec.close();

        people = loadPersons();
        movies = loadMovies();
        Relation();
    }

    List<Person> getPeople() {
        return people;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    private List<Person>  loadPersons(){
        List<Person> people = new ArrayList<Person>();
        String q = "PREFIX pref:\t<http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf:\t\t<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?x ?name ?age ?genre ?nation ?class\n" +
                "WHERE {\n" +
                "\t?x rdf:type pref:Actor .\n" +
                "\t?x pref:name ?name .\n" +
                "\tOPTIONAL {\n" +
                "\t\t?x pref:age ?age .\n" +
                "\t\t?x pref:gender ?genre .\n" +
                "\t\t?x pref:nationality ?nation .\n" +
                "\t\t?x rdf:type ?class .\n" +
                "\t}\n" +
                "}";

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            String type = (s.getResource("class") != null && !s.getResource("class").toString().contains("NamedIndividual")) ? s.getResource("class").toString().split("#")[1] : null;
            if(!people.contains(new Person(s.getResource("x").toString()))){
                String nationality = (s.getLiteral("nation") != null ) ? s.getLiteral("nation").toString() : null;
                String age = (s.getLiteral("age") != null ) ? s.getLiteral("age").toString().substring(0,2) : null;
                String genre = (s.getLiteral("genre") != null ) ? s.getLiteral("genre").toString() : null;
                people.add(new Person(s.getResource("x").toString(), s.getLiteral("name").toString(), nationality, age, genre , type));
            }
            else people.get(people.indexOf(new Person(s.getResource("x").toString()))).setType(type);
        }
        qexec.close();
        return people;
    }

    private List<Movie> loadMovies(){
        List<Movie> movies = new ArrayList<Movie>();
        String q = "PREFIX pref:\t<http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf:\t\t<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?x ?title ?year ?lang ?country ?class\n" +
                "WHERE {\n" +
                "\t?x pref:title ?title .\n" +
                "\t?x pref:year ?year .\n" +
                "\t?x pref:language ?lang .\n" +
                "\t?x pref:country ?country .\n" +
                "\t?x rdf:type ?class .\n" +
                "}" ;

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            String type = (s.getResource("class") != null && !s.getResource("class").toString().contains("NamedIndividual")) ? s.getResource("class").toString().split("#")[1] : null;
            if(!movies.contains(new Movie(s.getResource("x").toString()))){
                movies.add(new Movie(s.getResource("x").toString(), s.getLiteral("title").toString(), s.getLiteral("lang").toString(), s.getLiteral("year").toString().substring(0,4), s.getLiteral("country").toString() , type));
            }
            else movies.get(movies.indexOf(new Movie(s.getResource("x").toString()))).setType(type);
        }
        qexec.close();
        return movies;
    }

    private void Relation(){
        String q = "PREFIX pref:\t<http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf:\t\t<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?person ?rel ?movie\n" +
                "WHERE {\n" +
                "\t?person ?rel ?movie .\n" +
                "\t?movie pref:title ?x .\n" +
                "\t?person pref:name ?y .\n" +
                "}" ;

        Query query = QueryFactory.create(q);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            String relation = s.getResource("rel").toString().split("#")[1];
            if(relation.equals("isWriterOf")) {
                getPerson(s.getResource("person")).addWriterOf(getMovie(s.getResource("movie")));
                getMovie(s.getResource("movie")).addWritor(getPerson(s.getResource("person")));
            }
            if(relation.equals("isActorOf")) {
                getPerson(s.getResource("person")).addActIn(getMovie(s.getResource("movie")));
                getMovie(s.getResource("movie")).addActors(getPerson(s.getResource("person")));
            }
            if(relation.equals("isDirectorOf")) {
                getPerson(s.getResource("person")).addDirectorOf(getMovie(s.getResource("movie")));
                getMovie(s.getResource("movie")).addDirector(getPerson(s.getResource("person")));
            }

        }
        qexec.close();
        q = "PREFIX pref:\t<http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf:\t\t<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?person ?rel ?movie\n" +
                "WHERE {\n" +
                "\t?movie ?rel ?person .\n" +
                "\t?movie pref:title ?x .\n" +
                "\t?person pref:name ?y .\n" +
                "}" ;

        query = QueryFactory.create(q);

        qexec = QueryExecutionFactory.create(query, model);
        resultSet = qexec.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            String relation = s.getResource("rel").toString().split("#")[1];
            if(relation.contains("hasWriter")) {
                getPerson(s.getResource("person")).addWriterOf(getMovie(s.getResource("movie")));
                getMovie(s.getResource("movie")).addWritor(getPerson(s.getResource("person")));
            }
            if(relation.contains("hasActor")) {
                getPerson(s.getResource("person")).addActIn(getMovie(s.getResource("movie")));
                getMovie(s.getResource("movie")).addActors(getPerson(s.getResource("person")));
            }
            if(relation.contains("hasDirector")) {
                getMovie(s.getResource("movie")).addDirector(getPerson(s.getResource("person")));
                getPerson(s.getResource("person")).addDirectorOf(getMovie(s.getResource("movie")));
            }

        }
        qexec.close();
    }

    private Person getPerson(Resource id){
        return (id != null )? people.get(people.indexOf(new Person(id.toString()))): null;
    }
    private Movie getMovie(Resource id) { return (id != null )? movies.get(movies.indexOf(new Movie(id.toString()))): null; }
}
