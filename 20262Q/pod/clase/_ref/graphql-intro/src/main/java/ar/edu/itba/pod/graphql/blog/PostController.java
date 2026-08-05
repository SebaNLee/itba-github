package ar.edu.itba.pod.graphql.blog;

import ar.edu.itba.pod.graphql.blog.dao.AuthorDao;
import ar.edu.itba.pod.graphql.blog.dao.PostDao;
import ar.edu.itba.pod.graphql.blog.model.Author;
import ar.edu.itba.pod.graphql.blog.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class PostController {
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private PostDao postDao;

    @QueryMapping
    public List<Post> recentPosts(@Argument int count,@Argument int offset) {
        return postDao.getRecentPosts(count, offset);
    }

//    @SchemaMapping(typeName = "Post", field = "author")
//    public Author getAuthor(Post post) {
//        System.out.println("Estudioooo");
//        return authorDao.getAuthor(post.authorId());
//    }

    @BatchMapping
    public Mono<Map<Post, Author>> author(List<Post> posts) {
        Map<Post, Author> authors = new HashMap<>();
        for (Post post : posts) {
            authors.put(post, authorDao.getAuthor(post.authorId()));
        }
        System.out.println("Estudioooo");
        return Mono.just(authors);
    }
}
