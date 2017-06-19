package mx.infotec.dads.sekc.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import static mx.infotec.dads.sekc.repository.ObjectIdToDocumentConverter.mongoTemplate;

import org.bson.types.ObjectId;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class DocumentConverterUtils {
    
    private static Object getById(String id, Class obj){
        Criteria crit = new Criteria();
        crit.and("_id").is(new ObjectId(id));
        return mongoTemplate.findOne(new Query(crit), obj);
    }
    public static SECheckpoint getCheckpointByID(String id) {
        return (SECheckpoint) getById(id, SECheckpoint.class);
    }
    
    public static SEState getStateByID(String id) {
        return (SEState) getById(id, SEState.class);
    }
    
    public static SEAlpha getAlphaByID(String id) {
        return (SEAlpha) getById(id, SEAlpha.class);
    }
}