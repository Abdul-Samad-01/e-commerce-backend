package com.sellerbuyer.sellerbuyer.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {
    public static Pageable getPageable(int page, int pageSize,
                                       List<String> sortByProperties) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortByProperties != null && !sortByProperties.isEmpty()) {
            for (String field : sortByProperties) {
                orders.add(new Sort.Order(null, field));
            }
        }
        return PageRequest.of(page, pageSize, Sort.by(orders));
    }
}
