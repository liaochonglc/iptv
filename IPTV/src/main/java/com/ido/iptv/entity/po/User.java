package com.ido.iptv.entity.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.ido.iptv.common.validator.Add;
import com.ido.iptv.common.validator.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel
public class User {

    private Integer id;

    @ApiParam("用户名")
    @NotBlank(message = "用户名不能为空", groups = {
            Query.class,
            Add.class
    })
    private String username;

    @ApiParam("密码")
    @NotBlank(message = "密码不能为空", groups = {
            Query.class,
            Add.class
    })
    @Length(min = 6, max = 20, message = "密码长度应该在6-20位之间", groups = {
            Query.class,
            Add.class
    })
    private String password;

    private String roles;

    private Date createTime;

    private Date updateTime;
}