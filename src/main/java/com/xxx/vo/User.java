package com.xxx.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

/**
* 
* @TableName t_user
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    /**
    * 主键
    */

    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("")
    @Length(max= 20,message="编码长度不能超过20")
    private String userName;
    /**
    * 
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("")
    @Length(max= 100,message="编码长度不能超过100")
    private String userPwd;
    /**
    * 
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("")
    @Length(max= 20,message="编码长度不能超过20")
    private String trueName;
    /**
    * 
    */
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("")
    @Length(max= 30,message="编码长度不能超过30")
    private String email;
    /**
    * 
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer isValid;
    /**
    * 
    */
    @ApiModelProperty("")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;
    /**
    * 
    */
    @ApiModelProperty("")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
    private Date updateDate;

    private String roleIds;
}
