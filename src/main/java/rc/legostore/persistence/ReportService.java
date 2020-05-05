package rc.legostore.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;
import rc.legostore.model.AvgRatingModel;
import rc.legostore.model.LegoSet;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportService {

    @Autowired
    private MongoTemplate mongoTemplate;

//    public ReportService(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }


    //-----------------------------------------------------------------------------
    // SECTION 7  video 32
    // PROJECTIONS
    public List<AvgRatingModel> getAvgRatingReport() {
        ProjectionOperation projectToMatchModel = project()
                .andExpression("name").as("productName")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");
        Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectToMatchModel);

        return this.mongoTemplate
                .aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class)
                .getMappedResults();
    }
}

// Run this on robomongo
//        db.legosets.aggregate([{
//              $project : {
//                      legoSetName: "$name",
//                      avgRating: {$avg: "$reviews.rating"}
//              }
//        }])