package com.etiya.ecommercedemo4.business.concretes;

import com.etiya.ecommercedemo4.business.abstracts.IInvoiceService;
import com.etiya.ecommercedemo4.business.abstracts.IOrderService;
import com.etiya.ecommercedemo4.business.abstracts.IPaymentService;
import com.etiya.ecommercedemo4.business.constants.Messages;
import com.etiya.ecommercedemo4.business.dtos.request.invoices.AddInvoiceRequest;
import com.etiya.ecommercedemo4.business.dtos.request.order.AddOrderRequest;
import com.etiya.ecommercedemo4.core.util.exceptions.BusinessException;
import com.etiya.ecommercedemo4.core.util.mapping.ModelMapperService;
import com.etiya.ecommercedemo4.core.util.messages.IMessagesService;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.core.util.results.SuccessDataResult;
import com.etiya.ecommercedemo4.core.util.results.SuccessResult;
import com.etiya.ecommercedemo4.entities.concretes.Order;
import com.etiya.ecommercedemo4.entities.concretes.Payment;
import com.etiya.ecommercedemo4.repository.IOrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderManager implements IOrderService {

    private IOrderRepository orderRepository;
    private ModelMapperService modelMapperService;
    private IPaymentService paymentService;

    private IMessagesService messagesService;
    private IInvoiceService invoiceService;


    @Override
    public DataResult<List<Order>> getAll() {
        List<Order> response = this.orderRepository.findAll();
        return new SuccessDataResult<List<Order>>(response,messagesService.getMessage(Messages.SuccessMessages.ListAll));
    }

    @Override
    public DataResult<Order> getById(int id) {
        Order response = this.orderRepository.findById(id).orElseThrow();
        return new SuccessDataResult<Order>(response, messagesService.getMessage(Messages.SuccessMessages.ListById));
    }
    //TODO invoiceNumber
    @Transactional
    @Override
    public Result add(AddOrderRequest addOrderRequest) {
        checkIfPaymentExists(addOrderRequest.getPaymentId());
        Order order = this.modelMapperService.forRequest().map(addOrderRequest,Order.class);
        order.setId(0);
        Date date = new Date();
        order.setOrderDate(date);
        Order savedOrder = this.orderRepository.save(order);
        AddInvoiceRequest invoiceRequest = AddInvoiceRequest.builder().invoiceNumber("15").orderId(savedOrder.getId()).build();
        invoiceService.add(invoiceRequest);
        return new SuccessResult(messagesService.getMessage(Messages.SuccessMessages.Add));
    }

    @Override
    public Page<Order> getAllWithPagination(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    private void checkIfPaymentExists(int id){

        DataResult<Payment> payment = this.paymentService.getById(id);
        if(payment==null){
            throw new BusinessException(messagesService.getMessage(Messages.Order.OrderDoesNotExist));
        }
    }
}
