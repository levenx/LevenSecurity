package com.leven.security.properties;

import lombok.Data;

/**
 *
 * @author 孙乐进
 * @date 2019/2/25
 */
@Data
public class PageProperties {

    private String indexPage = "/index.html";

    private String loginPage = "/common/login.html";

    private String loginPath = "/login";

    private String indexPath = "/index";


}
