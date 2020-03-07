<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>
<#if know>
    <#assign
        loggedUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = loggedUser.getUsername()
        isAdmin = loggedUser.isAdmin()
        currentUserId = loggedUser.getId()
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
        currentUserId = -1
    >
</#if>