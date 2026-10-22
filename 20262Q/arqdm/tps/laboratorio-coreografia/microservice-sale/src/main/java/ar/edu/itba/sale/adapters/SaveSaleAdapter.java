package ar.edu.itba.sale.adapters;

import ar.edu.itba.sale.adapters.out.repository.SaleRepository;
import ar.edu.itba.sale.adapters.out.repository.mapper.SaleMapper;
import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.port.out.SaveSaleOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveSaleAdapter implements SaveSaleOutputPort {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public Sale execute(final Sale sale) {
        final var saleEntity = this.saleMapper.saleToSaleEntity(sale);
        final var saleSaved = this.saleRepository.save(saleEntity);

        return this.saleMapper.saleEntityToSale(saleSaved);
    }
}
