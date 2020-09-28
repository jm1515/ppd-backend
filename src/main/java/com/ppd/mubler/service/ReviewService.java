package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.Review;
import com.ppd.mubler.model.repository.MublerRepository;
import com.ppd.mubler.model.repository.MublerRequestRepository;
import com.ppd.mubler.model.repository.ReviewRepository;
import com.ppd.mubler.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewService implements Service<Review>    {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MublerRepository mublerRepository;
    @Autowired
    private MublerRequestRepository mublerRequestRepository;

    @Override
    public void save(Review entity) {
        this.reviewRepository.save(entity);
    }

    public boolean isValid(Review review) {
        return review.getRating() >= 0 && review.getRating() <= 5 && userRepository.findById(review.getIdWriter()) != null && userRepository.findById(review.getIdConcerned()) != null;
    }

    public Review getById(long id){
        return this.reviewRepository.findById(id);
    }

    public List<Review> getReviewsByIdConcerned(long idConcerned){
        return this.reviewRepository.findByIdConcerned(idConcerned);
    }

    public List<Review> getReviewsByIdWriter(long idWriter){
        return this.reviewRepository.findByIdWriter(idWriter);
    }
}
