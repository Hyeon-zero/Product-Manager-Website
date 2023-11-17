package hello.productmanager.repository;

import hello.productmanager.domain.Product;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);
    Product findById(Long id);
    List<Product> findAll();
    void update(Long id, Product updateParam);
    void delete(Long id);

}