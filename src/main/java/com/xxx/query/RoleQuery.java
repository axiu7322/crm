package com.xxx.query;

import com.xxx.base.BaseQuery;
import lombok.Data;


public class RoleQuery extends BaseQuery {
        private String roleName;

        public String getRoleName() {
                return roleName;
        }

        public void setRoleName(String roleName) {
                this.roleName = roleName;
        }
}
