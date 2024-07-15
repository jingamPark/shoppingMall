package com.example.toyproject_shoppingmall.repository.search;

import com.example.toyproject_shoppingmall.constant.Role;
import com.example.toyproject_shoppingmall.dto.UserSearchDTO;
import com.example.toyproject_shoppingmall.entity.QProduct;
import com.example.toyproject_shoppingmall.entity.QShopUser;
import com.example.toyproject_shoppingmall.entity.ShopUser;
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

public class ShopUserRepositoryCustomImpl implements ShopUserRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ShopUserRepositoryCustomImpl(EntityManager em) {
        this.queryFactory =new JPAQueryFactory(em);
    }

    private BooleanExpression searchRoleEq(Role searchRole) {
        return searchRole == null ? null : QShopUser.shopUser.role.eq(searchRole);

    }

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
        return QShopUser.shopUser.regTime.after(dateTime);
    }//오케이

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("loginId", searchBy)) {
            return QShopUser.shopUser.loginId.like("%" + searchQuery + "%");

        } else if (StringUtils.equals("email", searchBy)) {
            return QShopUser.shopUser.email.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("name", searchBy)) {
            return QShopUser.shopUser.name.like("%" + searchQuery + "%");

        }

        return null;
    }


    @Override
    public Page<ShopUser> getAdminShopUserPage (UserSearchDTO userSearchDTO, Pageable pageable) {
        QueryResults<ShopUser> results = queryFactory
                .selectFrom(QShopUser.shopUser)
                .where(regDTsAfter(userSearchDTO.getSearchDateType()),
                        searchRoleEq(userSearchDTO.getRole()),
                        searchByLike(userSearchDTO.getSearchBy(),
                                userSearchDTO.getSearchQuery()))
                .orderBy(QShopUser.shopUser.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ShopUser> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }



}//class
