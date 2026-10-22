package ar.edu.itba.sale.adapters.out.repository.mapper;

import ar.edu.itba.sale.adapters.out.repository.entity.SaleEntity;
import ar.edu.itba.sale.application.core.domain.Sale;
import ar.edu.itba.sale.application.core.domain.enums.SaleStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "status", target = "statusId", qualifiedByName = "setStatusId")
    SaleEntity saleToSaleEntity(Sale sale);

    @Mapping(source = "statusId", target = "status", qualifiedByName = "setStatus")
    Sale saleEntityToSale(SaleEntity saleEntity);

    @Named("setStatusId")
    default Integer setStatusId(SaleStatusEnum saleStatusEnum) {
        return saleStatusEnum.getStatusId();
    }

    @Named("setStatus")
    default SaleStatusEnum setStatus(Integer saleStatusId) {
        return SaleStatusEnum.getSaleStatusByStatusId(saleStatusId);
    }
}
