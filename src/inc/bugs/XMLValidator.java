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

public class XMLValidator {

    public static boolean isValidXML(String xmlPath, String xsdPath){

        try {
            // loads the schema from the XSD file
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));

            // creates a Validator object and checks the XML file against the schema
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));

        } catch (IOException e) {
            // if the files don't exist we assume it to be incorrect
            return false;
        } catch (SAXException e){
            // if it is not "well formed" according to the XML specification we assume it to be incorrect
            return false;
        }

        // if nothing goes wrong then we assume it to be valid
        return true;
    }
}