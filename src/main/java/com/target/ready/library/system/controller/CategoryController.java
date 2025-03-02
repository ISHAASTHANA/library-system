package com.target.ready.library.system.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.ready.library.system.entity.Category;
import com.target.ready.library.system.exceptions.ResourceNotFoundException;
import com.target.ready.library.system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("library_system/v2")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("inventory/category")
    @Operation(
            description = "Addition of new categories",
            responses = { @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json"
            ))})
    public ResponseEntity<?> addCategory(@RequestBody Category category)  {
        try {
            return new ResponseEntity<>(categoryService.addCategory(category),HttpStatus.CREATED);
        }
        catch (JsonProcessingException e) {
            return new ResponseEntity<>("An error occurred while processing the request",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/categories")
    @Operation(
            description = "Getting all the categories present in the database",
            responses = { @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    ))})
    public ResponseEntity<?> findAllCategories(@RequestParam(value = "page_number", defaultValue = "0", required = false) Integer pageNumber) {
        List<Category> categories;
        int pageSize = 10;
        try {
            if (pageNumber < 0) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }
            categories = categoryService.findAllCategories(pageNumber, pageSize);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("No category available currently!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("categories/{book_id}")
    @Operation(
            description = "Finding all the categories of the given book",
            responses = { @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    ))})
    public ResponseEntity<?> findAllCategoriesByBookId(@PathVariable("book_id") int bookId) {

            return new ResponseEntity<>(categoryService.findAllCategoriesByBookId(bookId),HttpStatus.OK);



    }

    @GetMapping("inventory/categories/{category_name}")
    @Operation(
            description = "Finding all the categories of the given book",
            responses = { @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json"
                    ))})
    public ResponseEntity<?> findCategoryByCategoryName(@PathVariable("category_name") String categoryName){

            return new ResponseEntity<>(categoryService.findCategoryBycategoryName(categoryName),HttpStatus.OK);

    }
}
