package com.example.toyproject_shoppingmall.repository.search;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.ProductImgDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ProductImg;
import com.example.toyproject_shoppingmall.entity.QProduct;
import com.example.toyproject_shoppingmall.entity.QProductImg;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    public ProductRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ProdSellStatus searchSellStatus) {
        return searchSellStatus ==
                null ? null: QProduct.product.prodSellStatus.eq(searchSellStatus);
    } //오케이

    private BooleanExpression regDTsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QProduct.product.regTime.after(dateTime);
    }//오케이

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("prodName", searchBy)) {
            return QProduct.product.prodName.like("%"+searchQuery+"%");

        } else if (StringUtils.equals("createBy", searchBy)) {
            return QProduct.product.createBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        QueryResults<Product> results = jpaQueryFactory
                .selectFrom(QProduct.product)
                .where(regDTsAfter(productSearchDTO.getSearchDateType()),
                        searchSellStatusEq(productSearchDTO.getSearchSellStatus()),
                        searchByLike(productSearchDTO.getSearchBy(),
                                productSearchDTO.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content =results.getResults();
        long total =results.getTotal();
        return new PageImpl<>(content, pageable, total);


    }



}
