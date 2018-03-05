package cn.stu.model;

/*
 * 收藏夹
 */
public class Collection {
	private int userId;//用户ID
	private int goodId;//物品ID
//	private int[] goodsId;//物品们的ID
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	
}
