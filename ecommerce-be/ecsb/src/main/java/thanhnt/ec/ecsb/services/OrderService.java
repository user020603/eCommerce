package thanhnt.ec.ecsb.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import thanhnt.ec.ecsb.dto.OrderDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;
import thanhnt.ec.ecsb.model.Order;
import thanhnt.ec.ecsb.model.OrderStatus;
import thanhnt.ec.ecsb.model.User;
import thanhnt.ec.ecsb.repositories.OrderRepository;
import thanhnt.ec.ecsb.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // find existed userid
        User user = userRepository.
                findById(orderDTO.getUserId()).
                orElseThrow(
                        () -> new DataNotFoundException("Cannot find user with id: "
                                + orderDTO.getUserId())
                );

        // convert orderDTO => Order, use ModelMapper
        modelMapper.typeMap(OrderDTO.class, Order.class).
                addMappings(mapper -> mapper.skip(Order::setId));

        // update fields of Order in orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        // check shipping date greater than today
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        order.setActive(true);
        order.setShippingDate(shippingDate);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Can not find order with id: " + id)
        );
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId())
        );
        modelMapper.typeMap(OrderDTO.class, Order.class).
                addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        // no hard delete, soft-delete
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
