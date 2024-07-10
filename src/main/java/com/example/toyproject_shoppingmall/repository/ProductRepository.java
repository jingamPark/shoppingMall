package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.repository.search.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {

    List<Product> findByCategoryTitle(String categoryTitle);
    //제품 제목으로 검색
    List<Product> findByProdName (String prodName);

    //제품 제목으로 검색어가 포함되는거
    List<Product> findByProdNameContains(String prodName);

    //제품 내용으로 검색
    List<Product> findByProdDetail(String prodDetail);

    //제품 내용으로 검색포함
    List<Product> findByProdDetailContains(String prodDetail);

    //제품 내용과 제목으로 검색
    List<Product> findByProdDetailAndProdName(String prodDetail, String prodName);

    //제품 내용과 제목 검색어로 검색
    List<Product>findByProdDetailContainsAndProdNameContains(String prodDetail, String prodName);

    //제품 가격대별 검색 3단계
    List<Product>findByPriceBetween(int minPrice,int maxPrice);

    //상품 판매중인것만 / 상품 미판매 중 인것만
    List<Product>findByProdSellStatus(ProdSellStatus prodSellStatus);

    //검색으로 검색시 제품 판매중인것만
    @Query("SELECT i FROM Product i WHERE i.prodSellStatus = 'SELL'")     //#{파라미터}
    List<Product>findByProdSellStatusSell();

    //검색으로 검색시 제품 판매 미판매 둘다
    List<Product>findByProdSellStatusContains(ProdSellStatus prodSellStatus);

    //수량이 몇개 이상인거 검색
    List<Product>findByStockNumberGreaterThanEqual(int stockNumber);

}
