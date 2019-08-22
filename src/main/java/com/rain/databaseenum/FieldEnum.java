package com.rain.databaseenum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FieldEnum {

    MYSQL_FIELD_VARCHAR("varchar"),

    MYSQL_FIELD_INT("int"),

    MYSQL_FIELD_BIGINT("bigInt"),

    MYSQL_FIELD_TEXT("text"),

    MYSQL_FIELD_DATE("date"),

    MYSQL_FIELD_DATETIME("datetime"),

    ORACLE_FIELD_NUMBER("NUMBER"),

    ORACLE_FIELD_NVARCHAR2("NVARCHAR2"),

    ORACLE_FIELD_VARCHAR2("VARCHAR2"),

    ORACLE_FIELD_VARCHAR("VARCHAR"),

    ORACLE_FIELD_CHAR("CHAR"),

    ORACLE_FIELD_NCHAR("NCHAR"),

    ORACLE_FIELD_DATE("DATE"),

    ORACLE_FIELD_FLOAT("FLOAT"),

    ORACLE_FIELD_LONG("LONG");

    private String type;


    FieldEnum(String varchar) {
    }
}
