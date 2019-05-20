package com.sg.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;

/**
 * @Author: jiangwei
 * @Date: 2019-05-20
 * @Desc:
 */
public class MyJwtAuthenticator extends JwtAuthenticator {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<EncryptionConfiguration> encryptionConfigurations = new ArrayList();
    private List<SignatureConfiguration> signatureConfigurations = new ArrayList();


    public MyJwtAuthenticator() {
        super();
    }

    @Override
    protected void createJwtProfile(TokenCredentials credentials, JWT jwt) throws ParseException {
        JWTClaimsSet claimSet = jwt.getJWTClaimsSet();
        String subject = claimSet.getSubject();
        if (subject == null) {
            throw new TechnicalException("JWT must contain a subject ('sub' claim)");
        } else {
            Date expirationTime = claimSet.getExpirationTime();
            if (expirationTime != null) {
                Date now = new Date();
                if (expirationTime.before(now)) {
                    this.logger.error("The JWT is expired: no profile is built");

                    throw new ParseException("token expired at expirationTime , please relogin", 5001);
                }
            }

            Map<String, Object> attributes = new HashMap(claimSet.getClaims());
            attributes.remove("sub");
            List<String> roles = (List) attributes.get("$int_roles");
            attributes.remove("$int_roles");
            List<String> permissions = (List) attributes.get("$int_perms");
            attributes.remove("$int_perms");
            CommonProfile profile = ProfileHelper.restoreOrBuildProfile(this.getProfileDefinition(), subject, attributes, new Object[0]);
            if (roles != null) {
                profile.addRoles(roles);
            }

            if (permissions != null) {
                profile.addPermissions(permissions);
            }

            credentials.setUserProfile(profile);
        }
    }
}
