package unc.group16.data;

import unc.group16.interfaces.XmlManager;

import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Order implements XmlManager {
    private Long id;
    private Long clientId;
    private Date orderDate;
    private Date deliveryDate;
    private String comments;

    public Order() {}

    public Order(Long id, Long clientId, Date orderDate, Date deliveryDate, String comments) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    @XmlElement
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @XmlElement
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    @XmlElement
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComments() {
        return comments;
    }

    @XmlElement
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public Object clone() {
        return new Order(id, clientId, orderDate, deliveryDate, comments);
    }
}
