package cn.com.cis.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class SqlScriptUtil {
    public static String getInsertSql(String[] columnNames, String tableName) {
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder parameterStringBuffer = new StringBuilder();
        if (columnNames.length < 1) {
            return "";
        }
        stringBuffer.append("INSERT INTO ").append(tableName).append(" (");

        for (int i = 0; i < columnNames.length; i++) {
            if (i != 0) {
                parameterStringBuffer.append(",").append("?");
                stringBuffer.append(",").append(columnNames[i]);
            } else {
                parameterStringBuffer.append("?");
                stringBuffer.append(columnNames[i]);
            }
        }
        stringBuffer.append(")  VALUES (").append(parameterStringBuffer.toString()).append(")");
        return stringBuffer.toString();
    }

    public static String preparePlaceHolders(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ) {
            builder.append("?");
            if (++i < length) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    public static List<String> getSqlParameter(String sql) {
        List<String> paramNameList = new ArrayList<String>();
        if (sql == null || "".equals(sql)) {
            throw new IllegalArgumentException("Sql 脚本不能为空！");
        }
        String[] tmp = sql.split("#");
        for (int i = 1; i < tmp.length; i++) {
            String s = tmp[i];
            if (s.startsWith("{") && s.indexOf("}") > 1)
                s = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
            else
                continue;
            paramNameList.add(s);
        }
        return paramNameList;
    }

    public static void getSqlParameter(Set<String> param, String sql) {
        if (param == null) {
            param = new HashSet<String>();
        }
        if (sql == null)
            throw new NullPointerException();
        if (sql.indexOf("#{") > 0) {
            String temp = sql.substring(sql.indexOf("#{") + 2);
            String s = temp.substring(0, temp.indexOf("}"));
            param.add(s);
            getSqlParameter(param, temp);
        }
    }

    public static void getSqlParameter(HashMap<String, Integer> param, String sql, int start) {
        if (start <= 0) {
            start = 1;
        }
        if (param == null) {
            param = new HashMap<String, Integer>();
        }
        if (sql == null)
            throw new NullPointerException();
        if (sql.indexOf("#{") > 0) {
            String temp = sql.substring(sql.indexOf("#{") + 2);
            String s = temp.substring(0, temp.indexOf("}"));
            param.put(s, start);
            start++;
            getSqlParameter(param, temp, start);
        }
    }

    public static String replaceSql(String sql) {
        Set<String> paramNames = new HashSet<String>();
        getSqlParameter(paramNames, sql);
        if (paramNames.size() > 0) {
            for (String s : paramNames) {
                sql = sql.replace("#{" + s + "}", " ? ");
            }
            return sql;
        } else {
            return sql;
        }
    }
}
