/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : oj

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2022-03-13 21:08:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_exam
-- ----------------------------
DROP TABLE IF EXISTS `sys_exam`;
CREATE TABLE `sys_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `radio` varchar(255) DEFAULT NULL,
  `radio_value` int(11) DEFAULT NULL,
  `judge` varchar(255) DEFAULT NULL,
  `judge_value` int(11) DEFAULT NULL,
  `fill` varchar(255) DEFAULT NULL,
  `fill_value` int(11) DEFAULT NULL,
  `programme` varchar(255) DEFAULT NULL,
  `programme_value` varchar(255) DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_username` varchar(255) NOT NULL,
  `participate` varchar(255) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_exam
-- ----------------------------
INSERT INTO `sys_exam` VALUES ('1000', 'id测试', '', '1', '6;', '3', '', '1', '', '', '2022-03-09 00:00:00', '2022-03-31 00:00:00', '2022-03-04 17:16:13', 'admin', '管理员', '1');
INSERT INTO `sys_exam` VALUES ('1001', 'id测试2', '', '1', '6;', '1', '', '1', '', '', '2022-03-12 00:00:00', '2022-03-12 03:00:00', '2022-03-04 17:16:42', 'admin', '管理员', '1');
INSERT INTO `sys_exam` VALUES ('1003', '测试科目', '3;11;13;22;', '3', '', '1', '', '1', '', '', '2022-03-10 11:00:00', '2022-03-11 00:00:00', '2022-03-06 15:55:23', 'admin', '普通用户', '1');
INSERT INTO `sys_exam` VALUES ('1004', 'test2', '3;5;10;11;13;20;22;28;30;', '1', '', '1', '5;', '1', '', '', '2022-03-08 00:00:00', '2022-03-11 00:00:00', '2022-03-08 12:47:51', 'admin', '管理员', '0');
INSERT INTO `sys_exam` VALUES ('1006', '成绩分析测试考试', '3;5;10;', '5', '1;3;4;5;6;7;', '5', '1;3;4;5;7;8;9;', '5', '', '', '2022-03-10 00:00:00', '2022-03-11 00:00:00', '2022-03-10 17:24:01', 'admin', '普通用户', '1');
INSERT INTO `sys_exam` VALUES ('1007', '测试科目', '3;5;10;11;13;20;22;28;30;', '1', '', '1', '', '1', '', '', '2022-03-13 14:06:00', '2022-03-14 00:00:00', '2022-03-13 19:26:27', 'admin', '管理员', '1');

-- ----------------------------
-- Table structure for sys_fill
-- ----------------------------
DROP TABLE IF EXISTS `sys_fill`;
CREATE TABLE `sys_fill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `sure` varchar(255) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_fill
-- ----------------------------
INSERT INTO `sys_fill` VALUES ('1', '若有定义int m=5,y=2，则执行表达式y+=y-=m*=y后，y的值为【】', '16', '知乎', '2022-02-28 15:46:26');
INSERT INTO `sys_fill` VALUES ('3', '数据元素之间  【】  的整体称为逻辑结构', '逻辑关系', null, '2022-02-28 15:46:26');
INSERT INTO `sys_fill` VALUES ('4', '软件测试中路径覆盖测试是整个测试的基础，它是对软件  【】  进行测试', '结构', '百度文库', '2022-02-28 15:46:26');
INSERT INTO `sys_fill` VALUES ('5', '注释说明了程序的功能，它分为  【】  注释和功能性注释', '序言性', '自编', '2022-02-28 15:47:07');
INSERT INTO `sys_fill` VALUES ('6', '若有定义int m=5,y=2，则执行表达式y+=y-=m*=y后，y的值为', '16', '知乎', '2022-03-02 13:37:11');
INSERT INTO `sys_fill` VALUES ('7', '若x和y都是double型变量，]ix的初值为3．0，y的初值为2．0，则表达式pow(y，fabs(x))的值为  【】', '8', null, '2022-03-02 13:37:11');
INSERT INTO `sys_fill` VALUES ('8', '数据元素之间  【】  的整体称为逻辑结构', '逻辑关系', null, '2022-03-02 13:37:11');
INSERT INTO `sys_fill` VALUES ('9', '软件测试中路径覆盖测试是整个测试的基础，它是对软件  【】  进行测试', '结构', '百度文库', '2022-03-02 13:37:11');

-- ----------------------------
-- Table structure for sys_judge
-- ----------------------------
DROP TABLE IF EXISTS `sys_judge`;
CREATE TABLE `sys_judge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `sure` varchar(5) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_judge
-- ----------------------------
INSERT INTO `sys_judge` VALUES ('1', '逻辑结构反映数据元素之间的逻辑关系，线性结构表示数据元素之间一对一的关系，非线性结构表示数据元素之间一对多或多对一的关系', '1', '职友集', '2022-02-28 15:12:53');
INSERT INTO `sys_judge` VALUES ('2', '能将高级语言程序转换成目标语言程序的是编译程序', '1', '职友集', '2022-02-28 15:12:53');
INSERT INTO `sys_judge` VALUES ('3', '宏不存在类型问题，宏名无类型，它的参数也无类型', '1', '未知来源', '2022-02-28 15:12:53');
INSERT INTO `sys_judge` VALUES ('4', '宏替换只不过是字符替代而已', '1', '百度文库', '2022-02-28 15:12:53');
INSERT INTO `sys_judge` VALUES ('5', '在数据结构中，从逻辑上可以把数据结构分为线性结构和非线性结构', '1', 'test', '2022-02-28 15:25:50');
INSERT INTO `sys_judge` VALUES ('6', '栈是一种后进先出表，选项c中，先出栈的是3，说明此时栈内必然有1，2，由于l先于2进栈，所以l不可能在2之前出栈，故选项C这种出栈序列是不可能的', '1', '', '2022-02-28 15:28:55');
INSERT INTO `sys_judge` VALUES ('7', '在软件开发过程中，软件结构设计是描述软件结构测试', '0', '职友集', '2022-02-28 15:29:28');

