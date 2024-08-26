package thanhnt.ec.ecsb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thanhnt.ec.ecsb.dto.OrderDetailDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;
import thanhnt.ec.ecsb.model.Order;
import thanhnt.ec.ecsb.model.OrderDetail;
import thanhnt.ec.ecsb.model.Product;
import thanhnt.ec.ecsb.repositories.OrderDetailRepository;
import thanhnt.ec.ecsb.repositories.OrderRepository;
import thanhnt.ec.ecsb.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find Order with id: " + orderDetailDTO.getOrderId())
        );

        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find Product with id " + orderDetailDTO.getProductId())
        );

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .color(orderDetailDTO.getColor())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find OrderDetail with id: " + id)
        );
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
         OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElseThrow(
                 () -> new DataNotFoundException("Cannot find order detail with id: " + id)
         );

         Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(
                 () -> new DataNotFoundException("Cannot find order with id: " + orderDetailDTO.getOrderId())
         );

         Product existingProduct = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(
                 () -> new DataNotFoundException("Cannot find order with id: " + orderDetailDTO.getProductId())
         );

         existingOrderDetail.setPrice(orderDetailDTO.getPrice());
         existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
         existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
         existingOrderDetail.setColor(orderDetailDTO.getColor());
         existingOrderDetail.setOrder(existingOrder);
         existingOrderDetail.setProduct(existingProduct);
         existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
