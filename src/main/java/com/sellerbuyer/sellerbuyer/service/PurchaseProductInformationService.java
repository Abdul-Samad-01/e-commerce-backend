package com.sellerbuyer.sellerbuyer.service;

import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseProductInformationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseProductInformationService {

    Page<PurchaseProductInformationDto> getPurchasesBySeller(Pageable pageable);
}
