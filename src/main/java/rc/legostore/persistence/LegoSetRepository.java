package rc.legostore.persistence;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import rc.legostore.model.LegoSet;
import rc.legostore.model.LegoSetDifficulty;

import java.util.Collection;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String> , QuerydslPredicateExecutor<LegoSet> {

    //method-name composition, out of the box with Spring boot
    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort); //sorted
    Collection<LegoSet> findAllByThemeContains(String theme); //unsorted

    //find by multiple filters
    Collection<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty difficulty, String name);

    //Using Query and inside put a filter SECTION 6 viddeo 26
    //  db.getCollection('legosets').find({"delivery.deliveryFee" : {$lt : 50}})
    @Query("{'delivery.deliveryFee' : {$lt : ?0}}") //?0 but if more params ?1 ?2
    Collection<LegoSet> findAllByDeliveryPriceLessThan(int price);

    //find by rating
    //  db.getCollection('legosets').find({"reviews.rating" : {$eq : 10}})
    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();

    // FOR FULL text search
    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

//-----------------------------------------------------------------------------
    // SECTION 7  video 29 Worlong with generated QueryDSL -> QLegoSet
    //For QueryDSL implmentation we nee to extend class LegoSetRepository
    //QuerydslPredicateExecutor<LegoSet>
}


//public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet> {
//    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort);
//    Collection<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty difficulty, String name);
//    Collection<LegoSet> findAllBy(TextCriteria textCriteria);
//
//    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
//    Collection<LegoSet> findAllByDeliveryPriceLessThan(int price);
//
//    @Query("{'reviews.rating' : {$eq : 10}}")
//    Collection<LegoSet> findAllByGreatReviews();
//
//    @Query("{'paymentOptions.id' : ?0}")
//    Collection<LegoSet> findByPaymentOptionId(String id);
//}
