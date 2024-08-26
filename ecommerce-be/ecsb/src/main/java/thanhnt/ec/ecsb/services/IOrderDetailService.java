package thanhnt.ec.ecsb.services;

import thanhnt.ec.ecsb.dto.OrderDetailDTO;
import thanhnt.ec.ecsb.exceptions.DataNotFoundException;
import thanhnt.ec.ecsb.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetail) throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
