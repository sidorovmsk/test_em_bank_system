package spring_boot_java.test_em.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_boot_java.test_em.models.Comment;
import spring_boot_java.test_em.repositories.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findById(int id) {
        return commentRepository.findById(id).orElse(null);
    }
}
