package com.cfs.ecommerce.service;

import com.cfs.ecommerce.dto.OrderDTO;
import com.cfs.ecommerce.dto.OrderItemDTO;
import com.cfs.ecommerce.model.OrderItem;
import com.cfs.ecommerce.model.Orders;
import com.cfs.ecommerce.model.Product;
import com.cfs.ecommerce.model.User;
import com.cfs.ecommerce.repo.OrderRepository;
import com.cfs.ecommerce.repo.ProductRepository;
import com.cfs.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;



@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderDTO placeOrder(Long userId, Map<Long, Integer>productQuantities , double totalAmount)
    {
       User user= userRepository.findById(userId)
               .orElseThrow(()-> new RuntimeException("User Not Found"));
       Orders order= new Orders();
       order.setUser(user);
       order.setOrderDate(new Date());
       order.setStatus("Pending");
       order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems= new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS= new ArrayList<>();

        for(Map.Entry<Long , Integer> entry: productQuantities.entrySet())
        {
            Product product= productRepository.findById(entry.getKey())
                    .orElseThrow(()->new RuntimeException("Product Not Found"));
            OrderItem orderItem= new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);
            orderItemDTOS.add(new OrderItemDTO(product.getName(), product.getPrice(), entry.getValue()));
        }
        order.setOrderItems(orderItems);
        Orders saveOrder=orderRepository.save(order);
        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount(),saveOrder.getOrderDate(), saveOrder.getStatus(),orderItemDTOS);
    }

    public  List<OrderDTO> getAllOrders()
    {
        List<Orders> orders = orderRepository.findAllOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders orders)
    {
         List<OrderItemDTO> orderItems= orders.getOrderItems().stream()
                 .map(item -> new OrderItemDTO(
                         item.getProduct().getName(),
                         item.getProduct().getPrice(),
                         item.getQuantity())).collect(Collectors.toList());
                 return new OrderDTO(
                                 orders.getId(),
                                 orders.getTotalAmount(),
                                 orders.getOrderDate(),
                                 orders.getStatus(),
                                 orders.getUser() != null ? orders.getUser().getName():"Unknown",
                                 orders.getUser() != null ? orders.getUser().getEmail():"Unknown",
                                 orderItems

                 );
    }
    public List<OrderDTO> getOrderByUser(Long userId)
    {
       Optional<User> userOp= userRepository.findById(userId);
       if(userOp.isEmpty())
       {
           throw new RuntimeException("User Not Found");

       }
       User user=userOp.get();
       List<Orders> orderList= orderRepository.findByUser(user);
       return orderList.stream().map(this::convertToDTO).collect(Collectors.toList());

    }
}
