/*
 * Copyright 2022 varyuan <varyuan@qq.com>
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

package com.varyuan.awesome.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class Generator {
    static String controller(Bom bom) {
        String po = readString("E:\\gitwork\\controller.txt");
        po = po.replace("{packageName}", bom.getPackageName());

        po = po.replace("{poName}", bom.getPoName());
        po = po.replace("{poNameLow}", bom.getPoNameLow());
        po = po.replace("{tableName}", bom.getTableName());
        return po;
    }

    static String service(Bom bom) {
        String po = readString("E:\\gitwork\\service.txt");
        po = po.replace("{packageName}", bom.getPackageName());

        po = po.replace("{poName}", bom.getPoName());
        po = po.replace("{poNameLow}", bom.getPoNameLow());
        po = po.replace("{tableName}", bom.getTableName());
        return po;
    }

    static String dao(Bom bom) {
        String po = readString("E:\\gitwork\\dao.txt");
        po = po.replace("{packageName}", bom.getPackageName());

        po = po.replace("{poName}", bom.getPoName());
        po = po.replace("{poNameLow}", bom.getPoNameLow());
        po = po.replace("{tableName}", bom.getTableName());

        return po;
    }

    static String mapper(Bom bom) {
        String po = readString("E:\\gitwork\\mapper.xml");
        po = po.replace("{packageName}", bom.getPackageName());
        po = po.replace("{poName}", bom.getPoName());
        po = po.replace("{tableName}", bom.getTableName());

        List<Column> columnList = bom.getColumnList();

        // 主键不参与
        columnList = columnList.subList(1, columnList.size());

        //
        String format = "        <if test=\"#%s != null\">\n" +
                "           %s=#{%s} and\n" +
                "        </if>\n";
        StringBuilder selectWhere = new StringBuilder();
        for (Column column : columnList) {
            selectWhere.append(String.format(format, column.getField(), column.getColumn(), column.getField()));
        }
        po = po.replace("{select_where}", selectWhere);

        String columns = columnList.stream().map(Column::getColumn).collect(Collectors.joining(","));
        po = po.replace("{columns}", columns);
        String fields = columnList.stream().map(column -> "#{" + column.getField() + "}").collect(Collectors.joining(","));
        po = po.replace("{fields}", fields);

        //
        format = "        <if test=\"#%s!=null\">\n" +
                "            %s=#{%s},\n" +
                "        </if>\n";
        StringBuilder setColumns = new StringBuilder();
        for (Column column : columnList) {
            setColumns.append(String.format(format, column.getField(), column.getColumn(), column.getField()));
        }
        po = po.replace("{setColumns}", setColumns);
        return po;
    }

    static String po(Bom bom) {
        String po = readString("E:\\gitwork\\po.txt");
        po = po.replace("{packageName}", bom.getPackageName());
        po = po.replace("{poName}", bom.getPoName());
        List<Column> columnList = bom.getColumnList();
        StringBuilder columns = new StringBuilder();
        String format = "    private %s %s;\n";
        for (Column column : columnList) {
            columns.append(String.format(format, column.getType(), column.getField()));
        }
        po = po.replace("{columns}", columns);
        return po;
    }

    static String readString(String path) {
        StringBuilder result = new StringBuilder();
        File file = new File(path);
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(line -> {
                result.append(line).append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
