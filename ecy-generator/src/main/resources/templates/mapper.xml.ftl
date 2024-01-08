<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <!--    获取分页数据-->
    <select id="get${entity}ByPage" resultType="${package.Entity}.${entity}">
        select *
        from  ${table.name}
        <where>
            <#list table.fields as field>
            <if test="null != ${entity?uncap_first}.${field.propertyName}<#if field.propertyType=="String"> and ''!=${entity?uncap_first}.${field.propertyName}</#if>">
                <#if field.propertyType=="String">
                and ${field.name} LIKE CONCAT('%',${'#{'}${entity?uncap_first}.${field.propertyName}  ${'}'},'%')
                    <#else >
                and ${field.name} = ${'#{'}${entity?uncap_first}.${field.propertyName}${'}'}
                </#if>
            </if>
            </#list>
        </where>
    </select>

</mapper>