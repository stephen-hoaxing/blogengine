package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(Category category, String id) throws Exception {
        Optional<Category> toBeUpdated = categoryRepository.findById(Long.parseLong(id));
        if (toBeUpdated.isPresent()) {
            Category updatable = toBeUpdated.get();
            updatable.setTitle(category.getTitle());
            updatable.getPosts().addAll(category.getPosts());
            updatable.getTags().addAll(category.getTags());
            categoryRepository.save(updatable);
            return category;
        } else {
            throw new Exception("The category you want to update does not exist.");
        }
    }

    public void deleteCategory(String id) throws Exception {
        Optional<Category> toBeDeleted = categoryRepository.findById(Long.parseLong(id));
        if (toBeDeleted.isPresent()) {
            Category deletable = toBeDeleted.get();
            categoryRepository.delete(deletable);
        } else {
            throw new Exception("The category does not exist.");
        }
    }

    public void addTag(Tag tag, Category category) throws Exception {
        Optional<Tag> tagToAdd = tagRepository.findById(tag.getId());
        if (tagToAdd.isPresent()) {
            Tag t = tagToAdd.get();
            t.setCategory(category);
            category.addTag(tag);
            updateCategory(category, category.getId().toString());
        } else {
            throw new Exception("The tag does not exist.");
        }
    }

    public void addPost(Post post, Category category) throws Exception {
        Optional<Post> postToAdd = postRepository.findById(post.getId());
        if (postToAdd.isPresent()) {
            Post p = postToAdd.get();
            if (p.getCategories().size() > 5) {
                throw new Exception("Post is featured in more than 5 categories.");
            } else {
                p.getCategories().add(category);
                updateCategory(category, category.getId().toString());
            }
        } else {
            throw new Exception("The post does not exist.");
        }
    }

    public void removePost(Post post, Category category) throws Exception {
        Optional<Post> postToAdd = postRepository.findById(post.getId());
        if (postToAdd.isPresent()) {
            Post p = postToAdd.get();
            p.getCategories().removeIf(p1 -> p1.getId().equals(p.getId()));
            updateCategory(category, category.getId().toString());
        } else {
            throw new Exception("The post does not exist.");
        }
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(Long.parseLong(id)).get();
    }

}
