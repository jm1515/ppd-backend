package com.ppd.mubler.controllers.webservice;

import com.ppd.mubler.model.entity.Review;
import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.service.ReviewService;
import com.ppd.mubler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    @RequestMapping(value = "/reviews", method = RequestMethod.POST)
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        mublerLogger.logInfo("URL = /reviews Method = POST RequestBody = " + review);
        if (reviewService.isValid(review)) {
            review.setTimestamp(new Date());
            reviewService.save(review);
            mublerLogger.logInfo("URL = /reviews Method = POST RequestBody = " + review + ", Return code = 200");
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }else {
            mublerLogger.logInfo("URL = /reviews Method = POST RequestBody = " + review + ", Return code = 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
