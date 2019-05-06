package com.sg;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@Data
@Entity
@Accessors(chain = true)
@Table(name="sg_user")
public class SgUser implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sg_id")
    private Integer sgId;


    @Column
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 6 ,max = 12,message = "用户名长度在6-12位之间")
    private String username;


    @Column
    @NotEmpty(message = "密码不能为空")
    private String password;


    @Column
    @NotNull(message = "电话不能为空")
    private String mobile;


    @Column
    private String gitId;


    @Column
    private String idcard;

    public SgUser() {
        super();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SgUser)) return false;
        final SgUser other = (SgUser) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sgId = this.getSgId();
        final Object other$sgId = other.getSgId();
        if (this$sgId == null ? other$sgId != null : !this$sgId.equals(other$sgId)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$mobile = this.getMobile();
        final Object other$mobile = other.getMobile();
        if (this$mobile == null ? other$mobile != null : !this$mobile.equals(other$mobile)) return false;
        final Object this$gitId = this.getGitId();
        final Object other$gitId = other.getGitId();
        if (this$gitId == null ? other$gitId != null : !this$gitId.equals(other$gitId)) return false;
        final Object this$idcard = this.getIdcard();
        final Object other$idcard = other.getIdcard();
        if (this$idcard == null ? other$idcard != null : !this$idcard.equals(other$idcard)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SgUser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sgId = this.getSgId();
        result = result * PRIME + ($sgId == null ? 43 : $sgId.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $mobile = this.getMobile();
        result = result * PRIME + ($mobile == null ? 43 : $mobile.hashCode());
        final Object $gitId = this.getGitId();
        result = result * PRIME + ($gitId == null ? 43 : $gitId.hashCode());
        final Object $idcard = this.getIdcard();
        result = result * PRIME + ($idcard == null ? 43 : $idcard.hashCode());
        return result;
    }

}