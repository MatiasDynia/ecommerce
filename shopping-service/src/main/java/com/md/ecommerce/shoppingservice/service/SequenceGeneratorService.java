package com.md.ecommerce.shoppingservice.service;

import com.md.ecommerce.shoppingservice.entity.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public String generateSequence(String sequenceName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(sequenceName)),
                new Update().inc("sequence", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        return Objects.nonNull(counter) ? Long.toString(counter.getSequence()) : "1";
    }
}
