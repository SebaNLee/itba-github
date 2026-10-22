package ar.edu.itba.sale.adapters.out.message;

import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.core.domain.enums.SaleEventEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleMessage {

    private Sale sale;
    private SaleEventEnum saleEventEnum;
}
