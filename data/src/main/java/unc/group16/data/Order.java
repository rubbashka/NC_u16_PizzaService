package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;

import java.util.Date;


@Table(name = "ORDERS", columns = 5)
public class Order implements TableRecord {
    @Column(id = 1, name = "ORD_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "CLNT_CLNT_ID")
    private Long clientId;

    @Column(id = 3, name = "ORDER_DATE")
    private Date orderDate;

    @Column(id = 4, name = "DELIVERY_DATE")
    private Date deliveryDate;

    @Column(id = 5, name = "DESCRIPTION")
    private String description;

    public Order() {}

    public Order(Long id, Long clientId, Date orderDate, Date deliveryDate, String description) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getClientId() {
        return clientId;
    }

    public Order setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComments() {
        return description;
    }

    public Order setComments(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Object clone() {
        Order result = null;
        try {
            result = (Order) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
