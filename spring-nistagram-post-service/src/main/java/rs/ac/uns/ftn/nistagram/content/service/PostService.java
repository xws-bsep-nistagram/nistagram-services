package rs.ac.uns.ftn.nistagram.content.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.repository.PostRepository;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(Post post) {
        post.setTime(LocalDateTime.now());
        postRepository.save(post);
    }

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
