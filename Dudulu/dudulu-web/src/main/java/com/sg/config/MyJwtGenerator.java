package com.sg.config;

import com.nimbusds.jwt.JWTClaimsSet;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: jiangwei
 * @Date: 2019-05-20
 * @Desc:
 */
public class MyJwtGenerator extends JwtGenerator {

    public MyJwtGenerator() {
        super();
    }

    public MyJwtGenerator(SignatureConfiguration signatureConfiguration, EncryptionConfiguration encryptionConfiguration) {
        super(signatureConfiguration, encryptionConfiguration);
    }

    public String generate(CommonProfile profile) {
        this.verifyProfile(profile);
        return this.internalGenerate(this.buildJwtClaimsSet(profile));
    }

    protected JWTClaimsSet buildJwtClaimsSet(CommonProfile profile) {
        JWTClaimsSet.Builder builder = (new JWTClaimsSet.Builder()).subject(profile.getTypedId());
        Map<String, Object> attributes = profile.getAttributes();
        Iterator var4 = attributes.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var4.next();
            builder.claim((String) entry.getKey(), entry.getValue());
        }
        builder.claim("exp", expiretime());
        builder.claim("$int_roles", profile.getRoles());
        builder.claim("$int_perms", profile.getPermissions());
        return builder.build();
    }

    private Date expiretime() {
        long l = new Date().getTime() + 3600l;
        return new Date(l);
    }

}
