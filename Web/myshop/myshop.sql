/*
Navicat MySQL Data Transfer

Source Server         : test01
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : myshop

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-09-06 18:35:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_collection`;
CREATE TABLE `t_collection` (
  `user_id` int(11) NOT NULL,
  `good_id` int(11) NOT NULL,
  PRIMARY KEY (`good_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_collection
-- ----------------------------
INSERT INTO `t_collection` VALUES ('2', '2');
INSERT INTO `t_collection` VALUES ('1', '4');
INSERT INTO `t_collection` VALUES ('2', '9');
INSERT INTO `t_collection` VALUES ('3', '46');
INSERT INTO `t_collection` VALUES ('3', '51');

-- ----------------------------
-- Table structure for t_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_detail`;
CREATE TABLE `t_detail` (
  `good_id` int(11) NOT NULL,
  `detail_id` int(11) NOT NULL,
  `detail_name` varchar(255) NOT NULL,
  PRIMARY KEY (`good_id`,`detail_id`),
  KEY `detail_id` (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_detail
-- ----------------------------
INSERT INTO `t_detail` VALUES ('1', '1', '红色');
INSERT INTO `t_detail` VALUES ('1', '2', '蓝色');
INSERT INTO `t_detail` VALUES ('1', '3', '黑色');
INSERT INTO `t_detail` VALUES ('2', '4', '紫色');

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `good_id` int(11) NOT NULL,
  `good_kind` int(255) NOT NULL,
  `good_name` varchar(255) NOT NULL,
  `good_dis` int(255) DEFAULT NULL,
  `good_mid` int(11) DEFAULT NULL,
  `good_price` float NOT NULL,
  `good_over` int(255) DEFAULT NULL,
  `good_pre` float(255,0) DEFAULT NULL,
  `istoday` int(11) DEFAULT NULL,
  PRIMARY KEY (`good_id`),
  KEY `kind` (`good_kind`),
  CONSTRAINT `kind` FOREIGN KEY (`good_kind`) REFERENCES `t_kind` (`kind_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('1', '1', '夏普 (SHARP) LCD-60SU465A 60英寸4K超高清wifi智能网络液晶平板电视机', '5', '5', '5599', '1000', '6668', '0');
INSERT INTO `t_goods` VALUES ('2', '1', '索尼（SONY）KD-55X7000D 55英寸高清4K HDR 安卓6.0系统 智能液晶电视', '5', '6', '4599', '248', '5499', '0');
INSERT INTO `t_goods` VALUES ('3', '1', '长虹（CHANGHONG）55U3C 55英寸双64位4K安卓智能LED液晶电视(黑色)', '6', '8', '2999', '549', '3500', '0');
INSERT INTO `t_goods` VALUES ('4', '1', '飞利浦（PHILIPS）50PUF6061/T3 50英寸 64位十一核4K超高清智能液晶平板电视机（黑色）', '9', '1', '2699', '247', '2999', '0');
INSERT INTO `t_goods` VALUES ('5', '1', '乐视超级电视 超4 X55 55英寸 HDR 3GB+32GB 4K高清2D智能语音遥控 LED液晶电视（标配挂架）', '6', '1', '3889', '147', '4988', '0');
INSERT INTO `t_goods` VALUES ('6', '2', '海尔（Haier）BCD-452WDPF 452升 风冷无霜对开门 纤薄设计 节能静音 电脑控温双循环', '21', '1', '2799', '97', '3099', '1');
INSERT INTO `t_goods` VALUES ('7', '2', '海尔（Haier）BCD-571WDEMU1 571升变频风冷无霜对开门冰箱 人工智慧 智能WiFi（APP手机控制）藕纱白', '4', '1', '3399', '354', '3899', '0');
INSERT INTO `t_goods` VALUES ('8', '2', 'CL BCD-398KZ50 398升 冷藏自动除霜 电脑温控 十字对开门冰箱（流光金）', '0', '1', '1999', '9478', '2300', '0');
INSERT INTO `t_goods` VALUES ('9', '2', '美的 Midea BCD-350WTPZV(E) 350升 风冷变频智能多门冰箱 WIFI远程操控 宽幅变温 芙蓉金(智能APP控制)', '1', '1', '4499', '3145', '4999', '0');
INSERT INTO `t_goods` VALUES ('10', '2', '西门子（SIEMENS） BCD-321W(KG32NV21EC) 321升 风冷无霜 双门冰箱 电脑温控 LED内显（白色）', '0', '1', '3598', '344', '3989', '0');
INSERT INTO `t_goods` VALUES ('11', '2', '美菱(MeiLing) BCD-446ZP9CX 446升 节能变频 时尚静音 科学多温区 电脑十字对开门冰箱(银)', '0', '1', '2999', '2248', '3500', '1');
INSERT INTO `t_goods` VALUES ('12', '2', '海尔（Haier）BCD-432WDVMU1 432升变频风冷无霜多门冰箱 大冷冻能力智能WIFI(手机APP控制)香槟金', '0', '1', '4199', '989', '4699', '0');
INSERT INTO `t_goods` VALUES ('13', '2', '容声 (Ronshen) BCD-245WD11NY 245升 三门冰箱 风冷无霜 电脑控温 宽幅变温室', '0', '1', '1799', '4810', '2999', '0');
INSERT INTO `t_goods` VALUES ('14', '2', '博世（BOSCH） BCD-321W(KGN33V2QEC) 321升 风冷无霜 双门冰箱 电脑温控 LED内显（流沙金）', '0', '1', '3285', '1266', '4097', '0');
INSERT INTO `t_goods` VALUES ('15', '3', '西门子(SIEMENS) XQG80-WD12G4C01W 8公斤 洗烘一体变频 滚筒洗衣机 LED显示屏 静音 热风除菌（白色）', '0', '1', '4798', '2150', '5864', '0');
INSERT INTO `t_goods` VALUES ('16', '3', '海尔（Haier) EG8014HB39GU1 8公斤变频洗烘一体滚筒洗衣机 蒸汽熨防皱烘干 智能APP控制', '0', '1', '4499', '2010', '5899', '1');
INSERT INTO `t_goods` VALUES ('17', '3', '西门子(SIEMENS) XQG80-WD12G4681W 8公斤 洗烘一体变频 滚筒洗衣机 LED显示屏 静音 热风除菌（银色）', '0', '1', '4990', '24489', '5599', '0');
INSERT INTO `t_goods` VALUES ('18', '3', '小天鹅（LittleSwan）10公斤洗烘一体 变频滚筒洗衣机 微联智能 热风旋流烘干 京东微联APP控制 白色 TD100V80WDX', '0', '1', '4398', '988', '5647', '0');
INSERT INTO `t_goods` VALUES ('19', '3', 'TCL XQB55-36SP 5.5公斤 全自动波轮洗衣机 一键脱水（亮灰色）', '0', '1', '729', '578', '999', '0');
INSERT INTO `t_goods` VALUES ('20', '3', '海尔（Haier) 8公斤直驱变频波轮全自动洗衣机 京东微联智能APP控制 EB80BM2WU1', '0', '1', '1499', '364', '2199', '0');
INSERT INTO `t_goods` VALUES ('21', '3', '美的（Midea）7.5公斤全自动波轮 京东微联智能APP手机控制 MB75-eco11W', '0', '1', '918', '2498', '1389', '1');
INSERT INTO `t_goods` VALUES ('22', '3', '松下（Panasonic） 95度高温除菌 9公斤大容量变频全自动滚筒洗衣机 泡沫净 三维立体洗（银色）XQG90-E59L2H', '0', '1', '3999', '658', '4399', '0');
INSERT INTO `t_goods` VALUES ('23', '3', '大宇（DAEWOO）XQG30-888G 韩国进口小型壁挂式婴儿洗衣机 迷你全自动变频儿童宝宝内衣专用 香槟金', '0', '1', '2899', '2147', '3899', '0');
INSERT INTO `t_goods` VALUES ('24', '3', '小鸭 3.5公斤 半自动单筒迷你小洗衣机 蓝光杀菌 不锈钢沥水篮XPB35-599', '0', '1', '259', '3354', '369', '0');
INSERT INTO `t_goods` VALUES ('25', '3', '松下（Panasonic)3公斤智能婴儿迷你滚筒洗衣机 四重抗菌法宝 精巧外观设计（灵动银）XQG30-A3021', '0', '1', '3098', '249', '3699', '0');
INSERT INTO `t_goods` VALUES ('26', '4', '格力（GREE）正1.5匹 变频 品圆 冷暖 壁挂式空调 KFR-35GW/(35592)FNhDa-A3', '0', '1', '3299', '2447', '4399', '0');
INSERT INTO `t_goods` VALUES ('27', '4', '格力(GREE) 3匹 变频 Q铂 立柜式冷暖空调 KFR-72LW/(72596)FNAa-A3', '0', '1', '6399', '6597', '7299', '0');
INSERT INTO `t_goods` VALUES ('28', '4', '格力（GREE）3匹 变频 静享 微联智能 冷暖圆柱柜机空调 KFR-72LW/(72555)FNhAd-A3（WIFI）', '0', '1', '8699', '597', '9599', '0');
INSERT INTO `t_goods` VALUES ('29', '4', '格力（GREE）大1匹 变频 二级能效 冷静享 壁挂式冷暖空调 KFR-26GW/(26583)FNCb-A2', '0', '1', '3249', '4168', '3699', '0');
INSERT INTO `t_goods` VALUES ('30', '4', '美的（Midea）正1.5匹 智弧 智能 静音 光线感应 定速冷暖壁挂式空调挂机 KFR-35GW/WDAD3@', '0', '1', '2399', '684', '2499', '0');
INSERT INTO `t_goods` VALUES ('31', '4', '美的（Midea）2匹 制冷王 一级能效 变频冷暖 智能WiFi圆柱柜机 KFR-51LW/BP3DN8Y-YB300(B1)', '0', '1', '9199', '515', '1249', '0');
INSERT INTO `t_goods` VALUES ('32', '4', '美的（Midea）大1匹 变频 智弧 冷暖 智能壁挂式空调 KFR-26GW/WDAA3@', '0', '1', '2599', '589', '3699', '0');
INSERT INTO `t_goods` VALUES ('33', '4', '奥克斯（AUX）3匹 冷暖 定速 空调柜机(KFR-72LW/NSP1+3)', '0', '1', '4399', '648', '5499', '1');
INSERT INTO `t_goods` VALUES ('34', '4', '奥克斯（AUX）3匹 二级能效 冷暖 WIFI智能 京东微联APP控制 圆柱空调柜机(KFR-72LW/R1TC01+2)', '0', '1', '5299', '1789', '6999', '0');
INSERT INTO `t_goods` VALUES ('35', '4', '海尔（Haier）2匹 定频 冷暖 二级能效 APP智能操控 智能 圆柱空调柜机 KFR-50LW/10UAC12U1', '0', '1', '5799', '2940', '8999', '0');
INSERT INTO `t_goods` VALUES ('36', '4', '海尔（Haier）3匹 云净 变频 冷暖 二级能效 自清洁 除PM0.3 智能 圆柱空调柜机 KFR-72LW/12MAP22AU1', '0', '1', '7999', '4547', '8999', '0');
INSERT INTO `t_goods` VALUES ('37', '4', '美的（Midea）3匹变频风管机 适用40-48㎡ 家用/商用中央空调 6年包修 KFR-72T2W/BP2DN1-TR', '0', '1', '6880', '4653', '7699', '0');
INSERT INTO `t_goods` VALUES ('38', '4', '美的（Midea）大1.5匹变频风管机 适用20-24㎡ 家用/商用中央空调 6年包修 KFR-35T2W/BP2DN1-TR', '0', '1', '4280', '1868', '4646', '0');
INSERT INTO `t_goods` VALUES ('39', '4', '美的（Midea）一拖四变频一级能效 多联风管机空调 5匹 包含安装 6年包修 家用中央空调 MDVH-V120W/N1-612P(E1)', '0', '1', '28880', '2488', '32999', '0');
INSERT INTO `t_goods` VALUES ('40', '4', '美的（Midea）一拖三变频一级能效 多联风管机空调 4匹 包含安装 6年包修 家用中央空调 MDVH-V100W/N1-520P(E1)', '0', '1', '21580', '9885', '28690', '0');
INSERT INTO `t_goods` VALUES ('41', '4', '格力(GREE) 5匹 冷暖 定频 吸顶式天花机天井机商用中央空调（适用46-70㎡） KFR-120TW/(1256S)NhBa-3', '0', '1', '11000', '3322', '14299', '0');
INSERT INTO `t_goods` VALUES ('42', '4', '大金（DAIKIN）5匹风管机变频冷暖中静压40m�0�5以上380VFBQ305BA标准型包安装', '0', '1', '26560', '1588', '29899', '0');
INSERT INTO `t_goods` VALUES ('43', '4', '格力（GREE）移动空调大1.5P 京东自营配送 即插即用 KY-36N', '0', '1', '2999', '5997', '3999', '0');
INSERT INTO `t_goods` VALUES ('44', '4', '美的（Midea）移动空调1.5P 家用厨房一体机冷暖免安装便捷式空调 京东自营 KYR-35/N1Y-PD', '0', '1', '2780', '1483', '3199', '0');
INSERT INTO `t_goods` VALUES ('45', '4', '登比（DENBIG）移动空调 冷暖1.5P 免打孔安装 带独立除湿功能一体机 A018-12KRH/A', '0', '1', '2099', '2135', '2698', '0');
INSERT INTO `t_goods` VALUES ('46', '5', '桑乐(SANGLE)太阳能热水器家用 全自动电热水器 美丽家园 光电两用 包送货 免费安装 20管_170L【4-5人】', '0', '1', '3599', '8486', '3899', '0');
INSERT INTO `t_goods` VALUES ('47', '5', '申科（Shenke）太阳能热水器 太阳能热水器家用 电热水器 彩钢型热水器SK-CGX 20管彩钢款主机+豪华配件套餐', '0', '1', '1663', '5447', '1796', '0');
INSERT INTO `t_goods` VALUES ('48', '5', '四季沐歌（MICOE）全自动家用 配电加热 智能仪表 包送货安装 太阳能热水器 升级C系列 20管_155升(3-4人)', '0', '1', '4680', '1577', '4999', '0');
INSERT INTO `t_goods` VALUES ('49', '5', '太阳雨(Sunrain) 黄金甲大水量家用全自动太阳能热水器 自动上水 18管160L 黄金甲', '0', '1', '3999', '9854', '4699', '0');
INSERT INTO `t_goods` VALUES ('50', '5', '力诺瑞特 阳台壁挂式太阳能热水器家用100升 一级能效光电两用新型太阳能热水器立式平板集热', '0', '1', '5580', '1777', '5999', '0');
INSERT INTO `t_goods` VALUES ('51', '5', '奥克斯(AUX)纤薄储水式电热水器 双内胆扁桶40/50/60/80升速热节能双管速热家用 60升/内胆采用美国原装福禄粉/内胆保8年 不包安装', '0', '1', '1380', '5634', '1599', '0');

-- ----------------------------
-- Table structure for t_kind
-- ----------------------------
DROP TABLE IF EXISTS `t_kind`;
CREATE TABLE `t_kind` (
  `kind_id` int(11) NOT NULL,
  `kind_name` varchar(255) NOT NULL,
  KEY `kind_id` (`kind_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_kind
-- ----------------------------
INSERT INTO `t_kind` VALUES ('1', '电视');
INSERT INTO `t_kind` VALUES ('2', '冰箱');
INSERT INTO `t_kind` VALUES ('3', '洗衣机');
INSERT INTO `t_kind` VALUES ('4', '空调');
INSERT INTO `t_kind` VALUES ('5', '热水器');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_status` varchar(255) NOT NULL,
  `good_num` varchar(255) NOT NULL,
  `good_id` varchar(255) NOT NULL,
  `detail_id` varchar(255) NOT NULL,
  `addr` varchar(255) NOT NULL,
  `add_name` varchar(255) DEFAULT NULL,
  `add_tel` varchar(255) DEFAULT NULL,
  `time_submit` bigint(20) NOT NULL,
  `time_pay` bigint(20) DEFAULT NULL,
  `time_cancel` bigint(20) DEFAULT NULL,
  `time_ship` bigint(20) DEFAULT NULL,
  `time_return` bigint(20) DEFAULT NULL,
  `time_finish` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `good_id` (`good_id`),
  KEY `detail_id` (`detail_id`),
  KEY `add_id` (`add_tel`),
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=428 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('3', '1', 'finish', '1;', '46;', '-1;', '份额鹅鹅鹅', '动物', '12121212', '1503213229100', '1503286969871', null, '1503327700456', null, '1503327708863');
INSERT INTO `t_order` VALUES ('404', '1', 'finish', '2;', '19;', '-1;', '我的文档无多无无', '我弟弟', '4564684464', '1503232165437', '1503233605975', null, '1503233615975', '1503233625975', '1503234625975');
INSERT INTO `t_order` VALUES ('405', '1', 'cancel', '100;', '2;', '-1;', '漂漂亮亮绿绿绿绿', '陪我看', '15987', '1503234243245', '1503284102664', '1503295117482', null, null, null);
INSERT INTO `t_order` VALUES ('406', '1', 'finish', '8;', '21;', '-1;', '亏几句话', '驴妈妈', '159', '1503235726267', '1503236194201', null, '1503327722079', '1503327727446', '1503327735081');
INSERT INTO `t_order` VALUES ('407', '1', 'finish', '5;', '18;', '-1;', '砰砰砰了吗', '卢旺达', '156168', '1503236236196', '1503237618682', null, '1503237698682', '1503237722709', '1503327718373');
INSERT INTO `t_order` VALUES ('408', '1', 'pay', '100;', '8;', '-1;', '啦啦啦啦绿绿绿木木木木木木木木木木木', '礼品卡', '158', '1503282927734', '1503282938319', null, null, null, null);
INSERT INTO `t_order` VALUES ('409', '1', 'finish', '1;', '21;', '-1;', '秘密密码木木木木木木木木木木木多多多多多多多多多多', '啦啦啦啦', '123456789', '1503295598097', '1503295604192', null, '1503299604192', null, '1504365251551');
INSERT INTO `t_order` VALUES ('410', '1', 'return', '1;', '1;', '3;', '名称莫闹女偶in围殴no', '皮卡开始', '555-555-5474', '1503300981111', '1504364565092', null, '1504683960232', '1504684003774', null);
INSERT INTO `t_order` VALUES ('411', '1', 'pay', '1;1;', '6;28;', '-1;-1;', '疲劳疲劳爬起来楼盘抢礼物', '快快快', '1564684', '1503305272143', '1503319630364', null, null, null, null);
INSERT INTO `t_order` VALUES ('412', '1', 'pay', '1;1;', '6;28;', '-1;-1;', '疲劳疲劳爬起来楼盘抢礼物', '快快快', '1564684', '1503305376467', '1504535264216', null, null, null, null);
INSERT INTO `t_order` VALUES ('413', '1', 'cancel', '1;1;', '28;50;', '-1;-1;', '逗我玩无无无无无无无无无无无无无无无无无无无无无', '我的文档', '5555555', '1503320346084', '1503320351746', '1503320356360', null, null, null);
INSERT INTO `t_order` VALUES ('414', '3', 'pay', '88;', '50;', '-1;', 'wdwdkopkcwew', 'haha', '45687s', '1503389383892', '1503389390790', null, null, null, null);
INSERT INTO `t_order` VALUES ('415', '1', 'cancel', '1;1;', '1;9;', '1;-1;', '捱三顶五无无无若过', '阿萨德', '15687', '1503559328319', null, '1503560563081', null, null, null);
INSERT INTO `t_order` VALUES ('416', '1', 'cancel', '1;1;', '1;9;', '1;-1;', '捱三顶五无无无若过', '阿萨德', '15687', '1503559573687', null, '1503560380117', null, null, null);
INSERT INTO `t_order` VALUES ('417', '1', 'submit', '19;19;', '2;49;', '-1;-1;', 'asdasd', 'haha', '159', '1504277969806', null, null, null, null, null);
INSERT INTO `t_order` VALUES ('418', '1', 'ship', '2;', '2;', '-1;', '3', '1', '2', '1504330033485', '1504687288416', null, '1504687314496', null, null);
INSERT INTO `t_order` VALUES ('419', '1', 'submit', '18;', '2;', '-1;', '123456', 'hahah', '1', '1504535302309', null, null, null, null, null);
INSERT INTO `t_order` VALUES ('420', '1', 'pay', '18;', '2;', '-1;', '123456', 'hahah', '1', '1504535307289', '1504683916622', null, null, null, null);
INSERT INTO `t_order` VALUES ('421', '1', 'submit', '1;', '49;', '-1;', 'aaa', 'kkkkk', '159', '1504594761426', null, null, null, null, null);
INSERT INTO `t_order` VALUES ('422', '1', 'cancel', '5;', '2;', '-1;', '我是地址我是地址', '哈哈', '15942311234', '1504599098150', '1504599106482', '1504599117012', null, null, null);
INSERT INTO `t_order` VALUES ('423', '1', 'submit', '6;', '2;', '-1;', 'China', 'MyName', '15012341234', '1504683786365', null, null, null, null, null);

-- ----------------------------
-- Table structure for t_shoppingcar
-- ----------------------------
DROP TABLE IF EXISTS `t_shoppingcar`;
CREATE TABLE `t_shoppingcar` (
  `user_id` int(11) NOT NULL,
  `good_id` int(11) NOT NULL,
  `detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shoppingcar
-- ----------------------------
INSERT INTO `t_shoppingcar` VALUES ('1', '8', null);
INSERT INTO `t_shoppingcar` VALUES ('1', '49', null);
INSERT INTO `t_shoppingcar` VALUES ('3', '2', null);
INSERT INTO `t_shoppingcar` VALUES ('3', '5', null);
INSERT INTO `t_shoppingcar` VALUES ('3', '40', null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_pw` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_kind` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '123', 'user1', '1');
INSERT INTO `t_user` VALUES ('2', '123', 'root', '0');
INSERT INTO `t_user` VALUES ('3', '123', 'user2', '1');
INSERT INTO `t_user` VALUES ('4', '123456', 'user3', '1');
INSERT INTO `t_user` VALUES ('5', '1', '123', '1');
INSERT INTO `t_user` VALUES ('6', '123', 'user4', '1');
INSERT INTO `t_user` VALUES ('7', '123', 'user5', '1');
