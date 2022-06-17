package com.seezoon.maven.generator.constants;

/**
 * 生成模板
 *
 * @author hdf
 */
public enum CodeTemplate {

    SQL_MAPPER("sql.xml.tpl", "${baseSqlMapperPath}/mappings/${moduleName}", "${className}Mapper.xml"),

    PO("po.java.tpl", "src/main/java/${baseRepositoryPackage?replace('.','/')}/${moduleName}/repository/po",
            "${className}PO.java"),

    PO_CONDITION("po_condition.java.tpl",
            "src/main/java/${baseRepositoryPackage?replace('.','/')}/${moduleName}/repository/po",
            "${className}POCondition.java"),

    MAPPER("mapper.java.tpl", "src/main/java/${baseRepositoryPackage?replace('.','/')}/${moduleName}/repository/mapper",
            "${className}Mapper.java"),


    REPOSITORY("repository.java.tpl",
            "src/main/java/${baseRepositoryPackage?replace('.','/')}/${moduleName}/repository",
            "${className}Repository.java"),

    CONTROLLER("controller.java.tpl", "src/main/java/${baseControllerPackage?replace('.','/')}/${moduleName}/web",
            "${className}Controller.java"),

    ;

    // 模板名，相对路径
    private String tplName;
    // 生成路径
    private String path;
    // 文件名
    private String fileName;

    CodeTemplate(String tplName, String path, String fileName) {
        this.tplName = tplName;
        this.path = path;
        this.fileName = fileName;
    }


    public String tplName() {
        return tplName;
    }

    public String path() {
        return path;
    }

    public String fileName() {
        return fileName;
    }
}
