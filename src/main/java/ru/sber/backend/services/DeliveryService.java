package ru.sber.backend.services;

import ru.sber.backend.entities.Delivery;


public interface DeliveryService {
    Long addDelivery(Delivery delivery);

    Delivery getDeliveryByOrderId(Long orderId);

}
