package com.urbanclone.catalog.service;

import com.urbanclone.catalog.dto.CategoryDto;
import com.urbanclone.catalog.entity.ServiceCategory;
import com.urbanclone.catalog.exception.CategoryNotFoundException;
import com.urbanclone.catalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Category Service Tests")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private ServiceCategory testCategory;
    private ServiceCategory parentCategory;

    @BeforeEach
    void setUp() {
        parentCategory = ServiceCategory.builder()
                .id(1L)
                .name("Home Services")
                .description("All home related services")
                .active(true)
                .displayOrder(1)
                .createdAt(LocalDateTime.now())
                .build();

        testCategory = ServiceCategory.builder()
                .id(2L)
                .name("Cleaning Services")
                .description("Professional cleaning services")
                .parentCategory(parentCategory)
                .active(true)
                .displayOrder(1)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create category successfully")
    void shouldCreateCategory() {
        // Given
        when(categoryRepository.save(any(ServiceCategory.class))).thenReturn(testCategory);

        // When
        CategoryDto result = categoryService.createCategory(testCategory);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(testCategory.getName());
        assertThat(result.getDescription()).isEqualTo(testCategory.getDescription());
        verify(categoryRepository).save(any(ServiceCategory.class));
    }

    @Test
    @DisplayName("Should get category by id successfully")
    void shouldGetCategoryById() {
        // Given
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));

        // When
        CategoryDto result = categoryService.getCategoryById(2L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(testCategory.getId());
        assertThat(result.getName()).isEqualTo(testCategory.getName());
        verify(categoryRepository).findById(2L);
    }

    @Test
    @DisplayName("Should throw exception when category not found")
    void shouldThrowExceptionWhenCategoryNotFound() {
        // Given
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoryService.getCategoryById(999L))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 999");
    }

    @Test
    @DisplayName("Should get all active categories")
    void shouldGetAllActiveCategories() {
        // Given
        List<ServiceCategory> categories = Arrays.asList(parentCategory, testCategory);
        when(categoryRepository.findByActiveTrue()).thenReturn(categories);

        // When
        List<CategoryDto> result = categoryService.getAllActiveCategories();

        // Then
        assertThat(result).hasSize(2);
        verify(categoryRepository).findByActiveTrue();
    }

    @Test
    @DisplayName("Should get root categories")
    void shouldGetRootCategories() {
        // Given
        List<ServiceCategory> rootCategories = Arrays.asList(parentCategory);
        when(categoryRepository.findByParentCategoryIsNull()).thenReturn(rootCategories);

        // When
        List<CategoryDto> result = categoryService.getRootCategories();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParentCategoryId()).isNull();
        verify(categoryRepository).findByParentCategoryIsNull();
    }

    @Test
    @DisplayName("Should get subcategories")
    void shouldGetSubcategories() {
        // Given
        List<ServiceCategory> subcategories = Arrays.asList(testCategory);
        when(categoryRepository.findByParentCategoryId(1L)).thenReturn(subcategories);

        // When
        List<CategoryDto> result = categoryService.getSubcategories(1L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParentCategoryId()).isEqualTo(1L);
        verify(categoryRepository).findByParentCategoryId(1L);
    }

    @Test
    @DisplayName("Should update category successfully")
    void shouldUpdateCategory() {
        // Given
        CategoryDto updateDto = CategoryDto.builder()
                .id(2L)
                .name("Premium Cleaning Services")
                .description("Updated description")
                .build();

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(ServiceCategory.class))).thenReturn(testCategory);

        // When
        CategoryDto result = categoryService.updateCategory(2L, updateDto);

        // Then
        assertThat(result).isNotNull();
        ArgumentCaptor<ServiceCategory> categoryCaptor = ArgumentCaptor.forClass(ServiceCategory.class);
        verify(categoryRepository).save(categoryCaptor.capture());
        ServiceCategory updatedCategory = categoryCaptor.getValue();
        assertThat(updatedCategory.getName()).isEqualTo("Premium Cleaning Services");
        assertThat(updatedCategory.getDescription()).isEqualTo("Updated description");
    }

    @Test
    @DisplayName("Should deactivate category")
    void shouldDeactivateCategory() {
        // Given
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(ServiceCategory.class))).thenReturn(testCategory);

        // When
        categoryService.deactivateCategory(2L);

        // Then
        ArgumentCaptor<ServiceCategory> categoryCaptor = ArgumentCaptor.forClass(ServiceCategory.class);
        verify(categoryRepository).save(categoryCaptor.capture());
        ServiceCategory deactivatedCategory = categoryCaptor.getValue();
        assertThat(deactivatedCategory.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should update display order")
    void shouldUpdateDisplayOrder() {
        // Given
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(ServiceCategory.class))).thenReturn(testCategory);

        // When
        categoryService.updateDisplayOrder(2L, 5);

        // Then
        ArgumentCaptor<ServiceCategory> categoryCaptor = ArgumentCaptor.forClass(ServiceCategory.class);
        verify(categoryRepository).save(categoryCaptor.capture());
        ServiceCategory updatedCategory = categoryCaptor.getValue();
        assertThat(updatedCategory.getDisplayOrder()).isEqualTo(5);
    }

    @Test
    @DisplayName("Should check if category has subcategories")
    void shouldCheckIfCategoryHasSubcategories() {
        // Given
        when(categoryRepository.existsByParentCategoryId(1L)).thenReturn(true);

        // When
        boolean result = categoryService.hasSubcategories(1L);

        // Then
        assertThat(result).isTrue();
        verify(categoryRepository).existsByParentCategoryId(1L);
    }

    @Test
    @DisplayName("Should get category hierarchy")
    void shouldGetCategoryHierarchy() {
        // Given
        List<ServiceCategory> rootCategories = Arrays.asList(parentCategory);
        List<ServiceCategory> subcategories = Arrays.asList(testCategory);
        
        when(categoryRepository.findByParentCategoryIsNull()).thenReturn(rootCategories);
        when(categoryRepository.findByParentCategoryId(1L)).thenReturn(subcategories);

        // When
        List<CategoryDto> result = categoryService.getCategoryHierarchy();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSubcategories()).hasSize(1);
        verify(categoryRepository).findByParentCategoryIsNull();
    }
}
