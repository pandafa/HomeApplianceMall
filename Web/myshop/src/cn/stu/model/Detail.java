package cn.stu.model;

/*
 * 单个物品分类
 */
public class Detail {
	private int goodId;//物品ID
	private int id;//详细分类ID
	private String name;//详细分类名称
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
