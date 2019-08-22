package com.rain.dao;

import com.rain.config.PrefixConfig;
import com.rain.uitls.CreatModel;
import com.rain.uitls.TableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.rain.uitls.JsonUtil.*;

/**
 * @author Rain
 * Oracle table transformation entity Dao layer
 */
@Component
public class OracleTableToModel {


    Logger logger = LoggerFactory.getLogger(MysqlTableToModel.class);

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleTemplate;

    /**
     * table prefix length
     */
    @Resource
    private PrefixConfig prefixConfig;

    public void TableToModel() {
        String str = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual ";
        Map<String, Object> map = oracleTemplate.queryForMap(str);
        if(!map.isEmpty()){
            logger.info("Database connection successful....");

            List<String> names = TableName.getTableName();
            AtomicBoolean bl = new AtomicBoolean(true);
            names.forEach(name->{

                String  tableName =  name.toUpperCase();
                String sql = "select * from user_tab_columns where Table_Name=\'"+ tableName+"\'";
                logger.info(sql);
                List<Map<String, Object>> list = oracleTemplate.queryForList(sql);

                boolean dateFlag = true;
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();
                //首字母大写
                name = camelNamePrefix(name,prefixConfig.getPrefixLength());
                //String t = name.substring(0, 1).toUpperCase() + name.substring(1);

                sb.append("package com.rain.model;\r\n");
                sb.append("public class " + name + "{\r\n");
                for (Map<String, Object> li : list) {
                    String field = String.valueOf(li.get("COLUMN_NAME"));
                    String type = String.valueOf(li.get("DATA_TYPE"));
                    if (type.contains("NUMBER")) {
                        type = "int";
                    } else if (type.contains("NVARCHAR2") || type.contains("VARCHAR2")||type.contains("VARCHAR") || type.contains("CHAR")|| type.contains("NCHAR")) {
                        type = "String";
                    } else if (type.contains("DATE")) {
                        //标识用以只增加一次
                        if (dateFlag) {
                            int a = sb.indexOf("\n");
                            sb.insert(a, "import java.util.Date;");
                            dateFlag = false;
                        }
                        type = "Date";
                    }else if(type.contains("FLOAT")){
                        type = "double";
                    }else if(type.contains("LONG")){
                        type = "Long";
                    }
                    field = camelName(field);
                    sb.append("private " + type + " " + field + ";\r\n\n");
                    sb1.append("\r\npublic void set" +  field.substring(0, 1).toUpperCase()+field.substring(1) +
                            "(" + type + " " + field + "){\r\n\n");
                    sb1.append("this." + field + "=" + field + ";\r\n}\r\n");
                    sb1.append("public " + type + " get" + field.substring(0, 1).toUpperCase()+field.substring(1)
                            + "(){\r\n\n" + "return " + field + ";\r\n}");
                }
                sb1.append("\r\n}");
                String model=sb.toString()+sb1.toString();

                CreatModel.Creat(name,model, bl.get());
                bl.set(false);
            });
            logger.info("Database table transformation entity completion....");

        }else {
            logger.error("Database connection failed....");
        }
    }
}