-- ----------------------------
-- Table structure for sys_programme
-- ----------------------------
DROP TABLE IF EXISTS `sys_programme`;
CREATE TABLE `sys_programme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `sure` varchar(255) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_programme
-- ----------------------------
INSERT INTO `sys_programme` VALUES ('1', 'c语言的特点以及C语言与Python的比较？', 'C语言与Python的比较：\n（1）首先Python的使用要更加简单灵活，要实现相同的功能，Python的代码量通常只有C语言的30%\n（2）C语言是面向过程的语言，很多时候需要自己手动实现函数来完成一些功能，Python中引入了类和对象，是面向对象编程的语言。\n（3）Python拥有许多优秀的第三方库，在编程的时候更加的简单。\n（4）c语言的数据类型丰富，Python的数据类型只有数字、字符串、列表、元组、集合、字典6种，不需要事先定义变量类型，使用更加简便。', '知乎', '2022-02-28 16:53:42');
INSERT INTO `sys_programme` VALUES ('2', 'c语言为什么要规定，对所有使用的变量要“先定义，后使用”？', '凡是未被事先定义的变量，在使用过程中不会被当作变量名，这样能够保证程序中的变量名使用正确。每一个变量被指定一个确定的数据类型，在编译时就能为其分配相应的存储单元。指定一个变量属于一个类型，在编译的时候就能检查该变量是否使用正确。通俗来讲，给变量一个存储位置，方便操作。', null, '2022-02-28 16:53:42');
INSERT INTO `sys_programme` VALUES ('3', '关键字和一般标识符有什么不同？', '标识符用来标识源程序中的某个对象的名字，一个标识符由字母、数字和下划线组成。关键字可以定义变量、表达式语句功能和对一些文件进行预处理，关键字已被编译系统本身使用，所以用户编写程序时不能够使用这些关键字来作为标识符，如int，if，for等。', 'csdn', '2022-02-28 16:54:51');

-- ----------------------------
-- Table structure for sys_radio
-- ----------------------------
DROP TABLE IF EXISTS `sys_radio`;
CREATE TABLE `sys_radio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `plan_a` varchar(255) NOT NULL,
  `plan_b` varchar(255) NOT NULL,
  `plan_c` varchar(255) NOT NULL,
  `plan_d` varchar(255) NOT NULL,
  `sure` varchar(5) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_radio
