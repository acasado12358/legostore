package rc.legostore.model;

public class AvgRatingModel {
    private String id;
    private String productName;
    private double avgRating;

    public double getAvgRating() {
        return avgRating;
    }

    public String getProductName() {
        return productName;
    }

    public String getId() {
        return id;
    }
}

// Run this on robomongo
//db.legosets.aggregate([{
//        $project : {
//        legoSetName: "$name",
//        avgRating: {$avg: "$reviews.rating"}
//        }
//        }])