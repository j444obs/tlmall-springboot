package fun.sherman.tlmall.domain;

import java.math.BigDecimal;

/**
 * 订单模块
 */
public class Order {

    private Integer id;
    private Long orderNo;
    private Integer userId;
    private Integer shippingId;
    /**
     * 实际付款金额，单位元，保留两位小数
     */
    private BigDecimal payment;
    /**
     * 支付类型，1-在线支付，其它保留
     */
    private Integer paymentType;
    /**
     * 运费
     */
    private Integer postage;
    /**
     * 订单状态：0-已取消，10-未支付，20-已付款，40-已发货，50-交易成功，60-交易关闭
     */
    private Integer status;
    /**
     * 支付时间，支付成功，系统回调时间
     */
    private java.util.Date paymentTime;
    /**
     * 发货时间
     */
    private java.util.Date sendTime;
    /**
     * 交易完成时间
     */
    private java.util.Date endTime;
    /**
     * 交易关闭时间，下单但有效时间内未付款
     */
    private java.util.Date closeTime;
    private java.util.Date createTime;
    private java.util.Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.util.Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(java.util.Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public java.util.Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(java.util.Date sendTime) {
        this.sendTime = sendTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public java.util.Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(java.util.Date closeTime) {
        this.closeTime = closeTime;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}
