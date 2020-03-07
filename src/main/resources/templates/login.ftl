<#import "components/index.ftl" as i>
<#import "components/login.ftl" as l>

<@i.page>
<#if message??>
    <div class="alert alert-${messageType} col-md-6 col-centered" role="alert">
        ${message}
    </div>
</#if>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
<div class="alert alert-danger col-md-6 col-centered" role="alert">
    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
</div>
</#if>
<@l.login "/login" false/>
</@i.page>

