package cn.stu.model;

/*
 * 商品
 */
public class Goods {
	private int id;	//商品ID
	private int kind; //商品类型ID
	private String name;//商品名称
	private float price;//商品价格
	private float preprice;//商品原价
	private int overNumber;//已卖数量
	private int pic_dis;//商品图片显示的个数
	private int pic_mid;//商品图片描述的个数
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPreprice() {
		return preprice;
	}
	public void setPreprice(float preprice) {
		this.preprice = preprice;
	}
	public int getOverNumber() {
		return overNumber;
	}
	public void setOverNumber(int overNumber) {
		this.overNumber = overNumber;
	}
	public int getPic_dis() {
		return pic_dis;
	}
	public void setPic_dis(int pic_dis) {
		this.pic_dis = pic_dis;
	}
	public int getPic_mid() {
		return pic_mid;
	}
	public void setPic_mid(int pic_mid) {
		this.pic_mid = pic_mid;
	}
}