/**
 * Created by aclima on 15/10/15.
 */

package inc.bugs;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XMLValidator {

    private static Schema schema;

    /**
     * Checks if the contents of an XML file conforms to a XSD
     *
     * @param xmlFileContent Contents of the XML file to be evaluated
     * @param xsdPath Path to the XSD used for validation
     * @return boolean value for validity of XML file
     */
    public boolean isValidXML(String xmlFileContent, String xsdPath){

        try {

            if(schema == null) {
                // loads the schema from the XSD file
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                schema = factory.newSchema(new File(xsdPath));
            }

            // creates a Validator object and checks the XML file against the schema
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlFileContent)));

        } catch (IOException e) {
            // if the files don't exist we assume it to be incorrect
            e.printStackTrace();
            return false;
        } catch (SAXException e){
            // if it is not "well formed" according to the XML specification we assume it to be incorrect
            e.printStackTrace();
            return false;
        }

        // if nothing goes wrong then we assume it to be valid
        return true;
    }
}