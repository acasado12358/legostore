package rc.legostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Document(collection = "legosets") // name of the collection
public class LegoSet {

    @Id
    private String id;

    @TextIndexed
    private String name;

    private LegoSetDifficulty difficulty;

    //These indexes are not created automatically
    // indexing for big collection and we will query a lot
    @TextIndexed
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;

    private Collection<ProductReview> reviews = new ArrayList<>();

    @Field("delivery") // we give specific name
    private DeliveryInfo deliveryInfo;

    public LegoSet(String name,
                   String theme,
                   LegoSetDifficulty difficulty,
                   DeliveryInfo deliveryInfo,
                   Collection<ProductReview> reviews) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.deliveryInfo = deliveryInfo;
        if (reviews != null) {
            this.reviews = reviews;
        }
    }

    private int nbParts; //@Transient if we want to indicate do not store this

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public LegoSetDifficulty getDifficulty() {
        return difficulty;
    }

    public String getTheme() {
        return theme;
    }

    public Collection<ProductReview> getReviews() {
        return Collections.unmodifiableCollection(this.reviews);
    }

    public int getNbParts() {
        return nbParts;
    }
}
