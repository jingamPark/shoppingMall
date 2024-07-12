package com.example.toyproject_shoppingmall.repository.search;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.dto.QMainProductDTO;
import com.example.toyproject_shoppingmall.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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

    private BooleanExpression prodNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.prodName.like("%" + searchQuery + "%");
    }

    private BooleanExpression categoryTitleLike(String categoryQuery) {
        return StringUtils.isEmpty(categoryQuery) ? null : QCategory.category.title.like("%" + categoryQuery + "%");
    }

    @Override
    public Page<MainProductDTO> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImg productImg = QProductImg.productImg;
        QCategory category =QCategory.category;


        QueryResults<MainProductDTO> results = jpaQueryFactory
                .select(
                        new QMainProductDTO(
                                product.id,
                                product.prodName,
                                product.prodDetail,
                                productImg.imgUrl,
                                product.price,
                                category.title
                                                                )
                )
                .from(productImg)
                .join(productImg.product, product)
                .join(product.category, category)
                .where(productImg.repImgYn.eq("Y"))
                .where(prodNmLike(productSearchDTO.getSearchQuery()))
                .where(categoryTitleLike(productSearchDTO.getCategoryQuery()))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<MainProductDTO> content = results.getResults();
        long total =results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
    /*fetchResults 지원중단으로 인해 대체 방법 강구하자*/

}
