<#import "components/index.ftl" as i>
<#import "components/login.ftl" as l>

<@i.page>
<#if message??>
    ${message}
</#if>
<@l.login "/registration" true/>
</@i.page>