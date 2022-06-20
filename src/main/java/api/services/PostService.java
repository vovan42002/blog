package api.services;

import api.models.Post;
import api.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll () {
        return postRepository.findAll();
    }
    public Post save (Post post) {
        return postRepository.save(post);
    }
    public Optional<Post> findById (long id) {
        return postRepository.findById(id);
    }
    public boolean existById (long id) {
        return postRepository.existsById(id);
    }
    public void remove (Post post) {
        postRepository.delete(post);
    }
    public Post addView (Post post) {
        post.setLikes(post.getLikes()+1);
        return post;
    }
}