-- ----------------------------
INSERT INTO `sys_radio` VALUES ('3', 'C语言程序从( )开始执行。', '程序中第一条可执行语句', '程序中第一个函数', '程序中的main函数 ', '包含文件中的第一个函数', 'c', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('4', '若int x=2,y=3,z=4 则表达式x<z?y:z的结果是( ).', '4', '3', '2', '0', 'b', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('5', '下列四组选项中，正确的C语言标识符是（ ）。', '%x', 'a+b ', 'a123', '123', 'c', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('6', '如果int a=3,b=4；则条件表达式\"a<b? a:b\"的值是( )。', '3', '4', '0', '1', 'a', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('7', '以下能对一维数组a进行初始化的语句是: ( )。', 'int a[5]=(0,1,2,3,4,) ', 'int a(5)={}', 'int a[3]={0,1,2} ', 'int a{5}={10*1}', 'c', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('8', '已知：int a[10]; 则对a数组元素的正确引用是（ ）。', 'a[10] ', 'a[3.5] ', 'a(5)', 'a[0]', 'd', null, '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('10', '已知char x[]=\"hello\", y[]={\'h\',\'e\',\'a\',\'b\',\'e\'};, 则关于两个数组长度的正确描述是( )', '相同', 'x大于y', 'x小于y', '以上答案都不对', 'b', 'test', '2022-02-25 14:35:15');
INSERT INTO `sys_radio` VALUES ('11', 'C语言程序从( )开始执行', '程序中第一条可执行语句', '程序中第一个函数', '程序中的main函数 ', '包含文件中的第一个函数', 'c', '知乎', '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('12', '若int x=2,y=3,z=4 则表达式x<z?y:z的结果是( ).', '4', '3', '2', '0', 'b', null, '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('13', '下列四组选项中，正确的C语言标识符是（ ）。', '%x', 'a+b ', 'a123', '123', 'c', null, '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('14', '如果int a=3,b=4；则条件表达式\"a<b? a:b\"的值是( )。', '3', '4', '0', '1', 'a', null, '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('15', '以下能对一维数组a进行初始化的语句是: ( )。', 'int a[5]=(0,1,2,3,4,) ', 'int a(5)={}', 'int a[3]={0,1,2} ', 'int a{5}={10*1}', 'c', null, '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('16', '已知：int a[10]; 则对a数组元素的正确引用是（ ）。', 'a[10] ', 'a[3.5] ', 'a(5)', 'a[0]', 'd', null, '2022-02-25 14:39:08');
INSERT INTO `sys_radio` VALUES ('19', 'etst', 'a', 'b', 'c', 'd', 'A', '', '2022-03-03 19:56:44');
INSERT INTO `sys_radio` VALUES ('20', 'C语言程序从( )开始执行。', '程序中第一条可执行语句', '程序中第一个函数', '程序中的main函数 ', '包含文件中的第一个函数', 'c', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('21', '若int x=2,y=3,z=4 则表达式x<z?y:z的结果是( ).', '4', '3', '2', '0', 'b', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('22', '下列四组选项中，正确的C语言标识符是（ ）。', '%x', 'a+b ', 'a123', '123', 'c', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('23', '如果int a=3,b=4；则条件表达式\"a<b? a:b\"的值是( )。', '3', '4', '0', '1', 'a', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('24', '以下能对一维数组a进行初始化的语句是: ( )。', 'int a[5]=(0,1,2,3,4,) ', 'int a(5)={}', 'int a[3]={0,1,2} ', 'int a{5}={10*1}', 'c', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('25', '已知：int a[10]; 则对a数组元素的正确引用是（ ）。', 'a[10] ', 'a[3.5] ', 'a(5)', 'a[0]', 'd', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('26', '若有以下数组说明，则i=10;a[a[i]]元素数值是（ 　）。int a[12]={1,4,7,10,2,5,8,11,3,6,9,12};', '10', '9', '6', '5', 'b', null, '2022-03-06 15:54:40');
INSERT INTO `sys_radio` VALUES ('28', 'C语言程序从( )开始执行。', '程序中第一条可执行语句', '程序中第一个函数', '程序中的main函数 ', '包含文件中的第一个函数', 'c', null, '2022-03-06 21:48:32');
INSERT INTO `sys_radio` VALUES ('29', '若int x=2,y=3,z=4 则表达式x<z?y:z的结果是( ).', '4', '3', '2', '0', 'b', null, '2022-03-06 21:48:32');
INSERT INTO `sys_radio` VALUES ('30', '下列四组选项中，正确的C语言标识符是（ ）。', '%x', 'a+b ', 'a123', '123', 'c', null, '2022-03-06 21:48:32');
INSERT INTO `sys_radio` VALUES ('31', '如果int a=3,b=4；则条件表达式\"a<b? a:b\"的值是( )。', '3', '4', '0', '1', 'a', null, '2022-03-06 21:48:32');
INSERT INTO `sys_radio` VALUES ('32', '以下能对一维数组a进行初始化的语句是: ( )。', 'int a[5]=(0,1,2,3,4,) ', 'int a(5)={}', 'int a[3]={0,1,2} ', 'int a{5}={10*1}', 'c', null, '2022-03-06 21:48:32');
INSERT INTO `sys_radio` VALUES ('33', '已知：int a[10]; 则对a数组元素的正确引用是（ ）。', 'a[10] ', 'a[3.5] ', 'a(5)', 'a[0]', 'd', null, '2022-03-06 21:48:32');

-- ----------------------------
-- Table structure for sys_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_right`;
CREATE TABLE `sys_right` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `parent` varchar(20) DEFAULT NULL,
  `path` varchar(20) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_right
-- ----------------------------
INSERT INTO `sys_right` VALUES ('10000', '系统管理', null, '10000', 'el-icon-menu', null, null);
INSERT INTO `sys_right` VALUES ('10001', '用户管理', '10000', 'user', 'el-icon-user-solid', null, null);
INSERT INTO `sys_right` VALUES ('10101', '添加用户', '10001', null, null, '/user', 'POST');
INSERT INTO `sys_right` VALUES ('10102', '查询用户信息', '10001', null, null, '/user/page', 'GET');
INSERT INTO `sys_right` VALUES ('10103', '更新用户信息', '10001', null, null, '/user', 'PUT');
INSERT INTO `sys_right` VALUES ('10104', '删除用户', '10001', null, null, '/user/delete', 'DELETE');
INSERT INTO `sys_right` VALUES ('10105', '批量导入用户信息', '10001', null, null, '/user/import', 'POST');
INSERT INTO `sys_right` VALUES ('10106', '批量导出用户信息', '10001', null, null, '/user/export', 'GET');
INSERT INTO `sys_right` VALUES ('10107', '批量删除用户信息', '10001', null, null, '/user/batchDelete', 'DELETE');
INSERT INTO `sys_right` VALUES ('10108', '修改用户登录状态', '10001', null, null, '/user/state', 'PATCH');
INSERT INTO `sys_right` VALUES ('10109', '分配角色', '10001', null, null, '/user/allocationByUsername', 'PATCH');
INSERT INTO `sys_right` VALUES ('20000', '权限管理', null, '20000', 'el-icon-s-check', null, null);
INSERT INTO `sys_right` VALUES ('20001', '角色列表', '20000', 'roles', 'el-icon-share', null, null);
INSERT INTO `sys_right` VALUES ('20002', '权限列表', '20000', 'rights', 'el-icon-s-tools', null, null);
INSERT INTO `sys_right` VALUES ('20101', '给分配角色权限', '20001', null, null, '/role/setRoleRight', 'POST');
INSERT INTO `sys_right` VALUES ('20102', '删除角色以及所有权限', '20001', null, null, '/role/deleteRole', 'DELETE');
INSERT INTO `sys_right` VALUES ('20103', '修改角色信息', '20001', null, null, '/role/updateRole', 'PUT');
INSERT INTO `sys_right` VALUES ('20104', '添加角色', '20001', null, null, '/role/updateRole', 'POST');
INSERT INTO `sys_right` VALUES ('20105', '获取所有角色以及权限信息', '20001', null, null, '/role/roleRights', 'GET');
INSERT INTO `sys_right` VALUES ('20106', '删除单个权限', '20001', null, null, '/role/role', 'DELETE');
INSERT INTO `sys_right` VALUES ('20201', '更改ICON', '20002', null, null, '/right/icon', 'PATCH');
INSERT INTO `sys_right` VALUES ('20202', '修改权限信息', '20002', null, null, '/right/updateUM', 'PATCH');
INSERT INTO `sys_right` VALUES ('20203', '获取权限信息（影响角色列表权限分配）', '20002', null, null, '/right/rights', 'POST');
INSERT INTO `sys_right` VALUES ('30000', '题库管理', null, '30000', 'el-icon-question', null, null);
INSERT INTO `sys_right` VALUES ('30001', '单选题题库', '30000', 'radio', 'el-icon-s-opportunity', null, null);
INSERT INTO `sys_right` VALUES ('30002', '判断题题库', '30000', 'judge', 'el-icon-folder-checked', null, null);
INSERT INTO `sys_right` VALUES ('30003', '填空题题库', '30000', 'fill', 'el-icon-edit-outline', null, null);
INSERT INTO `sys_right` VALUES ('30004', '简答题题库', '30000', 'programme', 'el-icon-document', null, null);
INSERT INTO `sys_right` VALUES ('30101', '查询题目', '30001', null, null, '/radio/page', 'GET');
INSERT INTO `sys_right` VALUES ('30102', '添加题目', '30001', null, null, '/radio/add', 'POST');
INSERT INTO `sys_right` VALUES ('30103', '批量导入题目', '30001', null, null, '/radio/import', 'POST');
INSERT INTO `sys_right` VALUES ('30104', '编辑题目', '30001', null, null, '/radio/add', 'PUT');
INSERT INTO `sys_right` VALUES ('30105', '删除题目', '30001', null, null, '/radio/deleteById', 'DELETE');
INSERT INTO `sys_right` VALUES ('30201', '查询题目', '30002', null, null, '/judge/page', 'GET');
INSERT INTO `sys_right` VALUES ('30202', '添加题目', '30002', null, null, '/judge/add', 'POST');
INSERT INTO `sys_right` VALUES ('30203', '批量导入题目', '30002', null, null, '/judge/import', 'POST');
INSERT INTO `sys_right` VALUES ('30204', '编辑题目', '30002', null, null, '/judge/add', 'PUT');
INSERT INTO `sys_right` VALUES ('30205', '删除题目', '30002', null, null, '/judge/deleteById', 'DELETE');
INSERT INTO `sys_right` VALUES ('30301', '查询题目', '30003', null, null, '/fill/page', 'GET');
INSERT INTO `sys_right` VALUES ('30302', '添加题目', '30003', null, null, '/fill/add', 'POST');
INSERT INTO `sys_right` VALUES ('30303', '批量导入题目', '30003', null, null, '/fill/import', 'POST');
INSERT INTO `sys_right` VALUES ('30304', '编辑题目', '30003', null, null, '/fill/add', 'PUT');
INSERT INTO `sys_right` VALUES ('30305', '删除题目', '30003', null, null, '/fill/deleteById', 'DELETE');
INSERT INTO `sys_right` VALUES ('30401', '查询题目', '30004', null, null, '/programme/page', 'GET');
INSERT INTO `sys_right` VALUES ('30402', '添加题目', '30004', null, null, '/programme/add', 'POST');
INSERT INTO `sys_right` VALUES ('30403', '批量导入题目', '30004', null, null, '/programme/import', 'POST');
INSERT INTO `sys_right` VALUES ('30404', '编辑题目', '30004', null, null, '/programme/add', 'PUT');
INSERT INTO `sys_right` VALUES ('30405', '删除题目', '30004', null, null, '/programme/deleteById', 'DELETE');
INSERT INTO `sys_right` VALUES ('40000', '考试管理', null, '40000', 'el-icon-s-order', null, null);
INSERT INTO `sys_right` VALUES ('40001', '创建试卷', '40000', 'exam', 'el-icon-notebook-2', null, null);
INSERT INTO `sys_right` VALUES ('40002', '试卷管理', '40000', 'exams', 'el-icon-document-copy', null, null);
INSERT INTO `sys_right` VALUES ('40003', '成绩统计', '40000', 'statistics', 'el-icon-s-data', null, null);
INSERT INTO `sys_right` VALUES ('40101', '试卷创建完成', '40001', null, null, '/exam/save', 'POST');
INSERT INTO `sys_right` VALUES ('40102', '查询选择题', '40001', null, null, '/radio/queryByLike', 'POST');
INSERT INTO `sys_right` VALUES ('40103', '查询填空题', '40001', '', '', '/fill/queryByLike', 'POST');
INSERT INTO `sys_right` VALUES ('40104', '查询判断题', '40001', '', '', '/judge/queryByLike', 'POST');
INSERT INTO `sys_right` VALUES ('40105', '查询简答题', '40001', '', '', '/programme/queryByLike', 'POST');
INSERT INTO `sys_right` VALUES ('40201', '查询考试信息', '40002', null, null, '/exam/getExams', 'GET');
INSERT INTO `sys_right` VALUES ('40202', '编辑试卷', '40002', null, null, '/exam/editExams', 'POST');
INSERT INTO `sys_right` VALUES ('40203', '删除试卷', '40002', null, null, '/exam/deleteById', 'DELETE');
INSERT INTO `sys_right` VALUES ('40204', '试卷是否发布', '40002', null, null, '/exam/examState', 'PATCH');
INSERT INTO `sys_right` VALUES ('40301', '成绩分析', '40003', null, null, '/exam/getUserList', 'POST');
INSERT INTO `sys_right` VALUES ('50000', '考试中心', '', '50000', 'el-icon-s-help', null, null);
INSERT INTO `sys_right` VALUES ('50001', '我的考试', '50000', 'contest', 'el-icon-trophy', '', '');
INSERT INTO `sys_right` VALUES ('50101', '获取考试信息', '50001', null, null, '', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `name` varchar(10) NOT NULL,
  `description` varchar(15) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('普通用户', '普通用户', '2');
INSERT INTO `sys_role` VALUES ('管理员', '拥有当前已开发完成功能的权限', '3');
INSERT INTO `sys_role` VALUES ('测试角色', '测试1131', '4');
INSERT INTO `sys_role` VALUES ('测试角色3', '测试212', '6');
INSERT INTO `sys_role` VALUES ('学生', '只能考试', '7');
INSERT INTO `sys_role` VALUES ('test', 'test', '9');
INSERT INTO `sys_role` VALUES ('etst2', '121', '10');
INSERT INTO `sys_role` VALUES ('testwad', 'dad', '12');
INSERT INTO `sys_role` VALUES ('教师', '教师', '14');
INSERT INTO `sys_role` VALUES ('超级管理员', '拥有所有权限', '15');

-- ----------------------------
-- Table structure for sys_role_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_right`;
CREATE TABLE `sys_role_right` (
  `role_id` int(11) NOT NULL,
  `right_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_right
-- ----------------------------
INSERT INTO `sys_role_right` VALUES ('2', '50000');
INSERT INTO `sys_role_right` VALUES ('2', '50001');
INSERT INTO `sys_role_right` VALUES ('2', '50101');
INSERT INTO `sys_role_right` VALUES ('3', '10000');
INSERT INTO `sys_role_right` VALUES ('3', '10001');
INSERT INTO `sys_role_right` VALUES ('3', '10101');
INSERT INTO `sys_role_right` VALUES ('3', '10102');
INSERT INTO `sys_role_right` VALUES ('3', '10103');
INSERT INTO `sys_role_right` VALUES ('3', '10104');
INSERT INTO `sys_role_right` VALUES ('3', '10105');
INSERT INTO `sys_role_right` VALUES ('3', '10106');
INSERT INTO `sys_role_right` VALUES ('3', '10107');
INSERT INTO `sys_role_right` VALUES ('3', '10108');
INSERT INTO `sys_role_right` VALUES ('3', '10109');
INSERT INTO `sys_role_right` VALUES ('3', '20000');
INSERT INTO `sys_role_right` VALUES ('3', '20001');
INSERT INTO `sys_role_right` VALUES ('3', '20002');
INSERT INTO `sys_role_right` VALUES ('3', '20101');
INSERT INTO `sys_role_right` VALUES ('3', '20102');
INSERT INTO `sys_role_right` VALUES ('3', '20103');
INSERT INTO `sys_role_right` VALUES ('3', '20104');
INSERT INTO `sys_role_right` VALUES ('3', '20105');
INSERT INTO `sys_role_right` VALUES ('3', '20106');
INSERT INTO `sys_role_right` VALUES ('3', '20201');
INSERT INTO `sys_role_right` VALUES ('3', '20202');
INSERT INTO `sys_role_right` VALUES ('3', '20203');
INSERT INTO `sys_role_right` VALUES ('3', '30000');
INSERT INTO `sys_role_right` VALUES ('3', '30001');
INSERT INTO `sys_role_right` VALUES ('3', '30002');
INSERT INTO `sys_role_right` VALUES ('3', '30003');
INSERT INTO `sys_role_right` VALUES ('3', '30101');
INSERT INTO `sys_role_right` VALUES ('3', '30102');
INSERT INTO `sys_role_right` VALUES ('3', '30103');
INSERT INTO `sys_role_right` VALUES ('3', '30104');
INSERT INTO `sys_role_right` VALUES ('3', '30105');
INSERT INTO `sys_role_right` VALUES ('3', '30201');
INSERT INTO `sys_role_right` VALUES ('3', '30202');
INSERT INTO `sys_role_right` VALUES ('3', '30203');
INSERT INTO `sys_role_right` VALUES ('3', '30204');
INSERT INTO `sys_role_right` VALUES ('3', '30205');
INSERT INTO `sys_role_right` VALUES ('3', '30301');
INSERT INTO `sys_role_right` VALUES ('3', '30302');
INSERT INTO `sys_role_right` VALUES ('3', '30303');
INSERT INTO `sys_role_right` VALUES ('3', '30304');
INSERT INTO `sys_role_right` VALUES ('3', '30305');
INSERT INTO `sys_role_right` VALUES ('3', '40000');
INSERT INTO `sys_role_right` VALUES ('3', '40001');
INSERT INTO `sys_role_right` VALUES ('3', '40002');
INSERT INTO `sys_role_right` VALUES ('3', '40003');
INSERT INTO `sys_role_right` VALUES ('3', '40101');
INSERT INTO `sys_role_right` VALUES ('3', '40102');
INSERT INTO `sys_role_right` VALUES ('3', '40103');
INSERT INTO `sys_role_right` VALUES ('3', '40104');
INSERT INTO `sys_role_right` VALUES ('3', '40201');
INSERT INTO `sys_role_right` VALUES ('3', '40202');
INSERT INTO `sys_role_right` VALUES ('3', '40203');
INSERT INTO `sys_role_right` VALUES ('3', '40204');
INSERT INTO `sys_role_right` VALUES ('3', '40301');
INSERT INTO `sys_role_right` VALUES ('3', '40304');
INSERT INTO `sys_role_right` VALUES ('3', '50000');
INSERT INTO `sys_role_right` VALUES ('3', '50001');
INSERT INTO `sys_role_right` VALUES ('3', '50101');
INSERT INTO `sys_role_right` VALUES ('6', '10000');
INSERT INTO `sys_role_right` VALUES ('6', '10001');
INSERT INTO `sys_role_right` VALUES ('6', '10101');
INSERT INTO `sys_role_right` VALUES ('6', '20000');
INSERT INTO `sys_role_right` VALUES ('6', '20001');
INSERT INTO `sys_role_right` VALUES ('6', '20002');
INSERT INTO `sys_role_right` VALUES ('6', '20101');
INSERT INTO `sys_role_right` VALUES ('6', '20102');
INSERT INTO `sys_role_right` VALUES ('6', '20103');
INSERT INTO `sys_role_right` VALUES ('6', '20104');
INSERT INTO `sys_role_right` VALUES ('6', '20105');
INSERT INTO `sys_role_right` VALUES ('6', '20106');
INSERT INTO `sys_role_right` VALUES ('6', '20201');
INSERT INTO `sys_role_right` VALUES ('7', '10000');
INSERT INTO `sys_role_right` VALUES ('7', '10001');
INSERT INTO `sys_role_right` VALUES ('7', '10101');
INSERT INTO `sys_role_right` VALUES ('7', '20000');
INSERT INTO `sys_role_right` VALUES ('7', '20001');
INSERT INTO `sys_role_right` VALUES ('7', '20002');
INSERT INTO `sys_role_right` VALUES ('7', '20101');
INSERT INTO `sys_role_right` VALUES ('7', '20201');
INSERT INTO `sys_role_right` VALUES ('7', '30000');
INSERT INTO `sys_role_right` VALUES ('7', '30001');
INSERT INTO `sys_role_right` VALUES ('7', '30002');
INSERT INTO `sys_role_right` VALUES ('7', '30003');
INSERT INTO `sys_role_right` VALUES ('7', '30004');
INSERT INTO `sys_role_right` VALUES ('7', '30101');
INSERT INTO `sys_role_right` VALUES ('7', '30201');
INSERT INTO `sys_role_right` VALUES ('7', '30301');
INSERT INTO `sys_role_right` VALUES ('7', '30401');
INSERT INTO `sys_role_right` VALUES ('7', '40000');
INSERT INTO `sys_role_right` VALUES ('7', '40001');
INSERT INTO `sys_role_right` VALUES ('7', '40002');
INSERT INTO `sys_role_right` VALUES ('7', '40101');
INSERT INTO `sys_role_right` VALUES ('7', '40201');
INSERT INTO `sys_role_right` VALUES ('9', '10000');
INSERT INTO `sys_role_right` VALUES ('9', '10001');
INSERT INTO `sys_role_right` VALUES ('9', '10101');
INSERT INTO `sys_role_right` VALUES ('9', '10102');
INSERT INTO `sys_role_right` VALUES ('9', '10103');
INSERT INTO `sys_role_right` VALUES ('9', '10104');
INSERT INTO `sys_role_right` VALUES ('9', '10105');
INSERT INTO `sys_role_right` VALUES ('9', '10106');
INSERT INTO `sys_role_right` VALUES ('9', '10107');
INSERT INTO `sys_role_right` VALUES ('9', '10108');
INSERT INTO `sys_role_right` VALUES ('14', '30000');
INSERT INTO `sys_role_right` VALUES ('14', '30001');
INSERT INTO `sys_role_right` VALUES ('14', '30002');
INSERT INTO `sys_role_right` VALUES ('14', '30003');
INSERT INTO `sys_role_right` VALUES ('14', '30004');
INSERT INTO `sys_role_right` VALUES ('14', '30101');
INSERT INTO `sys_role_right` VALUES ('14', '30102');
INSERT INTO `sys_role_right` VALUES ('14', '30103');
INSERT INTO `sys_role_right` VALUES ('14', '30104');
INSERT INTO `sys_role_right` VALUES ('14', '30105');
INSERT INTO `sys_role_right` VALUES ('14', '30201');
INSERT INTO `sys_role_right` VALUES ('14', '30202');
INSERT INTO `sys_role_right` VALUES ('14', '30203');
INSERT INTO `sys_role_right` VALUES ('14', '30204');
INSERT INTO `sys_role_right` VALUES ('14', '30205');
INSERT INTO `sys_role_right` VALUES ('14', '30301');
INSERT INTO `sys_role_right` VALUES ('14', '30302');
INSERT INTO `sys_role_right` VALUES ('14', '30303');
INSERT INTO `sys_role_right` VALUES ('14', '30304');
INSERT INTO `sys_role_right` VALUES ('14', '30305');
INSERT INTO `sys_role_right` VALUES ('14', '30401');
INSERT INTO `sys_role_right` VALUES ('14', '30402');
INSERT INTO `sys_role_right` VALUES ('14', '30403');
INSERT INTO `sys_role_right` VALUES ('14', '30404');
INSERT INTO `sys_role_right` VALUES ('14', '30405');
INSERT INTO `sys_role_right` VALUES ('14', '40000');
INSERT INTO `sys_role_right` VALUES ('14', '40001');
INSERT INTO `sys_role_right` VALUES ('14', '40002');
INSERT INTO `sys_role_right` VALUES ('14', '40003');
INSERT INTO `sys_role_right` VALUES ('14', '40101');
INSERT INTO `sys_role_right` VALUES ('14', '40102');
INSERT INTO `sys_role_right` VALUES ('14', '40103');
INSERT INTO `sys_role_right` VALUES ('14', '40104');
INSERT INTO `sys_role_right` VALUES ('14', '40105');
INSERT INTO `sys_role_right` VALUES ('14', '40201');
INSERT INTO `sys_role_right` VALUES ('14', '40202');
INSERT INTO `sys_role_right` VALUES ('14', '40203');
INSERT INTO `sys_role_right` VALUES ('14', '40301');
INSERT INTO `sys_role_right` VALUES ('14', '50000');
INSERT INTO `sys_role_right` VALUES ('14', '50001');
INSERT INTO `sys_role_right` VALUES ('14', '50101');
INSERT INTO `sys_role_right` VALUES ('15', '10000');
INSERT INTO `sys_role_right` VALUES ('15', '10001');
INSERT INTO `sys_role_right` VALUES ('15', '10101');
INSERT INTO `sys_role_right` VALUES ('15', '10102');
INSERT INTO `sys_role_right` VALUES ('15', '10103');
INSERT INTO `sys_role_right` VALUES ('15', '10104');
INSERT INTO `sys_role_right` VALUES ('15', '10105');
INSERT INTO `sys_role_right` VALUES ('15', '10106');
INSERT INTO `sys_role_right` VALUES ('15', '10107');
INSERT INTO `sys_role_right` VALUES ('15', '10108');
INSERT INTO `sys_role_right` VALUES ('15', '10109');
INSERT INTO `sys_role_right` VALUES ('15', '20000');
INSERT INTO `sys_role_right` VALUES ('15', '20001');
INSERT INTO `sys_role_right` VALUES ('15', '20002');
INSERT INTO `sys_role_right` VALUES ('15', '20101');
INSERT INTO `sys_role_right` VALUES ('15', '20102');
INSERT INTO `sys_role_right` VALUES ('15', '20103');
INSERT INTO `sys_role_right` VALUES ('15', '20104');
INSERT INTO `sys_role_right` VALUES ('15', '20105');
INSERT INTO `sys_role_right` VALUES ('15', '20106');
INSERT INTO `sys_role_right` VALUES ('15', '20201');
INSERT INTO `sys_role_right` VALUES ('15', '20202');
INSERT INTO `sys_role_right` VALUES ('15', '20203');
INSERT INTO `sys_role_right` VALUES ('15', '30000');
INSERT INTO `sys_role_right` VALUES ('15', '30001');
INSERT INTO `sys_role_right` VALUES ('15', '30002');
INSERT INTO `sys_role_right` VALUES ('15', '30003');
INSERT INTO `sys_role_right` VALUES ('15', '30004');
INSERT INTO `sys_role_right` VALUES ('15', '30101');
INSERT INTO `sys_role_right` VALUES ('15', '30102');
INSERT INTO `sys_role_right` VALUES ('15', '30103');
INSERT INTO `sys_role_right` VALUES ('15', '30104');
INSERT INTO `sys_role_right` VALUES ('15', '30105');
INSERT INTO `sys_role_right` VALUES ('15', '30201');
INSERT INTO `sys_role_right` VALUES ('15', '30202');
INSERT INTO `sys_role_right` VALUES ('15', '30203');
INSERT INTO `sys_role_right` VALUES ('15', '30204');
INSERT INTO `sys_role_right` VALUES ('15', '30205');
INSERT INTO `sys_role_right` VALUES ('15', '30301');
INSERT INTO `sys_role_right` VALUES ('15', '30302');
INSERT INTO `sys_role_right` VALUES ('15', '30303');
INSERT INTO `sys_role_right` VALUES ('15', '30304');
INSERT INTO `sys_role_right` VALUES ('15', '30305');
INSERT INTO `sys_role_right` VALUES ('15', '30401');
INSERT INTO `sys_role_right` VALUES ('15', '30402');
INSERT INTO `sys_role_right` VALUES ('15', '30403');
INSERT INTO `sys_role_right` VALUES ('15', '30404');
INSERT INTO `sys_role_right` VALUES ('15', '30405');
INSERT INTO `sys_role_right` VALUES ('15', '40000');
INSERT INTO `sys_role_right` VALUES ('15', '40001');
INSERT INTO `sys_role_right` VALUES ('15', '40002');
INSERT INTO `sys_role_right` VALUES ('15', '40003');
INSERT INTO `sys_role_right` VALUES ('15', '40101');
INSERT INTO `sys_role_right` VALUES ('15', '40102');
INSERT INTO `sys_role_right` VALUES ('15', '40103');
INSERT INTO `sys_role_right` VALUES ('15', '40104');
INSERT INTO `sys_role_right` VALUES ('15', '40105');
INSERT INTO `sys_role_right` VALUES ('15', '40201');
INSERT INTO `sys_role_right` VALUES ('15', '40202');
INSERT INTO `sys_role_right` VALUES ('15', '40203');
INSERT INTO `sys_role_right` VALUES ('15', '40204');
INSERT INTO `sys_role_right` VALUES ('15', '40301');
INSERT INTO `sys_role_right` VALUES ('15', '50000');
INSERT INTO `sys_role_right` VALUES ('15', '50001');
INSERT INTO `sys_role_right` VALUES ('15', '50101');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `username` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `role` varchar(10) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `confirm_code` varchar(255) DEFAULT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0',
  `avatar` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('111', '111', '教师', '清风', '2577380101@qq.com', '2022-02-12 20:37:15', null, '1', 'http://localhost:9090/user/file/1bdd3d1a7baa4e0e9399041ca1850814.jpg');
INSERT INTO `sys_user` VALUES ('111111', '111', '普通用户', null, '2577380101@qq.com', '2022-03-07 21:49:57', '1500468726905049088', '1', null);
INSERT INTO `sys_user` VALUES ('222', '222', '管理员', '清风', '2577380101@qq.com', '2022-02-15 15:49:00', null, '1', 'http://localhost:9090/user/file/2671446cfb7a47baba43cf5ca7e8dcee.jpg');
INSERT INTO `sys_user` VALUES ('2223', '222', '普通用户', '清风', '2577380101@qq.com', '2022-02-16 13:20:55', null, '1', null);
INSERT INTO `sys_user` VALUES ('2224', '222', '教师', '清风', '2577380101@qq.com', '2022-02-16 13:09:18', null, '1', null);
INSERT INTO `sys_user` VALUES ('admin', 'admin', '管理员', 'admin', 'sjylearn@qq.com', '2022-02-12 16:10:12', null, '1', 'http://localhost:9090/user/file/102a26d2b63b471782ebd36e31d40638.jpg');
INSERT INTO `sys_user` VALUES ('dad1', '111', '教师', '', '2577380101@qq.com', '2022-02-13 17:16:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('dawfa', '111', '管理员', null, '2577380101@qq.com', '2022-03-11 17:06:19', '', '1', null);
INSERT INTO `sys_user` VALUES ('sjy', 'admin', '超级管理员', 'sjy', '2577380101@qq.com', '2022-02-12 16:09:18', null, '1', 'http://localhost:9090/user/file/a1c6374d598a4b42a3f3f3623a3b0e72.jpg');
INSERT INTO `sys_user` VALUES ('test', '111', '测试角色', '', '2577380101@qq.com', '2022-02-12 20:37:54', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1', 'admin', '教师', 'test', '2577380101@qq.com', '2022-02-14 11:00:06', null, '0', null);
INSERT INTO `sys_user` VALUES ('test10', '117', '普通用户', 'sjy', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1000', '111', '普通用户', null, '2577380101@qq.com', '2022-03-07 15:52:46', '', '1', 'http://localhost:9090/user/file/1a4c61aff96c43778a2dadb6015e7bac.jpg');
INSERT INTO `sys_user` VALUES ('test1001', '111', '普通用户', 'test1001', '2577380101@qq.com', '2022-03-10 18:00:27', null, '0', null);
INSERT INTO `sys_user` VALUES ('test1002', '111', '普通用户', 'test1002', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1003', '111', '普通用户', 'test1003', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1004', '111', '普通用户', 'test1004', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1005', '111', '普通用户', 'test1005', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1006', '111', '普通用户', 'test1006', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1007', '111', '普通用户', 'test1007', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1008', '111', '普通用户', 'test1008', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1009', '111', '普通用户', 'test1009', '2577380101@qq.com', '2022-03-10 18:00:27', null, '0', null);
INSERT INTO `sys_user` VALUES ('test1010', '111', '普通用户', 'test1010', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1011', '111', '普通用户', 'test1011', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1012', '111', '普通用户', 'test1012', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1013', '111', '普通用户', 'test1013', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1014', '111', '普通用户', 'test1014', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1015', '111', '普通用户', 'test1015', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1016', '111', '普通用户', 'test1016', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1017', '111', '普通用户', 'test1017', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1018', '111', '普通用户', 'test1018', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1019', '111', '普通用户', 'test1019', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1020', '111', '普通用户', 'test1020', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1021', '111', '普通用户', 'test1021', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1022', '111', '普通用户', 'test1022', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1023', '111', '普通用户', 'test1023', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1024', '111', '普通用户', 'test1024', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1025', '111', '普通用户', 'test1025', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1026', '111', '普通用户', 'test1026', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1027', '111', '普通用户', 'test1027', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1028', '111', '普通用户', 'test1028', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1029', '111', '普通用户', 'test1029', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1030', '111', '普通用户', 'test1030', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1031', '111', '普通用户', 'test1031', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1032', '111', '普通用户', 'test1032', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1033', '111', '普通用户', 'test1033', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1034', '111', '普通用户', 'test1034', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1035', '111', '普通用户', 'test1035', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1036', '111', '普通用户', 'test1036', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1037', '111', '普通用户', 'test1037', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1038', '111', '普通用户', 'test1038', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1039', '111', '普通用户', 'test1039', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1040', '111', '普通用户', 'test1040', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1041', '111', '普通用户', 'test1041', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1042', '111', '普通用户', 'test1042', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1043', '111', '普通用户', 'test1043', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1044', '111', '普通用户', 'test1044', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1045', '111', '普通用户', 'test1045', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1046', '111', '普通用户', 'test1046', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1047', '111', '普通用户', 'test1047', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1048', '111', '普通用户', 'test1048', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1049', '111', '普通用户', 'test1049', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1050', '111', '普通用户', 'test1050', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1051', '111', '普通用户', 'test1051', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1052', '111', '普通用户', 'test1052', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1053', '111', '普通用户', 'test1053', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1054', '111', '普通用户', 'test1054', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1055', '111', '普通用户', 'test1055', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1056', '111', '普通用户', 'test1056', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1057', '111', '普通用户', 'test1057', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1058', '111', '普通用户', 'test1058', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test1059', '111', '普通用户', 'test1059', '2577380101@qq.com', '2022-03-10 18:00:27', null, '1', null);
INSERT INTO `sys_user` VALUES ('test11', '118', '普通用户', '', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test12', '119', '普通用户', 'test', '2577380101@qq.com', '2022-02-25 14:47:17', null, '0', null);
INSERT INTO `sys_user` VALUES ('test13', '120', '普通用户', 'test', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test14', '111', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 20:03:11', null, '0', null);
INSERT INTO `sys_user` VALUES ('test15', '112', '测试角色', '清风', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test16', '111', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test17', '114', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test18', '115', '普通用户', 'admin', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test19', '116', '普通用户', '', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test2', 'admin', '管理员', 'test', '2577380101@qq.com', '2022-02-14 11:00:06', null, '1', null);
INSERT INTO `sys_user` VALUES ('test20', '117', '普通用户', 'sjy', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test21', '118', '普通用户', '', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test22', '119', '普通用户', 'test', '2577380101@qq.com', '2022-02-25 20:03:11', null, '0', null);
INSERT INTO `sys_user` VALUES ('test23', '120', '普通用户', 'test', '2577380101@qq.com', '2022-02-25 20:03:11', null, '1', null);
INSERT INTO `sys_user` VALUES ('test4', '111111', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test5', '112', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test6', '113', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test7', '114', '普通用户', '清风', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test8', '115', '普通用户', 'admin', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test9', '116', '普通用户', '', '2577380101@qq.com', '2022-02-25 14:47:17', null, '1', null);
INSERT INTO `sys_user` VALUES ('test92', '111111', '管理员', '测试昵称', '2577380101@qq.com', '2022-03-04 10:46:49', '', '1', null);
INSERT INTO `sys_user` VALUES ('test99', '111', '学生', 'ddwaad', '2577380101@qq.com', '2022-03-03 14:19:28', null, '1', null);
INSERT INTO `sys_user` VALUES ('testadmin', '111', '普通用户', null, '2577380101@qq.com', '2022-03-07 15:39:42', '', '1', null);

-- ----------------------------
-- Table structure for sys_user_exam
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_exam`;
CREATE TABLE `sys_user_exam` (
  `username` varchar(255) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `radio` varchar(255) DEFAULT NULL,
  `judge` varchar(255) DEFAULT NULL,
  `fill` varchar(255) DEFAULT NULL,
  `programme` varchar(255) DEFAULT NULL,
  `score` int(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_exam
-- ----------------------------
INSERT INTO `sys_user_exam` VALUES ('admin', '1004', 'D;D;C;D;D;D;C;D;C;D;', '', 'waad;', '', '2', null);
INSERT INTO `sys_user_exam` VALUES ('test1001', '1006', 'B;A;C;', '1;1;0;0;1;0;', '16;逻辑关系;不会;不会;8;逻辑关系;不会;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1002', '1006', 'B;A;A;', '1;0;0;0;0;1;', '不会;逻辑关系;不会;不会;不会;逻辑关系;不会;', null, '15', null);
INSERT INTO `sys_user_exam` VALUES ('test1003', '1006', 'B;C;B;', '0;1;0;0;1;1;', '16;不会;结构;不会;不会;不会;不会;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1004', '1006', 'B;D;A;', '0;1;1;0;1;1;', '不会;不会;结构;序言性;不会;不会;不会;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1005', '1006', 'C;D;C;', '1;0;0;1;1;1;', '16;不会;不会;不会;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1006', '1006', 'B;A;A;', '0;1;1;0;1;0;', '不会;逻辑关系;结构;序言性;8;不会;结构;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1007', '1006', 'A;B;B;', '1;0;0;1;1;0;', '不会;逻辑关系;结构;序言性;8;逻辑关系;不会;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1008', '1006', 'A;B;C;', '0;1;0;1;0;1;', '16;不会;不会;不会;8;不会;结构;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1009', '1006', 'C;A;B;', '0;0;1;1;0;0;', '不会;不会;结构;不会;8;不会;结构;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1010', '1006', 'C;C;D;', '0;1;0;1;1;1;', '不会;逻辑关系;结构;序言性;8;不会;结构;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1011', '1006', 'C;C;A;', '1;0;1;0;0;1;', '16;逻辑关系;不会;序言性;不会;逻辑关系;不会;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1012', '1006', 'D;D;B;', '0;1;0;0;0;0;', '不会;逻辑关系;结构;序言性;8;不会;结构;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1013', '1006', 'D;B;A;', '0;1;1;0;0;0;', '16;逻辑关系;不会;不会;不会;逻辑关系;不会;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1014', '1006', 'A;A;B;', '0;1;1;1;0;0;', '不会;不会;不会;不会;不会;逻辑关系;不会;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1015', '1006', 'C;B;C;', '1;0;0;0;0;1;', '16;逻辑关系;不会;序言性;不会;逻辑关系;不会;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1016', '1006', 'C;A;C;', '1;1;1;0;0;0;', '16;逻辑关系;不会;不会;8;不会;不会;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1017', '1006', 'D;B;A;', '0;0;0;0;1;0;', '不会;不会;结构;序言性;不会;不会;不会;', null, '20', null);
INSERT INTO `sys_user_exam` VALUES ('test1018', '1006', 'B;A;B;', '0;0;0;1;1;1;', '不会;不会;结构;不会;不会;不会;结构;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1019', '1006', 'D;A;D;', '1;0;1;1;1;0;', '不会;不会;结构;序言性;8;逻辑关系;结构;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1020', '1006', 'C;C;B;', '1;1;0;1;0;1;', '不会;逻辑关系;结构;不会;不会;逻辑关系;结构;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1021', '1006', 'C;B;D;', '1;1;1;0;1;0;', '不会;逻辑关系;结构;序言性;8;不会;结构;', null, '60', null);
INSERT INTO `sys_user_exam` VALUES ('test1022', '1006', 'C;B;C;', '0;1;0;1;1;1;', '不会;不会;不会;序言性;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1023', '1006', 'C;B;D;', '1;1;0;0;0;1;', '不会;不会;不会;不会;不会;逻辑关系;不会;', null, '20', null);
INSERT INTO `sys_user_exam` VALUES ('test1024', '1006', 'D;C;C;', '1;1;0;1;0;0;', '不会;不会;不会;不会;不会;逻辑关系;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1025', '1006', 'C;A;C;', '0;1;1;0;1;1;', '不会;不会;结构;不会;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1026', '1006', 'D;C;A;', '1;0;1;1;1;0;', '16;逻辑关系;不会;序言性;不会;逻辑关系;不会;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1027', '1006', 'A;A;A;', '1;1;0;0;1;0;', '不会;不会;不会;不会;不会;不会;结构;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1028', '1006', 'A;B;D;', '0;0;0;1;1;1;', '不会;逻辑关系;结构;不会;8;逻辑关系;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1029', '1006', 'B;C;B;', '0;1;0;1;0;0;', '不会;不会;结构;序言性;不会;逻辑关系;结构;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1030', '1006', 'B;A;D;', '0;0;0;1;1;0;', '不会;逻辑关系;结构;序言性;不会;不会;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1031', '1006', 'B;C;B;', '1;0;0;1;0;1;', '16;逻辑关系;结构;序言性;不会;不会;结构;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1032', '1006', 'C;A;C;', '1;1;1;0;0;1;', '16;不会;不会;序言性;不会;逻辑关系;结构;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1033', '1006', 'C;D;B;', '0;0;0;0;1;0;', '16;逻辑关系;不会;不会;不会;逻辑关系;结构;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1034', '1006', 'D;C;B;', '0;1;0;0;1;0;', '不会;逻辑关系;结构;序言性;8;逻辑关系;不会;', null, '50', null);
INSERT INTO `sys_user_exam` VALUES ('test1035', '1006', 'B;C;A;', '1;1;0;0;0;0;', '16;逻辑关系;结构;不会;8;逻辑关系;不会;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1036', '1006', 'B;C;A;', '1;0;0;0;0;0;', '不会;不会;不会;序言性;8;不会;不会;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1037', '1006', 'A;B;A;', '1;0;0;0;1;1;', '16;不会;结构;不会;不会;逻辑关系;不会;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1038', '1006', 'B;B;B;', '0;1;1;0;0;1;', '不会;不会;结构;不会;不会;不会;结构;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1039', '1006', 'A;D;C;', '0;1;0;1;1;1;', '不会;逻辑关系;结构;不会;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1040', '1006', 'A;C;D;', '0;0;0;1;0;1;', '不会;逻辑关系;不会;序言性;8;逻辑关系;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1041', '1006', 'B;D;C;', '0;0;0;1;1;0;', '16;逻辑关系;不会;序言性;不会;逻辑关系;结构;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1042', '1006', 'C;C;B;', '0;0;1;1;1;0;', '不会;逻辑关系;结构;序言性;不会;逻辑关系;结构;', null, '60', null);
INSERT INTO `sys_user_exam` VALUES ('test1043', '1006', 'B;D;C;', '1;0;1;0;0;1;', '不会;逻辑关系;不会;序言性;不会;逻辑关系;不会;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1044', '1006', 'D;B;C;', '0;0;0;0;0;0;', '不会;逻辑关系;结构;序言性;8;不会;结构;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1045', '1006', 'B;C;B;', '0;1;0;1;0;1;', '16;不会;结构;序言性;8;逻辑关系;不会;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1046', '1006', 'C;C;A;', '0;1;0;1;1;0;', '不会;逻辑关系;结构;序言性;8;逻辑关系;不会;', null, '55', null);
INSERT INTO `sys_user_exam` VALUES ('test1047', '1006', 'B;D;A;', '0;0;1;0;0;0;', '16;不会;结构;序言性;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1048', '1006', 'B;C;C;', '1;0;0;1;0;1;', '16;不会;不会;不会;8;逻辑关系;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1049', '1006', 'B;D;C;', '1;1;0;0;0;0;', '不会;不会;不会;不会;不会;逻辑关系;不会;', null, '20', null);
INSERT INTO `sys_user_exam` VALUES ('test1050', '1006', 'D;A;D;', '1;0;1;1;1;1;', '16;不会;不会;不会;8;不会;结构;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1051', '1006', 'C;A;C;', '1;1;1;0;0;1;', '不会;逻辑关系;结构;序言性;8;不会;不会;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1052', '1006', 'A;D;B;', '1;0;1;1;1;0;', '16;不会;不会;不会;8;不会;结构;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1053', '1006', 'B;A;B;', '0;0;0;0;1;1;', '不会;不会;不会;序言性;8;逻辑关系;结构;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1054', '1006', 'C;B;C;', '0;0;1;1;0;1;', '不会;逻辑关系;不会;序言性;8;逻辑关系;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1055', '1006', 'D;A;C;', '1;1;0;0;1;1;', '16;逻辑关系;结构;序言性;不会;逻辑关系;不会;', null, '40', null);
INSERT INTO `sys_user_exam` VALUES ('test1056', '1006', 'C;A;D;', '0;1;0;0;0;1;', '不会;逻辑关系;结构;序言性;8;不会;不会;', null, '30', null);
INSERT INTO `sys_user_exam` VALUES ('test1057', '1006', 'B;A;C;', '1;0;1;0;1;1;', '不会;不会;结构;不会;不会;不会;结构;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1058', '1006', 'C;A;D;', '0;0;0;1;1;0;', '16;逻辑关系;结构;不会;8;不会;结构;', null, '45', null);
INSERT INTO `sys_user_exam` VALUES ('test1059', '1006', 'D;B;A;', '1;1;0;0;1;1;', '16;不会;结构;序言性;8;不会;不会;', null, '35', null);
INSERT INTO `sys_user_exam` VALUES ('test1060', '1006', 'D;C;C;', '1;0;0;0;0;1;', '不会;逻辑关系;不会;序言性;8;不会;不会;', null, '25', null);
INSERT INTO `sys_user_exam` VALUES ('test1002', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1002', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1003', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1003', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1004', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1004', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1005', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1005', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1006', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1006', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1007', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1007', '1002', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('test1010', '1003', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('admin', '1001', null, null, null, null, '0', '缺考');
INSERT INTO `sys_user_exam` VALUES ('admin', '1000', '', '1;', '', '', '3', null);
