package com.etiya.ecommercedemo4.business.abstracts;

import com.etiya.ecommercedemo4.business.dtos.request.order.AddOrderRequest;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.entities.concretes.Order;

import java.util.List;

public interface IOrderService {
    Result add(AddOrderRequest addOrderRequest);
    DataResult<List<Order>> getAll();
    DataResult<Order> getById(int id);
}
