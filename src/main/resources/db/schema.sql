/*
 * Copyright 2021 varyuan <varyuan@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

CREATE TABLE `dict`  (
                         `id` int UNSIGNED AUTO_INCREMENT COMMENT '主键',
                         `kind` varchar(255) NOT NULL COMMENT '种类',
                         `code` varchar(255) NOT NULL COMMENT '键',
                         `val` varchar(255) DEFAULT NULL COMMENT '值',
                         `note` varchar(255) DEFAULT NULL COMMENT '注释',
                         `is_del` tinyint DEFAULT 0 COMMENT '逻辑删除标志',
                         `insert_time` datetime  DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
                         `update_time` datetime  DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COMMENT '字典表';

INSERT INTO `dict`(`kind`, `code`, `val`, `note`) VALUES ('召唤师技能', 'flash', '闪现', NULL);