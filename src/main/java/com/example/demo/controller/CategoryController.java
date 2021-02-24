package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.Tag;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PostService;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @PostMapping("/")
    public Category createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return category;
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category, @PathVariable("id") String id) {
        try {
            categoryService.updateCategory(category, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") String id) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/{category_id}/{tag_id}")
    public void addTag(@PathVariable("category_id") String categoryId, @PathVariable("tag_id") String tagId) {
        Tag tag = tagService.getTagById(tagId);
        Category category = categoryService.getCategoryById(categoryId);
        try {
            categoryService.addTag(tag, category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/addpost/{category_id}/{post_id}")
    public void addPost(@PathVariable("category_id") String categoryId, @PathVariable("post_id") String postId) {
        Post post = postService.getPostById(postId);
        Category category = categoryService.getCategoryById(categoryId);
        try {
            categoryService.addPost(post, category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/removepost/{category_id}/{post_id}")
    public void removePost(@PathVariable("category_id") String categoryId, @PathVariable("post_id") String postId) {
        Post post = postService.getPostById(postId);
        Category category = categoryService.getCategoryById(categoryId);
        try {
            categoryService.removePost(post, category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
