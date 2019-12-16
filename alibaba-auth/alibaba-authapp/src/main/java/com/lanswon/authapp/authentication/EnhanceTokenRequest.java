package com.lanswon.authapp.authentication;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TokenRequest 增强类型，将clientDetails里的属性自定义加入TokenRequest
 * @Author GU-YW
 * @Date 2019/12/14 9:58
 */
public class EnhanceTokenRequest extends TokenRequest {

    /**
     * Default constructor
     */
    protected EnhanceTokenRequest() {
        super();
    }

    /**
     * Full constructor. Sets this TokenRequest's requestParameters map to an unmodifiable version of the one provided.
     *
     * @param requestParameters
     * @param clientId
     * @param scope
     * @param grantType
     */
    public EnhanceTokenRequest(Map<String, String> requestParameters, String clientId, Collection<String> scope,
                        String grantType) {
        super(requestParameters,clientId,scope,grantType);
    }

    @Override
    public OAuth2Request createOAuth2Request(ClientDetails client) {
        Map<String, String> requestParameters = getRequestParameters();
        HashMap<String, String> modifiable = new HashMap<String, String>(requestParameters);
        // Remove password if present to prevent leaks
        modifiable.remove("password");
        modifiable.remove("client_secret");
        // Add grant type so it can be retrieved from OAuth2Request
        modifiable.put("grant_type", getGrantType());
        return new OAuth2Request(modifiable, client.getClientId(), client.getAuthorities(), true, this.getScope(),
                client.getResourceIds(), client.getRegisteredRedirectUri().iterator().next(), null, null);
    }
}
