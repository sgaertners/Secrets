package de.sgs.secrets.knowhow.services;

import de.sgs.secrets.knowhow.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



}
