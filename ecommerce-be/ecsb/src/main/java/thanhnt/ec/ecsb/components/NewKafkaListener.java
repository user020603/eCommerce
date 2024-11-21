package thanhnt.ec.ecsb.components;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import thanhnt.ec.ecsb.model.Category;

import java.util.List;

@Component
@KafkaListener(id = "groupA", topics = { "get-all-categories", "insert-a-category" })
public class NewKafkaListener {
    @KafkaHandler
    public void listenCategory(Category category) {
        System.out.println("Received from Kafka: " + category);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown from Kafka: " + object);
    }
    @KafkaHandler
    public void listenListOfCategories(List<Category> categories) {
        System.out.println("Received from Kafka: " + categories);
    }

}
