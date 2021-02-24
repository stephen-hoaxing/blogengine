package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public Post createPost(@RequestBody Post post) {
        postService.createPost(post);
        return post;
    }

    @PutMapping("/{id}")
    public Post updatePost(@RequestBody Post post, @PathVariable("id") String id) {
        try {
            postService.updatePost(post, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") String id) {
        try {
            postService.deletePost(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/add/{post_id}/{category_id}")
    public void addToCategory(@PathVariable("post_id") String postId, @PathVariable("category_id") String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        Post post = postService.getPostById(postId);
        if (post.getCategories().size() < 5) {
            post.addCategory(category);
            postService.createPost(post);
        } else {
            try {
                throw new Exception("This post is featured in more than 5 categories.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") String id) {
        return postService.getPostById(id);
    }

    @GetMapping("/getbytag/{tag_id}")
    public List<Post> getPostsByTag(@PathVariable("tag_id") String tagId) {
        try {
            return postService.getPostsByTag(tagId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
