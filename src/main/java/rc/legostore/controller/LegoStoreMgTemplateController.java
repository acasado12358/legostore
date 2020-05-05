package rc.legostore.controller;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import rc.legostore.model.LegoSet;

import java.util.Collection;

@RestController
@RequestMapping("legostore/api/mgtemplate")
public class LegoStoreMgTemplateController {

    private MongoTemplate mongoTemplate;

    public LegoStoreMgTemplateController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        //insert-> if there is no id an id is generated
        //but if we have an id and is present will return ERROR
        this.mongoTemplate.insert(legoSet);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        //save-> if there is no id an id is generated
        //but if we have an id and is present will be UPDATED
        this.mongoTemplate.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.mongoTemplate.remove(new Query(Criteria.where("id").is(id)), LegoSet.class);
    }

    @GetMapping("/all")
    public Collection<LegoSet> all() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        Collection<LegoSet> legosets = this.mongoTemplate.findAll(LegoSet.class);
        return legosets;
    }

    @GetMapping("/{id}")
    public LegoSet byId(@PathVariable String id) {
        LegoSet legoSet = this.mongoTemplate.findById(new Query(Criteria.where("id").is(id)), LegoSet.class);
        return legoSet;
    }
}
