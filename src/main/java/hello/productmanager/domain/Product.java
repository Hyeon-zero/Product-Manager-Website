package hello.productmanager.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
@Setter
public class Product {
    private Long id;

    @NotBlank(message = "제품명을 입력하세요.")
    private String productName;

    @NotNull(message = "가격을 입력하세요.")
    @Range(min = 1000, max = 10000000, message = "가격은 1,000원 ~ 10,000,000원까지 입력 가능합니다.")
    private Integer price;
    private String priceFormat;

    @NotNull(message = "수량을 입력하세요.")
    @Range(min = 1, max = 5000, message = "수량은 1개 ~ 5,000개까지 입력 가능합나다.")
    private Integer quantity;
    private String quantityFormat;

    private String registrationDate;
    private String modifyingRegistrationDate;

    public Product(String productName, Integer price, Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // 테스트용 데이터를 처리할 생성자
    public Product(String productName, Integer price, Integer quantity, String registrationDate, String modifyingRegistrationDate) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.registrationDate = registrationDate;
        this.modifyingRegistrationDate = modifyingRegistrationDate;
    }

}