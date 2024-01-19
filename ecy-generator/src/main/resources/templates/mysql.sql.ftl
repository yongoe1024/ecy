
        insert  into `sys_menu`(`id`,`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,
        `create_time`,`update_time`,`create_by`,`update_by`) values
    (null,${parentId},2,'','/${packageName}/${entity}','${moduleName}','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    -- 按钮父菜单ID
    set @parentId = @@identity;

    <#if get> insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/page*','','查','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>
    <#if add>   insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/add','','增','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>
    <#if update>insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/update','','改','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>
    <#if delete> insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/delete/*','','删','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>


    <#if upload>insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/upload','','导入','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>
    <#if export>insert  into `sys_menu`(`parent_id`,`type`,`url`,`component`,`name`,`icon`,`sort`,`keep_alive`,`is_show`,`enabled`,`create_time`,`update_time`,`create_by`,`update_by`) values
        (@parentId,3,'/${packageName}/${entity?lower_case}/export','','导出','',0,0,1,1,NOW(),NOW(),'${author}','${author}');
    </#if>

