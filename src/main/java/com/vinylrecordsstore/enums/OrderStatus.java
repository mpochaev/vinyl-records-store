package com.vinylrecordsstore.enums;

public enum OrderStatus {
    PLACED,     // оформлен (ждет оплаты)
    PURCHASED,  // куплен (оплачен и забран)
    CANCELLED   // отменён (не забрали вовремя)
}