package spring_boot_java.test_em.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_java.test_em.models.Comment;
import spring_boot_java.test_em.services.CommentService;


@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findById(@PathVariable("id") int id) {
        log.info("");
        Comment comment = commentService.findById(id);
        return comment != null ? new ResponseEntity<>(comment, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
