package ru.panic.dto;

import lombok.Data;

import java.util.List;

@Data
public class WooCommerceHookDto {
    private Long id;
    private Long parent_id;
    private String number;
    private String status;
    private String currency;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;
    private Double total;
    private Double subtotal;
    private Double total_tax;
    private Double total_shipping;
    private Double cart_tax;
    private Double shipping_tax;
    private Double discount_total;
    private Double discount_tax;
    private List<ShippingMethod> shipping_methods;
    private String payment_method;
    private String payment_method_title;
    private String transaction_id;
    private String customer_ip_address;
    private String customer_user_agent;
    private Long customer_id;
    private String customer_note;
    private BillingAddress billing;
    private ShippingAddress shipping;
    private List<OrderLineItem> line_items;

    @Data
    public static class ShippingMethod {
        private String id;
        private String title;
        private String method_id;
        private Double cost;
        private Double total;
        private List<Tax> taxes;
    }
    @Data
    public static class Tax {
        private String id;
        private String total;
    }
    @Data
    public static class BillingAddress {
        private String first_name;
        private String last_name;
        private String company;
        private String address_1;
        private String address_2;
        private String city;
        private String state;
        private String postcode;
        private String country;
        private String email;
        private String phone;
    }
    @Data
    public static class ShippingAddress {
        private String first_name;
        private String last_name;
        private String company;
        private String address_1;
        private String address_2;
        private String city;
        private String state;
        private String postcode;
        private String country;
    }
    @Data
    public static class OrderLineItem {
        private Long id;
        private String name;
        private Long product_id;
        private Long variation_id;
        private Integer quantity;
        private String tax_class;
        private Double subtotal;
        private Double subtotal_tax;
        private Double total;
        private Double total_tax;
        private List<Tax> taxes;
    }
}