import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

public class Jena2 {

    public Jena2(){
        Model model = ModelFactory.createDefaultModel();
        String inputFileName = "data/Movie.rdf";
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        String sparql = "PREFIX pref: <http://www.semanticweb.org/nancy/ontologies/2019/2/untitled-ontology-5#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "\n" +
                "SELECT ?name\n" +
                "WHERE {\n" +
                " ?x rdf:type pref:Actor .\n" +
                " ?x pref:name ?name .\n" +
                "}";

        ParameterizedSparqlString parameterizedSparql = new ParameterizedSparqlString(model);
        parameterizedSparql.setCommandText(sparql);

        Query query = QueryFactory.create(parameterizedSparql.asQuery());

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();

        if (resultSet.hasNext()) {

            /*QuerySolution querySolution = resultSet.next();
            Label label = new Label(querySolution.getLiteral("name"));

            if (!resultSet.hasNext()) return label;

            throw new ModelException("%s has more than one label in language '%s'", resource.getURI(), language.getCode());*/
        }

    }
}
