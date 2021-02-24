package com.example.demo.service;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        tagRepository.save(tag);
        return tag;
    }

    public Tag getTagById(String id) {
        return tagRepository.findById(Long.parseLong(id)).orElse(null);
    }

}
