import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Jena3 {

    public Jena3(Model model, String inputRuleFile){
        Model infModel = readInferencedModelFromRuleFile(model, inputRuleFile);
        if(infModel != null ) {
            StmtIterator iter = infModel.listStatements();

            // print out the predicate, subject and object of each statement
            while (iter.hasNext()) {
                Statement stmt = iter.nextStatement();  // get next statement
                Resource subject = stmt.getSubject();     // get the subject
                Property predicate = stmt.getPredicate();   // get the predicate
                RDFNode object = stmt.getObject();      // get the object

                if (predicate.toString().contains("knows")) System.out.println(object.toString());
            }
        }
        else System.out.println("Nulllllllllllllll");
    }

    private Model readInferencedModelFromRuleFile(Model model, String inputRuleFile) {
        InputStream in = FileManager.get().open(inputRuleFile);
        if (in == null) {
            System.out.println("Rule File: " + inputRuleFile + " not found");
            return null;
        } else {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return null;
            }
        }

        List<Rule> rules = Rule.rulesFromURL(inputRuleFile);
        GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
        reasoner.setDerivationLogging(true);
        reasoner.setOWLTranslation(true);               // not needed in RDFS case
        reasoner.setTransitiveClosureCaching(true);
        /*InfModel inf =*/ return ModelFactory.createInfModel(reasoner, model);
    }
}
