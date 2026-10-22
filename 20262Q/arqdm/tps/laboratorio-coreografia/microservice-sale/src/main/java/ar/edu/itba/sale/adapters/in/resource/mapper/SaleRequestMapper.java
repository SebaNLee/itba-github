package ar.edu.itba.sale.adapters.in.resource.mapper;

import ar.edu.itba.sale.adapters.in.resource.request.SaleRequest;
import ar.edu.itba.sale.application.core.domain.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleRequestMapper {
    Sale saleRequestToSale(SaleRequest saleRequest);
}
