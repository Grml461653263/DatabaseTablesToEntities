package com.rain.dao;


import com.rain.uitls.CreatModel;
import com.rain.uitls.TableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.rain.uitls.JsonUtil.camelName;
import static com.rain.uitls.JsonUtil.underscoreName;

@Component
public class MysqlTableToModel {

    Logger logger = LoggerFactory.getLogger(MysqlTableToModel.class);

   /* @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleTemplate;*/

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;


    //表映射为实体
    public void TableToModel() {
        String str = "select 1";
        Map<String, Object> map = mysqlTemplate.queryForMap(str);
        if(!map.isEmpty()){
            logger.info("Database connection successful....");

            List<String> names = TableName.getTableName();
            names.forEach(name->{
                boolean bl = true;
                String sql = "desc "+ name;
                List<Map<String, Object>> list = mysqlTemplate.queryForList(sql);
                boolean dateFlag = true;
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();
                //首字母大写
                name = camelName(name);
                String t = name.substring(0, 1).toUpperCase() + name.substring(1);

                sb.append("package com.rain.model;\r\n");
                sb.append("public class " + t + "{\r\n");
                for (Map<String, Object> li : list) {
                    String field = String.valueOf(li.get("field"));
                    String type = String.valueOf(li.get("type"));
                    if (type.contains("int") || type.contains("bigInt")) {
                        type = "int";
                    } else if (type.contains("varchar") || type.contains("text")) {
                        type = "String";
                    } else if (type.contains("date") || type.contains("datetime")) {
                        //标识用以只增加一次
                        if (dateFlag) {
                            int a = sb.indexOf("\n");
                            sb.insert(a, "import java.util.Date;");
                            dateFlag = false;
                        }
                        type = "Date";
                    }
                    field = camelName(field);
                    sb.append("private " + type + " " + field + ";\r\n\n");
                    sb1.append("\r\npublic void set" + field.replace(name.substring(0, 1), field.substring(0, 1).toUpperCase()) +
                            "(" + type + " " + name + "){\r\n\n");
                    sb1.append("this." + field + "=" + field + ";\r\n}\r\n");
                    sb1.append("public " + type + " get" + field.replace(name.substring(0, 1), field.substring(0, 1).toUpperCase())
                            + "(){\r\n\n" + "return " + field + ";\r\n}");
                }
                sb1.append("\r\n}");
                String model=sb.toString()+sb1.toString();
                CreatModel.Creat(t,model,bl);
                bl=false;
            });


        }else {
            logger.error("Database connection failed....");
        }
    }
}
