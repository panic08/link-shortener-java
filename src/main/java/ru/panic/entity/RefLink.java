package ru.panic.entity;

import lombok.Data;
import ru.panic.dto.Client;
import ru.panic.dto.Product;

import java.util.List;

@Data
public class RefLink {
    private Long id;
    private String title;
    private String URL;
    private Long timestamp;
    private Long transitions;
    private Long registrations;
    private Long purchases;
    private Double pfu;
    private Double pfk;
    private List<Client> clients;
    private List<Product> products;
}
