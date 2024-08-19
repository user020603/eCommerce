package thanhnt.ec.ecsb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thanhnt.ec.ecsb.dto.OrderDetailDTO;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetail
    ) {
        return ResponseEntity.ok("Create order detail successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail (
            @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok("Get order detail with id = " + id);
    }

    // get list of order detail of an order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId
    ) {
        return ResponseEntity.ok("Get order details with id = " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailData
    ) {
        return ResponseEntity.ok("Update Order Detail with id = " + id +
                                    " with new data is: " + newOrderDetailData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.noContent().build();
    }
}
