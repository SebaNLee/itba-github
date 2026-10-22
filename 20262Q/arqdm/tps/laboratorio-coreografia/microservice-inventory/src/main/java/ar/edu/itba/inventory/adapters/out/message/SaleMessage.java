package ar.edu.itba.inventory.adapters.out.message;

import ar.edu.itba.inventory.application.core.domain.Sale;
import ar.edu.itba.inventory.application.core.domain.enums.SaleEventEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleMessage {

    private Sale sale;
    private SaleEventEnum saleEventEnum;

}
