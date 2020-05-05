package rc.legostore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rc.legostore.model.AvgRatingModel;
import rc.legostore.persistence.ReportService;

import java.util.List;

//-----------------------------------------------------------------------------
// SECTION 7  video 31
// PROJECTIONS

@RestController
@RequestMapping("legostore/api/reports")
public class ReportingController {
    private ReportService reportService;

    public ReportingController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("avgRatingsReport")
    public List<AvgRatingModel> avgRatingReport(){
        return this.reportService.getAvgRatingReport();
    }
}
