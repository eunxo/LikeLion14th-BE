package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.category.entity.Category;
import com.project.likelion14thbe.domain.category.repository.CategoryRepository;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.dto.response.ProductResDTO;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public ProductResDTO.ProductCreateResult createProduct(ProductReqDTO.ProductCreateReq request) {
        Category category = resolveCategory(request.getCategoryId());
        Member seller = memberRepository.findFirstByDeletedAtIsNullOrderByUserIdAsc()
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        Product product = ProductConverter.toProduct(request, category, seller);
        productRepository.save(product);
        return ProductConverter.toCreateResult(product);
    }

    private Category resolveCategory(Long requestedCategoryId) {
        if (requestedCategoryId != null) {
            return categoryRepository.findById(requestedCategoryId)
                    .orElseGet(() -> categoryRepository.save(
                            Category.builder().name("기본 카테고리").build()
                    ));
        }
        return categoryRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> categoryRepository.save(
                        Category.builder().name("기본 카테고리").build()
                ));
    }
}
