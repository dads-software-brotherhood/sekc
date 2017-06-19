package mx.infotec.dads.sekc.repository;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
//import mx.infotec.dads.essence.repository.SEStateRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author wisog
 */
public class ObjectIdToDocumentConverter {

    /* At this level we're unable to make a connection to find Documnt using MongoRepository*/
    //private SEStateRepository stateRepository;
    @Autowired
    static MongoTemplate mongoTemplate;
    
    /**
     * This is invoked when is a manual link between documents
     */
    public static enum ObjectIdToSEStateDocumentConverter implements 
            Converter<org.bson.types.ObjectId, mx.infotec.dads.essence.model.alphaandworkproduct.SEState> {
        INSTANCE;
        
        public SEState convert(org.bson.types.ObjectId id) {
            try {
                return DocumentConverterUtils.getStateByID(id.toString());
                
            } catch (Exception e) {
                return new SEState();
            }
        }
    }
    /**
     * This is invoked when is a $ref link between documents
     */
    public static enum ObjectIdToStateDocumentConverter implements 
            Converter<org.bson.types.ObjectId, org.omg.essence.model.alphaandworkproduct.State> {
        INSTANCE;
        
        public SEState convert(org.bson.types.ObjectId id) {
            try {
                return DocumentConverterUtils.getStateByID(id.toString());
                
            } catch (Exception e) {
                return new SEState();
            }
        }
    }
    
    public static enum ObjectIdToAlphaDocumentConverter implements 
            Converter<org.bson.types.ObjectId, mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha> {
        INSTANCE;
        
        public SEAlpha convert(org.bson.types.ObjectId id) {
            try {
                return DocumentConverterUtils.getAlphaByID(id.toString());
            } catch (Exception e) {
                return new SEAlpha();
            }
        }
    }
}
