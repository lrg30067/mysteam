package com.akkafun.order.service.gateway;

import com.akkafun.base.exception.RemoteCallException;
import com.akkafun.product.api.ProductUrl;
import com.akkafun.product.api.dtos.ProductDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liubin on 2016/7/18.
 */
@Service
public class ProductGateway {

    protected Logger logger = LoggerFactory.getLogger(ProductGateway.class);

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(ignoreExceptions = RemoteCallException.class)
    public List<ProductDto> findProducts(List<Long> productIds) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(ProductUrl.buildUrl(ProductUrl.PRODUCT_LIST_URL))
                .queryParam("id", productIds.toArray())
                .build().encode().toUri();

        ProductDto[] productDtos = restTemplate.getForObject(uri, ProductDto[].class);
        return Arrays.asList(productDtos);
    }
}
