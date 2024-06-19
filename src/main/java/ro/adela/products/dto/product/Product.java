package ro.adela.products.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String price;

    @Override
    public String toString() {
        return String.format("Weight [id=%s, name=%s, price=%s]", id, name, price);
    }

}
