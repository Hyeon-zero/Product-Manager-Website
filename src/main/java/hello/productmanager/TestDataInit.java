package hello.productmanager;

import hello.productmanager.domain.Product;
import hello.productmanager.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ProductRepository productRepository;

    /** 테스트용 데이터 */
    @PostConstruct
    public void init() {
        productRepository.save(new Product("iPhone", 1300000, 1000,
                "2023-07-08 15:16:33", "-"));
        productRepository.save(new Product("MacBook", 3500000, 2000,
                "2023-08-08 20:05:14", "-"));
    }

}