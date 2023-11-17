package hello.productmanager.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeleteProduct {

    @NotNull(message = "존재하지 않는 제품 ID 입니다.")
    private Long id;

}