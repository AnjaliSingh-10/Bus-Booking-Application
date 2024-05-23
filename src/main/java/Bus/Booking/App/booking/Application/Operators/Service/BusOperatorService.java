package com.RedBus.Operators.Service;

import com.RedBus.Operators.payload.BusOperatorDto;

import java.util.List;

public interface BusOperatorService {
    BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto);

    List<BusOperatorDto> getbyBusOperatorCompanyName(String busOperatorCompanyName, int pageNo, int pageSize);

}


