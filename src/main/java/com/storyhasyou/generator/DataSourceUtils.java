package com.storyhasyou.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangxi created by 2020/11/17
 */
public class DataSourceUtils {

    private static final String COLUMNS_SQL = "show full columns from %s";

    private static final Splitter SPLITTER_COMMENT = Splitter.on(StringPool.COMMA);
    private static final Splitter SPLITTER_ELEMENT = Splitter.on(StringPool.DOT);



    public static Map<Object, String> textMap(DataSourceConfig dataSource, EnumGenerateModel enumGenerateModel) {
        Map<Object, String> textMap = new HashMap<>();
        try (Connection connection = dataSource.getConn()) {
            String tableName = enumGenerateModel.getTableName();
            String tableSql = String.format(COLUMNS_SQL, tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(tableSql);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                String field = resultSet.getString("Field");
                if (StringUtils.equals(field, enumGenerateModel.getColumn())) {
                    // 0.未分类，1.技术类，2.文学类，3.政治类，4.科幻类
                    String comment = resultSet.getString("Comment");
                    List<String> elements = SPLITTER_COMMENT.trimResults().splitToList(comment);
                    for (String element : elements) {
                        List<String> data = SPLITTER_ELEMENT.trimResults().splitToList(element);
                        textMap.put(data.get(0), data.get(1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textMap;
    }

}
