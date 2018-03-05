package cn.stu.model;

import java.util.Date;

/*
 * 订单
 */
public class Order {
	private int id; //订单ID
	private int userId;//用户ID
	private int status;//订单状态
	private int goodId;//商品ID
	private String add;//收件人地址
	private String addName;//收件人姓名
	private String addTel;//收件人电话
	private int detailId;//详情ID
	private Date timeSubmit;//订单时间-提交
	private Date timePay;//订单时间-支付
	private Date timeCancel;//订单时间-取消
	private Date timeShip;//订单时间-发货
	private Date timeReturn;//订单时间-退货
	private Date timeFinish;//订单时间-完成
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public String getAddTel() {
		return addTel;
	}
	public void setAddTel(String addTel) {
		this.addTel = addTel;
	}
	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public Date getTimeSubmit() {
		return timeSubmit;
	}
	public void setTimeSubmit(Date timeSubmit) {
		this.timeSubmit = timeSubmit;
	}
	public Date getTimePay() {
		return timePay;
	}
	public void setTimePay(Date timePay) {
		this.timePay = timePay;
	}
	public Date getTimeCancel() {
		return timeCancel;
	}
	public void setTimeCancel(Date timeCancel) {
		this.timeCancel = timeCancel;
	}
	public Date getTimeShip() {
		return timeShip;
	}
	public void setTimeShip(Date timeShip) {
		this.timeShip = timeShip;
	}
	public Date getTimeReturn() {
		return timeReturn;
	}
	public void setTimeReturn(Date timeReturn) {
		this.timeReturn = timeReturn;
	}
	public Date getTimeFinish() {
		return timeFinish;
	}
	public void setTimeFinish(Date timeFinish) {
		this.timeFinish = timeFinish;
	}
	
}