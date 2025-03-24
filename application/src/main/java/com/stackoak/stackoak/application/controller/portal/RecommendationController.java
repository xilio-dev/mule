package com.stackoak.stackoak.application.controller.portal;


import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommender")
public class RecommendationController {


    @Autowired
    private AuthorRecommendationService authorRecommendationService;

    @GetMapping("/authors")
    public ResponseEntity<List<User>> recommendAuthors(
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<User> authors = authorRecommendationService.recommendAuthors(userId, page, size);
        return ResponseEntity.ok(authors);
    }

}
