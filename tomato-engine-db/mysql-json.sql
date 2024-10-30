use example;
drop table if exists json_example;
CREATE TABLE json_example
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    data JSON
);

INSERT INTO json_example (data)
VALUES ('{
  "name": "小明",
  "age": 30,
  "address": {
    "street": "北辰道",
    "city": "北京",
    "zip": "12345"
  },
  "phoneNumbers": [
    "1",
    "2"
  ],
  "isActive": true
}');

# JSON_EXTRACT() 提取 JSON 数据中的特定部分
SELECT JSON_EXTRACT(data, '$.name') AS extracted_name
FROM json_example;

# 提取 address 对象的 city 字段的值
SELECT JSON_EXTRACT(data, '$.address.city') AS city
FROM json_example;

# JSON_INSERT() 向 JSON 数据中插入新的部分，如果已存在则不会替换。
UPDATE json_example
SET data = JSON_INSERT(data, '$.phoneNumbers[0]', '3')
where id = 1;


# JSON_REPLACE() 替换 JSON 数据中的部分，如果 $.name 不存在则不会替换。
UPDATE json_example
SET data = JSON_REPLACE(data, '$.name', '小红')
where id = 1;

# JSON_REMOVE(): 从 JSON 数据中移除指定的部分
UPDATE json_example
SET data = JSON_REMOVE(data, '$.phoneNumbers[0]')
where id = 1;

# JSON_ARRAY() 和 JSON_OBJECT() 创建 JSON 数组和对象
# 创建一个新的 JSON 数组
SELECT JSON_ARRAY('a', 1, TRUE);
# 创建一个新的 JSON 对象
SELECT JSON_OBJECT('a', 1, 'b', TRUE);

# JSON_LENGTH() 获取 JSON 文档或数组的长度
SELECT JSON_LENGTH(data -> '$.phoneNumbers') AS phone_numbers_length
FROM json_example where id = 1;

# JSON_KEYS() 获取 JSON 对象的所有键
SELECT JSON_KEYS(data) AS object_keys FROM json_example;

# JSON_VALID() 验证 JSON 数据的有效性
SELECT JSON_VALID(data) AS is_valid_json FROM json_example;

# JSON_QUOTE() 和 JSON_UNQUOTE() 将字符串转换为 JSON 格式的字符串，以及反向操作。
SELECT JSON_QUOTE('Hello, World!') AS quoted_string;
SELECT JSON_UNQUOTE('"Hello, World!"') AS unquoted_string;

# JSON_CONTAINS() 检查 JSON 文档是否包含指定的值。
# 因为 JSON 中的字符串是被双引号包围的，所以我们在查询时也需要对搜索的字符串值加上双引号
SELECT JSON_CONTAINS(data->'$.interests', '"reading"') AS contains_reading FROM json_example;

# JSON_CONTAINS_PATH() 检查 JSON 文档是否包含指定的路径
SELECT JSON_CONTAINS_PATH(data, 'one', '$.friends[*].name') AS contains_path FROM json_example;

# JSON_ARRAY_APPEND() 向 JSON 数组追加元素。MySQL 8.0.17
UPDATE json_example
SET data = JSON_ARRAY_APPEND(data, '$.name', 'traveling');

UPDATE json_example
SET data = JSON_SET(data, '$.name[1]', 'traveling')

# TODO










