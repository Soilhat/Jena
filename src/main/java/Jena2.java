import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;

public class Jena2 {

    public Jena2(Model model){

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

        String vName = "name";

        while (resultSet.hasNext()) {
            QuerySolution s = resultSet.nextSolution();
            Literal c = s.getLiteral(vName);
            System.out.println(c);
        }
        qexec.close();

    }
}
