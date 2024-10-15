# tomato-engine
记录自己的日常工作

## 工作日志

[tomato-engine-idempotent](tomato-engine-idempotent)

- 幂等
- 页面做遮罩，数据库层面的唯一索引，先查询再添加
- 此注解只用于幂等，不用于锁

[tomato-engine-login](tomato-engine-login)

- 唯一性判断：手机号、邮箱
- 登录敏感词检测：分词、DFA算法
- 短信轰炸：滑块验证-->签名-->请求（数字签名）
- 限流

[tomato-engine-jackson](tomato-engine-jackson)

- jackson
- 数据脱敏

 [tomato-engine-easy-excel](tomato-engine-easy-excel) 

- easy-excel

[tomato-engine-rbac](tomato-engine-rbac)

- 权限
  - 页面权限、菜单权限、按钮权限等
  - 菜单也分一级菜单、二级菜单甚至三级菜单
  - 树形结构
- 角色
  - 权限分配给角色
  - 角色和权限是多对多的关系

- 用户组：
  - 用户组是一群用户的组合，而角色是用户和权限之间的桥梁。一个用户组可以是一个职级，可以是一个部门，可以是一起做事情的来自不同岗位的人。
  - 角色和用户组是多对多的关系
- 权限组
  - 解决权限和角色对应关系复杂的问题
  - 较少使用
- RBAC1：角色继承的RBAC模型
  - 角色继承：上层角色继承下层角色的所有权限，并且可以额外拥有其他权限。

- RBAC2：带约束的RBAC模型
  - 角色互斥：A 用户不能即有审核角色和提交角色，或者说不能审核自己提交的内容（代码控制）。
  - 基数约束：角色用户数量。
  - 先决条件约束：A 是 CEO 那么首先也必须是普通员工。
- 组织
  - 权限的分配可以根据组织架构来划分
  - 控制数据权限
  - 角色和组织是多对多的关系
- 职位
  - 一个组织下面会有很多职位
  - 用户跟职位的对应关系时一对一的关系
  - 角色和职位是多对多的关系

![rbac](./image/rbac.jpeg)

## 开源项目（工具）推荐

本项目参考代码如下：

> https://github.com/pig-mesh/idempotent-spring-boot-starter/tree/features/springboot3
