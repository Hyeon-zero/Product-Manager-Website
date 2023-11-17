package hello.productmanager.repository;

import hello.productmanager.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryImlTest {

    private final static ProductRepositoryIml productRepository = new ProductRepositoryIml();
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");

    @AfterEach
    void afterEach() {
        productRepository.clearStore();
    }

    @Test
    void save() {
        Product product = new Product("productA", 10000, 10);

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Product saved = productRepository.save(product);
        Product findId = productRepository.findById(product.getId());

        String priceFormat = decimalFormat.format(saved.getPrice()) + " 원";
        String quantityFormat = decimalFormat.format(saved.getQuantity()) + " 개";

        assertThat(findId.getId()).isEqualTo(saved.getId());

        assertThat(findId.getPriceFormat()).isEqualTo(priceFormat);
        assertThat(findId.getQuantityFormat()).isEqualTo(quantityFormat);

        assertThat(findId.getRegistrationDate()).isEqualTo(nowDate);
    }

    @Test
    void findAll() {
        Product product1 = new Product("productA", 10000, 10);
        Product product2 = new Product("productB", 20000, 20);


        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        List<Product> list = productRepository.findAll();

        String priceFormat1 = decimalFormat.format(savedProduct1.getPrice()) + " 원";
        String priceFormat2 = decimalFormat.format(savedProduct2.getPrice()) + " 원";

        String quantity1 = decimalFormat.format(savedProduct1.getQuantity()) + " 개";
        String quantity2 = decimalFormat.format(savedProduct2.getQuantity()) + " 개";

        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(product1, product2);

        assertThat(savedProduct1.getPriceFormat()).isEqualTo(priceFormat1);
        assertThat(savedProduct2.getPriceFormat()).isEqualTo(priceFormat2);

        assertThat(savedProduct1.getQuantityFormat()).isEqualTo(quantity1);
        assertThat(savedProduct2.getQuantityFormat()).isEqualTo(quantity2);
    }

    @Test
    void update() {
        Product product = new Product("productA", 10000, 10);
        Product saved = productRepository.save(product);
        Product findProduct = productRepository.findById(saved.getId());

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String priceFormat1 = decimalFormat.format(findProduct.getPrice()) + " 원";
        String quantity1 = decimalFormat.format(findProduct.getQuantity()) + " 개";

        Product updateParam = new Product("productB", 20000, 20);
        productRepository.update(findProduct.getId(), updateParam);

        String priceFormat2 = decimalFormat.format(updateParam.getPrice()) + " 원";
        String quantity2 = decimalFormat.format(updateParam.getQuantity()) + " 개";

        assertThat(findProduct.getProductName()).isEqualTo(updateParam.getProductName());
        assertThat(findProduct.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findProduct.getQuantity()).isEqualTo(updateParam.getQuantity());

        assertThat(findProduct.getPriceFormat()).isNotEqualTo(priceFormat1);
        assertThat(findProduct.getPriceFormat()).isEqualTo(priceFormat2);

        assertThat(findProduct.getQuantityFormat()).isNotEqualTo(quantity1);
        assertThat(findProduct.getQuantityFormat()).isEqualTo(quantity2);

        assertThat(findProduct.getModifyingRegistrationDate()).isEqualTo(nowDate);
    }

    @Test
    void delete() {
        Product product1 = new Product("productA", 10000, 10);
        Product product2 = new Product("productB", 20000, 20);
        Product product3 = new Product("productC", 30000, 30);

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

        Product productId1 = productRepository.findById(savedProduct1.getId());
        Product productId2 = productRepository.findById(savedProduct2.getId());
        Product productId3 = productRepository.findById(savedProduct3.getId());

        /** delete 실행 */
        productRepository.delete(productId2.getId());

        List<Product> list = productRepository.findAll();

        assertThat(list.size()).isEqualTo(2);
        assertThat(list).contains(productId1, productId3);
    }

}