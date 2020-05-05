package rc.legostore.controller;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.*;
import rc.legostore.model.LegoSet;
import rc.legostore.model.LegoSetDifficulty;
import rc.legostore.model.QLegoSet;
import rc.legostore.persistence.LegoSetRepository;

import java.util.Collection;

@RestController
@RequestMapping("legostore/api")
public class LegoStoreController {

    private LegoSetRepository legoSetRepository;

    public LegoStoreController(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        //insert-> if there is no id an id is generated
        //but if we have an id and is present will return ERROR
        this.legoSetRepository.insert(legoSet);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        //save-> if there is no id an id is generated
        //but if we have an id and is present will be UPDATED
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/allUnsorted")
    public Collection<LegoSet> allUnsorted() {
        Collection<LegoSet> legosets = this.legoSetRepository.findAll();
        return legosets;
    }

    //-----------------------------------------------------------------------------
    // SECTION 6 26 Apply Ordering to Filters
    @GetMapping("/all")
    public Collection<LegoSet> all() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        Collection<LegoSet> legosets = this.legoSetRepository.findAll(sortByThemeAsc);
        return legosets;
    }

    @GetMapping("/{id}")
    public LegoSet byId(@PathVariable String id) {
        LegoSet legoSet = this.legoSetRepository.findById(id).orElse(null);
        return legoSet;
    }

    //-----------------------------------------------------------------------------
    // SECTION 6 26 Apply Ordering to Filters
    //
    //method-name composition, out of the box with Spring boot
    //
    //UNSORTED GET
    @GetMapping("/byTheme/unsorted/{theme}")
    public Collection<LegoSet> byTheme(@PathVariable String theme) {
        return this.legoSetRepository.findAllByThemeContains(theme);
    }

    //SORTED GET
    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> byThemeSorted(@PathVariable String theme) {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByThemeContains(theme, sortByThemeAsc);
    }

    //Filtering base in 2 fields: Difficulty And NameStartsWith
    @GetMapping("hardThatStartWithM")
    public Collection<LegoSet> hardThatStartWithM() {
        return this.legoSetRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("byDeliveryFeeLessThan/{price}")
    public Collection<LegoSet> byDeliveryFeeLessThan(@PathVariable int price) {
        return this.legoSetRepository.findAllByDeliveryPriceLessThan(price);
    }

    //filtering when review is an Array
    @GetMapping("byGreatReviews")
    public Collection<LegoSet> byGreatReviews() {
        return this.legoSetRepository.findAllByGreatReviews();
    }

    //-----------------------------------------------------------------------------
    // SECTION 7  video 29 Worlong with generated QueryDSL -> QLegoSet
    //
    // Warning is not using Mongo LegoSetRepository class
    @GetMapping("bestBuys")
    public Collection<LegoSet> bestBuys() {
        //build query
        QLegoSet queryDSL = new QLegoSet("query");

        // build filters
        BooleanExpression inStockFilter = queryDSL.deliveryInfo.inStock.isTrue();
        Predicate smallDeliveryFeeFilter = queryDSL.deliveryInfo.deliveryFee.lt(50);
        Predicate hasGreatReviews = queryDSL.reviews.any().rating.eq(10); //any because review is an array

        Predicate bestBuysFilter = inStockFilter
                .and(smallDeliveryFeeFilter)
                .and(hasGreatReviews);

        // pass the query to findAll()
        //return (Collection<LegoSet>) this.legoSetRepository.findAll((Pageable) bestBuysFilter);
        return (Collection<LegoSet>) this.legoSetRepository.findAll(bestBuysFilter);
    }

    @GetMapping("fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return this.legoSetRepository.findAllBy(textCriteria);
    }

//    @GetMapping("/byPaymentOption/{id}")
//    public Collection<LegoSet> byPaymentOption(@PathVariable String id) {
//        return this.legoSetRepository.findByPaymentOptionId(id);
//    }
}
