package com.etiya.ecommercedemo4.business.concretes;

import com.etiya.ecommercedemo4.business.abstracts.ICartService;
import com.etiya.ecommercedemo4.business.abstracts.IUserService;
import com.etiya.ecommercedemo4.business.constants.Messages;
import com.etiya.ecommercedemo4.business.dtos.request.cart.AddCartRequest;
import com.etiya.ecommercedemo4.core.util.exceptions.BusinessException;
import com.etiya.ecommercedemo4.core.util.mapping.ModelMapperService;
import com.etiya.ecommercedemo4.core.util.results.DataResult;
import com.etiya.ecommercedemo4.core.util.results.Result;
import com.etiya.ecommercedemo4.core.util.results.SuccessDataResult;
import com.etiya.ecommercedemo4.core.util.results.SuccessResult;
import com.etiya.ecommercedemo4.entities.concretes.Cart;
import com.etiya.ecommercedemo4.entities.concretes.User;
import com.etiya.ecommercedemo4.repository.ICartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CartManager implements ICartService {

    private ICartRepository cartRepository;
    private ModelMapperService modelMapperService;
    private IUserService userService;

    @Override
    public DataResult<List<Cart>> getAll() {
        List<Cart> response = this.cartRepository.findAll();
        return new SuccessDataResult<List<Cart>>(response, Messages.SuccessMessages.ListAll);
    }

    @Override
    public DataResult<Cart> getById(int id) {
        Cart response = this.cartRepository.findById(id).orElseThrow();
        return new SuccessDataResult<Cart>(response, Messages.SuccessMessages.ListById);
    }

    @Override
    public Result add(AddCartRequest addCartRequest) {
        checkIfUserExists(addCartRequest.getCustomerId());
        Cart cart = this.modelMapperService.forRequest().map(addCartRequest,Cart.class);
        cart.setId(0);
        this.cartRepository.save(cart);
        return new SuccessResult(Messages.SuccessMessages.Add);
    }

    private void checkIfUserExists(int id) {
        Cart cart = this.cartRepository.findByCustomerId(id);
        if (cart != null){
            throw new BusinessException(Messages.User.UserExists);
        }
    }
}
