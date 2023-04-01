package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.dtos.CategoryDto;
import com.bikkadIt.electronic.store.dtos.PagebaleResponse;
import com.bikkadIt.electronic.store.entities.Category;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.AppConstance;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.respository.CategoryRespository;
import com.bikkadIt.electronic.store.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.annotation.AnnotationValue;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CategoryImpl implements CategoryService {

    @Autowired
   private CategoryRespository categoryRespository;

    @Autowired
   private ModelMapper modelMapper;


    /**
     * @apiNote this method use for create category
     * @param categoryDto
     * @return
     */

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        log.info("Initiating dao call for the save the category Details");
        category.setIsactive(AppConstance.YES);
        Category save = this.categoryRespository.save(category);
        log.info("Completed dao call for the save the category Details");
        return this.modelMapper.map(save,CategoryDto.class);
    }

    /**
     * @apiNote this method use for update categorys
     * @param categoryDto
     * @param categoryId
     * @return
     */

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        log.info("Initiating dao call for update category details with:{}", categoryId);
        Category category = this.categoryRespository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(category.getCoverImage());
        Category save = this.categoryRespository.save(category);
       log.info("Complete dao call for update  category details: {}", categoryId);
        return this.modelMapper.map(save,CategoryDto.class);
    }

    /**
     * @apiNote this method use for delete categorys
     * @param categoryId
     */

    @Override
    public void delete(Long categoryId) {
        log.info("Initiating dao call for deleted category with:{}",categoryId);
        Category category = this.categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setIsactive(AppConstance.YES);
        log.info("Completed dao call for deleted category with:{}",categoryId);
        this.categoryRespository.delete(category);
    }

    /**
     * @apiNote this method use for get All categorys
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @Override
    public PagebaleResponse<CategoryDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating dao call for getAllCat  details ");
        Sort sort =sortDir.equalsIgnoreCase("desc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable page= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> list = this.categoryRespository.findAll(page);
        PagebaleResponse<CategoryDto> response = Helper.getPageableResponse(list, CategoryDto.class);
        log.info("Completed dao call for getAllCat  details ");
        return response;
    }

    /**
     * @apiNote this method use for get By category Id
     * @param categoryId
     * @return
     */

    @Override
    public CategoryDto getSingle(Long categoryId) {
        log.info("Initiating dao call for getSingle category details with:{}",categoryId);
        Category category = this.categoryRespository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        CategoryDto getSingl = this.modelMapper.map(category, CategoryDto.class);
        getSingl.setIsactive(AppConstance.YES);
        log.info("Complete dao call for getSingle category details with:{}",categoryId);
        return getSingl;
    }

    /**
     * @apiNote this method use for search categorys
     * @param keyword
     * @return
     */
    @Override
    public List<CategoryDto> serachCat(String keyword) {
        log.info("Initiating dao call for serach category details with:{}",keyword);
        List<Category> list = this.categoryRespository.findByTitleContaining(keyword);
        log.info("Completed dao call for search category details with:{}",keyword);
        List<CategoryDto> collect = list.stream().map((list1) -> this.modelMapper.map(list1, CategoryDto.class)).collect(Collectors.toList());
        return collect;
    }
}
