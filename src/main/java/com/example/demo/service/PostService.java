package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public void updatePost(Post post, String id) throws Exception {
        Optional<Post> tobeUpdated = postRepository.findById(Long.parseLong(id));
        if (tobeUpdated.isPresent()) {
            Post updateable = tobeUpdated.get();
            updateable.setTitle(post.getTitle());
            updateable.setContent(post.getContent());
            updateable.setCreatedAt(post.getCreatedAt());
            updateable.setUpdatedAt(new Date());
            postRepository.save(updateable);
        } else {
            throw new Exception("The post you want to update is not found.");
        }
    }

    public void deletePost(String id) throws Exception {
        Optional<Post> toBeDeleted = postRepository.findById(Long.parseLong(id));
        if (toBeDeleted.isPresent()) {
            Post deleteable = toBeDeleted.get();
            postRepository.delete(deleteable);
        } else {
            throw new Exception("The post you want to delete is not in the database.");
        }
    }

    public Post getPostById(String id) {
        return postRepository.findById(Long.parseLong(id)).get();
    }

    public void addToCategory(Post post, Category category) throws Exception {
        Optional<Category> toAdd = categoryRepository.findById(category.getId());
        if (toAdd.isPresent()) {
            Category c = toAdd.get();
            post.getCategories().add(c);
            postRepository.save(post);
        } else {
            throw new Exception("Category not found.");
        }
    }

    public void removeCategory(Post post, Category category) throws Exception {
        Optional<Category> toDelete = categoryRepository.findById(category.getId());
        if (toDelete.isPresent()) {
            Category c = toDelete.get();
            post.getCategories().removeIf(c1 -> c1.getId().equals(c.getId()));
            postRepository.save(post);
        } else {
            throw new Exception("Category not found.");
        }
    }

    public List<Post> getPostsByTag(String tagId) throws Exception {
        Optional<Tag> t = tagRepository.findById(Long.parseLong(tagId));
        if (t.isPresent()) {
            Tag tag = t.get();
            List<Category> categories = categoryRepository.findAll().stream().filter(cat -> cat.getTags().contains(tag)).collect(Collectors.toList());
            List<List<Post>> posts = categories.stream().map(c -> c.getPosts()).collect(Collectors.toList());
            return posts.stream().flatMap(List::stream).collect(Collectors.toList());
        } else {
            throw new Exception("Tag does not exist.");
        }
    }

}
