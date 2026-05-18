package com.project.likelion14thbe.domain.product.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.product.converter.ProductConverter;
import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.product.entity.Bookmark;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
import com.project.likelion14thbe.domain.product.repository.BookmarkRepository;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public void createProduct(final ProductReqDTO.CreateReq req, final String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        productRepository.save(ProductConverter.toProduct(req, member));
    }

    @Override
    public void updateProduct(final Long productId, final ProductReqDTO.CreateReq req, final String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getMember().getEmail().equals(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        product.update(req.getName(), req.getPrice(), req.getCategory(), req.getImageUrl(), req.getDescription());
    }

    @Override
    public void deleteProduct(final Long productId, final String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getMember().getEmail().equals(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        productRepository.delete(product);
    }

    @Override
    public void addBookmark(final Long productId, final String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (bookmarkRepository.existsByMemberIdAndProductId(member.getId(), product.getId())) {
            return;
        }

        Bookmark bookmark = Bookmark.builder()
                .member(member)
                .product(product)
                .build();

        bookmarkRepository.save(bookmark);
    }
}