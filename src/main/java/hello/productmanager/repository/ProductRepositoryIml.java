package hello.productmanager.repository;

import hello.productmanager.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Repository
public class ProductRepositoryIml implements ProductRepository {

    private static final Map<Long, Product> store = new HashMap<>();
    private static Long sequence = 0L;
    private final static DecimalFormat decimalFormat = new DecimalFormat("###,###");

    @Override
    public Product save(Product product) {
        product.setId(++sequence);

        if (product.getRegistrationDate() == null) {
            product.setRegistrationDate(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        }

        product.setModifyingRegistrationDate("-");

        String priceFormat = decimalFormat.format(product.getPrice()) + " 원";
        product.setPriceFormat(priceFormat);

        String quantity = decimalFormat.format(product.getQuantity()) + " 개";
        product.setQuantityFormat(quantity);

        store.put(product.getId(), product);

        return product;
    }

    @Override
    public Product findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long id, Product updateParam) {
        Product findProduct = store.get(id);

        findProduct.setProductName(updateParam.getProductName());
        findProduct.setPrice(updateParam.getPrice());
        findProduct.setQuantity(updateParam.getQuantity());
        findProduct.setModifyingRegistrationDate(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        String priceFormat = decimalFormat.format(findProduct.getPrice()) + " 원";
        findProduct.setPriceFormat(priceFormat);

        String quantity = decimalFormat.format(findProduct.getQuantity()) + " 개";
        findProduct.setQuantityFormat(quantity);
    }

    @Override
    public void delete(Long id){
        store.remove(id);
    }

    public void clearStore() {
        store.clear();
    }

}