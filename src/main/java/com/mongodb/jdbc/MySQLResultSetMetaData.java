package com.mongodb.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.BsonType;

public class MySQLResultSetMetaData extends MongoResultSetMetaData implements ResultSetMetaData {
    protected List<MySQLColumnInfo> columnInfo;
    protected Map<String, Integer> columnPositions;

    public MySQLResultSetMetaData(MySQLResultDoc metadataDoc) {
        columnInfo = metadataDoc.columns;

        columnPositions = new HashMap<>(columnInfo.size());
        int i = 0;
        for (MySQLColumnInfo c : columnInfo) {
            columnPositions.put(c.columnAlias, i++);
        }
    }

    public int getColumnPositionFromLabel(String label) {
        return columnPositions.get(label);
    }

    public boolean hasColumnWithLabel(String label) {
        return columnPositions.containsKey(label);
    }

    @Override
    public MongoColumnInfo getColumnInfo(int column) throws SQLException {
        checkBounds(column);
        return columnInfo.get(column - 1);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnInfo.size();
    }
}
