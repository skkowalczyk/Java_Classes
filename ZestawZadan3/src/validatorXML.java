import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class validatorXML {

    public static void main(String[] args) {
        boolean isValid = validatorXMLSchema("schema.xsd", "osoby.xml");
        if (isValid) {
            System.out.println("Plik XML jest zgodny ze schematem XSD.");
        } else {
            System.out.println("Plik XML nie jest zgodny ze schematem XSD.");
        }
    }

    public static boolean validatorXMLSchema(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Błąd walidacji: " + e.getMessage());
        }
        return true;
    }
}